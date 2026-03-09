package com.mqttsnet.thinglinks.sdk.param.ota;

import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaReportRequest;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaReportResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-OTA上报软固件版本参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundOtaReportParam extends BaseParam<IotNorthboundOtaReportRequest, IotNorthboundOtaReportResponse> {

    @Override
    protected String method() {
        return "iot.northbound.ota.report";
    }
}
