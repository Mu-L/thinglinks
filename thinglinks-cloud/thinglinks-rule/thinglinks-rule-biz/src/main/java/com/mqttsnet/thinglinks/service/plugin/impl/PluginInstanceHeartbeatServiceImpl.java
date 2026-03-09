package com.mqttsnet.thinglinks.service.plugin.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.plugin.PluginInstanceHeartbeat;
import com.mqttsnet.thinglinks.manager.plugin.PluginInstanceHeartbeatManager;
import com.mqttsnet.thinglinks.service.plugin.PluginInstanceHeartbeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 插件实例心跳表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:31:15
 * @create [2024-08-27 16:31:15] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class PluginInstanceHeartbeatServiceImpl extends SuperServiceImpl<PluginInstanceHeartbeatManager, Long, PluginInstanceHeartbeat> implements PluginInstanceHeartbeatService {


}


