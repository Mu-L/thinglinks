package com.mqttsnet.thinglinks.base.service;

import com.mqttsnet.basic.base.manager.SuperCacheManager;
import com.mqttsnet.thinglinks.base.entity.user.BaseEmployee;

/**
 * @author mqttsnet
 * @version v1.0
 * @date 2022/9/20 11:31 AM
 * @create [2022/9/20 11:31 AM ] [mqttsnet] [初始创建]
 */
public interface BaseEmployeeTestService extends SuperCacheManager<BaseEmployee> {
    /**
     * 单体查询
     *
     * @param id id
     * @return com.mqttsnet.thinglinks.base.entity.user.BaseEmployee
     * @author mqttsnet
     * @date 2022/10/28 9:20 AM
     * @create [2022/10/28 9:20 AM ] [mqttsnet] [初始创建]
     */
    BaseEmployee get(Long id);
}
