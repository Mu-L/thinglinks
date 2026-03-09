package com.mqttsnet.thinglinks.template.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.template.entity.ProjectTemplate;

/**
 * <p>
 * 通用业务接口
 * 可视化项目模板表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:45:51
 * @create [2023-05-17 13:45:51] [mqttsnet]
 */
public interface ProjectTemplateManager extends SuperManager<ProjectTemplate> {

    /**
     * 按模型名称获取模版详细信息
     *
     * @param templateName 模版名称
     * @return {@link ProjectTemplate} 项目模版
     */
    ProjectTemplate getByTemplateName(String templateName);

    /**
     * 按模型标识获取模版详细信息
     *
     * @param templateIdentification 模版标识
     * @return {@link ProjectTemplate} 项目模版
     */
    ProjectTemplate getByTemplateIdentification(String templateIdentification);
}


