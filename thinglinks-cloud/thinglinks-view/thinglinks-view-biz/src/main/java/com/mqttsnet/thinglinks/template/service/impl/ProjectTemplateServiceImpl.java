package com.mqttsnet.thinglinks.template.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.converter.Builder;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.project.entity.Project;
import com.mqttsnet.thinglinks.project.enumeration.ProjectStatusEnum;
import com.mqttsnet.thinglinks.project.vo.save.ProjectSaveVO;
import com.mqttsnet.thinglinks.template.entity.ProjectTemplate;
import com.mqttsnet.thinglinks.template.manager.ProjectTemplateManager;
import com.mqttsnet.thinglinks.template.service.ProjectTemplateService;
import com.mqttsnet.thinglinks.template.vo.result.ProjectTemplateDetailsResultVO;
import com.mqttsnet.thinglinks.template.vo.result.ProjectTemplateResultVO;
import com.mqttsnet.thinglinks.template.vo.save.ProjectTemplateSaveVO;
import com.mqttsnet.thinglinks.template.vo.update.ProjectTemplateUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 可视化模版模板表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:45:51
 * @create [2023-05-17 13:45:51] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectTemplateServiceImpl extends SuperServiceImpl<ProjectTemplateManager, Long, ProjectTemplate> implements ProjectTemplateService {

    /**
     * 保存模版
     *
     * @param saveVO 保存参数
     * @return {@link R}<{@link ProjectTemplateSaveVO}>
     */
    @Override
    public ProjectTemplateSaveVO saveVO(ProjectTemplateSaveVO saveVO) {
        log.info("saveTemplate saveVO:{}", saveVO);
        //校验参数
        checkedTemplateSaveVO(saveVO);
        //构建参数
        ProjectTemplate template = builderTemplateSaveVO(saveVO);
        //保存
        superManager.save(template);

        return saveVO;
    }


    /**
     * 更新模版
     *
     * @param updateVO 保存参数
     * @return {@link ProjectSaveVO}
     */
    @Override
    public ProjectTemplateUpdateVO updateTemplate(ProjectTemplateUpdateVO updateVO) {
        log.info("updateTemplate updateVO:{}", updateVO);
        //校验参数
        checkTemplateUpdateVO(updateVO);
        //构建参数
        Builder<ProjectTemplate> deviceBuilder = builderTemplateUpdateVO(updateVO);
        //更新
        superManager.updateById(deviceBuilder.with(ProjectTemplate::setId, updateVO.getId()).build());
        return updateVO;
    }


    /**
     * 更新模版状态
     *
     * @param templateId     id
     * @param templateStatus 状态
     * @return {@link Boolean} 是否成功 true:成功 false:失败
     */
    @Override
    public Boolean updateTemplateStatus(Long templateId, Integer templateStatus) {
        // Check if templateId and templateStatus are not null
        ArgumentAssert.notNull(templateId, "templateId cannot be null");
        ArgumentAssert.notNull(templateStatus, "templateStatus cannot be null");

        // Validate the templateStatus value
        if (!ProjectStatusEnum.ALL_STATE_COLLECTION.contains(templateStatus)) {
            throw BizException.wrap("Invalid templateStatus value");
        }

        // Get the template by templateId
        ProjectTemplate template = superManager.getById(templateId);
        if (template == null) {
            throw BizException.wrap("The projectTemplate does not exist");
        }

        // Check if the template status is already the same as the updated status
        if (templateStatus.equals(template.getStatus())) {
            throw BizException.wrap("The projectTemplate status is already set to the desired status");
        }

        // Update the template status and save the changes
        template.setStatus(templateStatus);
        return superManager.updateById(template);
    }


    /**
     * 删除模版
     *
     * @param id id
     * @return {@link Boolean}
     */
    @Override
    public Boolean deleteTemplate(Long id) {

        ArgumentAssert.notNull(id, "id Cannot be null");
        ProjectTemplate template = superManager.getById(id);
        if (null == template) {
            throw BizException.wrap("The projectTemplate does not exist");
        }

        return superManager.removeById(id);
    }

    /**
     * 通过模版标识获取模版信息
     *
     * @param templateIdentification 模版标识
     * @return {@link ProjectTemplateDetailsResultVO} 模版信息
     */
    @Override
    public ProjectTemplateDetailsResultVO getProjectTemplateDetailsByTemplateIdentification(String templateIdentification) {
        ProjectTemplate projectTemplate = superManager.getByTemplateIdentification(templateIdentification);
        if (null == projectTemplate) {
            throw BizException.wrap("The projectTemplate does not exist");
        }
        return BeanPlusUtil.toBean(projectTemplate, ProjectTemplateDetailsResultVO.class);
    }


    /**
     * 检查参数
     *
     * @param saveVO 保存参数
     */
    private void checkedTemplateSaveVO(ProjectTemplateSaveVO saveVO) {
        log.info("ProjectTemplateSaveVO saveVO:{}", saveVO);

        //校验模版名称是否存在
        ProjectTemplate template = superManager.getByTemplateName(saveVO.getTemplateName());

        if (null != template) {
            throw BizException.wrap("projectName exists already");
        }

    }

    /**
     * 构建器模版保存文件
     *
     * @param saveVO 保存参数
     * @return {@link Project}
     */
    private ProjectTemplate builderTemplateSaveVO(ProjectTemplateSaveVO saveVO) {

        //模版标识生成规则: 雪花算法生成
        saveVO.setTemplateIdentification(String.valueOf(SnowflakeIdUtil.nextId()));

        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());

        return BeanPlusUtil.copyProperties(saveVO, ProjectTemplate.class);
    }

    /**
     * 检查模版更新参数
     *
     * @param updateVO 更新参数
     */
    private void checkTemplateUpdateVO(ProjectTemplateUpdateVO updateVO) {
        // Check if updateVO is null
        if (updateVO == null) {
            throw BizException.wrap("updateVO cannot be null");
        }

        // Check if id is null
        ArgumentAssert.notNull(updateVO.getId(), "id cannot be null");

        // Check if projectTemplate with the given id exists
        ProjectTemplate template = superManager.getById(updateVO.getId());
        if (template == null) {
            throw BizException.wrap("ProjectTemplate does not exist");
        }

        // Check if templateName is not empty and if it exists for a different template
        if (StringUtils.isNotEmpty(updateVO.getTemplateName())) {
            ProjectTemplate existingTemplate = superManager.getByTemplateName(updateVO.getTemplateName());
            if (existingTemplate != null && !updateVO.getId().equals(existingTemplate.getId())) {
                throw BizException.wrap("TemplateName already exists");
            }
        }
    }


    /**
     * 构建模版
     *
     * @param updateVO 更新参数
     * @return {@link Builder}<{@link Project}>
     */
    private Builder<ProjectTemplate> builderTemplateUpdateVO(ProjectTemplateUpdateVO updateVO) {

        return Builder.of(ProjectTemplate::new)
                .with(ProjectTemplate::setTemplateName, updateVO.getTemplateName())
                .with(ProjectTemplate::setIndexImageId, updateVO.getIndexImageId())
                .with(ProjectTemplate::setContent, updateVO.getContent())
                .with(ProjectTemplate::setRemark, updateVO.getRemark())
                .with(ProjectTemplate::setStatus, updateVO.getStatus())
                .with(ProjectTemplate::setCreatedOrgId, ContextUtil.getCurrentDeptId());

    }
}


