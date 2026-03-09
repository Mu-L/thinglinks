package com.mqttsnet.thinglinks.enumeration;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * @program: thinglinks
 * @description: webSocket 事件枚举
 * @packagename: com.mqttsnet.thinglinks.common.core.enums
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2022-12-16 19:42
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "WsEventEnum", description = "WEBSOCKET事件枚举")
public enum WsEventEnum {

    /**
     * Event type - Connection.
     */
    CONNECT("CONNECT"),

    /**
     * Event type - Closure.
     */
    DISCONNECTED("DISCONNECTED"),

    /**
     * Event type - Sending a message.
     */
    SEND("SEND"),

    /**
     * Event type - Receiving a message.
     */
    RECEIVE("RECEIVE"),

    /**
     * Event type - Error.
     */
    ERROR("ERROR"),

    /**
     * Event type - Heartbeat.
     */
    PING("PING"),

    /**
     * Event type - Heartbeat response.
     */
    PONG("PONG"),

    ;

    private String name;

    /**
     * Retrieves the corresponding WsEventEnum for a given name.
     *
     * @param name the name of the Ws event.
     * @return an Optional of WsEventEnum.
     */
    public static Optional<WsEventEnum> getWsEventEnum(String name) {
        if (StringUtils.isEmpty(name)) {
            return Optional.empty();
        }
        return Stream.of(WsEventEnum.values())
                .filter(event -> event.getName().equalsIgnoreCase(name))
                .findFirst();
    }
}