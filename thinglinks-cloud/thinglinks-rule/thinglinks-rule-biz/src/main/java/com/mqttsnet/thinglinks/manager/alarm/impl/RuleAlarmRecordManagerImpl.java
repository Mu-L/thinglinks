package com.mqttsnet.thinglinks.manager.alarm.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.entity.alarm.RuleAlarmRecord;
import com.mqttsnet.thinglinks.manager.alarm.RuleAlarmRecordManager;
import com.mqttsnet.thinglinks.mapper.alarm.RuleAlarmRecordMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 告警记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:15:22
 * @create [2023-09-09 21:15:22] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleAlarmRecordManagerImpl extends SuperManagerImpl<RuleAlarmRecordMapper, RuleAlarmRecord> implements RuleAlarmRecordManager {

}


