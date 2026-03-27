package com.mqttsnet.thinglinks.video.dto.device.event;

import com.mqttsnet.thinglinks.video.dto.device.VideoDeviceInfoResultDTO;
import com.mqttsnet.thinglinks.video.dto.gb28181.SipTransactionInfo;
import lombok.Getter;

/**
 * Description:
 * 设备信息上线事件对象
 * @author Sun ShiHuan
 * @version 1.0.0
 * @since 2025/5/22
 */
@Getter
public class DeviceInfoOnlineEvent extends DeviceInfoBaseEventAbstract<VideoDeviceInfoResultDTO> {

    private final SipTransactionInfo sipTransactionInfo;

    public DeviceInfoOnlineEvent(VideoDeviceInfoResultDTO source, SipTransactionInfo sipTransactionInfo) {
        super(source);
        this.sipTransactionInfo = sipTransactionInfo;
    }
}
