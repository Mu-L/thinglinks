package com.mqttsnet.thinglinks.sop.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.sop.entity.SopDocContent;

/**
 * <p>
 * 通用业务接口
 * 文档内容
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopDocContentManager extends SuperManager<SopDocContent> {
    void saveContent(Long docInfoId, String content);
}


