package com.mqttsnet.thinglinks.record.script;

import com.google.common.base.Joiner;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.thinglinks.common.cache.rule.groovy.GroovyScriptCacheKeyBuilder;
import com.mqttsnet.thinglinks.vo.query.script.RuleGroovyScriptQuery;

/**
 * ============================================================================
 * Description:
 * <p>
 * 脚本唯一标识 Record
 *
 * @param namespace              命名空间（必填）
 * @param platformCode           平台编码（必填）
 * @param productCode            产品编码（必填）
 * @param channelCode            渠道编码（必填）
 * @param businessCode           业务编码（必填）
 * @param businessIdentification 业务标识（必填,动态参数）
 *                               ============================================================================
 * @author Sun Shihuan
 * @version 1.0.0
 * @email
 * @date 2025/4/15 14:51
 */
public record ScriptIdentifier(
        String namespace,
        String platformCode,
        String productCode,
        String channelCode,
        String businessCode,
        String businessIdentification
) {

    /**
     * 构造器校验
     */
    public ScriptIdentifier {
        // 必填字段校验
        ArgumentAssert.notBlank(namespace, "命名空间不能为空");
        ArgumentAssert.notBlank(platformCode, "平台编码不能为空");
        ArgumentAssert.notBlank(productCode, "产品编码不能为空");
        ArgumentAssert.notBlank(channelCode, "渠道编码不能为空");
        ArgumentAssert.notBlank(businessCode, "业务编码不能为空");
        ArgumentAssert.notBlank(businessIdentification, "业务标识不能为空");
    }

    // 核心方法 -----------------------------------------------------

    /**
     * 生成 CacheKey
     *
     * @param query 查询对象
     * @return {@link CacheKey}
     */
    public static CacheKey buildCacheKey(RuleGroovyScriptQuery query) {
        String keyPart = Joiner.on(StrPool.COLON)
                .join(query.getNamespace(), query.getPlatformCode(),
                        query.getProductCode(), query.getChannelCode(),
                        query.getBusinessCode(), query.getBusinessIdentification());
        return GroovyScriptCacheKeyBuilder.builder(keyPart);
    }

    // 转换方法 -----------------------------------------------------

    /**
     * 从查询对象转换
     */
    public static ScriptIdentifier fromQuery(RuleGroovyScriptQuery query) {
        ArgumentAssert.notNull(query, "查询对象不能为空");
        return new ScriptIdentifier(
                query.getNamespace(),
                query.getPlatformCode(),
                query.getProductCode(),
                query.getChannelCode(),
                query.getBusinessCode(),
                query.getBusinessIdentification()
        );
    }

    /**
     * MQTT数据协议
     * 数据上报专用构造方法
     *
     * @param businessIdentification 产品标识
     */
    public static ScriptIdentifier forMqttToDatas(String businessIdentification) {
        return new ScriptIdentifier(
                "system",
                "iot",
                "dataProtocol",
                "mqtt",
                "datas",
                businessIdentification
        );
    }


    // 领域特定方法（DSL） -----------------------------------------------------

    /**
     * WebSocket数据协议
     * 数据上报专用构造方法
     *
     * @param businessIdentification 产品标识
     */
    public static ScriptIdentifier forWebSocketToDatas(String businessIdentification) {
        return new ScriptIdentifier(
                "system",
                "iot",
                "dataProtocol",
                "webSocket",
                "datas",
                businessIdentification
        );
    }

    public String buildCompositeKey() {
        return String.join(StrPool.COLON, namespace, platformCode, productCode, channelCode, businessCode, businessIdentification);
    }
}
