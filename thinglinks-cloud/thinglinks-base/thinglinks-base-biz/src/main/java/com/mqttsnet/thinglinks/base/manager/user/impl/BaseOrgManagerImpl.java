package com.mqttsnet.thinglinks.base.manager.user.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.manager.impl.SuperCacheManagerImpl;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.basic.utils.CollHelper;
import com.mqttsnet.thinglinks.base.entity.user.BaseOrg;
import com.mqttsnet.thinglinks.base.manager.user.BaseOrgManager;
import com.mqttsnet.thinglinks.base.mapper.user.BaseOrgMapper;
import com.mqttsnet.thinglinks.common.cache.base.user.OrgCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * 通用业务实现类
 * 组织
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-18
 * @create [2021-10-18] [mqttsnet] 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class BaseOrgManagerImpl extends SuperCacheManagerImpl<BaseOrgMapper, BaseOrg> implements BaseOrgManager {

    @Override
    protected CacheKeyBuilder cacheKeyBuilder() {
        return new OrgCacheKeyBuilder();
    }

    @Override
    @Transactional(readOnly = true)
    @DS(DsConstant.BASE_TENANT)
    public Map<Serializable, Object> findByIds(Set<Serializable> params) {
        if (CollUtil.isEmpty(params)) {
            return Collections.emptyMap();
        }
        Set<Serializable> ids = new HashSet<>();
        params.forEach(item -> {
            if (item instanceof Collection tempItem) {
                ids.addAll(tempItem);
            } else {
                ids.add(item);
            }
        });

        List<BaseOrg> list = findByIds(ids, null);

        return CollHelper.uniqueIndex(list.stream().filter(Objects::nonNull).toList(), BaseOrg::getId, BaseOrg::getName);
    }

}
