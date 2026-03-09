package com.mqttsnet.thinglinks.sdk.param.ota;

import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaRejectTaskRequest;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaRejectTaskResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 物联网北向API-OTA拒绝任务
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/1/6
 */
public class IotNorthboundOtaRejectTaskParam extends BaseParam<IotNorthboundOtaRejectTaskRequest, IotNorthboundOtaRejectTaskResponse> {
    @Override
    protected String method() {
        return "iot.northbound.ota.rejectTask";
    }
}
