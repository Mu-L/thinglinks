package com.mqttsnet.thinglinks.tcp.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @program: thinglinks-cloud
 * @description: 其他默认Topic处理器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-05 22:55
 **/
@Service
@Slf4j
public class TcpDefaultHandler implements TcpTopicHandler {

    /**
     * @param topic the MQTT topic the message was received on.
     * @param qos   the quality of service level of the message.
     * @param body  the payload of the message.
     */
    @Override
    public void handle(String topic, String qos, String body) {
        // 处理默认情况的逻辑
        log.info("处理默认情况的逻辑,topic:{},qos:{},body:{}", topic, qos, body);
    }
}
