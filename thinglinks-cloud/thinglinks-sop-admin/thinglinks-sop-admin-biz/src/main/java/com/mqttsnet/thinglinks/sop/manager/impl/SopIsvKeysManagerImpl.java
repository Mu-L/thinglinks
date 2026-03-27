package com.mqttsnet.thinglinks.sop.manager.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.sop.entity.SopIsvKeys;
import com.mqttsnet.thinglinks.sop.manager.SopIsvKeysManager;
import com.mqttsnet.thinglinks.sop.mapper.SopIsvKeysMapper;

/**
 * <p>
 * 通用业务实现类
 * ISV秘钥管理
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SopIsvKeysManagerImpl extends SuperManagerImpl<SopIsvKeysMapper, SopIsvKeys> implements SopIsvKeysManager {

}


