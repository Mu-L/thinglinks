package com.mqttsnet.thinglinks.device.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.device.entity.DeviceCommand;
import com.mqttsnet.thinglinks.device.vo.query.DeviceCommandPageQuery;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 设备命令下发及响应表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-10-20 17:27:25
 * @create [2023-10-20 17:27:25] [mqttsnet]
 */
public interface DeviceCommandManager extends SuperManager<DeviceCommand> {
    /**
     * Fetch a list of device command result VOs.
     *
     * @param query the query parameters
     * @return a list of DeviceCommandResultVOs
     */
    List<DeviceCommand> getDeviceCommandResultVOList(DeviceCommandPageQuery query);
}


