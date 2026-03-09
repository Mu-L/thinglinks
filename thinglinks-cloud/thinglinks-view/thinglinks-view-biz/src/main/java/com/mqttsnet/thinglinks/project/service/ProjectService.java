package com.mqttsnet.thinglinks.project.service;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.project.entity.Project;
import com.mqttsnet.thinglinks.project.vo.result.ProjectDetailsResultVO;
import com.mqttsnet.thinglinks.project.vo.save.ProjectSaveVO;
import com.mqttsnet.thinglinks.project.vo.update.ProjectUpdateVO;


/**
 * <p>
 * 业务接口
 * 可视化项目表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:47:04
 * @create [2023-05-17 13:47:04] [mqttsnet]
 */
public interface ProjectService extends SuperService<Long, Project> {


    /**
     * 保存项目
     *
     * @param saveVO 保存参数
     * @return {@link R}<{@link ProjectSaveVO}>
     */
    ProjectSaveVO saveVO(ProjectSaveVO saveVO);


    /**
     * 更新项目
     *
     * @param updateVO 保存参数
     * @return {@link ProjectSaveVO}
     */
    ProjectUpdateVO updateProject(ProjectUpdateVO updateVO);


    /**
     * 更新项目状态
     *
     * @param projectId     id
     * @param projectStatus 状态
     * @return {@link Boolean} true:更新成功
     */
    Boolean updateProjectStatus(Long projectId, Integer projectStatus);


    /**
     * 删除项目
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean deleteProject(Long id);

    /**
     * 根据项目标识获取项目详情
     *
     * @param projectIdentification 项目标识
     * @return {@link ProjectDetailsResultVO} 项目详情
     */
    ProjectDetailsResultVO getProjectDetailsByProjectIdentification(String projectIdentification);
}


