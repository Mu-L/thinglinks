package com.mqttsnet.thinglinks.sdk.param.device;

import com.mqttsnet.thinglinks.sdk.request.device.IotNorthboundDeviceDataReportRequest;
import com.mqttsnet.thinglinks.sdk.response.device.IotNorthboundDeviceDataReportResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-设备数据上报参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundDeviceDataReportParam extends BaseParam<IotNorthboundDeviceDataReportRequest, IotNorthboundDeviceDataReportResponse> {

    @Override
    protected String method() {
        return "iot.northbound.device.dataReport";
    }
}
