package com.mqttsnet.thinglinks.sop.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.thinglinks.sop.entity.SopIsvInfo;
import com.mqttsnet.thinglinks.sop.event.ChangeIsvInfoEvent;
import com.mqttsnet.thinglinks.sop.event.ChangeIsvPermEvent;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * <p>
 * 通用业务接口
 * isv信息表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopIsvInfoManager extends SuperManager<SopIsvInfo> {
    default void sendChangeEvent(Long isvId) {
        SpringUtils.publishEvent(new ChangeIsvInfoEvent(Collections.singletonList(isvId)));
    }

    default void sendChangeEvent(Collection<Long> isvIds) {
        SpringUtils.publishEvent(new ChangeIsvInfoEvent(isvIds));
    }

    default void refreshIsvPerm() {
        List<SopIsvInfo> list = list();
        SpringUtils.publishEvent(new ChangeIsvPermEvent(list.stream().map(SopIsvInfo::getId).distinct().toList()));
    }

    SopIsvInfo getIsvByAppId(String appId);

    /**
     * 获取平台密钥
     *
     * @param appId 应用唯一标识
     * @return 密钥
     */
    String getPrivatePlatformKey(String appId);
}


