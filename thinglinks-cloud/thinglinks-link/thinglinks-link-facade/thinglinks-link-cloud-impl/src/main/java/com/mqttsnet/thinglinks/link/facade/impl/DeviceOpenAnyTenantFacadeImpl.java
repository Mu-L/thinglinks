package com.mqttsnet.thinglinks.link.facade.impl;

import com.mqttsnet.thinglinks.device.vo.query.DeviceAuthenticationQuery;
import com.mqttsnet.thinglinks.link.api.anytenant.DeviceOpenAnyTenantApi;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyTenantFacade;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAuthenticationResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 * @author tangyh
 * @since 2024/12/24 17:02
 */
@Service
public class DeviceOpenAnyTenantFacadeImpl implements DeviceOpenAnyTenantFacade {
    @Lazy
    @Autowired
    private DeviceOpenAnyTenantApi deviceOpenAnyTenantApi;

    @Override
    public ResponseEntity<DeviceAuthenticationResultVO> clientConnectionAuthentication(DeviceAuthenticationQuery deviceAuthenticationQuery) {
        return deviceOpenAnyTenantApi.clientConnectionAuthentication(deviceAuthenticationQuery);
    }
}
