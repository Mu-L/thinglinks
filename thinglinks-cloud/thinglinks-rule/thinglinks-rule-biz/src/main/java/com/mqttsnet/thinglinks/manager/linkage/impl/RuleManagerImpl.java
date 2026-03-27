package com.mqttsnet.thinglinks.manager.linkage.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.linkage.Rule;
import com.mqttsnet.thinglinks.manager.linkage.RuleManager;
import com.mqttsnet.thinglinks.mapper.linkage.RuleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 规则信息
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:20:14
 * @create [2023-07-19 23:20:14] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
@DS(DsConstant.BASE_TENANT)
public class RuleManagerImpl extends SuperManagerImpl<RuleMapper, Rule> implements RuleManager {

    private final RuleMapper ruleMapper;

    /**
     * 根据规则标识查询规则信息
     *
     * @param ruleIdentification 规则标识
     * @return {@link Rule} 规则信息
     */
    @Override
    public Rule findOneByRuleIdentification(String ruleIdentification) {
        QueryWrap<Rule> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(ruleIdentification), Rule::getRuleIdentification, ruleIdentification);
        return ruleMapper.selectOne(queryWrap);
    }
}


