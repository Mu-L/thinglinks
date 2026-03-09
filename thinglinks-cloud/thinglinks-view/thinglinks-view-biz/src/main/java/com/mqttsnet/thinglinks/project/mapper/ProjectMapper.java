package com.mqttsnet.thinglinks.project.mapper;

import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.project.entity.Project;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 可视化项目表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-17 13:47:04
 * @create [2023-05-17 13:47:04] [mqttsnet]
 */
@Repository
public interface ProjectMapper extends SuperMapper<Project> {

    /**
     * 按项目标识获取
     *
     * @param projectIdentification 项目标识
     * @return {@link Project}
     */
    Project getByProjectIdentification(@Param("projectIdentification") String projectIdentification);
}


