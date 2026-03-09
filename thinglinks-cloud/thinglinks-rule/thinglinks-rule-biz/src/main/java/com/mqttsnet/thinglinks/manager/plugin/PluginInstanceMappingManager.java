package com.mqttsnet.thinglinks.manager.plugin;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.entity.plugin.PluginInstanceMapping;
import com.mqttsnet.thinglinks.vo.query.plugin.PluginInstanceMappingPageQuery;
import com.mqttsnet.thinglinks.vo.save.plugin.PluginInstanceMappingSaveVO;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 插件与实例及端口管理表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:30:09
 * @create [2024-08-27 16:30:09] [mqttsnet]
 */
public interface PluginInstanceMappingManager extends SuperManager<PluginInstanceMapping> {

    /**
     * 插件实例端口列表
     *
     * @param query
     * @return {@link List<PluginInstanceMapping>}
     */
    List<PluginInstanceMapping> getPluginInstanceMappingList(PluginInstanceMappingPageQuery query);

    /**
     * 删除插件实例及端口
     *
     * @param pluginIdentification   插件标识
     * @param instanceIdentification 实例标识
     */
    void deletePluginInstanceMapping(String pluginIdentification, String instanceIdentification);

    /**
     * 保存插件实例映射
     * @param pluginInstanceMappingSaveVO 插件实例映射保存VO
     */
    void savePluginInstanceMapping(PluginInstanceMappingSaveVO pluginInstanceMappingSaveVO);
}


