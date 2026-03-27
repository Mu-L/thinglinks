package com.mqttsnet.thinglinks.tcp.event;

import com.mqttsnet.thinglinks.enumeration.TcpEventEnum;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @program: thinglinks-cloud
 * @description: TcpPublishEvent
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 01:13
 **/
@Getter
public class TcpPublishEvent extends ApplicationEvent {
    private String message;
    private TcpEventEnum eventType;

    public TcpPublishEvent(Object source, TcpEventEnum eventType, String message) {
        super(source);
        this.message = message;
        this.eventType = eventType;
    }

}

