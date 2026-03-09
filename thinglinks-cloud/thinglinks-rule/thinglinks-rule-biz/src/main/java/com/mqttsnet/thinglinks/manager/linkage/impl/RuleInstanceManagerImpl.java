package com.mqttsnet.thinglinks.manager.linkage.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.linkage.RuleInstance;
import com.mqttsnet.thinglinks.manager.linkage.RuleInstanceManager;
import com.mqttsnet.thinglinks.mapper.linkage.RuleInstanceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 规则实例表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-05 23:04:02
 * @create [2023-07-05 23:04:02] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DS(DsConstant.BASE_TENANT)
public class RuleInstanceManagerImpl extends SuperManagerImpl<RuleInstanceMapper, RuleInstance> implements RuleInstanceManager {

    private final RuleInstanceMapper ruleInstanceMapper;

    @Override
    public RuleInstance selectOneByFlowId(String flowId) {
        QueryWrap<RuleInstance> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(flowId), RuleInstance::getFlowId, flowId);
        return ruleInstanceMapper.selectOne(queryWrap);
    }
}


