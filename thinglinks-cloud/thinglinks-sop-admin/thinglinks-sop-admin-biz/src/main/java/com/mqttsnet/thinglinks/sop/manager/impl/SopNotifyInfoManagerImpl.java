package com.mqttsnet.thinglinks.sop.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.sop.entity.SopNotifyInfo;
import com.mqttsnet.thinglinks.sop.manager.SopNotifyInfoManager;
import com.mqttsnet.thinglinks.sop.mapper.SopNotifyInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 回调信息
 * </p>
 *
 * @author zuihou
 * @date 2025-12-17 15:38:07
 * @create [2025-12-17 15:38:07] [zuihou] [代码生成器生成]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SopNotifyInfoManagerImpl extends SuperManagerImpl<SopNotifyInfoMapper, SopNotifyInfo> implements SopNotifyInfoManager {
}


