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
 * @description: webSocket Message kafka监听消息
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2024-03-11 11:46
 **/
@Slf4j
@Component
public class WebSocketMessageKafkaConsumerHandler {

    private final ProtocolHandlerFactory protocolHandlerFactory;

    public WebSocketMessageKafkaConsumerHandler(ProtocolHandlerFactory protocolHandlerFactory) {
        this.protocolHandlerFactory = protocolHandlerFactory;
    }

    /**
     * 监听kafka消息(批量)
     *
     * @param records kafka的批量消息，用consumerRecord可以接收到更详细的信息，也可以用String message只接收消息
     * @param ack     kafka的消息确认
     */
    @KafkaListener(topics = {KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_MQS_WEBSOCKET_MSG,
            KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_CLIENT_CONNECTED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_CLIENT_DISCONNECTED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_DEVICE_KICKED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_SUBSCRIPTION_ACKED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_UNSUBSCRIPTION_ACKED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_DISTRIBUTION_ERROR_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_DISTRIBUTION_COMPLETED_TOPIC,
            KafkaConsumerTopicConstant.Mqs.MqsWebSocket.THINGLINKS_WEBSOCKET_PING_REQ_TOPIC
    }, errorHandler = "myKafkaListenerErrorHandler", containerFactory = "kafkaListenerContainerFactory")
    @KafkaHandler
    public void handleBatchMessages(List<ConsumerRecord<?, ?>> records, Acknowledgment ack) {
        boolean hasProcessingError = false; // 标记处理过程中是否出现错误
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
                            success = true; // 即使消息为空，也算成功处理
                            continue; // 继续处理下一条消息
                        }

                        String message = kafkaMessage.get().toString();
                        String topic = record.topic();
                        log.debug("Processing message from topic: {}, offset: {}. Message: {}", topic, record.offset(), message);

                        processMessage(message); // 调用消息处理方法
                        success = true; // 处理成功，退出重试循环
                    } catch (Exception e) {
                        retryCount++;
                        log.error("Exception while processing message from topic: {}, offset: {}. Retry attempt: {}", record.topic(), record.offset(), retryCount, e);
                        if (retryCount >= 3) {
                            hasProcessingError = true; // 标记出现错误
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
        log.info("ThingLinks物联网平台数据消费-->Received message={}", message);
        try {
            ProtocolHandler handler = protocolHandlerFactory.getHandler(ProtocolTypeEnum.WEBSOCKET);
            if (handler != null) {
                handler.processMessage(message);
            } else {
                log.error("未找到对应的协议处理器: " + ProtocolTypeEnum.WEBSOCKET);
                throw new IllegalStateException("未找到对应的协议处理器: " + ProtocolTypeEnum.WEBSOCKET);
            }
        } catch (Exception e) {
            log.error("ThingLinks物联网平台数据消费-->消费失败，失败原因：{}", e.getMessage(), e);
            // 根据需求考虑在此处添加重试机制
        }
    }

}
