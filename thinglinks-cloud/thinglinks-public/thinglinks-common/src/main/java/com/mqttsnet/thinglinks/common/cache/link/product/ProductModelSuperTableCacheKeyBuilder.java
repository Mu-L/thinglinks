package com.mqttsnet.thinglinks.common.cache.link.product;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;
import java.util.Random;

/**
 * 产品模型 KEY
 * <p>
 * #product
 * <p>
 * [服务模块名:]业务类型[:业务字段][:value类型][:产品标识][:服务编码][:设备标识] -> obj
 * link:def_product_model_super_table:productIdentification:serviceCode:deviceIdentification:obj:1 -> {}
 *
 * @author mqttsnet
 * @date 2023/5/30 6:45 下午
 */
public class ProductModelSuperTableCacheKeyBuilder implements CacheKeyBuilder {

    private static final Random RANDOM = new Random();

    public static CacheKey build(String productIdentification, String serviceCode) {
        return new ProductModelSuperTableCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).key(productIdentification, serviceCode);
    }

    public static CacheKey build(String productIdentification, String serviceCode, String deviceIdentification) {
        return new ProductModelSuperTableCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).key(productIdentification, serviceCode, deviceIdentification);
    }

    @Override
    public String getTable() {
        return CacheKeyTable.Link.PRODUCT_MODEL_SUPER_TABLE;
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
        // 基础的10分钟
        Duration baseDuration = Duration.ofMinutes(10);
        // 生成一个随机数，0到5分钟之间的随机数
        int randomMinutes = RANDOM.nextInt(6);
        Duration randomDuration = Duration.ofMinutes(randomMinutes);
        return baseDuration.plus(randomDuration);
    }
}
