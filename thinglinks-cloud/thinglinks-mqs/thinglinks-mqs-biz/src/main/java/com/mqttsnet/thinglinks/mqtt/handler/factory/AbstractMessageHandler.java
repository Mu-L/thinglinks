package com.mqttsnet.thinglinks.mqtt.handler.factory;

import com.mqttsnet.basic.protocol.factory.ProtocolMessageAdapter;
import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.thinglinks.broker.MqttBrokerOpenAnyUserFacade;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.cache.vo.product.ProductModelCacheVO;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.tds.vo.result.SuperTableDescribeVO;
import com.mqttsnet.thinglinks.vo.query.PublishMessageRequestVO;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * @program: thinglinks-cloud
 * @description: 通用逻辑处理器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-30 15:07
 **/
@Slf4j
public abstract class AbstractMessageHandler {

    protected final LinkCacheDataHelper linkCacheDataHelper;
    protected final DeviceOpenAnyUserFacade deviceOpenAnyUserApi;
    protected final MqttBrokerOpenAnyUserFacade mqttBrokerOpenAnyTenantApi;
    protected final ProtocolMessageAdapter protocolMessageAdapter;

    public AbstractMessageHandler(LinkCacheDataHelper linkCacheDataHelper,
                                  DeviceOpenAnyUserFacade deviceOpenAnyUserApi,
                                  MqttBrokerOpenAnyUserFacade mqttBrokerOpenAnyTenantApi,
                                  ProtocolMessageAdapter protocolMessageAdapter) {
        this.linkCacheDataHelper = linkCacheDataHelper;
        this.deviceOpenAnyUserApi = deviceOpenAnyUserApi;
        this.mqttBrokerOpenAnyTenantApi = mqttBrokerOpenAnyTenantApi;
        this.protocolMessageAdapter = protocolMessageAdapter;
    }

    protected DeviceCacheVO getDeviceCacheVO(String deviceIdentification) {
        return linkCacheDataHelper.getDeviceCacheVO(deviceIdentification).get();
    }

    protected ProductModelCacheVO getProductModelCacheVO(String productIdentification) {
        return linkCacheDataHelper.getProductModelCacheVO(productIdentification);
    }

    protected void setDeviceDataCollectionPoolCacheVO(String productIdentification, String deviceIdentification, ProductResultVO productResultVO) {
        linkCacheDataHelper.setDeviceDataCollectionPoolCacheVO(productIdentification, deviceIdentification, productResultVO);
    }

    protected List<SuperTableDescribeVO> getProductModelSuperTableCacheVO(String productIdentification, String serviceCode, String deviceIdentification) {
        return linkCacheDataHelper.getProductModelSuperTableCacheVO(productIdentification, serviceCode, deviceIdentification);
    }

    protected void setProductModelSuperTableCacheVO(String productIdentification, String serviceCode, String deviceIdentification,
                                                    List<SuperTableDescribeVO> superTableDescribeOpt) {
        linkCacheDataHelper.setProductModelSuperTableCacheVO(productIdentification, serviceCode, deviceIdentification, superTableDescribeOpt);
    }

    protected void sendMessage(String topic, String qos, String message, String tenantId) {
        PublishMessageRequestVO publishMessageRequestVO = new PublishMessageRequestVO();
        publishMessageRequestVO.setReqId(Long.valueOf(SnowflakeIdUtil.nextId()));
        publishMessageRequestVO.setTenantId(tenantId);
        publishMessageRequestVO.setTopic(topic);
        publishMessageRequestVO.setQos(qos);
        publishMessageRequestVO.setClientType("web");
        publishMessageRequestVO.setPayload(message);
        publishMessageRequestVO.setExpirySeconds("3600");
        mqttBrokerOpenAnyTenantApi.sendMessage(publishMessageRequestVO);
    }

    /**
     * 生成响应主题字符串
     *
     * @param version       版本号
     * @param deviceId      设备ID
     * @param responseTopic 响应主题
     * @return 完整的响应主题字符串
     */
    protected String generateResponseTopic(String version, String deviceId, String responseTopic) {
        return String.format("/%s/devices/%s%s", version, deviceId, responseTopic);
    }

    protected abstract String processingTopicMessage(Object messageParam) throws Exception;
}
