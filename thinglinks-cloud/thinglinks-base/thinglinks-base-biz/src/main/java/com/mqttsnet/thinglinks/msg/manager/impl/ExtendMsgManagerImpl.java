package com.mqttsnet.thinglinks.msg.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.msg.entity.ExtendMsg;
import com.mqttsnet.thinglinks.msg.manager.ExtendMsgManager;
import com.mqttsnet.thinglinks.msg.mapper.ExtendMsgMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 消息
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-10 11:41:17
 * @create [2022-07-10 11:41:17] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ExtendMsgManagerImpl extends SuperManagerImpl<ExtendMsgMapper, ExtendMsg> implements ExtendMsgManager {

}


