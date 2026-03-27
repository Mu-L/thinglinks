package com.mqttsnet.thinglinks.video.cache;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.context.ContextConstants;
import com.mqttsnet.thinglinks.system.entity.tenant.DefTenant;
import com.mqttsnet.thinglinks.system.facade.DefTenantFacade;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * 缓存抽象类，提供共用方法
 *
 * @author mqttsnet
 */
@Slf4j
public abstract class CacheSuperAbstract {

    @Autowired
    protected DefTenantFacade defTenantApi;

    public void loadTenant(BiConsumer<DefTenant, String> consumer) {
        String traceId = IdUtil.fastSimpleUUID();
        MDC.put(ContextConstants.TRACE_ID_HEADER, StrUtil.isEmpty(traceId) ? StrUtil.EMPTY : traceId);

        R<List<DefTenant>> allTenant = defTenantApi.findAllTenant();
        if (!allTenant.getIsSuccess()) {
            return;
        }
        List<DefTenant> list = allTenant.getData();

        list.forEach(tenant -> {
            MDC.put(ContextConstants.TENANT_ID_KEY, tenant.getCode());
            consumer.accept(tenant, tenant.getCode());
        });
    }

}
