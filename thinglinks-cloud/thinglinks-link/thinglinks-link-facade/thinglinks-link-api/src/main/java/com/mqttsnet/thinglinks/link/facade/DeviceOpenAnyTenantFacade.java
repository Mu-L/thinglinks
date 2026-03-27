package com.mqttsnet.thinglinks.link.facade;

import com.mqttsnet.thinglinks.device.vo.query.DeviceAuthenticationQuery;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAuthenticationResultVO;
import org.springframework.http.ResponseEntity;

/**
 * @program: thinglinks-cloud
 * @description: 设备-开放接口API
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-06 12:35
 **/
public interface DeviceOpenAnyTenantFacade {


    ResponseEntity<DeviceAuthenticationResultVO> clientConnectionAuthentication(DeviceAuthenticationQuery deviceAuthenticationQuery);

}
