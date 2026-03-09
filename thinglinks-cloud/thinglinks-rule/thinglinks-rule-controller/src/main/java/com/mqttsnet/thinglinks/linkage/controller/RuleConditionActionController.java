package com.mqttsnet.thinglinks.linkage.controller;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.entity.linkage.RuleConditionAction;
import com.mqttsnet.thinglinks.service.linkage.RuleConditionActionService;
import com.mqttsnet.thinglinks.vo.query.linkage.RuleConditionActionPageQuery;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleConditionActionResultVO;
import com.mqttsnet.thinglinks.vo.save.linkage.RuleConditionActionSaveVO;
import com.mqttsnet.thinglinks.vo.update.linkage.RuleConditionActionUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 规则条件动作表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-07-19 23:24:37
 * @create [2023-07-19 23:24:37] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/ruleConditionAction")
@Tag(name = "规则条件动作")
public class RuleConditionActionController extends SuperController<RuleConditionActionService, Long, RuleConditionAction, RuleConditionActionSaveVO,
        RuleConditionActionUpdateVO, RuleConditionActionPageQuery, RuleConditionActionResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


