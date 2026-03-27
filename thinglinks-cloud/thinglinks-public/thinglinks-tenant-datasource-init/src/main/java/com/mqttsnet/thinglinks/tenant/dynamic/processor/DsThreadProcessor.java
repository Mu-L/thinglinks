package com.mqttsnet.thinglinks.tenant.dynamic.processor;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.processor.DsProcessor;
import com.mqttsnet.basic.context.ContextUtil;
import org.aopalliance.intercept.MethodInvocation;

/**
 * 从Thread变量中获取参数
 *
 * @author mqttsnet
 * @date 2020年03月15日11:12:54
 */
public class DsThreadProcessor extends DsProcessor {

    /**
     * header prefix
     */
    public static final String HEADER_PREFIX = "#thread";
    public static final String POOL_NAME = "{}_{}";

    public static String getPoolName(String prefix, String tenantId) {
        return StrUtil.format(POOL_NAME, prefix, tenantId);
    }

    @Override
    public boolean matches(String key) {
        return key.startsWith(HEADER_PREFIX);
    }

    @Override
    public String doDetermineDatasource(MethodInvocation invocation, String key) {
        String prefix = key.substring(HEADER_PREFIX.length() + 1);
        String tenantId = ContextUtil.get(prefix);
        return getPoolName(prefix, tenantId);
    }
}
