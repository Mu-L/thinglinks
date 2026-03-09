package com.mqttsnet.thinglinks.project.service.impl;

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
import com.mqttsnet.thinglinks.project.manager.ProjectManager;
import com.mqttsnet.thinglinks.project.service.ProjectService;
import com.mqttsnet.thinglinks.project.vo.result.ProjectDetailsResultVO;
import com.mqttsnet.thinglinks.project.vo.save.ProjectSaveVO;
import com.mqttsnet.thinglinks.project.vo.update.ProjectUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 可视化项目表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:47:04
 * @create [2023-05-17 13:47:04] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class ProjectServiceImpl extends SuperServiceImpl<ProjectManager, Long, Project> implements ProjectService {


    /**
     * 保存项目
     *
     * @param saveVO 保存参数
     * @return {@link R}<{@link ProjectSaveVO}>
     */
    @Override
    public ProjectSaveVO saveVO(ProjectSaveVO saveVO) {
        log.info("saveProject saveVO:{}", saveVO);
        //校验参数
        checkedProjectSaveVO(saveVO);
        //构建参数
        Project project = builderProjectSaveVO(saveVO);
        //保存
        superManager.save(project);

        return BeanPlusUtil.toBean(project, ProjectSaveVO.class);
    }


    /**
     * 更新项目
     *
     * @param updateVO 保存参数
     * @return {@link ProjectSaveVO}
     */
    @Override
    public ProjectUpdateVO updateProject(ProjectUpdateVO updateVO) {
        log.info("updateProject updateVO:{}", updateVO);
        //校验参数
        checkProjectUpdateVO(updateVO);
        //构建参数
        Builder<Project> deviceBuilder = builderProjectUpdateVO(updateVO);
        //更新
        superManager.updateById(deviceBuilder.with(Project::setId, updateVO.getId()).build());
        return updateVO;
    }


    /**
     * 更新项目状态
     *
     * @param projectId     id
     * @param projectStatus 状态
     * @return {@link Boolean} true:更新成功
     */
    @Override
    public Boolean updateProjectStatus(Long projectId, Integer projectStatus) {
        // Check if projectId and projectStatus are not null
        ArgumentAssert.notNull(projectId, "projectId cannot be null");
        ArgumentAssert.notNull(projectStatus, "projectStatus cannot be null");

        // Validate the projectStatus value
        if (!ProjectStatusEnum.ALL_STATE_COLLECTION.contains(projectStatus)) {
            throw BizException.wrap("Invalid projectStatus value");
        }

        // Get the project by projectId
        Project project = superManager.getById(projectId);
        if (project == null) {
            throw BizException.wrap("The project does not exist");
        }

        // Check if the project status is already the same as the updated status
        if (projectStatus.equals(project.getStatus())) {
            throw BizException.wrap("The project status is already set to the desired status");
        }

        // Update the project status and save the changes
        project.setStatus(projectStatus);
        return superManager.updateById(project);
    }


    /**
     * 删除项目
     *
     * @param id id
     * @return {@link Boolean}
     */
    @Override
    public Boolean deleteProject(Long id) {
        ArgumentAssert.notNull(id, "id Cannot be null");
        Project project = superManager.getById(id);
        if (null == project) {
            throw BizException.wrap("The project does not exist");
        }

        return superManager.removeById(id);
    }

    /**
     * 根据项目标识获取项目详情
     *
     * @param projectIdentification 项目标识
     * @return {@link ProjectDetailsResultVO} 项目详情
     */
    @Override
    public ProjectDetailsResultVO getProjectDetailsByProjectIdentification(String projectIdentification) {
        Project project = superManager.getByProjectIdentification(projectIdentification);
        if (null == project) {
            throw BizException.wrap("The project does not exist");
        }
        return BeanPlusUtil.toBean(project, ProjectDetailsResultVO.class);
    }


    /**
     * 检查参数
     *
     * @param saveVO 保存参数
     */
    private void checkedProjectSaveVO(ProjectSaveVO saveVO) {

        //校验项目名称是否存在
        Project project = superManager.getByProjectName(saveVO.getProjectName());

        if (null != project) {
            throw BizException.wrap("projectName exists already");
        }

    }

    /**
     * 构建参数
     *
     * @param saveVO 保存参数
     * @return {@link Project}
     */
    private Project builderProjectSaveVO(ProjectSaveVO saveVO) {

        //项目标识生成规则: 雪花算法生成
        saveVO.setProjectIdentification(String.valueOf(SnowflakeIdUtil.nextId()));

        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());

        return BeanPlusUtil.copyProperties(saveVO, Project.class);
    }

    /**
     * 检查项目更新参数
     *
     * @param updateVO 更新参数
     */
    private void checkProjectUpdateVO(ProjectUpdateVO updateVO) {
        // Check if updateVO is null
        if (updateVO == null) {
            throw BizException.wrap("updateVO cannot be null");
        }

        // Check if id is null
        ArgumentAssert.notNull(updateVO.getId(), "id cannot be null");

        // Check if project with the given id exists
        Project project = superManager.getById(updateVO.getId());
        if (project == null) {
            throw BizException.wrap("Project does not exist");
        }

        // Check if projectName is not empty and if it exists for a different project
        if (StringUtils.isNotEmpty(updateVO.getProjectName())) {
            Project existingProject = superManager.getByProjectName(updateVO.getProjectName());
            if (existingProject != null && !updateVO.getId().equals(existingProject.getId())) {
                throw BizException.wrap("Project name already exists");
            }
        }
    }


    /**
     * 构建项目
     *
     * @param updateVO 更新参数
     * @return {@link Builder}<{@link Project}>
     */
    private Builder<Project> builderProjectUpdateVO(ProjectUpdateVO updateVO) {

        return Builder.of(Project::new)
                .with(Project::setProjectName, updateVO.getProjectName())
                .with(Project::setIndexImageId, updateVO.getIndexImageId())
                .with(Project::setContent, updateVO.getContent())
                .with(Project::setRemark, updateVO.getRemark())
                .with(Project::setStatus, updateVO.getStatus())
                .with(Project::setCreatedOrgId, ContextUtil.getCurrentDeptId());

    }
}


