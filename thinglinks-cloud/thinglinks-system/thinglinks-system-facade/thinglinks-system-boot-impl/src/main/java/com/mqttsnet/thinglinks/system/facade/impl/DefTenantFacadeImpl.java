package com.mqttsnet.thinglinks.system.facade.impl;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.thinglinks.model.enumeration.system.DefTenantStatusEnum;
import com.mqttsnet.thinglinks.system.entity.tenant.DefTenant;
import com.mqttsnet.thinglinks.system.facade.DefTenantFacade;
import com.mqttsnet.thinglinks.system.service.tenant.DefTenantService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author tangyh
 * @since 2024/12/19 22:21
 */
@Service
@RequiredArgsConstructor
public class DefTenantFacadeImpl implements DefTenantFacade {
    private final DefTenantService defTenantService;

    @Override
    public R<List<DefTenant>> findAllTenant() {
        return R.success(defTenantService.list(Wraps.<DefTenant>lbQ().eq(DefTenant::getStatus, DefTenantStatusEnum.NORMAL)));
    }
}
