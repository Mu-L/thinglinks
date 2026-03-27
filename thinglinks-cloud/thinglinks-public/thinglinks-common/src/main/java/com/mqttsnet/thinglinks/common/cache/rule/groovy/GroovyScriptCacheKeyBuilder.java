package com.mqttsnet.thinglinks.common.cache.rule.groovy;

import com.mqttsnet.basic.base.entity.SuperEntity;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;

/**
 * <p>
 * 规则脚本 KEY
 * <p>
 * [服务模块名:]业务类型[:业务字段][:value类型][:命名空间+平台编码+产品编码+渠道编码+业务编码+业务标识] -> obj
 * rule:def_groovy_script:脚本KEY:obj:1 -> {}
 *
 * @author mqttsnet
 * @date 2023/5/30 6:45 下午
 */
public class GroovyScriptCacheKeyBuilder implements CacheKeyBuilder {


    /**
     * 构建脚本缓存KEY (以下参数均不可为空)
     *
     * @param key 命名空间+平台编码+产品编码+渠道编码+业务编码+业务标识
     * @return {@link CacheKey} 缓存KEY
     */
    public static CacheKey builder(String key) {
        return new GroovyScriptCacheKeyBuilder().setTenantId(ContextUtil.getTenantId()).key(key);
    }

    @Override
    public String getTable() {
        return CacheKeyTable.Rule.DEF_GROOVY_SCRIPT;
    }


    @Override
    public String getModular() {
        return CacheKeyModular.RULE;
    }

    @Override
    public String getField() {
        return SuperEntity.ID_FIELD;
    }

    @Override
    public ValueType getValueType() {
        return ValueType.string;
    }

    @Override
    public Duration getExpire() {
        return Duration.ofHours(24);
    }
}
