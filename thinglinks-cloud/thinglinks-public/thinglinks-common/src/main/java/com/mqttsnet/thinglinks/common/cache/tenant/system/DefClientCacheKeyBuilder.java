package com.mqttsnet.thinglinks.common.cache.tenant.system;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;

/**
 * 客户端
 * <p>
 * #def_client
 *
 * @author mqttsnet
 * @date 2020/9/20 6:45 下午
 */
public class DefClientCacheKeyBuilder implements CacheKeyBuilder {

    public static CacheKey builder(Long id) {
        return new DefClientCacheKeyBuilder().key(id);
    }


    @Override
    public String getTenant() {
        return null;
    }

    @Override
    public String getTable() {
        return CacheKeyTable.System.DEF_CLIENT;
    }

    @Override
    public String getModular() {
        return CacheKeyModular.SYSTEM;
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
