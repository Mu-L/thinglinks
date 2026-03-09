package com.mqttsnet.thinglinks.tcp.handler;

import cn.hutool.core.util.StrUtil;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.protocol.factory.ProtocolMessageAdapter;
import com.mqttsnet.basic.protocol.model.EncryptionDetailsDTO;
import com.mqttsnet.basic.protocol.model.ProtocolDataMessageDTO;
import com.mqttsnet.thinglinks.broker.MqttBrokerOpenAnyUserFacade;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaPullParam;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoOtaPullResponseParam;
import com.mqttsnet.thinglinks.tcp.handler.factory.TcpAbstractMessageHandler;
import com.mqttsnet.thinglinks.tcp.service.TcpEventOtaPullService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

/**
 * @program: thinglinks-cloud
 * @description: 处理OTA_PULL主题tcp.handler
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2024-03-13 15:00
 **/
@Slf4j
@Service
public class TcpOtaPullHandler extends TcpAbstractMessageHandler implements TcpTopicHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    @Autowired
    private TcpEventOtaPullService tcpEventOtaPullService;

    public TcpOtaPullHandler(LinkCacheDataHelper linkCacheDataHelper,
                             DeviceOpenAnyUserFacade deviceOpenAnyUserApi,
                             MqttBrokerOpenAnyUserFacade mqttBrokerOpenAnyTenantApi,
                             ProtocolMessageAdapter protocolMessageAdapter) {
        super(linkCacheDataHelper, deviceOpenAnyUserApi, mqttBrokerOpenAnyTenantApi, protocolMessageAdapter);
    }

    /**
     * Handles Tcp messages, decrypts them, and processes the command.
     *
     * @param topic The topic the message was published to.
     * @param qos   The Quality of Service level of the message.
     * @param body  The raw body of the MQTT message.
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
            TopoOtaPullParam topoOtaPullParam = JSON.parseObject(decryptedBody, TopoOtaPullParam.class);


            String resultDataBody = processingTopicMessage(topoOtaPullParam);

            // Handle result
            ProtocolDataMessageDTO handleResult = protocolMessageAdapter.buildResponse(protocolDataMessageDTO, resultDataBody, encryptionDetailsDTO);

            // Determine response topic based on request topic
            String responseTopic = "/topo/otaPullResponse";
            // Generate response topic string
            String responseTopicStr = generateResponseTopic(version, deviceId, responseTopic);

            // 序列化 handleResult 对象为 JSON 字符串
            String resultData = objectMapper.writeValueAsString(handleResult);

            // Push message to MQTT to notify device of successful/failed sub-device deletion
            sendMessage(responseTopicStr, qos, resultData, String.valueOf(ContextUtil.getTenantId()));
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

        Optional<TopoOtaPullResponseParam> topoOtaPullResponseParamOptional = tcpEventOtaPullService.handleMqttEventOtaPull((TopoOtaPullParam) messageParam);

        return topoOtaPullResponseParamOptional.map(JSON::toJSONString).orElse(StrUtil.EMPTY);
    }

}
