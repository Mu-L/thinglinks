package com.mqttsnet.thinglinks.tcp.event.listener;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.thinglinks.entity.tcp.TcpMessageEvent;
import com.mqttsnet.thinglinks.tcp.event.TcpPublishEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * @program: thinglinks-cloud
 * @description: TCP Publish事件监听器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-28 16:12
 **/
@Component
@Slf4j
public class TcpPublishEventListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 处理TCP PUBLISH事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void handleTcpPublishEvent(TcpPublishEvent event) {
        log.info("Received TCP PUBLISH event: message={}", event.getMessage());
        // 处理PUBLISH事件
        if (StringUtils.isEmpty(event.getMessage()) || !JSONUtil.isTypeJSON(event.getMessage())) {
            log.warn("The message is empty or invalid JSON, and ignored");
            return;
        }

        JSONObject messageObj = JSON.parseObject(event.getMessage());
        String topic = messageObj.getString("topic");
        String qos = messageObj.getString("qos");
        String body = messageObj.getString("body");
        String time = messageObj.getString("time");


        if (!JSONUtil.isTypeJSON(body)) {
            log.error("Topic:{}, The body is empty and ignored", topic);
            return;
        }

        log.info("TcpPublishEventListener handleTcpPublishEvent tcpMessage topic:{}, qos:{}, body:{}, time:{}", topic, qos, body, time);
        // 发布TCP消息事件
        publisher.publishEvent(new TcpMessageEvent(this, topic, qos, body, time));
    }


}
