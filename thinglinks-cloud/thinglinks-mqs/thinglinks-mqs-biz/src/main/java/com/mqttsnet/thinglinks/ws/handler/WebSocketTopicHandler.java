package com.mqttsnet.thinglinks.ws.handler;

/**
 * @program: thinglinks-cloud
 * @description:
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-05 22:45
 **/
public interface WebSocketTopicHandler {

    /**
     * @param topic the WebSocket topic the message was received on.
     * @param qos the quality of service level of the message.
     * @param body the payload of the message.
     */
    void handle(String topic, String qos, String body);
}
