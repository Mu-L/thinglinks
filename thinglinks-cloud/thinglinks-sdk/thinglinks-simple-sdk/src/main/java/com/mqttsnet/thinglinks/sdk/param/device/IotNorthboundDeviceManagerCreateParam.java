package com.mqttsnet.thinglinks.sdk.param.device;

import com.mqttsnet.thinglinks.sdk.request.device.IotNorthboundDeviceManagerCreateRequest;
import com.mqttsnet.thinglinks.sdk.response.device.IotNorthboundDeviceManagerCreateResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-创建设备参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/6/23
 */
public class IotNorthboundDeviceManagerCreateParam extends BaseParam<IotNorthboundDeviceManagerCreateRequest, IotNorthboundDeviceManagerCreateResponse> {
    @Override
    protected String method() {
        return "iot.northbound.device.create";
    }

}
