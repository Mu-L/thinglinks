package com.mqttsnet.thinglinks.common.cache.base.user;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;

/**
 * 组织 KEY
 * <p>
 * #base_org
 * <p>
 * [服务模块名:]业务类型[:业务字段][:value类型][:组织id] -> obj
 * base:base_org:id:obj:1 -> {}
 *
 * @author mqttsnet
 * @date 2020/9/20 6:45 下午
 */
public class OrgCacheKeyBuilder implements CacheKeyBuilder {
    @Override
    public String getTable() {
        return CacheKeyTable.Base.BASE_ORG;
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
        return ValueType.obj;
    }

    @Override
    public Duration getExpire() {
        return Duration.ofHours(24);
    }
}
