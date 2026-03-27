package com.mqttsnet.thinglinks.device.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.device.entity.DeviceLocation;
import com.mqttsnet.thinglinks.device.vo.query.DeviceLocationPageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceLocationResultVO;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 设备位置表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-30 23:05:31
 * @create [2023-05-30 23:05:31] [mqttsnet]
 */
public interface DeviceLocationManager extends SuperManager<DeviceLocation> {

    /**
     * 查询设备位置信息VO列表
     *
     * @param query 查询参数
     * @return {@link List<DeviceLocationResultVO>} 设备位置信息VO列表
     */
    List<DeviceLocationResultVO> getDeviceLocationResultVOList(DeviceLocationPageQuery query);
}


