package com.mqttsnet.thinglinks.device.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.device.entity.DeviceAction;
import com.mqttsnet.thinglinks.device.vo.query.DeviceActionPageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceActionResultVO;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 设备动作数据
 * </p>
 *
 * @author mqttsnet
 * @date 2023-06-10 16:38:09
 * @create [2023-06-10 16:38:09] [mqttsnet]
 */
public interface DeviceActionManager extends SuperManager<DeviceAction> {

    /**
     * 查询设备动作数据VO列表
     *
     * @param query 查询参数
     * @return {@link List<DeviceActionResultVO>} 设备动作数据VO列表
     */
    List<DeviceActionResultVO> getDeviceActionResultVOList(DeviceActionPageQuery query);
}


