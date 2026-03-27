package com.mqttsnet.thinglinks.link.facade.impl;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.device.entity.DeviceCommand;
import com.mqttsnet.thinglinks.device.vo.save.DeviceCommandSaveVO;
import com.mqttsnet.thinglinks.link.api.device.DeviceCommandApi;
import com.mqttsnet.thinglinks.link.facade.DeviceCommandFacade;
import com.mqttsnet.thinglinks.protocol.vo.param.DeviceCommandWrapperParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 *
 * @author tangyh
 * @since 2024/12/24 17:01
 */
@Service
public class DeviceCommandFacadeImpl implements DeviceCommandFacade {
    @Lazy
    @Autowired
    private DeviceCommandApi deviceCommandApi;

    @Override
    public R<DeviceCommand> saveDeviceCommand(DeviceCommandSaveVO deviceCommandSaveVO) {
        return deviceCommandApi.saveDeviceCommand(deviceCommandSaveVO);
    }

    @Override
    public R<?> issueCommands(DeviceCommandWrapperParam commandWrapper) {
        return deviceCommandApi.issueCommands(commandWrapper);
    }
}
