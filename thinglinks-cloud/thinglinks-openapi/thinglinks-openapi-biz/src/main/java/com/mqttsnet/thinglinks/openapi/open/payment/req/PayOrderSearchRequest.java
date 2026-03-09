package com.mqttsnet.thinglinks.openapi.open.payment.req;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
 * @author 六如
 */
@Data
public class PayOrderSearchRequest {

    /**
     * 订单编号
     * @mock xxxx
     */
    @Length(max = 64) // 最大长度
    private String orderNo;

}
