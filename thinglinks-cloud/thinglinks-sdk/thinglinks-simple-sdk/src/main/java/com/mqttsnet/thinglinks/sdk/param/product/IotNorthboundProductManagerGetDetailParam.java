package com.mqttsnet.thinglinks.sdk.param.product;

import com.mqttsnet.thinglinks.sdk.request.product.IotNorthboundProductGetDetailRequest;
import com.mqttsnet.thinglinks.sdk.response.product.IotNorthboundProductGetDetailResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-查询产品详情参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/01/22
 */
public class IotNorthboundProductManagerGetDetailParam extends BaseParam<IotNorthboundProductGetDetailRequest, IotNorthboundProductGetDetailResponse> {

    @Override
    protected String method() {
        return "iot.northbound.product.getDetail";
    }
}
