package com.mqttsnet.thinglinks.ws.event.listener;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.thinglinks.entity.protocol.base.DistedEvent;
import com.mqttsnet.thinglinks.entity.ws.WebSocketMessageEvent;
import com.mqttsnet.thinglinks.enumeration.QosEnum;
import com.mqttsnet.thinglinks.ws.event.WebSocketReceiveEvent;
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
 * @description: WebSocket  Receive 事件监听器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 00:12
 **/
@Component
@Slf4j
public class WebSocketReceiveEventListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 处理WebSocket Receive 事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void handleWebSocketReceiveEvent(WebSocketReceiveEvent event) {
        log.info("Received WebSocket  Receive event: message={}", event.getMessage());
        // 处理Receive事件
        if (StringUtils.isEmpty(event.getMessage()) || !JSONUtil.isTypeJSON(event.getMessage())) {
            log.warn("The message is empty or invalid JSON, and ignored");
            return;
        }

        DistedEvent distedEvent = JsonUtil.parse(event.getMessage(), DistedEvent.class);
        JSONObject wsMessage = JSON.parseObject(distedEvent.getPayload());
        String topic = wsMessage.getString("topic");
        String body = wsMessage.getString("body");
        String time = String.valueOf(distedEvent.getTimestamp());

        if (!JSONUtil.isTypeJSON(body)) {
            log.error("Topic:{}, The body is empty and ignored", topic);
            return;
        }

        log.info("WebSocketReceiveEventListener handleWebSocketReceiveEvent webSocketMessage topic:{}, body:{}, time:{}", topic, body, time);
        // 发布 WebSocket Receive 消息事件
        publisher.publishEvent(new WebSocketMessageEvent(this, topic, String.valueOf(QosEnum.AT_MOST_ONCE.getValue()), body, time));
    }


}
