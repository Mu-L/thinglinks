package com.mqttsnet.thinglinks.common.cache.base.user;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;

/**
 * 组织的角色
 * <p>
 *
 * @author mqttsnet
 * @date 2020/9/20 6:45 下午
 */
public class OrgRoleCacheKeyBuilder implements CacheKeyBuilder {
    public static CacheKey build(Long orgId) {
        return new OrgRoleCacheKeyBuilder().key(orgId);
    }

    @Override
    public String getTable() {
        return CacheKeyTable.Base.ORG_ROLE;
    }


    @Override
    public String getModular() {
        return CacheKeyModular.BASE;
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
