package com.mqttsnet.thinglinks.link.facade;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.device.entity.DeviceCommand;
import com.mqttsnet.thinglinks.device.vo.save.DeviceCommandSaveVO;
import com.mqttsnet.thinglinks.protocol.vo.param.DeviceCommandWrapperParam;

/**
 * -----------------------------------------------------------------------------
 * File Name: DeviceCommandApi.java
 * -----------------------------------------------------------------------------
 * Description:
 * DeviceCommand  API
 * -----------------------------------------------------------------------------
 *
 * @author ShiHuan Sun
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * <p>
 * -----------------------------------------------------------------------------
 * @email 13733918655@163.com
 * @date 2023-11-12 03:20
 */
public interface DeviceCommandFacade {


    /**
     * Creates a new device command entry in the database.
     *
     * @param deviceCommandSaveVO The device command data to be saved.
     * @return The saved device command data.
     */
    R<DeviceCommand> saveDeviceCommand(DeviceCommandSaveVO deviceCommandSaveVO);


    /**
     * Issues a list of commands to devices, handling both serial and parallel execution.
     *
     * @param commandWrapper The list of commands to be issued.
     * @return The result of the command execution.
     */
    R<?> issueCommands(DeviceCommandWrapperParam commandWrapper);

}
