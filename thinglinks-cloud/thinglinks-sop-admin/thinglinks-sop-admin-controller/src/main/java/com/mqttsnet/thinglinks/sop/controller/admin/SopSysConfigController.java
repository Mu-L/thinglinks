package com.mqttsnet.thinglinks.sop.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.sop.entity.SopSysConfig;
import com.mqttsnet.thinglinks.sop.service.SopSysConfigService;
import com.mqttsnet.thinglinks.sop.vo.query.SopSysConfigPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopSysConfigResultVO;
import com.mqttsnet.thinglinks.sop.vo.save.SopSysConfigSaveVO;
import com.mqttsnet.thinglinks.sop.vo.update.SopSysConfigUpdateVO;

/**
 * <p>
 * 前端控制器
 * 系统配置表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:44
 *
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/sopSysConfig")
@Tag(name = "系统配置表")
public class SopSysConfigController extends SuperController<SopSysConfigService, Long, SopSysConfig, SopSysConfigSaveVO, SopSysConfigUpdateVO, SopSysConfigPageQuery, SopSysConfigResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


