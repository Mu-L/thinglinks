package com.mqttsnet.thinglinks.sdk.param.device;

import com.mqttsnet.thinglinks.sdk.request.device.IotNorthboundDeviceGetDetailRequest;
import com.mqttsnet.thinglinks.sdk.response.device.IotNorthboundDeviceGetDetailResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-查询设备详情参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/01/22
 */
public class IotNorthboundDeviceManagerGetDetailParam extends BaseParam<IotNorthboundDeviceGetDetailRequest, IotNorthboundDeviceGetDetailResponse> {


    @Override
    protected String method() {
        return "iot.northbound.device.getDetail";
    }
}
