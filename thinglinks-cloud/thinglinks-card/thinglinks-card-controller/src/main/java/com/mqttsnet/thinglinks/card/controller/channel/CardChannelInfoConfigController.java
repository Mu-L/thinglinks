package com.mqttsnet.thinglinks.card.controller.channel;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfoConfig;
import com.mqttsnet.thinglinks.card.service.channel.CardChannelInfoConfigService;
import com.mqttsnet.thinglinks.card.vo.query.channel.CardChannelInfoConfigPageQuery;
import com.mqttsnet.thinglinks.card.vo.result.channel.CardChannelInfoConfigResultVO;
import com.mqttsnet.thinglinks.card.vo.save.channel.CardChannelInfoConfigSaveVO;
import com.mqttsnet.thinglinks.card.vo.update.channel.CardChannelInfoConfigUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 物联卡渠道信息配置表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-27 00:06:12
 * @create [2024-06-27 00:06:12] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/cardChannelInfoConfig")
@Tag(name = "物联卡渠道信息配置")
public class CardChannelInfoConfigController extends SuperController<CardChannelInfoConfigService, Long, CardChannelInfoConfig, CardChannelInfoConfigSaveVO,
        CardChannelInfoConfigUpdateVO, CardChannelInfoConfigPageQuery, CardChannelInfoConfigResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


