package com.mqttsnet.thinglinks.manager.linkage.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.entity.linkage.RuleExecutionLog;
import com.mqttsnet.thinglinks.manager.linkage.RuleExecutionLogManager;
import com.mqttsnet.thinglinks.mapper.linkage.RuleExecutionLogMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 规则执行日志表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-12-02 18:41:26
 * @create [2024-12-02 18:41:26] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleExecutionLogManagerImpl extends SuperManagerImpl<RuleExecutionLogMapper, RuleExecutionLog> implements RuleExecutionLogManager {

}


