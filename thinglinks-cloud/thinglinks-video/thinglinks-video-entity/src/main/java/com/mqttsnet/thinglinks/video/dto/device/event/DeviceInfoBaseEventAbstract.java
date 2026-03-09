package com.mqttsnet.thinglinks.video.dto.device.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * Description:
 * 设备基础事件对象（Base Event）
 *
 * @param <T> 事件源类型
 * @author Sun ShiHuan
 * @version 1.0.0
 * @since 2025/5/22
 */
@Getter
public class DeviceInfoBaseEventAbstract<T> extends ApplicationEvent {
    private final T source;

    public DeviceInfoBaseEventAbstract(T source) {
        super(source);
        this.source = source;
    }
}
