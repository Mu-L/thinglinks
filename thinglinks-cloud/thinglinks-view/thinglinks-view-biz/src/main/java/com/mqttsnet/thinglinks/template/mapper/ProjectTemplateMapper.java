package com.mqttsnet.thinglinks.template.mapper;

import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.template.entity.ProjectTemplate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 可视化项目模板表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:45:51
 * @create [2023-05-17 13:45:51] [mqttsnet]
 */
@Repository
public interface ProjectTemplateMapper extends SuperMapper<ProjectTemplate> {

    /**
     * 按模型标识获取模版详细信息
     *
     * @param templateIdentification 模版标识
     * @return {@link ProjectTemplate} 项目模版
     */
    ProjectTemplate getByTemplateIdentification(@Param("templateIdentification")  String templateIdentification);
}


