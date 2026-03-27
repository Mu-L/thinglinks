package com.mqttsnet.thinglinks.system.facade;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.system.entity.tenant.DefTenant;

import java.util.List;

/**
 *
 * @author tangyh
 * @since 2024/12/19 22:21
 */
public interface DefTenantFacade {
    R<List<DefTenant>> findAllTenant();
}
