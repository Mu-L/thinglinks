package com.mqttsnet.thinglinks.base.biz.system;

import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.base.entity.system.BaseRole;
import com.mqttsnet.thinglinks.base.service.system.BaseRoleService;
import com.mqttsnet.thinglinks.model.enumeration.base.RoleCategoryEnum;
import com.mqttsnet.thinglinks.model.enumeration.system.DataTypeEnum;
import com.mqttsnet.thinglinks.system.manager.application.DefTenantResourceRelManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

/**
 * @author mqttsnet
 * @date 2021/11/20 23:49
 */

@Service
@RequiredArgsConstructor
public class BaseRoleBiz {
    private final BaseRoleService baseRoleService;
    private final DefTenantResourceRelManager defTenantResourceRelManager;

    /**
     * 查询角色拥有的资源
     * 1. 系统管理员 拥有该租户下的所有资源
     * 2. 其他管理员 拥有分配给他的资源
     *
     * @param roleId 角色ID
     * @return java.util.Map<java.lang.Long, java.util.Collection < java.lang.Long>>
     * @author mqttsnet
     * @date 2022/10/19 5:29 PM
     * @create [2022/10/19 5:29 PM ] [mqttsnet] [初始创建]
     */
    public Map<Long, Collection<Long>> findResourceIdByRoleId(Long roleId) {
        BaseRole baseRole = baseRoleService.getById(roleId);
        if (baseRole == null) {
            return Collections.emptyMap();
        }
//        系统管理员有全部权限(管理员在 base_role_resource_rel 表无数据)
        if (DataTypeEnum.SYSTEM.eq(baseRole.getType())) {
            return defTenantResourceRelManager.findResourceIdByTenantId(ContextUtil.getTenantId());
        }

        return baseRoleService.findResourceIdByRoleId(roleId, RoleCategoryEnum.FUNCTION);
    }
}
