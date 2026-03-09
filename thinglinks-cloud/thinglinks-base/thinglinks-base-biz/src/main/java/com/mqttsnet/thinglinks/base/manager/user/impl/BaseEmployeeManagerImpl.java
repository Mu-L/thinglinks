package com.mqttsnet.thinglinks.base.manager.user.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.manager.impl.SuperCacheManagerImpl;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.base.entity.user.BaseEmployee;
import com.mqttsnet.thinglinks.base.manager.user.BaseEmployeeManager;
import com.mqttsnet.thinglinks.base.mapper.user.BaseEmployeeMapper;
import com.mqttsnet.thinglinks.base.vo.query.user.BaseEmployeePageQuery;
import com.mqttsnet.thinglinks.base.vo.result.user.BaseEmployeeResultVO;
import com.mqttsnet.thinglinks.common.cache.base.user.EmployeeCacheKeyBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 员工
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-18
 * @create [2021-10-18] [mqttsnet] 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BaseEmployeeManagerImpl extends SuperCacheManagerImpl<BaseEmployeeMapper, BaseEmployee> implements BaseEmployeeManager {

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new EmployeeCacheKeyBuilder();
    }

    @Override
    public IPage<BaseEmployeeResultVO> selectPageResultVO(IPage<BaseEmployee> page, Wrapper<BaseEmployee> wrapper, BaseEmployeePageQuery model) {
        return baseMapper.selectPageResultVO(page, wrapper, model);
    }
}
