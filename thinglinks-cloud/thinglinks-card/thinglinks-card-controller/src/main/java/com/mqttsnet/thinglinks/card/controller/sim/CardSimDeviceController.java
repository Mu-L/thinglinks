package com.mqttsnet.thinglinks.card.controller.sim;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.card.entity.sim.CardSimDevice;
import com.mqttsnet.thinglinks.card.service.sim.CardSimDeviceService;
import com.mqttsnet.thinglinks.card.vo.query.sim.CardSimDevicePageQuery;
import com.mqttsnet.thinglinks.card.vo.result.sim.CardSimDeviceResultVO;
import com.mqttsnet.thinglinks.card.vo.save.sim.CardSimDeviceSaveVO;
import com.mqttsnet.thinglinks.card.vo.update.sim.CardSimDeviceUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 物联卡设备关系表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-27 00:10:22
 * @create [2024-06-27 00:10:22] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/cardSimDevice")
@Tag(name = "物联卡设备关系")
public class CardSimDeviceController extends SuperController<CardSimDeviceService, Long, CardSimDevice, CardSimDeviceSaveVO,
        CardSimDeviceUpdateVO, CardSimDevicePageQuery, CardSimDeviceResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


