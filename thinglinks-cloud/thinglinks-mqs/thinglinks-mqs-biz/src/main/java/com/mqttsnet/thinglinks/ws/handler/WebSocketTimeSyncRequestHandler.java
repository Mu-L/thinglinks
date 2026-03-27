package com.mqttsnet.thinglinks.ws.handler;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.basic.protocol.factory.ProtocolMessageAdapter;
import com.mqttsnet.basic.protocol.model.EncryptionDetailsDTO;
import com.mqttsnet.basic.protocol.model.ProtocolDataMessageDTO;
import com.mqttsnet.thinglinks.broker.WebSocketBrokerOpenAnyUserFacade;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import com.mqttsnet.thinglinks.protocol.vo.param.TimeSyncRequestParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TimeSyncResponseParam;
import com.mqttsnet.thinglinks.ws.handler.factory.WebSocketAbstractMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.TimeZone;

/**
 * @program: thinglinks-cloud
 * @description: 处理 syncTime 主题
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2024-07-24 18:00
 **/
@Slf4j
@Service
public class WebSocketTimeSyncRequestHandler extends WebSocketAbstractMessageHandler implements WebSocketTopicHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketTimeSyncRequestHandler(LinkCacheDataHelper linkCacheDataHelper,
                                           DeviceOpenAnyUserFacade deviceOpenAnyUserApi,
                                           WebSocketBrokerOpenAnyUserFacade webSocketBrokerOpenAnyUserFacade,
                                           ProtocolMessageAdapter protocolMessageAdapter) {
        super(linkCacheDataHelper, deviceOpenAnyUserApi, webSocketBrokerOpenAnyUserFacade, protocolMessageAdapter);
    }

    /**
     * Synchronizing server time
     *
     * @param topic The topic the message was published to.
     * @param qos   The Quality of Service level of the message.
     * @param body  The raw body of the WebSocket message.
     */
    @Override
    public void handle(String topic, String qos, String body) {
        if (!protocolMessageAdapter.validateProtocolData(body)) {
            log.warn("The protocol format is incorrect");
            return;
        }

        Map<String, String> variables = protocolMessageAdapter.extractVariables(topic);
        String version = variables.get(CommonConstants.VERSION);
        String deviceId = variables.get(CommonConstants.DEVICE_ID);

        DeviceCacheVO deviceCacheVO = getDeviceCacheVO(deviceId);
        if (deviceCacheVO == null) {
            log.warn("Device with ID {} not found.", deviceId);
            return;
        }

        try {
            ProtocolDataMessageDTO protocolDataMessageDTO = protocolMessageAdapter.parseProtocolDataMessage(body);
            // 构造 EncryptionDetails 对象
            EncryptionDetailsDTO encryptionDetailsDTO = EncryptionDetailsDTO.builder()
                    .signKey(deviceCacheVO.getSignKey())
                    .encryptKey(deviceCacheVO.getEncryptKey())
                    .encryptVector(deviceCacheVO.getEncryptVector())
                    .cipherFlag(deviceCacheVO.getEncryptMethod())
                    .build();
            String decryptedBody = protocolMessageAdapter.decryptMessage(body, encryptionDetailsDTO);

            // Parse body
            TimeSyncRequestParam timeSyncRequestParam = JSON.parseObject(decryptedBody, TimeSyncRequestParam.class);

            log.info("timeSyncRequestParam:{} ", JsonUtil.toJson(timeSyncRequestParam));

            String resultDataBody = processingTopicMessage(timeSyncRequestParam);

            // Handle result
            ProtocolDataMessageDTO handleResult = protocolMessageAdapter.buildResponse(protocolDataMessageDTO, resultDataBody, encryptionDetailsDTO);

            // Determine response topic based on request topic
            String responseTopic = "/topo/timeSyncResponse";
            // Generate response topic string
            String responseTopicStr = generateResponseTopic(version, deviceId, responseTopic);

            // 序列化 handleResult 对象为 JSON 字符串
            String resultData = objectMapper.writeValueAsString(handleResult);

            // Push message to WebSocket to notify device of successful/failed sub-device deletion
            sendMessage(responseTopicStr, deviceCacheVO.getClientId(), resultData, String.valueOf(ContextUtil.getTenantId()));
        } catch (Exception e) {
            log.error("Failed to decrypt the message", e);
        }
    }


    @Override
    protected String processingTopicMessage(Object messageParam) throws Exception {
        // 获取处理开始时间戳（服务器端），以毫秒为单位
        long serverTimeMillis = System.currentTimeMillis();

        // 获取当前时间和时区
        String serverTime = DateUtil.format(DateUtil.date(serverTimeMillis), DatePattern.UTC_MS_PATTERN);
        TimeZone timeZone = TimeZone.getDefault();
        String currentZone = timeZone.getID();

        TimeSyncResponseParam timeSyncResponseParam = TimeSyncResponseParam.builder()
                .serverTime(serverTime)
                .serverTimeMillis(serverTimeMillis)
                .timeZone(currentZone)
                .build();

        return JsonUtil.toJson(timeSyncResponseParam);
    }

}