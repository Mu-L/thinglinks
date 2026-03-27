package com.mqttsnet.thinglinks.system.manager.tenant.impl;

import com.mqttsnet.basic.base.manager.impl.SuperCacheManagerImpl;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.tenant.tenant.DefUserTenantCacheKeyBuilder;
import com.mqttsnet.thinglinks.system.entity.tenant.DefUserTenantRel;
import com.mqttsnet.thinglinks.system.manager.tenant.DefUserTenantRelManager;
import com.mqttsnet.thinglinks.system.mapper.tenant.DefUserTenantRelMapper;
import com.mqttsnet.thinglinks.system.vo.result.tenant.DefUserTenantRelResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通用业务实现类
 * 员工
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-27
 * @create [2021-10-27] [mqttsnet] 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefUserTenantRelManagerImpl extends SuperCacheManagerImpl<DefUserTenantRelMapper, DefUserTenantRel> implements DefUserTenantRelManager {
    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new DefUserTenantCacheKeyBuilder();
    }


    @Override
    public List<DefUserTenantRelResultVO> listEmployeeByUserId(Long userId) {
        return baseMapper.listEmployeeByUserId(userId);
    }
}
