package com.mqttsnet.thinglinks.system.manager.application.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.system.entity.application.DefUserApplication;
import com.mqttsnet.thinglinks.system.manager.application.DefUserApplicationManager;
import com.mqttsnet.thinglinks.system.mapper.application.DefUserApplicationMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 用户的默认应用
 * </p>
 *
 * @author mqttsnet
 * @date 2022-03-06
 * @create [2022-03-06] [mqttsnet] 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefUserApplicationManagerImpl extends SuperManagerImpl<DefUserApplicationMapper, DefUserApplication> implements DefUserApplicationManager {
}
