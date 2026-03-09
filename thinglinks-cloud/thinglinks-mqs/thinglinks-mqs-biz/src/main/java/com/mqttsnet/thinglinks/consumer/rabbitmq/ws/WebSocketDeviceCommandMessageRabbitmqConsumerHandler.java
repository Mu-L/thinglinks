package com.mqttsnet.thinglinks.consumer.rabbitmq.ws;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSON;
import com.mqttsnet.thinglinks.common.mq.RabbitmqConsumerConstant;
import com.mqttsnet.thinglinks.vo.query.PublishWebSocketMessageRequestVO;
import com.mqttsnet.thinglinks.ws.WebSocketSubject;
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
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * ============================================================================
 * Description:
 * 作用：消费设备命令消息,  webSocket集群推送至各节点
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
public class WebSocketDeviceCommandMessageRabbitmqConsumerHandler {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.Queues.DEVICE_COMMAND_QUEUE),
            exchange = @Exchange(
                    name = RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.Exchanges.DEVICE_DIRECT_EXCHANGE,
                    type = ExchangeTypes.FANOUT),
            key = RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.RouteKeys.DEVICE_COMMAND_SEND
    ))
    @RabbitHandler
    public void handleWebSocketMessage(Message message, @Headers Map<String, Object> headers, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) {
        try {
            if (null == message.getBody() || message.getBody().length == 0) {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                return;
            }
            PublishWebSocketMessageRequestVO messageDto = JSON.parseObject(message.getBody(), PublishWebSocketMessageRequestVO.class);
            log.info("WebSocketDeviceCommandMessageRabbitmqConsumerHandler receive Message | 路由键:{} , messageProperties:{},message:{}", headers.get(AmqpHeaders.RECEIVED_ROUTING_KEY), message.getMessageProperties(), JSONUtil.toJsonStr(messageDto));

            // 获取路由键信息
            String routingKey = (String) headers.get(AmqpHeaders.RECEIVED_ROUTING_KEY);

            handleMessage(messageDto);

            channel.basicAck(tag, false);
            log.info("消息ACK完成 | 路由键:{} , messageId:{}", routingKey, message.getMessageProperties().getMessageId());
        } catch (Exception e) {
            handleMessageError(channel, tag, message, e);
        }
    }

    private void handleMessage(PublishWebSocketMessageRequestVO messageRequestVO) {
        log.info("WebSocketDeviceCommandMessageRabbitmqConsumerHandler 推送消息至 WebSocket设备成功..... param:{}", JSONUtil.toJsonStr(messageRequestVO));
        WebSocketSubject subject = WebSocketSubject.Holder.getSubject(messageRequestVO.getClientId());
        JSONObject message = new JSONObject();
        message.put("topic", messageRequestVO.getTopic());
        message.put("body", messageRequestVO.getPayload());
        subject.notify(JSONUtil.toJsonStr(message));
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
