package com.mqttsnet.thinglinks.common.cache.tenant.tenant;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;

/**
 * 租户拥有的资源
 *
 * @author mqttsnet
 * @date 2020/9/20 6:45 下午
 */
public class TenantResourceCacheKeyBuilder implements CacheKeyBuilder {
    public static CacheKey builder(Long tenantId, Long applicationId) {
        return new TenantResourceCacheKeyBuilder().key(tenantId, applicationId);
    }

    @Override
    public String getTenant() {
        return StrPool.EMPTY;
    }

    @Override
    public String getModular() {
        return CacheKeyModular.SYSTEM;
    }

    @Override
    public String getTable() {
        return CacheKeyTable.System.TENANT_APPLICATION_RESOURCE;
    }

    @Override
    public String getField() {
        return SuperEntity.ID_FIELD;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.number;
    }

    @Override
    public Duration getExpire() {
        return Duration.ofHours(24);
    }
}
