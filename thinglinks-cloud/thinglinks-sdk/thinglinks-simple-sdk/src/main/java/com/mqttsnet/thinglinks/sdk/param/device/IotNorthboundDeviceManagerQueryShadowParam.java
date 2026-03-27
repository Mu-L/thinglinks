package com.mqttsnet.thinglinks.sdk.param.device;

import com.mqttsnet.thinglinks.sdk.request.device.IotNorthboundDeviceQueryShadowRequest;
import com.mqttsnet.thinglinks.sdk.response.device.IotNorthboundDeviceQueryShadowResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-查询设备影子参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/6/22
 */
public class IotNorthboundDeviceManagerQueryShadowParam extends BaseParam<IotNorthboundDeviceQueryShadowRequest, IotNorthboundDeviceQueryShadowResponse> {

    @Override
    protected String method() {
        return "iot.northbound.device.queryShadow";
    }
}
