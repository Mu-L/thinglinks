package com.mqttsnet.thinglinks.tcp.handler;

import com.mqttsnet.basic.protocol.factory.ProtocolMessageAdapter;
import com.mqttsnet.basic.protocol.model.EncryptionDetailsDTO;
import com.mqttsnet.thinglinks.broker.MqttBrokerOpenAnyUserFacade;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import com.mqttsnet.thinglinks.tcp.handler.factory.TcpAbstractMessageHandler;
import com.mqttsnet.thinglinks.tcp.service.TcpEventOtaCommandResponseService;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: thinglinks-cloud
 * @description: 处理OTA_COMMAND_RESPONSE主题tcp.handler
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2024-01-15 23:00
 **/
@Slf4j
@Service
public class TcpOtaCommandResponseHandler extends TcpAbstractMessageHandler implements TcpTopicHandler {
    @Autowired
    private TcpEventOtaCommandResponseService tcpEventOtaCommandResponseService;

    public TcpOtaCommandResponseHandler(LinkCacheDataHelper linkCacheDataHelper,
                                        DeviceOpenAnyUserFacade deviceOpenAnyUserApi,
                                        MqttBrokerOpenAnyUserFacade mqttBrokerOpenAnyTenantApi,
                                        ProtocolMessageAdapter protocolMessageAdapter) {
        super(linkCacheDataHelper, deviceOpenAnyUserApi, mqttBrokerOpenAnyTenantApi, protocolMessageAdapter);
    }

    /**
     * Handles TCP messages, decrypts them, and processes the command.
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
        String deviceId = variables.get(CommonConstants.DEVICE_ID);

        DeviceCacheVO deviceCacheVO = getDeviceCacheVO(deviceId);
        if (deviceCacheVO == null) {
            log.warn("Device with ID {} not found.", deviceId);
            return;
        }

        try {
            EncryptionDetailsDTO encryptionDetailsDTO = EncryptionDetailsDTO.builder()
                    .signKey(deviceCacheVO.getSignKey())
                    .encryptKey(deviceCacheVO.getEncryptKey())
                    .encryptVector(deviceCacheVO.getEncryptVector())
                    .cipherFlag(deviceCacheVO.getEncryptMethod())
                    .build();
            String decryptedBody = protocolMessageAdapter.decryptMessage(body, encryptionDetailsDTO);
            tcpEventOtaCommandResponseService.saveMqttEventOtaCommandResponse(deviceCacheVO, decryptedBody);
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

        return null;
    }

}
