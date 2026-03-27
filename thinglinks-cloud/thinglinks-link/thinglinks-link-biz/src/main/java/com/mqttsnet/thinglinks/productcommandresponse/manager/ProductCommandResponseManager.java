package com.mqttsnet.thinglinks.productcommandresponse.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.productcommandresponse.entity.ProductCommandResponse;
import com.mqttsnet.thinglinks.productcommandresponse.vo.save.ProductCommandResponseSaveVO;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 产品模型服务命令属性响应参数
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
public interface ProductCommandResponseManager extends SuperManager<ProductCommandResponse> {

    /**
     * 校验CODE
     *
     * @param serviceId  服务ID
     * @param commandId  命令ID
     * @param parameterCode 参数编码
     * @return {@link List<ProductCommandResponse>} 产品模型服务命令属性响应参数
     */
    List<ProductCommandResponse> checkCode(Long serviceId, Long commandId, String parameterCode);

    /**
     * 根据命令ID查询请求命令信息
     *
     * @param commandIds
     * @return
     */
    List<ProductCommandResponseSaveVO> selectCommandResponses(List<Long> commandIds);
}


