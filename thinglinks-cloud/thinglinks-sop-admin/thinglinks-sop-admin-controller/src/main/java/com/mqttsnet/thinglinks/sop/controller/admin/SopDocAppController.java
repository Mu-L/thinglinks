package com.mqttsnet.thinglinks.sop.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.sop.entity.SopDocApp;
import com.mqttsnet.thinglinks.sop.service.SopDocAppService;
import com.mqttsnet.thinglinks.sop.vo.query.SopDocAppPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopDocAppResultVO;
import com.mqttsnet.thinglinks.sop.vo.save.SopDocAppSaveVO;
import com.mqttsnet.thinglinks.sop.vo.update.SopDocAppUpdateVO;

/**
 * <p>
 * 前端控制器
 * 文档应用
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/sopDocApp")
@Tag(name = "文档应用")
public class SopDocAppController extends SuperController<SopDocAppService, Long, SopDocApp, SopDocAppSaveVO, SopDocAppUpdateVO, SopDocAppPageQuery, SopDocAppResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }


}


