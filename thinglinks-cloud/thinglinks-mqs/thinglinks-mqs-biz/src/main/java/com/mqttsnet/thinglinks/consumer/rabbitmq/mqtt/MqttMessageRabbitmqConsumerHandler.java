package com.mqttsnet.thinglinks.consumer.rabbitmq.mqtt;

import com.mqttsnet.thinglinks.common.mq.RabbitmqConsumerConstant;
import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;
import com.mqttsnet.thinglinks.protocol.ProtocolHandlerFactory;
import com.mqttsnet.thinglinks.protocol.handler.ProtocolHandler;
import com.rabbitmq.client.Channel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Argument;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * ============================================================================
 * Description:
 * MQTT消息消费者 RabbitMq 消费者处理类
 * <p>
 * 实现特性：
 * 1. 使用TOPIC交换机实现消息路由
 * 2. 支持通配符路由键 mqtt.message.#
 * 3. 持久化队列保证消息可靠性
 * </p>
 * ============================================================================
 *
 * @author Sun Shihuan
 * @version 1.0.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2025/4/13      Sun Shihuan        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2025/4/13 11:18
 */
@Slf4j
@Component
@AllArgsConstructor
@RabbitListener(
        bindings = @QueueBinding(
                value = @Queue(
                        name = RabbitmqConsumerConstant.Mqs.MqttInfra.Queues.MQTT_MESSAGE_QUEUE,
                        durable = "true",
                        arguments = @Argument(name = "x-dead-letter-exchange", value = "dlx.exchange")
                ),
                exchange = @Exchange(
                        name = RabbitmqConsumerConstant.Mqs.MqttInfra.Exchanges.MQTT_TOPIC_EXCHANGE,
                        type = ExchangeTypes.TOPIC // 使用TOPIC类型交换机
                ),
                key = RabbitmqConsumerConstant.Mqs.MqttInfra.RouteKeys.MQTT_MESSAGE_PATTERN
        )
)
public class MqttMessageRabbitmqConsumerHandler {

    private static final int MAX_RETRIES = 3;
    private final ProtocolHandlerFactory protocolHandlerFactory;

    /**
     * 消息处理入口
     *
     * @param message 消息实体
     * @param channel RabbitMQ通道
     * @param tag     投递标签
     * @param topic   来自消息头的实际MQTT主题
     */
    @RabbitHandler
    public void handleMessage(Message message, Channel channel,
                              @Header(AmqpHeaders.DELIVERY_TAG) long tag,
                              @Header("mqtt_receivedTopic") String topic) {
        boolean success = false;
        int retryCount = 0;

        try {
            while (retryCount <= MAX_RETRIES && !success) {
                try {
                    processSingleMessage(message, topic);
                    success = true;
                    channel.basicAck(tag, false);
                    log.info("Message processed successfully [topic={}, messageId={}]", topic, getMessageId(message));
                } catch (Exception ex) {
                    handleRetry(message, channel, tag, topic, retryCount, ex);
                    retryCount++;
                }
            }
        } catch (IOException e) {
            log.error("Final message ack failed [topic={}]", topic, e);
        } finally {
            // 新增的最终确认保障
            if (!success) {
                try {
                    channel.basicNack(tag, false, false);
                    log.warn("Force nack after all retries [topic={}]", topic);
                } catch (IOException ioEx) {
                    log.error("Final nack failed [topic={}]", topic, ioEx);
                }
            }
        }
    }

    /**
     * 单个消息处理（对应Kafka的processMessage）
     */
    private void processSingleMessage(Message message, String topic) {
        // 空消息检查（与Kafka的kafkaMessage.isEmpty()对齐）
        if (message.getBody() == null || message.getBody().length == 0) {
            log.warn("Empty message received [topic={}]", topic);
            return;
        }

        String payload = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("Processing message [topic={}, size={}B]", topic, payload.length());

        ProtocolHandler handler = protocolHandlerFactory.getHandler(ProtocolTypeEnum.MQTT);
        if (handler == null) {
            throw new IllegalStateException("No MQTT protocol handler available");
        }
        handler.processMessage(payload);
    }

    /**
     * 重试处理（对应Kafka的while(retryCount < 3)逻辑）
     */
    private void handleRetry(Message message, Channel channel, long tag,
                             String topic, int retryCount, Exception ex) throws IOException {
        if (retryCount < MAX_RETRIES) {
            log.warn("Retry attempt {}/{} [topic={}]", retryCount + 1, MAX_RETRIES, topic, ex);
            // 重新入队
            channel.basicNack(tag, false, true);
        } else {
            log.error("Retries exhausted [topic={}]", topic, ex);
            handleFinalError(message, channel, tag, topic, ex);
        }
    }

    /**
     * 最终错误处理（对应Kafka的hasProcessingError标记）
     */
    private void handleFinalError(Message message, Channel channel, long tag,
                                  String topic, Exception ex) throws IOException {
        log.error("Message processing failed after {} retries [topic={}, messageId={}]", MAX_RETRIES, topic, getMessageId(message), ex);
        if (ex instanceof RuntimeException) {
            // 业务异常进入死信队列
            channel.basicNack(tag, false, false);
        } else {
            // 系统异常再次重试
            channel.basicNack(tag, false, true);
        }
    }

    private String getMessageId(Message message) {
        return message.getMessageProperties().getMessageId();
    }

}
