package com.mqttsnet.thinglinks.ota.controller;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.datascope.DataScopeHelper;
import com.mqttsnet.thinglinks.ota.entity.OtaUpgradeTargets;
import com.mqttsnet.thinglinks.ota.service.OtaUpgradeTargetsService;
import com.mqttsnet.thinglinks.ota.vo.query.OtaUpgradeTargetsPageQuery;
import com.mqttsnet.thinglinks.ota.vo.result.OtaUpgradeTargetsResultVO;
import com.mqttsnet.thinglinks.ota.vo.save.OtaUpgradeTargetsSaveVO;
import com.mqttsnet.thinglinks.ota.vo.update.OtaUpgradeTargetsUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * OTA升级目标
 * </p>
 *
 * @author mqttsnet
 * @date 2025-10-19 16:28:50
 * @create [2025-10-19 16:28:50] [mqttsnet] [代码生成器生成]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/otaUpgradeTargets")
@Tag(name = "OTA升级目标")
public class OtaUpgradeTargetsController extends SuperController<OtaUpgradeTargetsService, Long, OtaUpgradeTargets
        , OtaUpgradeTargetsSaveVO, OtaUpgradeTargetsUpdateVO, OtaUpgradeTargetsPageQuery, OtaUpgradeTargetsResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    @Override
    public QueryWrap<OtaUpgradeTargets> handlerWrapper(OtaUpgradeTargets model, PageParams<OtaUpgradeTargetsPageQuery> params) {
        QueryWrap<OtaUpgradeTargets> queryWrap = super.handlerWrapper(model, params);
        // 开启数据权限
        DataScopeHelper.startDataScope("ota_upgrade_targets");
        return queryWrap;
    }


}


