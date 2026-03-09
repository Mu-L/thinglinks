package com.mqttsnet.thinglinks.template.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.template.entity.ProjectTemplate;
import com.mqttsnet.thinglinks.template.manager.ProjectTemplateManager;
import com.mqttsnet.thinglinks.template.mapper.ProjectTemplateMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 可视化项目模板表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:45:51
 * @create [2023-05-17 13:45:51] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectTemplateManagerImpl extends SuperManagerImpl<ProjectTemplateMapper, ProjectTemplate> implements ProjectTemplateManager {


    private final ProjectTemplateMapper templateMapper;

    /**
     * 按模型名称获取模版详细信息
     *
     * @param templateName 模版名称
     * @return {@link ProjectTemplate} 项目模版
     */
    @Override
    public ProjectTemplate getByTemplateName(String templateName) {

        QueryWrapper<ProjectTemplate> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(ProjectTemplate::getTemplateName, templateName);
        return templateMapper.selectOne(queryWrapper);
    }

    /**
     * 按模型标识获取模版详细信息
     *
     * @param templateIdentification 模版标识
     * @return {@link ProjectTemplate} 项目模版
     */
    @Override
    public ProjectTemplate getByTemplateIdentification(String templateIdentification) {
        return templateMapper.getByTemplateIdentification(templateIdentification);
    }
}


