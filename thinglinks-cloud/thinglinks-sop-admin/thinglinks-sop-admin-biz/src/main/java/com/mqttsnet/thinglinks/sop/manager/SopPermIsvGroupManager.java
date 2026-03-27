package com.mqttsnet.thinglinks.sop.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.sop.entity.SopPermIsvGroup;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * isv分组
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopPermIsvGroupManager extends SuperManager<SopPermIsvGroup> {


    List<Long> listGroupIdByIsvId(Long isvId);
}


