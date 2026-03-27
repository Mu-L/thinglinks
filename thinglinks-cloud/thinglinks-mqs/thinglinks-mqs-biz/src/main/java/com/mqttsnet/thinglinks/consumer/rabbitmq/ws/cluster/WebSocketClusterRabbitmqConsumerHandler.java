package com.mqttsnet.thinglinks.consumer.rabbitmq.ws.cluster;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.mqttsnet.thinglinks.common.mq.RabbitmqConsumerConstant;
import com.mqttsnet.thinglinks.dto.ws.mq.WebSocketClusterMessageDTO;
import com.mqttsnet.thinglinks.enumeration.WsMessageTypeEnum;
import com.mqttsnet.thinglinks.ws.service.WebSocketService;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * ============================================================================
 * Description:
 * WebSocket 集群消息 Rabbitmq模式消费者
 * 作用：接收 webSocket 推送过来的集群事件消息
 * ============================================================================
 *
 * @author mqttsnet
 * @version 1.0.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/2/8      mqttsnet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/2/8 11:17
 */
@Slf4j
@Component
public class WebSocketClusterRabbitmqConsumerHandler {

    @Autowired
    private final WebSocketService webSocketService;

    public WebSocketClusterRabbitmqConsumerHandler(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
    }


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.Queues.WEBSOCKET_CLUSTER_STATUS_QUEUE),
            exchange = @Exchange(
                    name = RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.Exchanges.WEBSOCKET_EVENTS_EXCHANGE,
                    type = ExchangeTypes.FANOUT),
            key = {RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.RouteKeys.CLUSTER_INSTANCE_UP, RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.RouteKeys.CLUSTER_INSTANCE_DOWN}
    ))
    @RabbitHandler
    public void handleWebSocketMessage(Message message, @Headers Map<String, Object> headers, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            if (null == message.getBody() || message.getBody().length == 0) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            WebSocketClusterMessageDTO messageDto = JSON.parseObject(message.getBody(), WebSocketClusterMessageDTO.class);
            log.info("WebSocketClusterRabbitmqConsumerHandler receive Message | 路由键:{} , messageProperties:{},message:{}", headers.get(AmqpHeaders.RECEIVED_ROUTING_KEY), message.getMessageProperties(), JSONUtil.toJsonStr(messageDto));

            // 获取路由键信息
            String routingKey = (String) headers.get(AmqpHeaders.RECEIVED_ROUTING_KEY);

            // 业务处理逻辑（示例）
            handleClusterEvent(messageDto);

            channel.basicAck(tag, false);
            log.info("消息ACK完成 | 路由键:{} , messageId:{}", routingKey, message.getMessageProperties().getMessageId());
        } catch (Exception e) {
            handleMessageError(channel, tag, message, e);
        }
    }

    private void handleClusterEvent(WebSocketClusterMessageDTO msg) {
        if (WsMessageTypeEnum.INSTANCE_UP.equals(msg.getMessageType())) {
            log.info("[webSocket 实例上线] 节点IP:{},节点信息:{}", msg.getNodeInfo().getServerIp(), JSONUtil.toJsonStr(msg));
        } else if (WsMessageTypeEnum.INSTANCE_DOWN.equals(msg.getMessageType())) {
            log.info("[webSocket 实例下线] 节点IP:{},节点信息:{}", msg.getNodeInfo().getServerIp(), JSONUtil.toJsonStr(msg));
        }
    }

    private void handleMessageError(Channel channel, long tag, Message message, Exception ex) {
        if (ex instanceof RuntimeException) {
            log.error("webSocket 实例业务异常丢弃消息 | message={}", message.toString(), ex);
            rejectMessage(channel, tag, false);
        } else {
            log.error("系统异常需重试 | message={}", message.toString(), ex);
            rejectMessage(channel, tag, true);
        }
    }


    /**
     * 统一消息拒绝方法
     *
     * @param channel RabbitMQ通道实例 {@link com.rabbitmq.client.Channel}
     * @param tag     消息投递标签（从消息头获取） {@link AmqpHeaders#DELIVERY_TAG}
     * @param requeue 是否重新排队(false进入死信队列/true重新入队) {@link com.rabbitmq.client.Channel#basicNack(long, boolean, boolean) basicNack中的requeue参数}
     */
    private void rejectMessage(Channel channel, long tag, boolean requeue) {
        try {
            channel.basicNack(tag, false, requeue);
        } catch (IOException ex) {
            log.error("消息拒绝操作失败: {}", ex.getMessage());
        }
    }


}
