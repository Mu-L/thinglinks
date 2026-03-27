package com.mqttsnet.thinglinks.generator.controller;

import com.mqttsnet.basic.annotation.log.WebLog;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.generator.entity.DefGenTableColumn;
import com.mqttsnet.thinglinks.generator.service.DefGenTableColumnService;
import com.mqttsnet.thinglinks.generator.vo.query.DefGenTableColumnPageQuery;
import com.mqttsnet.thinglinks.generator.vo.result.DefGenTableColumnResultVO;
import com.mqttsnet.thinglinks.generator.vo.save.DefGenTableColumnSaveVO;
import com.mqttsnet.thinglinks.generator.vo.update.DefGenTableColumnUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 代码生成字段
 * </p>
 *
 * @author mqttsnet
 * @date 2022-03-24 14:13:43
 * @create [2022-03-24 14:13:43] [mqttsnet] 
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/defGenTableColumn")
@Tag(name = "代码生成字段")
public class DefGenTableColumnController extends SuperController<DefGenTableColumnService, Long, DefGenTableColumn, DefGenTableColumnSaveVO,
        DefGenTableColumnUpdateVO, DefGenTableColumnPageQuery, DefGenTableColumnResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }


    @Operation(summary = "同步字段结构", description = "同步字段结构")
    @PostMapping(value = "/syncField")
    @WebLog(value = "同步字段结构")
    public R<Boolean> syncField(@RequestParam Long tableId, @RequestParam Long id) {
        return R.success(superService.syncField(tableId, id));
    }
}


