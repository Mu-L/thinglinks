package com.mqttsnet.thinglinks.sdk.param.device;

import com.mqttsnet.thinglinks.sdk.request.device.IotNorthboundDeviceDeleteSubDeviceRequest;
import com.mqttsnet.thinglinks.sdk.response.device.IotNorthboundDeviceDeleteSubDeviceResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-删除子设备参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundDeviceDeleteSubDeviceParam extends BaseParam<IotNorthboundDeviceDeleteSubDeviceRequest, IotNorthboundDeviceDeleteSubDeviceResponse> {

    @Override
    protected String method() {
        return "iot.northbound.device.deleteSubDevice";
    }
}
