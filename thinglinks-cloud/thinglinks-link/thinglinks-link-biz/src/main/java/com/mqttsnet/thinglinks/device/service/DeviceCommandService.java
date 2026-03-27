package com.mqttsnet.thinglinks.device.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.device.entity.DeviceCommand;
import com.mqttsnet.thinglinks.device.vo.query.DeviceCommandPageQuery;
import com.mqttsnet.thinglinks.device.vo.save.DeviceCommandSaveVO;
import com.mqttsnet.thinglinks.protocol.vo.param.DeviceCommandWrapperParam;
import com.mqttsnet.thinglinks.protocol.vo.param.PublishMqttMessageRequestParam;
import com.mqttsnet.thinglinks.protocol.vo.param.PublishWebSocketMessageRequestParam;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceCommandResultVO;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 设备命令下发及响应表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-10-20 17:27:25
 * @create [2023-10-20 17:27:25] [mqttsnet]
 */
public interface DeviceCommandService extends SuperService<Long, DeviceCommand> {


    /**
     * Save device command data.
     *
     * @param deviceCommandSaveVO device command data
     * @return DeviceCommand saved device command data
     */
    DeviceCommand saveDeviceCommand(DeviceCommandSaveVO deviceCommandSaveVO);

    /**
     * Fetch a list of device command result VOs.
     *
     * @param query the query parameters
     * @return a list of DeviceCommandResultVOs
     */
    List<DeviceCommandResultVO> getDeviceCommandResultVOList(DeviceCommandPageQuery query);


    /**
     * Processes both serial and parallel device command requests.
     *
     * @param commandWrapper wrapper containing both serial and parallel command requests
     * @return List of device command results
     */
    List<DeviceCommandResultVO> processDeviceCommands(DeviceCommandWrapperParam commandWrapper);

    /**
     * Send a Mqtt custom message to a device.
     *
     * @param publishMqttMessageRequestParam the custom message to be sent
     */
    void sendMqttCustomMessage(PublishMqttMessageRequestParam publishMqttMessageRequestParam);


    /**
     * Send a WebSocket custom message to a device.
     *
     * @param publishWebSocketMessageRequestParam the custom message to be sent
     */
    void sendWebSocketCustomMessage(PublishWebSocketMessageRequestParam publishWebSocketMessageRequestParam);
}


