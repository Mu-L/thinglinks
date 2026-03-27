package com.mqttsnet.thinglinks.msg.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.msg.entity.DefInterface;


/**
 * <p>
 * 业务接口
 * 接口
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-04 16:45:45
 * @create [2022-07-04 16:45:45] [mqttsnet] 
 */
public interface DefInterfaceService extends SuperService<Long, DefInterface> {
    /**
     * 检查接口编码是否重复
     *
     * @param code
     * @param id
     * @return
     */
    Boolean check(String code, Long id);
}


