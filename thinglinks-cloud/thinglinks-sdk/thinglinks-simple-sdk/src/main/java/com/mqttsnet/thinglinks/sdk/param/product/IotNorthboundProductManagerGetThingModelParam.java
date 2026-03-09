package com.mqttsnet.thinglinks.sdk.param.product;

import com.mqttsnet.thinglinks.sdk.request.product.IotNorthboundProductGetThingModelRequest;
import com.mqttsnet.thinglinks.sdk.response.product.IotNorthboundProductGetThingModelResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-查询产品物模型参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
public class IotNorthboundProductManagerGetThingModelParam extends BaseParam<IotNorthboundProductGetThingModelRequest, IotNorthboundProductGetThingModelResponse> {

    @Override
    protected String method() {
        return "iot.northbound.product.getThingModel";
    }
}
