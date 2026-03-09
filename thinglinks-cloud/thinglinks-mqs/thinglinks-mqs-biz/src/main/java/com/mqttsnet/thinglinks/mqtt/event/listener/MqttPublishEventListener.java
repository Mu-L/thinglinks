package com.mqttsnet.thinglinks.mqtt.event.listener;

import cn.hutool.core.codec.Base64;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.thinglinks.entity.mqtt.MqttMessageEvent;
import com.mqttsnet.thinglinks.entity.mqtt.source.MqttMessageEventSource;
import com.mqttsnet.thinglinks.mqtt.event.MqttPublishEvent;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

/**
 * MQTT PUBLISH事件监听器
 * <p>
 * 上游数据格式：
 * - payload: Base64编码的原始数据，确保二进制数据安全传输
 * - payloadHex: 十六进制格式的原始数据，便于设备协议解析
 * - originalSize: 原始数据的大小，便于设备协议解析
 * - encoding: 编码方式,默认为Base64编码
 *
 * @description: MqttPublish事件监听器
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-04-28 00:12
 **/
@Component
@Slf4j
public class MqttPublishEventListener {

    @Autowired
    private ApplicationEventPublisher publisher;

    /**
     * 处理MQTT PUBLISH事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void handleMqttPublishEvent(MqttPublishEvent event) {
        log.info("Received MQTT PUBLISH event: message={}", event.getMessage());
        // 处理PUBLISH事件
        if (StringUtils.isEmpty(event.getMessage()) || !JSONUtil.isTypeJSON(event.getMessage())) {
            log.warn("The message is empty or invalid JSON, and ignored");
            return;
        }

        JSONObject messageObj = JSON.parseObject(event.getMessage());
        // 提取所有字段
        String topic = messageObj.getString("topic");
        String qos = messageObj.getString("qos");
        String payload = messageObj.getString("payload");
        String payloadHex = messageObj.getString("payloadHex");
        String time = messageObj.getString("timestamp");
        String originalSize = messageObj.getString("originalSize");
        String encoding = messageObj.getString("encoding");
        // 解码payload为字节数组
        byte[] payloadBytes = Base64.decode(payload);

        // 构建事件源数据
        MqttMessageEventSource eventSource = MqttMessageEventSource.builder()
                .topic(topic)
                .qos(qos)
                .payloadBytes(payloadBytes)
                .payload(payload)
                .payloadHex(payloadHex)
                .originalSize(originalSize)
                .encoding(encoding)
                .timestamp(time)
                .build();

        log.info("MQTT PUBLISH事件处理 - topic:{}, qos:{}, payload长度:{}, payloadHex长度:{}, originalSize:{}, encoding:{}",
                topic, qos, StringUtils.length(payload), StringUtils.length(payloadHex),
                originalSize, encoding);
        // 发布MQTT消息事件
        MqttMessageEvent messageEvent = new MqttMessageEvent(eventSource);
        publisher.publishEvent(messageEvent);
    }


}
