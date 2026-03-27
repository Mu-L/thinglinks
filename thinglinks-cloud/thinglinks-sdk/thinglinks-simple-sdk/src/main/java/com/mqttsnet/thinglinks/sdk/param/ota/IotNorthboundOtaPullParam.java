package com.mqttsnet.thinglinks.sdk.param.ota;

import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaPullRequest;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaPullResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-OTA拉取软固件信息参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundOtaPullParam extends BaseParam<IotNorthboundOtaPullRequest, IotNorthboundOtaPullResponse> {

    @Override
    protected String method() {
        return "iot.northbound.ota.pull";
    }
}
