package com.mqttsnet.thinglinks.sop.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.sop.entity.SopSysConfig;
import com.mqttsnet.thinglinks.sop.vo.result.DocSettingDTO;

/**
 * <p>
 * 通用业务接口
 * 系统配置表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:44
 *
 */
public interface SopSysConfigManager extends SuperManager<SopSysConfig> {
    SopSysConfig getByKey(String key);

    DocSettingDTO getDocSetting();
}


