package com.mqttsnet.thinglinks.template.service;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.template.entity.ProjectTemplate;
import com.mqttsnet.thinglinks.template.vo.result.ProjectTemplateDetailsResultVO;
import com.mqttsnet.thinglinks.template.vo.result.ProjectTemplateResultVO;
import com.mqttsnet.thinglinks.template.vo.save.ProjectTemplateSaveVO;
import com.mqttsnet.thinglinks.template.vo.update.ProjectTemplateUpdateVO;


/**
 * <p>
 * 业务接口
 * 可视化模版模板表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:45:51
 * @create [2023-05-17 13:45:51] [mqttsnet]
 */
public interface ProjectTemplateService extends SuperService<Long, ProjectTemplate> {

    /**
     * 保存模版
     *
     * @param saveVO 保存参数
     * @return {@link R}<{@link ProjectTemplateSaveVO}>
     */
    ProjectTemplateSaveVO saveVO(ProjectTemplateSaveVO saveVO);


    /**
     * 更新模版
     *
     * @param updateVO 保存参数
     * @return {@link ProjectTemplateUpdateVO}
     */
    ProjectTemplateUpdateVO updateTemplate(ProjectTemplateUpdateVO updateVO);


    /**
     * 更新模版状态
     *
     * @param templateId     id
     * @param templateStatus 状态
     * @return {@link Boolean}
     */
    Boolean updateTemplateStatus(Long templateId, Integer templateStatus);


    /**
     * 删除模版
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean deleteTemplate(Long id);

    /**
     * 通过模版标识获取模版信息
     *
     * @param templateIdentification 模版标识
     * @return {@link ProjectTemplateDetailsResultVO} 模版信息
     */
    ProjectTemplateDetailsResultVO getProjectTemplateDetailsByTemplateIdentification(String templateIdentification);
}


