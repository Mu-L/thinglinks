package com.mqttsnet.thinglinks.sdk.param.ota;

import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaSaveUpgradeRecordRequest;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaSaveUpgradeRecordResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-保存OTA升级记录参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundOtaSaveUpgradeRecordParam extends BaseParam<IotNorthboundOtaSaveUpgradeRecordRequest, IotNorthboundOtaSaveUpgradeRecordResponse> {

    @Override
    protected String method() {
        return "iot.northbound.ota.saveUpgradeRecord";
    }
}
