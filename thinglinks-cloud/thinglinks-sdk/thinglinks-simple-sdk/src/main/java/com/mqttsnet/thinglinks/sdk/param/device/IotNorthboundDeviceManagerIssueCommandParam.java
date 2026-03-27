package com.mqttsnet.thinglinks.sdk.param.device;

import com.mqttsnet.thinglinks.sdk.request.device.IotNorthboundDeviceIssueCommandRequest;
import com.mqttsnet.thinglinks.sdk.response.device.IotNorthboundDeviceIssueCommandResponse;
import com.mqttsnet.thinglinks.sdkcore.param.BaseParam;

/**
 * Description:
 * 北向API-下发设备命令参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/01/22
 */
public class IotNorthboundDeviceManagerIssueCommandParam extends BaseParam<IotNorthboundDeviceIssueCommandRequest, IotNorthboundDeviceIssueCommandResponse> {

    @Override
    protected String method() {
        return "iot.northbound.device.issueCommand";
    }
}
