package com.mqttsnet.thinglinks.sop.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.sop.entity.SopPermIsvGroup;

import java.util.List;


/**
 * <p>
 * 业务接口
 * isv分组
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopPermIsvGroupService extends SuperService<Long, SopPermIsvGroup> {

    List<Long> listByGroupId(Long isvId);
}


