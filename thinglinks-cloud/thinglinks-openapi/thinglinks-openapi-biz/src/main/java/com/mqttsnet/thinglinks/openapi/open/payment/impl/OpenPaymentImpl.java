package com.mqttsnet.thinglinks.openapi.open.payment.impl;

import com.gitee.sop.support.context.OpenContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Value;
import com.mqttsnet.thinglinks.openapi.open.payment.OpenPayment;
import com.mqttsnet.thinglinks.openapi.open.payment.req.PayOrderSearchRequest;
import com.mqttsnet.thinglinks.openapi.open.payment.req.PayTradeWapPayRequest;
import com.mqttsnet.thinglinks.openapi.open.payment.resp.PayOrderSearchResponse;
import com.mqttsnet.thinglinks.openapi.open.payment.resp.PayTradeWapPayResponse;

import java.util.UUID;


/**
 * 开放接口测试类实现
 *
 * @author 六如
 */
@DubboService
@Slf4j
//@DubboService(validation = "jvalidationNew")
public class OpenPaymentImpl implements OpenPayment {

    @Value("${dubbo.labels:}")
    private String env;

    @Override
    public PayTradeWapPayResponse tradeWapPay(PayTradeWapPayRequest request, OpenContext context) {
        log.info("appId={}, tenantId={}", context.getAppId(), context.getTenantId());

        PayTradeWapPayResponse payTradeWapPayResponse = new PayTradeWapPayResponse();
        payTradeWapPayResponse.setPageRedirectionData(UUID.randomUUID().toString());

        return payTradeWapPayResponse;
    }

    @Override
    public PayOrderSearchResponse orderSearch(PayOrderSearchRequest request) {
        PayOrderSearchResponse payOrderSearchResponse = new PayOrderSearchResponse();
        payOrderSearchResponse.setOrderNo(request.getOrderNo());
        payOrderSearchResponse.setPayNo("xxxx");
        payOrderSearchResponse.setPayUserId(111L);
        payOrderSearchResponse.setPayUserName("Jim");

        payOrderSearchResponse.setRemark("11" + ",env:" + env);
        return payOrderSearchResponse;
    }
}
