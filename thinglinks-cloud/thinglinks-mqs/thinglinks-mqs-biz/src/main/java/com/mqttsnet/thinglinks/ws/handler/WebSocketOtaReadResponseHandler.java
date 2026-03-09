package com.mqttsnet.thinglinks.ws.handler;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqttsnet.basic.protocol.factory.ProtocolMessageAdapter;
import com.mqttsnet.basic.protocol.model.EncryptionDetailsDTO;
import com.mqttsnet.basic.protocol.model.ProtocolDataMessageDTO;
import com.mqttsnet.thinglinks.broker.WebSocketBrokerOpenAnyUserFacade;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import com.mqttsnet.thinglinks.mqtt.service.MqttEventOtaReadResponseService;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaReadResponseParam;
import com.mqttsnet.thinglinks.ws.handler.factory.WebSocketAbstractMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: thinglinks-cloud
 * @description: 处理OTA_READ_RESPONSE主题mqtt.handler
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2024-03-13 15:00
 **/
@Slf4j
@Service
public class WebSocketOtaReadResponseHandler extends WebSocketAbstractMessageHandler implements WebSocketTopicHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private MqttEventOtaReadResponseService mqttEventOtaReadResponseService;

    public WebSocketOtaReadResponseHandler(LinkCacheDataHelper linkCacheDataHelper,
                                           DeviceOpenAnyUserFacade deviceOpenAnyUserApi,
                                           WebSocketBrokerOpenAnyUserFacade webSocketBrokerOpenAnyUserFacade,
                                           ProtocolMessageAdapter protocolMessageAdapter) {
        super(linkCacheDataHelper, deviceOpenAnyUserApi, webSocketBrokerOpenAnyUserFacade, protocolMessageAdapter);
    }

    /**
     * Handles WebSocket messages, decrypts them, and processes the command.
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
            TopoOtaReadResponseParam topoOtaReadResponseParam = JSON.parseObject(decryptedBody, TopoOtaReadResponseParam.class);


            processingTopicMessage(topoOtaReadResponseParam);
        } catch (Exception e) {
            log.error("Failed to decrypt the message", e);
        }
    }


    /**
     * Processes the message and returns the response body.
     *
     * @param messageParam The message body.
     * @return The response body.
     * @throws Exception If an error occurs while processing the message.
     */
    @Override
    protected String processingTopicMessage(Object messageParam) throws Exception {
        mqttEventOtaReadResponseService.handleMqttEventOtaReadResponse((TopoOtaReadResponseParam) messageParam);
        return null;
    }

}
