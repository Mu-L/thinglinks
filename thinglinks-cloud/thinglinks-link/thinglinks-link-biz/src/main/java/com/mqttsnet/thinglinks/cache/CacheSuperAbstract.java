package com.mqttsnet.thinglinks.cache;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.system.entity.tenant.DefTenant;
import com.mqttsnet.thinglinks.system.facade.DefTenantFacade;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 缓存抽象类，提供共用方法
 * @author xiaonannet
 */
public abstract class CacheSuperAbstract {

    public static final int PAGE_SIZE = 10000;

    @Autowired
    protected DefTenantFacade defTenantApi;

    /**
     * 获取所有租户
     *
     * @return {@link List<Long>} 租户ID列表
     */
    public List<Long> getTenantList() {
        R<List<DefTenant>> allTenant = defTenantApi.findAllTenant();
        if (allTenant.getData() != null) {
            return allTenant.getData().stream().map(DefTenant::getId).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

}
