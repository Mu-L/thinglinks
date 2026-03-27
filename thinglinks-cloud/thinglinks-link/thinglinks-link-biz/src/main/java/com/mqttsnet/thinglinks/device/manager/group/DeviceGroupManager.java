package com.mqttsnet.thinglinks.device.manager.group;

import java.util.List;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.device.entity.group.DeviceGroup;
import com.mqttsnet.thinglinks.device.vo.query.group.DeviceGroupPageQuery;

/**
 * <p>
 * 通用业务接口
 * 设备分组表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-19 18:05:14
 * @create [2025-06-19 18:05:14] [mqttsnet]
 */
public interface DeviceGroupManager extends SuperManager<DeviceGroup> {

    /**
     * 根据分页查询设备分组
     *
     * @param query 分页查询参数
     * @return {@link List<DeviceGroup>} 设备分组列表
     */
    List<DeviceGroup> getList(DeviceGroupPageQuery query);
}


