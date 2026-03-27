package com.mqttsnet.thinglinks.device.service.group;

import java.util.Collection;
import java.util.List;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.device.entity.group.DeviceGroupRel;


/**
 * <p>
 * 业务接口
 * 设备分组关系表
 * </p>
 *
 * @author mqttsnet
 * @since 2025-06-23 14:06:46
 */
public interface DeviceGroupRelService extends SuperService<Long, DeviceGroupRel> {

    /**
     * 根据分组ID删除设备分组关系
     *
     * @param groupIdList 分组ID列表
     */
    void removeByGroupIds(Collection<Long> groupIdList);


    /**
     * 根据分组ID查询设备标识列表
     *
     * @param groupIdList 分组ID列表
     * @return {@link List<String>} 设备标识列表
     */
    List<String> getDeviceIdentificationsByGroupIds(Collection<Long> groupIdList);
}


