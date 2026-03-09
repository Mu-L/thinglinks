package com.mqttsnet.thinglinks.msg.strategy.impl;

import com.mqttsnet.thinglinks.msg.entity.ExtendMsg;
import com.mqttsnet.thinglinks.msg.service.ExtendMsgService;
import com.mqttsnet.thinglinks.msg.strategy.MsgStrategy;
import com.mqttsnet.thinglinks.msg.strategy.domain.MsgParam;
import com.mqttsnet.thinglinks.msg.strategy.domain.MsgResult;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author mqttsnet
 * @date 2022/7/11 0011 10:29
 */
public class TestMsgStrategyImpl implements MsgStrategy {
    private static final Logger log = LoggerFactory.getLogger(TestMsgStrategyImpl.class);

    @Resource
    private ExtendMsgService extendMsgService;

    @Override
    public MsgResult exec(MsgParam msgParam) {

        ExtendMsg a = extendMsgService.getById(msgParam.getExtendMsg().getId());
        log.info("a {}", a);

        return MsgResult.builder().result("保存成功").build();
    }
}
