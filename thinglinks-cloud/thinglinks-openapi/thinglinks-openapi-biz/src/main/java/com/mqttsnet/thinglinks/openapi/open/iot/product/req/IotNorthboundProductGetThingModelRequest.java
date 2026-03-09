package com.mqttsnet.thinglinks.openapi.open.iot.product.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Description:
 * 北向API-查询产品物模型请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/02/02
 */
@Data
public class IotNorthboundProductGetThingModelRequest {

    /**
     * 产品标识（产品的唯一标识）
     * @mock PROD_001
     */
    @NotBlank(message = "请填写产品标识")
    @Size(max = 64, message = "产品标识长度不能超过{max}")
    private String productIdentification;

}
