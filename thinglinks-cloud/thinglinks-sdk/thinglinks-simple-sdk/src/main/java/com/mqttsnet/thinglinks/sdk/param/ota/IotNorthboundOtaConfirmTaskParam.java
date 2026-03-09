package com.mqttsnet.thinglinks.sdk.param.ota;

import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaConfirmTaskRequest;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaConfirmTaskResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 物联网北向API-OTA确认任务
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/1/6
 */
public class IotNorthboundOtaConfirmTaskParam extends BaseParam<IotNorthboundOtaConfirmTaskRequest, IotNorthboundOtaConfirmTaskResponse> {
    @Override
    protected String method() {
        return "iot.northbound.ota.confirmTask";
    }
}
