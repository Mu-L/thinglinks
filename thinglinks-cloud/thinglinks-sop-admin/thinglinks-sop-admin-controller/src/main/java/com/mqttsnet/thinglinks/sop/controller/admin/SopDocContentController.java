package com.mqttsnet.thinglinks.sop.controller.admin;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.sop.entity.SopDocContent;
import com.mqttsnet.thinglinks.sop.service.SopDocContentService;
import com.mqttsnet.thinglinks.sop.vo.query.SopDocContentPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopDocContentResultVO;
import com.mqttsnet.thinglinks.sop.vo.save.SopDocContentSaveVO;
import com.mqttsnet.thinglinks.sop.vo.update.SopDocContentUpdateVO;

/**
 * <p>
 * 前端控制器
 * 文档内容
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
@RequestMapping("/sopDocContent")
@Tag(name = "文档内容")
public class SopDocContentController extends SuperController<SopDocContentService, Long, SopDocContent, SopDocContentSaveVO, SopDocContentUpdateVO, SopDocContentPageQuery, SopDocContentResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


