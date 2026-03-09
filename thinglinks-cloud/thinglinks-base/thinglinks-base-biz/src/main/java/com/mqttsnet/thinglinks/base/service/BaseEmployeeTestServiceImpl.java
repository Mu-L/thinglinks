package com.mqttsnet.thinglinks.base.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.manager.impl.SuperCacheManagerImpl;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.base.entity.user.BaseEmployee;
import com.mqttsnet.thinglinks.base.mapper.BaseEmployeeTestMapper;
import com.mqttsnet.thinglinks.common.cache.base.user.EmployeeCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 仅测试使用
 *
 * @author mqttsnet
 * @version v1.0
 * @date 2022/9/20 11:31 AM
 * @create [2022/9/20 11:31 AM ] [mqttsnet] [初始创建]
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@DS(DsConstant.BASE_TENANT)
public class BaseEmployeeTestServiceImpl extends SuperCacheManagerImpl<BaseEmployeeTestMapper, BaseEmployee> implements BaseEmployeeTestService {
    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new EmployeeCacheKeyBuilder();
    }

    @Override
    public BaseEmployee get(Long id) {
        return baseMapper.get(id);
    }
}
