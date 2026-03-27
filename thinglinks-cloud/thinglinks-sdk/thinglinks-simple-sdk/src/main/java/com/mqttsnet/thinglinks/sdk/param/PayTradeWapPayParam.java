package com.mqttsnet.thinglinks.sdk.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import com.mqttsnet.thinglinks.sdk.request.PayTradeWapPayRequest;
import com.mqttsnet.thinglinks.sdk.response.PayTradeWapPayResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * pay.trade.wap.pay(手机网站支付接口)
 *
 * @author 六如
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PayTradeWapPayParam extends BaseParam<PayTradeWapPayRequest, PayTradeWapPayResponse> {
    @Override
    protected String method() {
        return "openapi.wap.pay";
    }
}
