package com.mqttsnet.thinglinks.project.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.project.entity.Project;

/**
 * <p>
 * 通用业务接口
 * 可视化项目表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:47:04
 * @create [2023-05-17 13:47:04] [mqttsnet]
 */
public interface ProjectManager extends SuperManager<Project> {

    /**
     * 按项目名称获取
     *
     * @param projectName 项目名称
     * @return {@link Project}
     */
    Project getByProjectName(String projectName);

    /**
     * 按项目标识获取
     *
     * @param projectIdentification 项目标识
     * @return {@link Project}
     */
    Project getByProjectIdentification(String projectIdentification);
}


