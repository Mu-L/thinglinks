package com.mqttsnet.thinglinks.ws.handler.listener;

import com.mqttsnet.thinglinks.entity.ws.WebSocketMessageEvent;
import com.mqttsnet.thinglinks.ws.handler.WebSocketTopicHandler;
import com.mqttsnet.thinglinks.ws.handler.factory.WebSocketTopicHandlerFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;


/**
 * @program: thinglinks-cloud
 * @description:
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 14:43
 **/
@Component
@Slf4j
public class WebSocketMessageListener {

    private final WebSocketTopicHandlerFactory topicHandlerFactory;

    @Autowired
    public WebSocketMessageListener(WebSocketTopicHandlerFactory topicHandlerFactory) {
        this.topicHandlerFactory = topicHandlerFactory;
    }

    @EventListener
    public void handleMessage(WebSocketMessageEvent event) {
        // 校验并处理Topic
        processTopic(event.getTopic(), event.getQos(), event.getMessage());
    }

    private void processTopic(String topic, String qos, String body) {
        // Using the TopicHandlerFactory to find the matching handler
        WebSocketTopicHandler matchingHandler = topicHandlerFactory.findMatchingHandler(topic);

        if (matchingHandler != null) {
            matchingHandler.handle(topic, qos, body);
        } else {
            // Handle the case when no topic matches
            log.warn("No topic handler found for topic: {}", topic);
        }
    }
}