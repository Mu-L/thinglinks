package com.mqttsnet.thinglinks.base.executor.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.mqttsnet.basic.context.ContextConstants;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.database.mybatis.conditions.query.LbQueryWrap;
import com.mqttsnet.thinglinks.model.enumeration.system.DefTenantStatusEnum;
import com.mqttsnet.thinglinks.system.entity.tenant.DefTenant;
import com.mqttsnet.thinglinks.system.service.tenant.DefTenantService;
import com.xxl.job.core.context.XxlJobHelper;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * @author mqttsnet
 * @date 2021/1/4 11:35 下午
 */
public abstract class AbstractTenantJob {
    @Autowired
    private DefTenantService defTenantService;

    protected void loadTenant(BiConsumer<DefTenant, String> consumer) {
        String traceId = IdUtil.fastSimpleUUID();
        MDC.put(ContextConstants.TRACE_ID_HEADER, StrUtil.isEmpty(traceId) ? StrUtil.EMPTY : traceId);

        LbQueryWrap<DefTenant> wrapper = Wraps.<DefTenant>lbQ()
                .eq(DefTenant::getStatus, DefTenantStatusEnum.NORMAL.getCode());

        List<DefTenant> list = defTenantService.list(wrapper);

        list.forEach(tenant -> {
            MDC.put(ContextConstants.TENANT_ID_KEY, tenant.getCode());
            consumer.accept(tenant, XxlJobHelper.getJobParam());
        });
    }

}
