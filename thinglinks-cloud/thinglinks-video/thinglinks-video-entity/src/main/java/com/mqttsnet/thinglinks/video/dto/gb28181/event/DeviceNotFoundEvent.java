package com.mqttsnet.thinglinks.video.dto.gb28181.event;

import lombok.Data;

/**
 * 设备不存在事件
 *
 * @author mqttsnet
 */
@Data
public class DeviceNotFoundEvent {

    private String callId;

    public DeviceNotFoundEvent(String callId) {
        this.callId = callId;
    }
}
