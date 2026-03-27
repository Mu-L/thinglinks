package com.mqttsnet.thinglinks.ws.controller;

import cn.hutool.core.util.StrUtil;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.thinglinks.device.service.DeviceShadowService;
import com.mqttsnet.thinglinks.device.vo.query.DeviceShadowPageQuery;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.ws.WebSocketObserver;
import com.mqttsnet.thinglinks.ws.WebSocketSubject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * 用于处理获取设备影子 WebSocket事件。
 */
@ServerEndpoint(value = "/anyTenant/deviceOpenSocket/queryDeviceShadow/{tenantId}/{deviceIdentification}")
@Component
@Slf4j
public class QueryDeviceShadowEndpoint {

    /**
     * 连接成功后进行认证
     *
     * @param session WebSocket 会话
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("tenantId") String tenantId, @PathParam("deviceIdentification") String deviceIdentification) throws IOException {
        log.info("WebSocket【QueryDeviceShadowEndpoint】 连接成功, Session ID: {}, tenantId: {}, deviceIdentification: {}", session.getId(), tenantId, deviceIdentification);

        // 添加观察者
        WebSocketObserver observer = new WebSocketObserver(session);
        WebSocketSubject subject = WebSocketSubject.Holder.getSubject(session.getId());
        subject.addObserver(observer);
    }


    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session, @PathParam("tenantId") String tenantId, @PathParam("deviceIdentification") String deviceIdentification) {
        log.info("WebSocket【QueryDeviceShadowEndpoint】 连接关闭, Session ID: {}, tenantId: {}, deviceIdentification: {}", session.getId(), tenantId, deviceIdentification);

        // get subject
        WebSocketSubject subject = WebSocketSubject.Holder.getSubject(session.getId());

        // get observer
        WebSocketObserver observer = new WebSocketObserver(session);
        // delete observer from subject
        subject.deleteObserver(observer);

        // close session and close Web Socket connection
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("close web socket session error.", e);
        }
    }

    /**
     * 接收到消息
     *
     * @param text                 消息(ping 或者 serviceCode)
     * @param tenantId             租户ID
     * @param deviceIdentification 设备标识
     * @return {@link }
     */
    @OnMessage
    public String onMsg(String text, @PathParam("tenantId") String tenantId, @PathParam("deviceIdentification") String deviceIdentification) {
        if (StrUtil.isEmpty(text) || "ping".equals(text)) {
            return StrPool.EMPTY;
        }


        log.info("tenantId={}, deviceIdentification={}, text={}", tenantId, deviceIdentification, text);
        ContextUtil.setTenantId(tenantId);
        if (ContextUtil.isEmptyBasePool() || ContextUtil.isEmptyTenantId()) {
            return StrPool.EMPTY;
        }
        try {
            //查询设备影子
            ProductResultVO productResultVO = SpringUtils.getBean(DeviceShadowService.class).queryDeviceShadow(DeviceShadowPageQuery.builder()
                    .build()
                    .setDeviceIdentification(deviceIdentification)
                    .setServiceCode(text)
            );
            return JsonUtil.toJson(productResultVO);
        } catch (Exception e) {
            log.error("Failed to get device shadow", e);
        }
        return StrPool.EMPTY;
    }


    @OnError
    public void onError(Session session, Throwable error) {
        log.info("WebSocket【QueryDeviceShadowEndpoint】连接error, Session ID: {}", session.getId());
        throw new RuntimeException("web socket error.", error);
    }


}
