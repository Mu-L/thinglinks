package com.mqttsnet.thinglinks.link.facade;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.device.entity.DeviceCommand;
import com.mqttsnet.thinglinks.device.service.DeviceCommandService;
import com.mqttsnet.thinglinks.device.vo.save.DeviceCommandSaveVO;
import com.mqttsnet.thinglinks.protocol.vo.param.DeviceCommandWrapperParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tangyh
 * @since 2024/12/24 17:01
 */
@Service
public class DeviceCommandFacadeImpl implements DeviceCommandFacade {
    @Autowired
    private DeviceCommandService deviceCommandService;

    @Override
    public R<DeviceCommand> saveDeviceCommand(DeviceCommandSaveVO deviceCommandSaveVO) {
        DeviceCommand savedDeviceCommand = deviceCommandService.saveDeviceCommand(deviceCommandSaveVO);
        return R.success(savedDeviceCommand);
    }

    @Override
    public R<?> issueCommands(DeviceCommandWrapperParam commandWrapper) {
        deviceCommandService.processDeviceCommands(commandWrapper);
        return R.success();
    }
}
