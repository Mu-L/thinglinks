package com.mqttsnet.thinglinks.common.cache.link.collectionpool;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;

/**
 * 下行设备原始数据收集池 KEY
 * <p>
 * #device
 * <p>
 * [服务模块名:]业务类型[:业务字段][:value类型][:设备标识][:时间戳] -> obj
 * link:def_descending_device_original_data_collection_pool:deviceIdentification:timestamp:obj:1 -> {}
 *
 * @author mqttsnet
 * @date 2023/11/22 18:45 下午
 */
public class DescendingDeviceOriginalDataCollectionPoolCacheKeyBuilder implements CacheKeyBuilder {

    public static CacheKey build(String deviceIdentification, Long timestamp) {
        return new DescendingDeviceOriginalDataCollectionPoolCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).key(deviceIdentification, timestamp);
    }

    @Override
    public String getTenant() {
        return null;
    }

    @Override
    public String getTable() {
        return CacheKeyTable.Link.DESCENDING_DEVICE_ORIGINAL_DATA_COLLECTION_POOL;
    }


    @Override
    public String getModular() {
        return CacheKeyModular.LINK;
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
