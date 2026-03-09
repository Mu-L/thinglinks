package com.mqttsnet.thinglinks.common.cache.tenant.base;


import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.model.cache.CacheHashKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.io.Serializable;

/**
 * 参数 KEY
 * <p>
 * key: lc:def_parameter:tenant:id:string:{id}
 * value: obj
 *
 * <p>
 * #def_parameter
 *
 * @author mqttsnet
 * @date 2020/9/20 6:45 下午
 */
public class DictParameterKeyBuilder implements CacheKeyBuilder {
    public static CacheHashKey builder(Serializable id) {
        return new DictParameterKeyBuilder().hashKey(id);
    }

    @Override
    public String getTenant() {
        return null;
    }

    @Override
    public String getTable() {
        return CacheKeyTable.System.DEF_PARAMETER;
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
        return ValueType.string;
    }

}
