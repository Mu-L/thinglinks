package com.mqttsnet.thinglinks.tcp.handler.listener;

import com.mqttsnet.thinglinks.entity.tcp.TcpMessageEvent;
import com.mqttsnet.thinglinks.tcp.handler.TcpTopicHandler;
import com.mqttsnet.thinglinks.tcp.handler.factory.TcpTopicHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * @program: thinglinks-cloud
 * @description: TcpMessageListener
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 14:43
 **/
@Component
@Slf4j
public class TcpMessageListener {

    private final TcpTopicHandlerFactory tcpTopicHandlerFactory;

    @Autowired
    public TcpMessageListener(TcpTopicHandlerFactory tcpTopicHandlerFactory) {
        this.tcpTopicHandlerFactory = tcpTopicHandlerFactory;
    }

    @EventListener
    public void handleMessage(TcpMessageEvent event) {
        // 校验并处理Topic
        processTopic(event.getTopic(), event.getQos(), event.getMessage());
    }

    private void processTopic(String topic, String qos, String body) {
        // Using the TopicHandlerFactory to find the matching handler
        TcpTopicHandler matchingHandler = tcpTopicHandlerFactory.findMatchingHandler(topic);

        if (matchingHandler != null) {
            matchingHandler.handle(topic, qos, body);
        } else {
            // Handle the case when no topic matches
            log.warn("No topic handler found for topic: {}", topic);
        }
    }
}