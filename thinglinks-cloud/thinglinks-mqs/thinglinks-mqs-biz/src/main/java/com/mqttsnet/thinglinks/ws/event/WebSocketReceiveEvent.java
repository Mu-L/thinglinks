package com.mqttsnet.thinglinks.ws.event;

import com.mqttsnet.thinglinks.enumeration.WsEventEnum;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

/**
 * @program: thinglinks-cloud
 * @description:
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 11:52
 **/
@Getter
public class WebSocketReceiveEvent extends ApplicationEvent {
    private final String message;
    private final WsEventEnum eventType;

    public WebSocketReceiveEvent(Object source, WsEventEnum eventType, String message) {
        super(source);
        this.message = message;
        this.eventType = eventType;
    }

}
