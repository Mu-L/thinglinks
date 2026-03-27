package com.mqttsnet.thinglinks.sop.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.sop.entity.SopPermGroupPermission;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 组权限表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopPermGroupPermissionService extends SuperService<Long, SopPermGroupPermission> {

    Boolean delete(Long groupId, List<Long> apiIdList);
}


