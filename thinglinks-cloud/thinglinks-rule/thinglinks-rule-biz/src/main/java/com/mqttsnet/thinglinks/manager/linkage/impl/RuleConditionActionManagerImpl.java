package com.mqttsnet.thinglinks.manager.linkage.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.linkage.RuleConditionAction;
import com.mqttsnet.thinglinks.manager.linkage.RuleConditionActionManager;
import com.mqttsnet.thinglinks.mapper.linkage.RuleConditionActionMapper;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionActionPageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通用业务实现类
 * 规则条件动作表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:24:37
 * @create [2023-07-19 23:24:37] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DS(DsConstant.BASE_TENANT)
public class RuleConditionActionManagerImpl extends SuperManagerImpl<RuleConditionActionMapper, RuleConditionAction> implements RuleConditionActionManager {

    private final RuleConditionActionMapper ruleConditionMapper;

    /**
     * Retrieves a list of rule condition actions based on the provided query criteria.
     *
     * @param query The query conditions encapsulated in {@link RuleConditionActionPageQuery}.
     * @return A list of {@link RuleConditionAction} entities that match the provided query.
     */
    @Override
    public List<RuleConditionAction> getRuleConditionActionList(RuleConditionActionPageQuery query) {
        QueryWrap<RuleConditionAction> queryWrap = new QueryWrap<>();

        queryWrap.lambda().eq(query.getId() != null, RuleConditionAction::getId, query.getId());
        queryWrap.lambda().eq(query.getRuleConditionId() != null, RuleConditionAction::getRuleConditionId, query.getRuleConditionId());
        queryWrap.lambda().eq(query.getActionType() != null, RuleConditionAction::getActionType, query.getActionType());
        queryWrap.lambda().like(CharSequenceUtil.isNotBlank(query.getActionContent()), RuleConditionAction::getActionContent, query.getActionContent());
        queryWrap.lambda().like(CharSequenceUtil.isNotBlank(query.getRemark()), RuleConditionAction::getRemark, query.getRemark());
        queryWrap.lambda().eq(query.getCreatedOrgId() != null, RuleConditionAction::getCreatedOrgId, query.getCreatedOrgId());

        return ruleConditionMapper.selectList(queryWrap);
    }
}


