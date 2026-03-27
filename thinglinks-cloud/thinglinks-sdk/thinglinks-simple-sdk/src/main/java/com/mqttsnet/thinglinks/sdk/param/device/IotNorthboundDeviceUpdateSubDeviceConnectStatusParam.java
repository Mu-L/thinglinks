package com.mqttsnet.thinglinks.sdk.param.device;

import com.mqttsnet.thinglinks.sdk.request.device.IotNorthboundDeviceUpdateSubDeviceConnectStatusRequest;
import com.mqttsnet.thinglinks.sdk.response.device.IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-修改子设备连接状态参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundDeviceUpdateSubDeviceConnectStatusParam extends BaseParam<IotNorthboundDeviceUpdateSubDeviceConnectStatusRequest, IotNorthboundDeviceUpdateSubDeviceConnectStatusResponse> {

    @Override
    protected String method() {
        return "iot.northbound.device.updateSubDeviceConnectStatus";
    }
}
