package com.mqttsnet.thinglinks.productcommandresponse.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.productcommandresponse.entity.ProductCommandResponse;
import com.mqttsnet.thinglinks.productcommandresponse.vo.result.ProductCommandResponseResultVO;
import com.mqttsnet.thinglinks.productcommandresponse.vo.save.ProductCommandResponseSaveVO;
import com.mqttsnet.thinglinks.productcommandresponse.vo.update.ProductCommandResponseUpdateVO;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 产品模型服务命令属性响应参数
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
public interface ProductCommandResponseService extends SuperService<Long, ProductCommandResponse> {

    ProductCommandResponse saveProductCommandResponse(ProductCommandResponseSaveVO saveVO);

    /**
     * 修改产品模型设备响应服务命令属性
     *
     * @param updateVO
     * @return
     */
    ProductCommandResponse updateProductCommandResponse(ProductCommandResponseUpdateVO updateVO);

    /**
     * 删除产品模型设备响应服务命令属性
     *
     * @param id
     * @return
     */
    Boolean deleteProductCommandResponse(Long id);

    /**
     * 根据命令ID查询请求命令信息
     *
     * @param commandIds
     * @return
     */
    List<ProductCommandResponseResultVO> selectCommandResponses(List<Long> commandIds);
}


