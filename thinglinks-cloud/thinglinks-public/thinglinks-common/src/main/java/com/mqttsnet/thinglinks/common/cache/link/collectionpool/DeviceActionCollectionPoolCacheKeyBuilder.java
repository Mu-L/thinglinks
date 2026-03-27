package com.mqttsnet.thinglinks.common.cache.link.collectionpool;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;

/**
 * 设备动作收集池 KEY
 * <p>
 * #device 匹配数据模型成功后，将数据写入到设备动作收集池中
 * <p>
 * [服务模块名:]业务类型[:业务字段][:value类型][:产品标识][:设备标识] -> obj
 * link:def_device_action_collection_pool:productIdentification:deviceIdentification:obj:1 -> {}
 *
 * @author mqttsnet
 * @date 2023/5/30 6:45 下午
 */
public class DeviceActionCollectionPoolCacheKeyBuilder implements CacheKeyBuilder {

    private Long tenantId;

    public static CacheKey build(String productIdentification) {
        return new DeviceActionCollectionPoolCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).key(productIdentification);
    }

    public static CacheKey build(String productIdentification, String deviceIdentification) {
        return new DeviceActionCollectionPoolCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).key(productIdentification, deviceIdentification);
    }

    @Override
    public DeviceActionCollectionPoolCacheKeyBuilder setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    public String getTenant() {
        return String.valueOf(this.tenantId);
    }

    @Override
    public String getTable() {
        return CacheKeyTable.Link.DEVICE_ACTION_COLLECTION_POOL;
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
