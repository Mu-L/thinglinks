package com.mqttsnet.thinglinks.service.plugin.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.plugin.PluginInstanceMapping;
import com.mqttsnet.thinglinks.manager.plugin.PluginInstanceMappingManager;
import com.mqttsnet.thinglinks.service.plugin.PluginInstanceMappingService;
import com.mqttsnet.thinglinks.vo.query.plugin.PluginInstanceMappingPageQuery;
import com.mqttsnet.thinglinks.vo.result.plugin.PluginInstanceMappingResultVO;
import com.mqttsnet.thinglinks.vo.save.plugin.PluginInstanceMappingSaveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 插件与实例及端口管理表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:30:09
 * @create [2024-08-27 16:30:09] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class PluginInstanceMappingServiceImpl extends SuperServiceImpl<PluginInstanceMappingManager, Long, PluginInstanceMapping> implements PluginInstanceMappingService {


    @Override
    public List<PluginInstanceMappingResultVO> getPluginInstanceMappingResultVOList(PluginInstanceMappingPageQuery query) {
        return BeanPlusUtil.toBeanList(superManager.getPluginInstanceMappingList(query), PluginInstanceMappingResultVO.class);
    }

    @Override
    public void deletePluginInstanceMapping(String pluginIdentification, String instanceIdentification) {
        superManager.deletePluginInstanceMapping(pluginIdentification, instanceIdentification);
    }

    @Override
    public void savePluginInstanceMapping(PluginInstanceMappingSaveVO saveVO) {
        superManager.savePluginInstanceMapping(saveVO);
    }

    @Override
    public boolean isPluginInstalledOnInstance(String pluginIdentification, String instanceIdentification) {
        PluginInstanceMappingPageQuery query = new PluginInstanceMappingPageQuery()
                .setPluginIdentification(pluginIdentification)
                .setInstanceIdentification(instanceIdentification);
        List<PluginInstanceMapping> pluginInstanceMappingList = superManager.getPluginInstanceMappingList(query);
        return CollUtil.isNotEmpty(pluginInstanceMappingList);
    }

}


