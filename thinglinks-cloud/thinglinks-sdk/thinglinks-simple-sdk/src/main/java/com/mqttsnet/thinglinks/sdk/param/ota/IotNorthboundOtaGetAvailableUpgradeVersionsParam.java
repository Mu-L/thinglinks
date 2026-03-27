package com.mqttsnet.thinglinks.sdk.param.ota;

import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaListUpgradeableVersionsRequest;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaGetAvailableUpgradeVersionsResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-OTA获取可用升级版本参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/09
 */
public class IotNorthboundOtaGetAvailableUpgradeVersionsParam extends BaseParam<IotNorthboundOtaListUpgradeableVersionsRequest, IotNorthboundOtaGetAvailableUpgradeVersionsResponse> {

    @Override
    protected String method() {
        return "iot.northbound.ota.getAvailableUpgradeVersions";
    }
}
