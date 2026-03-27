package com.mqttsnet.thinglinks.system.controller.system;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.system.entity.system.DefClient;
import com.mqttsnet.thinglinks.system.service.system.DefClientService;
import com.mqttsnet.thinglinks.system.vo.query.system.DefClientPageQuery;
import com.mqttsnet.thinglinks.system.vo.result.system.DefClientResultVO;
import com.mqttsnet.thinglinks.system.vo.save.system.DefClientSaveVO;
import com.mqttsnet.thinglinks.system.vo.update.system.DefClientUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 * 前端控制器
 * 客户端
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-13
 */
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/defClient")
@Tag(name = "客户端")
public class DefClientController extends SuperController<DefClientService, Long, DefClient, DefClientSaveVO, DefClientUpdateVO, DefClientPageQuery, DefClientResultVO> {

    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}
