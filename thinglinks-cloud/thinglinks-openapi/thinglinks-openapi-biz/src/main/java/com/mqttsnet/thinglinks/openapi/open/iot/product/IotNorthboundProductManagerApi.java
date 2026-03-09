package com.mqttsnet.thinglinks.openapi.open.iot.product;

import com.gitee.sop.support.annotation.Open;
import com.gitee.sop.support.context.OpenContext;
import com.mqttsnet.thinglinks.openapi.open.iot.product.req.IotNorthboundProductGetDetailRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.product.req.IotNorthboundProductGetThingModelRequest;
import com.mqttsnet.thinglinks.openapi.open.iot.product.resp.IotNorthboundProductGetDetailResponse;
import com.mqttsnet.thinglinks.openapi.open.iot.product.resp.IotNorthboundProductGetThingModelResponse;

/**
 * 物联网北向API-产品管理
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/01/22
 */
public interface IotNorthboundProductManagerApi {

    /**
     * 查询产品详情接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API查询产品详细信息
     * 典型场景：应用服务器可调用此接口根据产品标识查询产品的完整信息
     */
    @Open(
            value = "iot.northbound.product.getDetail",
            version = "1.0"
    )
    IotNorthboundProductGetDetailResponse getProductDetail(IotNorthboundProductGetDetailRequest request, OpenContext context);

    /**
     * 查询产品物模型接口
     *
     * @apiNote 该接口用于平台管理员或授权应用通过北向API查询产品物模型完整信息
     * 典型场景：应用服务器可调用此接口根据产品标识查询产品的物模型信息（包含服务、属性、命令）
     */
    @Open(
            value = "iot.northbound.product.getThingModel",
            version = "1.0"
    )
    IotNorthboundProductGetThingModelResponse getProductThingModel(IotNorthboundProductGetThingModelRequest request, OpenContext context);

}
