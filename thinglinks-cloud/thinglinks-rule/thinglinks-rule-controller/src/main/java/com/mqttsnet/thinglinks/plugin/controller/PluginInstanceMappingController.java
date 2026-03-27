package com.mqttsnet.thinglinks.plugin.controller;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.entity.plugin.PluginInstanceMapping;
import com.mqttsnet.thinglinks.service.plugin.PluginInstanceMappingService;
import com.mqttsnet.thinglinks.vo.query.plugin.PluginInstanceMappingPageQuery;
import com.mqttsnet.thinglinks.vo.result.plugin.PluginInstanceMappingResultVO;
import com.mqttsnet.thinglinks.vo.save.plugin.PluginInstanceMappingSaveVO;
import com.mqttsnet.thinglinks.vo.update.plugin.PluginInstanceMappingUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 插件与实例及端口管理
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:30:09
 * @create [2024-08-27 16:30:09] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/pluginInstanceMapping")
@Tag(name = "插件实例端口管理")
public class PluginInstanceMappingController extends SuperController<PluginInstanceMappingService, Long, PluginInstanceMapping, PluginInstanceMappingSaveVO,
        PluginInstanceMappingUpdateVO, PluginInstanceMappingPageQuery, PluginInstanceMappingResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


