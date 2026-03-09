package com.mqttsnet.thinglinks.sdk.param.ota;

import com.mqttsnet.thinglinks.sdk.request.ota.IotNorthboundOtaReadResponseRequest;
import com.mqttsnet.thinglinks.sdk.response.ota.IotNorthboundOtaReadResponseResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-OTA读取设备软固件版本信息响应参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundOtaReadResponseParam extends BaseParam<IotNorthboundOtaReadResponseRequest, IotNorthboundOtaReadResponseResponse> {

    @Override
    protected String method() {
        return "iot.northbound.ota.readResponse";
    }
}
