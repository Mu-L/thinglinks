package com.mqttsnet.thinglinks.device.service.group;

import java.util.List;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.device.entity.group.DeviceGroup;
import com.mqttsnet.thinglinks.device.vo.query.group.DeviceGroupPageQuery;
import com.mqttsnet.thinglinks.device.vo.result.group.DeviceGroupResultVO;


/**
 * <p>
 * 业务接口
 * 设备分组表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-19 18:05:14
 * @create [2025-06-19 18:05:14] [mqttsnet]
 */
public interface DeviceGroupService extends SuperService<Long, DeviceGroup> {

    /**
     * 查询树结构
     *
     * @param query 参数
     * @return 树
     */
    List<DeviceGroupResultVO> findTree(DeviceGroupPageQuery query);


    /**
     * 获取设备分组结果VO列表
     *
     * @param query 查询参数
     * @return {@link List<DeviceGroupResultVO>} 列表结果
     */
    List<DeviceGroupResultVO> getDeviceGroupResultVOList(DeviceGroupPageQuery query);
}


