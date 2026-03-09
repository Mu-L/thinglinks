package com.mqttsnet.thinglinks.groovy.controller.script;

import com.mqttsnet.basic.groovy.entity.ExecuteParams;
import com.mqttsnet.thinglinks.dto.script.OrderInfoDTO;
import com.mqttsnet.thinglinks.dto.script.ProductInfoDTO;

import java.util.Date;

/**
 * 规则脚本基础 controller
 *
 * @author mqttsnet 2025/03/02 17:28
 */
public class RuleGroovyScriptBaseController {
    /**
     * 构建订单入参
     */
    public ExecuteParams buildOrderParams() {
        OrderInfoDTO orderInfoDTO = new OrderInfoDTO();
        orderInfoDTO.setOrderId(1000);
        orderInfoDTO.setOrderAmount("1000");
        orderInfoDTO.setOrderName("测试订单");
        orderInfoDTO.setOrderNumber("ThingLinks-12345");
        ExecuteParams executeParams = new ExecuteParams();
        executeParams.put("orderInfo", orderInfoDTO);
        return executeParams;
    }

    /**
     * 构建商品入参
     */
    public ExecuteParams buildProductParams() {
        ProductInfoDTO productInfoDTO = new ProductInfoDTO();
        productInfoDTO.setCreateDate(new Date());
        productInfoDTO.setName("小米手机");
        productInfoDTO.setPrice(10D);
        ExecuteParams executeParams = new ExecuteParams();
        executeParams.put("productInfo", productInfoDTO);
        return executeParams;
    }


}
