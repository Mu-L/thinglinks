package com.mqttsnet.thinglinks.sop.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.thinglinks.sop.entity.SopIsvKeys;
import com.mqttsnet.thinglinks.sop.event.ChangeIsvKeyEvent;

import java.util.Collections;

/**
 * <p>
 * 通用业务接口
 * ISV秘钥管理
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopIsvKeysManager extends SuperManager<SopIsvKeys> {

    default void sendChangeEvent(Long isvId) {
        SpringUtils.publishEvent(new ChangeIsvKeyEvent(Collections.singletonList(isvId)));
    }

}


