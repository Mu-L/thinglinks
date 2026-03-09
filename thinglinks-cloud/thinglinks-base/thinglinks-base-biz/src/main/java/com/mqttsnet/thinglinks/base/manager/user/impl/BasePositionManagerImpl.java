package com.mqttsnet.thinglinks.base.manager.user.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.manager.impl.SuperCacheManagerImpl;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.basic.utils.CollHelper;
import com.mqttsnet.thinglinks.base.entity.user.BasePosition;
import com.mqttsnet.thinglinks.base.manager.user.BasePositionManager;
import com.mqttsnet.thinglinks.base.mapper.user.BasePositionMapper;
import com.mqttsnet.thinglinks.common.cache.base.user.PositionCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 通用业务实现类
 * 岗位
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-18
 * @create [2021-10-18] [mqttsnet] 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BasePositionManagerImpl extends SuperCacheManagerImpl<BasePositionMapper, BasePosition> implements BasePositionManager {
    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new PositionCacheKeyBuilder();
    }

    @Transactional(readOnly = true)
    @Override
    @DS(DsConstant.BASE_TENANT)
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {
        List<BasePosition> list = findByIds(ids, null).stream().filter(Objects::nonNull).toList();
        return CollHelper.uniqueIndex(list, BasePosition::getId, BasePosition::getName);
    }
}
