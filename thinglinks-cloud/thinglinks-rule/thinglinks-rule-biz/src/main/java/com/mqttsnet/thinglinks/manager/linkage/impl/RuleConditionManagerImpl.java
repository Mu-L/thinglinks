package com.mqttsnet.thinglinks.manager.linkage.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.linkage.RuleCondition;
import com.mqttsnet.thinglinks.manager.linkage.RuleConditionManager;
import com.mqttsnet.thinglinks.mapper.linkage.RuleConditionMapper;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionPageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通用业务实现类
 * 规则条件表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:36:30
 * @create [2023-07-19 23:36:30] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DS(DsConstant.BASE_TENANT)
public class RuleConditionManagerImpl extends SuperManagerImpl<RuleConditionMapper, RuleCondition> implements RuleConditionManager {

    private final RuleConditionMapper ruleConditionMapper;


    /**
     * Finds a RuleCondition based on its condition identification.
     *
     * @param conditionIdentification The condition's identification string.
     * @return {@link RuleCondition} The rule condition associated with the given identification.
     */
    @Override
    public RuleCondition findOneByConditionIdentification(String conditionIdentification) {
        QueryWrap<RuleCondition> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(conditionIdentification), RuleCondition::getConditionIdentification, conditionIdentification);
        return ruleConditionMapper.selectOne(queryWrap);
    }

    /**
     * Retrieves a list of rule conditions based on the provided query criteria.
     *
     * @param query The query conditions encapsulated in {@link RuleConditionPageQuery}.
     * @return A list of {@link RuleCondition} entities that match the provided query.
     */
    @Override
    public List<RuleCondition> getRuleConditionList(RuleConditionPageQuery query) {
        QueryWrap<RuleCondition> queryWrap = new QueryWrap<>();

        queryWrap.lambda().eq(query.getRuleId() != null, RuleCondition::getRuleId, query.getRuleId());
        queryWrap.lambda().like(CharSequenceUtil.isNotBlank(query.getConditionIdentification()), RuleCondition::getConditionIdentification, query.getConditionIdentification());
        queryWrap.lambda().eq(query.getConditionType() != null, RuleCondition::getConditionType, query.getConditionType());
        queryWrap.lambda().like(CharSequenceUtil.isNotBlank(query.getConditionScheme()), RuleCondition::getConditionScheme, query.getConditionScheme());
        queryWrap.lambda().eq(query.getStatus() != null, RuleCondition::getStatus, query.getStatus());
        queryWrap.lambda().eq(query.getAntiShake() != null, RuleCondition::getAntiShake, query.getAntiShake());
        queryWrap.lambda().like(CharSequenceUtil.isNotBlank(query.getAntiShakeScheme()), RuleCondition::getAntiShakeScheme, query.getAntiShakeScheme());
        queryWrap.lambda().like(CharSequenceUtil.isNotBlank(query.getRemark()), RuleCondition::getRemark, query.getRemark());
        queryWrap.lambda().eq(query.getCreatedOrgId() != null, RuleCondition::getCreatedOrgId, query.getCreatedOrgId());

        return ruleConditionMapper.selectList(queryWrap);
    }


}


