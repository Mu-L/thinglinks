package com.mqttsnet.thinglinks.service.linkage.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.linkage.RuleConditionAction;
import com.mqttsnet.thinglinks.manager.linkage.RuleConditionActionManager;
import com.mqttsnet.thinglinks.service.linkage.RuleConditionActionService;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionActionPageQuery;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleConditionActionResultVO;
import com.mqttsnet.thinglinks.vo.save.linkage.RuleConditionActionSaveVO;
import com.mqttsnet.thinglinks.vo.update.linkage.RuleConditionActionUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 规则条件动作表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:24:37
 * @create [2023-07-19 23:24:37] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class RuleConditionActionServiceImpl extends SuperServiceImpl<RuleConditionActionManager, Long, RuleConditionAction> implements RuleConditionActionService {

    /**
     * 保存规则规则条件动作表
     *
     * @param saveVO
     * @return
     */
    @Override
    public RuleConditionAction saveRuleConditionAction(RuleConditionActionSaveVO saveVO) {
        log.info("saveRuleConditionAction saveVO:{}", saveVO);
        //校验参数
        checkedRuleConditionActionSaveVO(saveVO);
        //构建参数
        RuleConditionAction ruleConditionAction = builderRuleConditionActionSaveVO(saveVO);
        //更新
        superManager.save(ruleConditionAction);
        return ruleConditionAction;
    }

    /**
     * 修改规则规则条件动作表
     *
     * @param updateVO
     * @return
     */
    @Override
    public RuleConditionAction updateRuleConditionAction(RuleConditionActionUpdateVO updateVO) {
        log.info("updateRuleConditionAction updateVO:{}", updateVO);
        //校验参数
        checkedRuleConditionActionUpdateVO(updateVO);
        //构建参数
        RuleConditionAction ruleConditionAction = BeanPlusUtil.toBeanIgnoreError(updateVO, RuleConditionAction.class);
        //更新
        superManager.updateById(ruleConditionAction);
        return ruleConditionAction;
    }

    /**
     * 删除规则规则条件动作表
     *
     * @param id
     * @return
     */
    @Override
    public Boolean deleteRuleConditionAction(Long id) {
        ArgumentAssert.notNull(id, "id Cannot be null");
        RuleConditionAction ruleConditionAction = superManager.getById(id);
        if (null == ruleConditionAction) {
            throw BizException.wrap("The RuleConditionAction does not exist");
        }
        return superManager.removeById(id);
    }

    /**
     * Retrieves a list of rule condition actions based on the provided query criteria.
     *
     * @param query The query conditions encapsulated in {@link RuleConditionActionPageQuery}.
     * @return A list of {@link RuleConditionActionResultVO} entities that match the provided query.
     */
    @Override
    public List<RuleConditionActionResultVO> getRuleConditionActionList(RuleConditionActionPageQuery query) {
        List<RuleConditionAction> ruleConditionActionList = superManager.getRuleConditionActionList(query);
        return BeanPlusUtil.toBeanList(ruleConditionActionList, RuleConditionActionResultVO.class);
    }

    /**
     * 新增 校验参数
     *
     * @param saveVO
     */
    private void checkedRuleConditionActionSaveVO(RuleConditionActionSaveVO saveVO) {
        ArgumentAssert.notNull(saveVO.getRuleConditionId(), "RuleConditionId Cannot be null");
    }

    /**
     * 新增 构建参数
     *
     * @param saveVO
     * @return
     */
    private RuleConditionAction builderRuleConditionActionSaveVO(RuleConditionActionSaveVO saveVO) {
        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return BeanPlusUtil.toBeanIgnoreError(saveVO, RuleConditionAction.class);
    }

    /**
     * 修改 校验参数
     *
     * @param updateVO
     */
    private void checkedRuleConditionActionUpdateVO(RuleConditionActionUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "id Cannot be null");
    }

}


