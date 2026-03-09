package com.mqttsnet.thinglinks.mqs.ws.controller;

import cn.hutool.core.util.StrUtil;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.thinglinks.common.mq.KafkaConsumerTopicConstant;
import com.mqttsnet.thinglinks.consumer.kafka.KafkaProducerService;
import com.mqttsnet.thinglinks.ws.WebSocketObserver;
import com.mqttsnet.thinglinks.ws.WebSocketSubject;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * TestEndpoint 类用于处理WebSocket连接的各种事件。
 * 示例2
 */
@ServerEndpoint("/anno/test2")
@Component
@Slf4j
public class TestEndpoint2 {

    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        log.info("连接成功");
        WebSocketObserver observer = new WebSocketObserver(session);
        // get subject
        WebSocketSubject subject = WebSocketSubject.Holder.getSubject(session.getId());
        // register observer into subject
        subject.addObserver(observer);
        KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
        kafkaProducerService.thingLinksProKafkaTemplateSendMsg(KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_CLIENT_CONNECTED_TOPIC, "new connection");
    }

    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        log.info("连接关闭");
        // get subject
        WebSocketSubject subject = WebSocketSubject.Holder.getSubject(session.getId());

        // get observer
        WebSocketObserver observer = new WebSocketObserver(session);
        // delete observer from subject
        subject.deleteObserver(observer);

        // close session and close Web Socket connection
        try {
            KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
            kafkaProducerService.thingLinksProKafkaTemplateSendMsg(KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_CLIENT_DISCONNECTED_TOPIC, "connection closed");
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("close web socket session error.", e);
        }
    }

    /**
     * 接收到消息
     *
     * @param text
     */
    @OnMessage
    public String onMsg(@PathParam("principal") String principal, String text) {
        if (StrUtil.isEmpty(text)) {
            return "";
        }
        KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
        kafkaProducerService.thingLinksProKafkaTemplateSendMsg(KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_DISTRIBUTION_COMPLETED_TOPIC, text);
        return "server 收到消息：" + text;
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("连接error");
        KafkaProducerService kafkaProducerService = SpringUtils.getBean(KafkaProducerService.class);
        kafkaProducerService.thingLinksProKafkaTemplateSendMsg(KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_DISTRIBUTION_ERROR_TOPIC, "WebSocket error");
        throw new RuntimeException("web socket error.", error);
    }
}
