package com.mqttsnet.thinglinks.plugin.controller;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.datascope.DataScopeHelper;
import com.mqttsnet.thinglinks.entity.plugin.PluginInstanceHeartbeat;
import com.mqttsnet.thinglinks.service.plugin.PluginInstanceHeartbeatService;
import com.mqttsnet.thinglinks.vo.query.plugin.PluginInstanceHeartbeatPageQuery;
import com.mqttsnet.thinglinks.vo.result.plugin.PluginInstanceHeartbeatResultVO;
import com.mqttsnet.thinglinks.vo.save.plugin.PluginInstanceHeartbeatSaveVO;
import com.mqttsnet.thinglinks.vo.update.plugin.PluginInstanceHeartbeatUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 插件实例心跳表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:31:15
 * @create [2024-08-27 16:31:15] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/pluginInstanceHeartbeat")
@Tag(name = "插件实例心跳")
public class PluginInstanceHeartbeatController extends SuperController<PluginInstanceHeartbeatService, Long, PluginInstanceHeartbeat, PluginInstanceHeartbeatSaveVO,
        PluginInstanceHeartbeatUpdateVO, PluginInstanceHeartbeatPageQuery, PluginInstanceHeartbeatResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    @Override
    public QueryWrap<PluginInstanceHeartbeat> handlerWrapper(PluginInstanceHeartbeat model, PageParams<PluginInstanceHeartbeatPageQuery> params) {
        QueryWrap<PluginInstanceHeartbeat> queryWrap = super.handlerWrapper(model, params);
        // 开启数据权限
        DataScopeHelper.startDataScope("plugin_instance_heartbeat");
        return queryWrap;
    }

}


