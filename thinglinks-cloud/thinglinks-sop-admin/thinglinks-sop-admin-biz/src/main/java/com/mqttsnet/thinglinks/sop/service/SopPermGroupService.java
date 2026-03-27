package com.mqttsnet.thinglinks.sop.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.sop.entity.SopPermGroup;
import com.mqttsnet.thinglinks.sop.vo.save.SopPermIsvGroupSaveVO;


/**
 * <p>
 * 业务接口
 * 分组表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopPermGroupService extends SuperService<Long, SopPermGroup> {

    Boolean updateIsvGroup(SopPermIsvGroupSaveVO param);
}


