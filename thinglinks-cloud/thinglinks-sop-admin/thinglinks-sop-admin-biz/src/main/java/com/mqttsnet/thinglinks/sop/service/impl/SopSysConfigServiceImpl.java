package com.mqttsnet.thinglinks.sop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.sop.entity.SopSysConfig;
import com.mqttsnet.thinglinks.sop.manager.SopSysConfigManager;
import com.mqttsnet.thinglinks.sop.service.SopSysConfigService;

/**
 * <p>
 * 业务实现类
 * 系统配置表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:44
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SopSysConfigServiceImpl extends SuperServiceImpl<SopSysConfigManager, Long, SopSysConfig> implements SopSysConfigService {


}


