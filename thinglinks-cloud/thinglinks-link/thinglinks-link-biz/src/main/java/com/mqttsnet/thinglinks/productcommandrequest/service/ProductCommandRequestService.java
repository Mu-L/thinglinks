package com.mqttsnet.thinglinks.productcommandrequest.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.productcommandrequest.entity.ProductCommandRequest;
import com.mqttsnet.thinglinks.productcommandrequest.vo.result.ProductCommandRequestResultVO;
import com.mqttsnet.thinglinks.productcommandrequest.vo.save.ProductCommandRequestSaveVO;
import com.mqttsnet.thinglinks.productcommandrequest.vo.update.ProductCommandRequestUpdateVO;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 产品模型服务命令属性请求参数
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
public interface ProductCommandRequestService extends SuperService<Long, ProductCommandRequest> {

    /**
     * 保存产品模型设备下发服务命令属性
     *
     * @param saveVO
     * @return
     */
    ProductCommandRequest saveProductCommandRequest(ProductCommandRequestSaveVO saveVO);

    /**
     * 修改产品模型设备下发服务命令属性
     *
     * @param updateVO
     * @return
     */
    ProductCommandRequest updateProductCommandRequest(ProductCommandRequestUpdateVO updateVO);

    /**
     * 删除产品模型设备下发服务命令属性
     *
     * @param id
     * @return
     */
    Boolean deleteProductCommandRequest(Long id);

    /**
     * 根据命令ID查询请求命令信息
     *
     * @param commandIds
     * @return
     */
    List<ProductCommandRequestResultVO> selectCommandRequests(List<Long> commandIds);
}


