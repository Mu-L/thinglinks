package com.mqttsnet.thinglinks.msg.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.msg.entity.ExtendInterfaceLogging;
import com.mqttsnet.thinglinks.msg.manager.ExtendInterfaceLoggingManager;
import com.mqttsnet.thinglinks.msg.mapper.ExtendInterfaceLoggingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 接口执行日志记录
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-09 23:58:59
 * @create [2022-07-09 23:58:59] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ExtendInterfaceLoggingManagerImpl extends SuperManagerImpl<ExtendInterfaceLoggingMapper, ExtendInterfaceLogging> implements ExtendInterfaceLoggingManager {

}


