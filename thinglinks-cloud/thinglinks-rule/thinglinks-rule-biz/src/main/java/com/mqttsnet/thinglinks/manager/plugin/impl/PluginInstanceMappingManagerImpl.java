package com.mqttsnet.thinglinks.manager.plugin.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.entity.plugin.PluginInstanceMapping;
import com.mqttsnet.thinglinks.manager.plugin.PluginInstanceMappingManager;
import com.mqttsnet.thinglinks.mapper.plugin.PluginInstanceMappingMapper;
import com.mqttsnet.thinglinks.vo.query.plugin.PluginInstanceMappingPageQuery;
import com.mqttsnet.thinglinks.vo.save.plugin.PluginInstanceMappingSaveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通用业务实现类
 * 插件与实例及端口管理表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:30:09
 * @create [2024-08-27 16:30:09] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class PluginInstanceMappingManagerImpl extends SuperManagerImpl<PluginInstanceMappingMapper, PluginInstanceMapping> implements PluginInstanceMappingManager {

    private final PluginInstanceMappingMapper pluginInstanceMappingMapper;

    @Override
    public List<PluginInstanceMapping> getPluginInstanceMappingList(PluginInstanceMappingPageQuery query) {
        QueryWrapper<PluginInstanceMapping> queryWrapper = new QueryWrapper<>();

        // 拼接查询条件
        queryWrapper.lambda().eq(query.getId() != null, PluginInstanceMapping::getId, query.getId());
        queryWrapper.lambda().eq(StrUtil.isNotBlank(query.getPluginIdentification()), PluginInstanceMapping::getPluginIdentification, query.getPluginIdentification());
        queryWrapper.lambda().eq(StrUtil.isNotBlank(query.getInstanceIdentification()), PluginInstanceMapping::getInstanceIdentification, query.getInstanceIdentification());
        queryWrapper.lambda().eq(StrUtil.isNotBlank(query.getPortType()), PluginInstanceMapping::getPortType, query.getPortType());
        queryWrapper.lambda().eq(query.getStatus() != null, PluginInstanceMapping::getStatus, query.getStatus());

        return pluginInstanceMappingMapper.selectList(queryWrapper);
    }

    @Override
    public void deletePluginInstanceMapping(String pluginIdentification, String instanceIdentification) {
        QueryWrapper<PluginInstanceMapping> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(PluginInstanceMapping::getPluginIdentification, pluginIdentification);
        queryWrapper.lambda().eq(PluginInstanceMapping::getInstanceIdentification, instanceIdentification);
        pluginInstanceMappingMapper.delete(queryWrapper);
    }

    @Override
    public void savePluginInstanceMapping(PluginInstanceMappingSaveVO pluginInstanceMappingSaveVO) {
        PluginInstanceMapping pluginInstanceMapping = BeanPlusUtil.toBeanIgnoreError(pluginInstanceMappingSaveVO, PluginInstanceMapping.class);
        pluginInstanceMappingMapper.insert(pluginInstanceMapping);
    }
}


