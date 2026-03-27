package com.mqttsnet.thinglinks.link.api.anyuser.hystrix;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.link.api.anyuser.ProductOpenAnyUserApi;
import com.mqttsnet.thinglinks.product.vo.param.ProductParamVO;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;

/**
 * 产品开放接口降级处理
 *
 * @author mqttsnet
 * @date 2025-06-22
 */
public class ProductOpenAnyUserApiFallback implements ProductOpenAnyUserApi {
    @Override
    public R<ProductResultVO> getProductDetailByNorthbound(String productIdentification) {
        return R.timeout();
    }

    @Override
    public R<ProductParamVO> getProductThingModelByNorthbound(String productIdentification) {
        return R.timeout();
    }
}
