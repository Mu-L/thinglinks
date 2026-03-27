package com.mqttsnet.thinglinks.consumer.kafka;

import java.util.List;
import java.util.Optional;

import cn.hutool.core.util.IdUtil;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.common.mq.KafkaConsumerTopicConstant;
import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;
import com.mqttsnet.thinglinks.protocol.ProtocolHandlerFactory;
import com.mqttsnet.thinglinks.protocol.handler.ProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

/**
 * @program: thinglinks-cloud
 * @description: Mqtt Message kafka监听消息
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-06-18 11:46
 **/
@Slf4j
@Component
public class MqttMessageKafkaConsumerHandler {

    private final ProtocolHandlerFactory protocolHandlerFactory;

    public MqttMessageKafkaConsumerHandler(ProtocolHandlerFactory protocolHandlerFactory) {
        this.protocolHandlerFactory = protocolHandlerFactory;
    }

    /**
     * 监听kafka消息(批量)
     *
     * @param records kafka的批量消息，用consumerRecord可以接收到更详细的信息，也可以用String message只接收消息
     * @param ack     kafka的消息确认
     */
    @KafkaListener(topics = {KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQS_MQTT_MSG,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_CLIENT_CONNECTED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_CLIENT_DISCONNECTED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_SERVER_CONNECTED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_DEVICE_KICKED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_SUBSCRIPTION_ACKED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_UNSUBSCRIPTION_ACKED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_DISTRIBUTION_ERROR_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_DISTRIBUTION_COMPLETED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsMqtt.THINGLINKS_MQTT_PING_REQ_TOPIC
    }, errorHandler = "myKafkaListenerErrorHandler", containerFactory = "kafkaListenerContainerFactory")
    @KafkaHandler
    public void handleBatchMessages(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
        boolean hasProcessingError = false;
        log.info("Batch message listener started. Thread ID: {}, Number of records: {}", Thread.currentThread().getId(), records.size());

        try {
            for (ConsumerRecord<?, ?> record : records) {
                ContextUtil.setLogTraceId(IdUtil.fastSimpleUUID());
                int retryCount = 0;
                boolean success = false;

                while (retryCount < 3 && !success) {
                    try {
                        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
                        if (kafkaMessage.isEmpty()) {
                            log.warn("Skipping message: Empty or invalid format in topic: {}, offset: {}", record.topic(), record.offset());
                            // 即使消息为空，也算成功处理
                            success = true;
                            // 继续处理下一条消息
                            continue;
                        }

                        String message = kafkaMessage.get().toString();
                        String topic = record.topic();
                        log.debug("Processing message from topic: {}, offset: {}. Message: {}", topic, record.offset(), message);
                        // 调用消息处理方法
                        processMessage(message);
                        // 处理成功，退出重试循环
                        success = true;
                    } catch (Exception e) {
                        retryCount++;
                        log.error("Exception while processing message from topic: {}, offset: {}. Retry attempt: {}", record.topic(), record.offset(), retryCount, e);
                        if (retryCount >= 3) {
                            // 标记出现错误
                            hasProcessingError = true;
                        }
                    }
                }
            }
        } finally {
            ack.acknowledge(); // 提交偏移量以避免重复消费
            if (hasProcessingError) {
                log.warn("Processing errors occurred. Offset acknowledged, but some messages may not have been successfully processed.");
                // 这里可以将处理失败的消息记录或发送到DLC（死信队列）
            } else {
                log.info("All messages processed successfully. Offset acknowledged.");
            }
        }
    }


    /**
     * 处理消息
     *
     * @param message 消息记录
     */
    private void processMessage(String message) {
        log.info("ThingLinks物联网平台Kafka数据消费-->Received message={}", message);
        try {
            ProtocolHandler handler = protocolHandlerFactory.getHandler(ProtocolTypeEnum.MQTT);
            if (handler != null) {
                handler.processMessage(message);
            } else {
                log.error("未找到对应的协议处理器: " + ProtocolTypeEnum.MQTT);
                throw new IllegalStateException("未找到对应的协议处理器: " + ProtocolTypeEnum.MQTT);
            }
        } catch (Exception e) {
            log.error("ThingLinks物联网平台数据消费-->消费失败，失败原因：{}", e.getMessage(), e);
            // 根据需求考虑在此处添加重试机制
        }
    }

}
