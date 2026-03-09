package com.mqttsnet.thinglinks.link.facade.impl;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.device.vo.result.DeviceDetailsResultVO;
import com.mqttsnet.thinglinks.link.api.device.DeviceApi;
import com.mqttsnet.thinglinks.link.facade.DeviceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author tangyh
 * @since 2024/12/24 17:02
 */
@Service
public class DeviceFacadeImpl implements DeviceFacade {
    @Lazy
    @Autowired
    private DeviceApi deviceApi;

    @Override
    public R<Boolean> updateDeviceConnectionStatus(Long id, Integer connectionStatus) {
        return deviceApi.updateDeviceConnectionStatus(id, connectionStatus);
    }

    @Override
    public R<List<DeviceDetailsResultVO>> getDeviceDetailsByIdentifications(List<String> deviceIdentifications) {
        return deviceApi.getDeviceDetailsByIdentifications(deviceIdentifications);
    }
}
