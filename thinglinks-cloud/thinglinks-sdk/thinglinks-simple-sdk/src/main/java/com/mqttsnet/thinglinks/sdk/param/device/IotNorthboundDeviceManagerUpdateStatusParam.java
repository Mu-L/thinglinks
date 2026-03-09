package com.mqttsnet.thinglinks.sdk.param.device;

import com.mqttsnet.thinglinks.sdk.request.device.IotNorthboundDeviceUpdateStatusRequest;
import com.mqttsnet.thinglinks.sdk.response.device.IotNorthboundDeviceUpdateStatusResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-修改设备状态参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundDeviceManagerUpdateStatusParam extends BaseParam<IotNorthboundDeviceUpdateStatusRequest, IotNorthboundDeviceUpdateStatusResponse> {

    @Override
    protected String method() {
        return "iot.northbound.device.updateStatus";
    }
}
