package com.mqttsnet.thinglinks.sop.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.thinglinks.sop.entity.SopPermGroup;
import com.mqttsnet.thinglinks.sop.event.ChangeIsvPermEvent;

import java.util.Collections;

/**
 * <p>
 * 通用业务接口
 * 分组表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopPermGroupManager extends SuperManager<SopPermGroup> {
    default void sendChangeEvent(Long isvId) {
        SpringUtils.publishEvent(new ChangeIsvPermEvent(Collections.singletonList(isvId)));
    }
}


