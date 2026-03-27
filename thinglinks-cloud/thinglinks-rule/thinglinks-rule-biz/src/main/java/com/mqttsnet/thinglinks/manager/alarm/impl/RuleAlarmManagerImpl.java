package com.mqttsnet.thinglinks.manager.alarm.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.text.CharSequenceUtil;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.entity.alarm.RuleAlarm;
import com.mqttsnet.thinglinks.manager.alarm.RuleAlarmManager;
import com.mqttsnet.thinglinks.mapper.alarm.RuleAlarmMapper;
import com.mqttsnet.thinglinks.vo.query.alarm.RuleAlarmPageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通用业务实现类
 * 告警规则表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:14:37
 * @create [2023-09-09 21:14:37] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleAlarmManagerImpl extends SuperManagerImpl<RuleAlarmMapper, RuleAlarm> implements RuleAlarmManager {

    private final RuleAlarmMapper ruleAlarmMapper;

    /**
     * Retrieve a list of RuleAlarm based on the provided query.
     *
     * @param query Search parameters for filtering RuleAlarm.
     * @return List of RuleAlarm that match the provided criteria.
     */
    @Override
    public List<RuleAlarm> getRuleAlarmList(RuleAlarmPageQuery query) {
        QueryWrap<RuleAlarm> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(null != query.getStatus(), RuleAlarm::getStatus, query.getStatus());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getAlarmIdentification()), RuleAlarm::getAlarmIdentification, query.getAlarmIdentification());
        queryWrap.lambda().in(CollUtil.isNotEmpty(query.getAlarmIdentificationList()), RuleAlarm::getAlarmIdentification, query.getAlarmIdentificationList());
        return ruleAlarmMapper.selectList(queryWrap);
    }
}


