package com.mqttsnet.thinglinks.ws.handler;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.protocol.factory.ProtocolMessageAdapter;
import com.mqttsnet.basic.protocol.model.EncryptionDetailsDTO;
import com.mqttsnet.basic.protocol.model.ProtocolDataMessageDTO;
import com.mqttsnet.thinglinks.broker.WebSocketBrokerOpenAnyUserFacade;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import com.mqttsnet.thinglinks.protocol.vo.param.TopoUpdateSubDeviceStatusParam;
import com.mqttsnet.thinglinks.protocol.vo.result.TopoDeviceOperationResultVO;
import com.mqttsnet.thinglinks.ws.handler.factory.WebSocketAbstractMessageHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @program: thinglinks-cloud
 * @description: 处理UPDATE_SUB_DEVICE主题
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-05 23:00
 **/
@Slf4j
@Service
public class WebSocketUpdateSubDeviceHandler extends WebSocketAbstractMessageHandler implements WebSocketTopicHandler {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public WebSocketUpdateSubDeviceHandler(LinkCacheDataHelper linkCacheDataHelper,
                                           DeviceOpenAnyUserFacade deviceOpenAnyUserApi,
                                           WebSocketBrokerOpenAnyUserFacade webSocketBrokerOpenAnyUserFacade,
                                           ProtocolMessageAdapter protocolMessageAdapter) {
        super(linkCacheDataHelper, deviceOpenAnyUserApi, webSocketBrokerOpenAnyUserFacade, protocolMessageAdapter);
    }

    /**
     * @param topic the WebSocket topic the message was received on.
     * @param qos   the quality of service level of the message.
     * @param body  the payload of the message.
     */
    @Override
    public void handle(String topic, String qos, String body) {
        if (!protocolMessageAdapter.validateProtocolData(body)) {
            log.warn("The protocol format is incorrect");
            return;
        }

        // Extract variables from the topic
        Map<String, String> stringStringMap = protocolMessageAdapter.extractVariables(topic);
        String version = stringStringMap.get("version");
        String deviceId = stringStringMap.get("deviceId");

        DeviceCacheVO deviceCacheVO = getDeviceCacheVO(deviceId);
        if (deviceCacheVO == null) {
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
            String dataBody = protocolMessageAdapter.decryptMessage(body, encryptionDetailsDTO);

            // Parse body
            TopoUpdateSubDeviceStatusParam topoUpdateSubDeviceStatusParam = JSON.parseObject(dataBody, TopoUpdateSubDeviceStatusParam.class);
            topoUpdateSubDeviceStatusParam.setGatewayIdentification(deviceId);
            String resultDataBody = processingTopicMessage(topoUpdateSubDeviceStatusParam);

            // Handle result
            ProtocolDataMessageDTO handleResult = protocolMessageAdapter.buildResponse(protocolDataMessageDTO, resultDataBody, encryptionDetailsDTO);

            // Determine response topic based on request topic
            String responseTopic = "/topo/updateResponse";
            // Generate response topic string
            String responseTopicStr = generateResponseTopic(version, deviceId, responseTopic);

            // 序列化 handleResult 对象为 JSON 字符串
            String resultData = objectMapper.writeValueAsString(handleResult);
            // Push message to WebSocket to notify device of successful/failed sub-device update
            sendMessage(responseTopicStr, deviceCacheVO.getClientId(), resultData, String.valueOf(ContextUtil.getTenantId()));
        } catch (Exception e) {
            log.error("Failed to decrypt the message", e);
        }
    }

    /**
     * Process /topo/update Topic for gateway device to update sub-device
     *
     * @param topoUpdateSubDeviceParam update device data
     * @return Processing result json
     */
    @Override
    protected String processingTopicMessage(Object topoUpdateSubDeviceParam) throws Exception {
        R<TopoDeviceOperationResultVO> topoDeviceOperationResultVOR =
                deviceOpenAnyUserApi.updateSubDeviceConnectStatusByMqtt((TopoUpdateSubDeviceStatusParam) topoUpdateSubDeviceParam);
        log.info("processingTopoUpdateTopic Processing result:{}", JSON.toJSONString(topoDeviceOperationResultVOR));
        return JSON.toJSONString(topoDeviceOperationResultVOR.getData());
    }
}
