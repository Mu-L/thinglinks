package com.mqttsnet.thinglinks.project.manager.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.project.entity.Project;
import com.mqttsnet.thinglinks.project.manager.ProjectManager;
import com.mqttsnet.thinglinks.project.mapper.ProjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 可视化项目表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:47:04
 * @create [2023-05-17 13:47:04] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProjectManagerImpl extends SuperManagerImpl<ProjectMapper, Project> implements ProjectManager {


    private final ProjectMapper projectMapper;

    /**
     * 按项目名称获取
     *
     * @param projectName 项目名称
     * @return {@link Project
     */
    @Override
    public Project getByProjectName(String projectName) {

        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Project::getProjectName, projectName);
        return projectMapper.selectOne(queryWrapper);
    }

    /**
     * 按项目标识获取
     *
     * @param projectIdentification 项目标识
     * @return {@link Project}
     */
    @Override
    public Project getByProjectIdentification(String projectIdentification) {
        return projectMapper.getByProjectIdentification(projectIdentification);
    }
}


