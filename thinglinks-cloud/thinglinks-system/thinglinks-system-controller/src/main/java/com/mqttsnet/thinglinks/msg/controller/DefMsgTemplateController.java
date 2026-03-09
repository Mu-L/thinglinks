package com.mqttsnet.thinglinks.msg.controller;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.msg.entity.DefMsgTemplate;
import com.mqttsnet.thinglinks.msg.service.DefMsgTemplateService;
import com.mqttsnet.thinglinks.msg.vo.query.DefMsgTemplatePageQuery;
import com.mqttsnet.thinglinks.msg.vo.result.DefMsgTemplateResultVO;
import com.mqttsnet.thinglinks.msg.vo.save.DefMsgTemplateSaveVO;
import com.mqttsnet.thinglinks.msg.vo.update.DefMsgTemplateUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static com.mqttsnet.thinglinks.common.constant.SwaggerConstants.DATA_TYPE_LONG;
import static com.mqttsnet.thinglinks.common.constant.SwaggerConstants.DATA_TYPE_STRING;

/**
 * <p>
 * 前端控制器
 * 消息模板
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-04 15:51:37
 * @create [2022-07-04 15:51:37] [mqttsnet] 
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/defMsgTemplate")
@Tag(name = "消息模板")
public class DefMsgTemplateController extends SuperController<DefMsgTemplateService, Long, DefMsgTemplate, DefMsgTemplateSaveVO,
        DefMsgTemplateUpdateVO, DefMsgTemplatePageQuery, DefMsgTemplateResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    @Parameters({
            @Parameter(name = "id", description = "ID", schema = @Schema(type = DATA_TYPE_LONG), in = ParameterIn.QUERY),
            @Parameter(name = "code", description = "编码", schema = @Schema(type = DATA_TYPE_STRING), in = ParameterIn.QUERY),
    })
    @Operation(summary = "检测资源编码是否可用", description = "检测资源编码是否可用")
    @GetMapping("/check")
    public R<Boolean> check(@RequestParam(required = false) Long id, @RequestParam String code) {
        return success(superService.check(code, id));
    }
}


