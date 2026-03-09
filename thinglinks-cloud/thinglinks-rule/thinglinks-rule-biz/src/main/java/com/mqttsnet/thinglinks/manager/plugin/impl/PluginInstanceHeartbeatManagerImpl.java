package com.mqttsnet.thinglinks.manager.plugin.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.entity.plugin.PluginInstanceHeartbeat;
import com.mqttsnet.thinglinks.manager.plugin.PluginInstanceHeartbeatManager;
import com.mqttsnet.thinglinks.mapper.plugin.PluginInstanceHeartbeatMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 插件实例心跳表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:31:15
 * @create [2024-08-27 16:31:15] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PluginInstanceHeartbeatManagerImpl extends SuperManagerImpl<PluginInstanceHeartbeatMapper, PluginInstanceHeartbeat> implements PluginInstanceHeartbeatManager {

}


