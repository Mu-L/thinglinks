package com.mqttsnet.thinglinks.test.controller;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.test.entity.DefGenTestSimple;
import com.mqttsnet.thinglinks.test.service.DefGenTestSimpleService;
import com.mqttsnet.thinglinks.test.vo.query.DefGenTestSimplePageQuery;
import com.mqttsnet.thinglinks.test.vo.result.DefGenTestSimpleResultVO;
import com.mqttsnet.thinglinks.test.vo.save.DefGenTestSimpleSaveVO;
import com.mqttsnet.thinglinks.test.vo.update.DefGenTestSimpleUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 测试单表
 * </p>
 *
 * @author mqttsnet
 * @date 2022-04-15 15:36:45
 * @create [2022-04-15 15:36:45] [mqttsnet] 
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/defGenTestSimple")
@Tag(name = "测试单表")
public class DefGenTestSimpleController extends SuperController<DefGenTestSimpleService, Long, DefGenTestSimple, DefGenTestSimpleSaveVO,
        DefGenTestSimpleUpdateVO, DefGenTestSimplePageQuery, DefGenTestSimpleResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


