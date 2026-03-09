package com.mqttsnet.thinglinks.mqs.ws.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import cn.hutool.core.util.StrUtil;
import com.mqttsnet.basic.context.ContextConstants;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.mqttsnet.thinglinks.common.mq.KafkaConsumerTopicConstant;
import com.mqttsnet.thinglinks.consumer.kafka.KafkaProducerService;
import com.mqttsnet.thinglinks.device.enumeration.DeviceAuthModeEnum;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAuthenticationQuery;
import com.mqttsnet.thinglinks.entity.protocol.base.ClientConnectedEvent;
import com.mqttsnet.thinglinks.entity.protocol.base.ClientDisconnectEvent;
import com.mqttsnet.thinglinks.entity.protocol.base.DistErrorEvent;
import com.mqttsnet.thinglinks.entity.protocol.base.DistedEvent;
import com.mqttsnet.thinglinks.enumeration.WsEventEnum;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyTenantFacade;
import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAuthenticationResultVO;
import com.mqttsnet.thinglinks.ws.WebSocketObserver;
import com.mqttsnet.thinglinks.ws.WebSocketSubject;
import jakarta.websocket.CloseReason;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import jakarta.websocket.server.ServerEndpointConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

/**
 * 用于处理设备开放 WebSocket连接的各种事件。
 * 请求中 需要携带TenantId，但 不需要携带Token(不需要登录) 和 不需要验证uri权限
 * 设备鉴权
 * 设备消息上报
 */
@ServerEndpoint(value = "/anyUser/deviceOpenSocket/accessProtocol/socket/{version}/devices/{clientId}/datas", configurator = WebSocketDeviceOpenAccessProtocolEndpoint.Configurator.class)
@Component
@Slf4j
public class WebSocketDeviceOpenAccessProtocolEndpoint {

    /**
     * 连接成功后进行认证
     *
     * @param session  WebSocket 会话
     * @param version  版本
     * @param clientId 客户端ID
     */
    @OnOpen
    public void onOpen(Session session, @PathParam(CommonConstants.VERSION) String version, @PathParam(CommonConstants.CLIENT_ID) String clientId) throws IOException {
        log.info("WebSocket【DeviceOpenAccessProtocolEndpoint】 连接成功, Session ID: {}, version: {}, clientId: {}", session.getId(), version, clientId);

        // 从 UserProperties 中获取用户名和密码
        Map<String, Object> userProperties = session.getUserProperties();
        String username = (String) userProperties.get("username");
        String password = (String) userProperties.get("password");

        // 异步认证
        authenticate(clientId, username, password).thenAccept(authSuccess -> {
            if (!authSuccess) {
                log.info("认证失败，关闭连接。clientId: {}, username: {},password: {}", clientId, username, password);
                try {
                    session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "认证失败"));
                } catch (IOException e) {
                    log.error("关闭 WebSocket 连接时出错: {}", e.getMessage());
                }
            } else {
                log.info("认证成功，clientId: {}, username: {},password: {}", clientId, username, password);

                // 添加观察者
                WebSocketObserver observer = new WebSocketObserver(session);
                WebSocketSubject subject = WebSocketSubject.Holder.getSubject(clientId);
                subject.addObserver(observer);

                String tenantId = Optional.ofNullable(clientId)
                        .filter(StrUtil::isNotBlank)
                        .map(id -> StrUtil.subAfter(id, ContextConstants.SPECIAL_CHARACTER, true))
                        .orElse(ContextConstants.BUILT_IN_TENANT_ID_STR);

                Map<String, Object> userProps = session.getUserProperties();
                userProps.put(CommonConstants.CLIENT_ID, clientId);
                userProps.put(CommonConstants.TENANT_ID, tenantId);

                ClientConnectedEvent event = ClientConnectedEvent.builder()
                        .tenantId(tenantId)
                        .clientId(clientId)
                        .event(WsEventEnum.CONNECT.getName())
                        .success("success")
                        .version(session.getProtocolVersion())
                        .userId(username)
                        .address(session.getRequestURI().getHost())
                        .channelId(session.getId())
                        .build();

                try {
                    //发送连接成功设备事件消息
                    KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
                    kafkaProducerService.thingLinksProKafkaTemplateSendMsg(KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_CLIENT_CONNECTED_TOPIC, JsonUtil.toJson(event));
                } catch (Exception e) {
                    log.error("发送连接成功设备事件消息时出错: {}", e.getMessage());
                }
            }
        }).exceptionally(ex -> {
            log.error("clientId: {} 认证过程中出错: {}", clientId, ex.getMessage());
            try {
                session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "认证出错"));
            } catch (IOException e) {
                log.error("关闭 WebSocket 连接时出错: {}", e.getMessage());
            }
            return null;
        });
    }

    /**
     * 异步认证方法，通过 Feign 调用外部 API
     */
    private CompletableFuture<Boolean> authenticate(String clientId, String username, String password) {
        return CompletableFuture.supplyAsync(() -> {
            log.info("开始认证客户端 - clientId: {}, username: {}, password: {}", clientId, username, password);
            DeviceAuthenticationQuery deviceAuthenticationQuery = new DeviceAuthenticationQuery();
            deviceAuthenticationQuery.setClientIdentifier(clientId);
            deviceAuthenticationQuery.setUsername(username);
            deviceAuthenticationQuery.setPassword(password);
            deviceAuthenticationQuery.setAuthMode(DeviceAuthModeEnum.ACCOUNT_MODE.getValue());
            deviceAuthenticationQuery.setProtocolType(ProtocolTypeEnum.WEBSOCKET.getValue());

            log.info("认证请求参数: {}", JsonUtil.toJson(deviceAuthenticationQuery));

            // 通过 Feign 调用外部 API
            DeviceOpenAnyTenantFacade deviceOpenAnyTenantApi = SpringUtils.getBean(DeviceOpenAnyTenantFacade.class);
            ResponseEntity<DeviceAuthenticationResultVO> responseEntity = deviceOpenAnyTenantApi.clientConnectionAuthentication(deviceAuthenticationQuery);

            // 输出完整的响应内容
            log.info("认证API响应: 状态码 = {}, 响应内容 = {}", responseEntity.getStatusCode(), responseEntity.getBody());

            // 处理响应
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                DeviceAuthenticationResultVO result = responseEntity.getBody();
                if (result != null && result.getCertificationResult()) {
                    log.info("认证成功，clientId: {}", clientId);
                    return true;
                } else {
                    log.info("认证失败，响应内容未通过认证");
                }
            } else {
                log.error("认证失败，clientId: {},状态码: {}", clientId, responseEntity.getStatusCode());
            }

            return false;
        });
    }

    /**
     * 连接关闭
     *
     * @param session  会话
     * @param version  版本
     * @param clientId 客户端ID
     */
    @OnClose
    public void onClose(Session session, @PathParam("version") String version, @PathParam(CommonConstants.CLIENT_ID) String clientId) {
        log.info("WebSocket【DeviceOpenAccessProtocolEndpoint】 连接关闭, Session ID: {},version: {},clientId: {}", session.getId(), version, clientId);

        // get subject
        WebSocketSubject subject = WebSocketSubject.Holder.getSubject(clientId);
        // get observer
        WebSocketObserver observer = new WebSocketObserver(session);

        // close session and close Web Socket connection
        try {
            Map<String, Object> userProps = observer.getSession().getUserProperties();

            ClientDisconnectEvent event = ClientDisconnectEvent.builder()
                    .tenantId((String) userProps.getOrDefault("tenantId", ContextConstants.BUILT_IN_TENANT_ID_STR))
                    .clientId(clientId)
                    .event(WsEventEnum.DISCONNECTED.getName())
                    .success("success")
                    .channelId(session.getId())
                    .version(version)
                    .address(session.getRequestURI() != null ? session.getRequestURI().getHost() : "")
                    .build();


            KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
            kafkaProducerService.thingLinksProKafkaTemplateSendMsg(KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_CLIENT_DISCONNECTED_TOPIC, JsonUtil.toJson(event));
            if (session.isOpen()) {
                session.close();
                // delete observer from subject
                subject.deleteObserver(observer);
            }
        } catch (IOException e) {
            throw new RuntimeException("close web socket session error.", e);
        }
    }

    /**
     * 接收到消息
     *
     * @param session  会话
     * @param text     消息内容
     * @param version  版本
     * @param clientId 客户端ID
     * @return 消息内容
     */
    @OnMessage
    public String onMsg(Session session, String text, @PathParam(CommonConstants.VERSION) String version, @PathParam(CommonConstants.CLIENT_ID) String clientId) {
        log.info("WebSocket【DeviceOpenAccessProtocolEndpoint】onMsg, Session ID: {}, Text: {}", session.getId(), text);
        if (StrUtil.isEmpty(text)) {
            return "";
        }
        try {
            // get subject
            WebSocketSubject subject = WebSocketSubject.Holder.getSubject(clientId);
            // get observer
            WebSocketObserver observer = new WebSocketObserver(session);
            Map<String, Object> userProps = observer.getSession().getUserProperties();

            DistedEvent event = DistedEvent.builder()
                    .tenantId((String) userProps.getOrDefault(CommonConstants.TENANT_ID, ContextConstants.BUILT_IN_TENANT_ID_STR))
                    .clientId((String) userProps.getOrDefault(CommonConstants.CLIENT_ID, ""))
                    .event(WsEventEnum.RECEIVE.getName())
                    .success("success")
                    .messageId(Long.valueOf(SnowflakeIdUtil.nextId()))
                    .payload(text)
                    .body(text)
                    .build();

            KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
            kafkaProducerService.thingLinksProKafkaTemplateSendMsg(KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_DISTRIBUTION_COMPLETED_TOPIC, JsonUtil.toJson(event));
        } catch (Exception e) {
            log.error("WebSocket【DeviceOpenAccessProtocolEndpoint】onMsg 异常, Session ID: {}", session.getId(), e);
        }
        return "server 收到消息：" + text;
    }


    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        log.info("WebSocket【DeviceOpenAccessProtocolEndpoint】连接error, Session ID: {}", session.getId());
        // get observer
        WebSocketObserver observer = new WebSocketObserver(session);

        try {
            Map<String, Object> userProps = observer.getSession().getUserProperties();

            DistErrorEvent event = DistErrorEvent.builder()
                    .tenantId((String) userProps.getOrDefault("tenantId", ContextConstants.BUILT_IN_TENANT_ID_STR))
                    .clientId((String) userProps.getOrDefault("clientId", ""))
                    .event(WsEventEnum.ERROR.getName())
                    .success("success")
                    .errorMessage(error.getMessage())
                    .build();

            KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
            kafkaProducerService.thingLinksProKafkaTemplateSendMsg(KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_DISTRIBUTION_ERROR_TOPIC, JsonUtil.toJson(event));
        } catch (Exception e) {
            log.error("WebSocket【DeviceOpenAccessProtocolEndpoint】连接error, Session ID: {}", session.getId(), e);
        }
    }

    /**
     * 自定义 Configurator 类，用于在握手阶段获取查询参数中的用户名和密码
     */
    public static class Configurator extends ServerEndpointConfig.Configurator {
        @Override
        public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
            // 获取查询参数
            Map<String, List<String>> params = request.getParameterMap();
            String username = params.getOrDefault("username", List.of("")).get(0);
            String password = params.getOrDefault("password", List.of("")).get(0);

            // 将用户名和密码放入 UserProperties 中，供 OnOpen 使用
            sec.getUserProperties().put("username", username);
            sec.getUserProperties().put("password", password);
        }
    }

}
