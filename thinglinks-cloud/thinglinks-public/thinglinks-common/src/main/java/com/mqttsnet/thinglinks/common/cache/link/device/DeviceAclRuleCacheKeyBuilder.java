package com.mqttsnet.thinglinks.common.cache.link.device;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheHashKey;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.io.Serializable;
import java.time.Duration;

/**
 * <p>
 * 设备ACL规则 KEY
 * <p>
 * [服务模块名:]业务类型[:业务字段][:value类型][:ID] -> obj
 * link:def_device_acl_rule:productIdentification:deviceIdentification:obj:1 -> {}
 *
 * @author mqttsnet
 * @date 2023/5/30 6:45 下午
 */
public class DeviceAclRuleCacheKeyBuilder implements CacheKeyBuilder {

    private Long tenantId;

    /**
     * build key
     *
     * @param productIdentification 产品标识
     * @param deviceIdentification  设备标识
     * @return {@link  CacheKey} key
     */
    public static CacheKey builder(String productIdentification, String deviceIdentification) {
        return new DeviceAclRuleCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).key(productIdentification, deviceIdentification);
    }

    /**
     * build  key
     *
     * @param key productIdentification
     * @return {@link CacheKey}  key
     */
    public static CacheKey builder(Serializable key) {
        return new DeviceAclRuleCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).key(key);
    }

    /**
     * build hash field key
     *
     * @param productIdentification 产品标识
     * @param deviceIdentification  设备标识
     * @param field                 优先级
     * @return {@link CacheHashKey} hash key
     */
    public static CacheHashKey builder(String productIdentification, String deviceIdentification, String field) {
        return new DeviceAclRuleCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).hashFieldKey(field, productIdentification, deviceIdentification);
    }

    @Override
    public String getTenant() {
        return String.valueOf(this.tenantId);
    }

    @Override
    public DeviceAclRuleCacheKeyBuilder setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    @Override
    public String getTable() {
        return CacheKeyTable.Link.DEVICE_ACL_RULE;
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
        return Duration.ofHours(1);
    }
}
