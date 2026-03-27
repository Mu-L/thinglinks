package com.mqttsnet.thinglinks.ws.handler;

import cn.hutool.json.JSONUtil;
import com.mqttsnet.thinglinks.common.mq.RabbitmqConsumerConstant;
import com.mqttsnet.thinglinks.dto.ws.mq.WebSocketClusterMessageDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * webSocket 生命周期事件处理
 *
 * @author mqttsnet
 * @since 2021/3/23
 */
@Slf4j
@Component
public class WebSocketServerClusterLifecycleEventHandler {

    private final RabbitTemplate rabbitTemplate;

    public WebSocketServerClusterLifecycleEventHandler(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void handleInstanceUp() throws InterruptedException {
        Thread.sleep(5000);
        sendClusterEvent(true);
    }

    @EventListener(ContextClosedEvent.class)
    public void handleInstanceDown() {
        sendClusterEvent(false);
    }

    private void sendClusterEvent(boolean isUp) {
        try {
            WebSocketClusterMessageDTO clusterMessageDto = WebSocketClusterMessageDTO.createInstanceEvent(isUp);
            String mqMsg = JSONUtil.toJsonStr(clusterMessageDto);

            String routingKey = isUp ?
                    RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.RouteKeys.CLUSTER_INSTANCE_UP :
                    RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.RouteKeys.CLUSTER_INSTANCE_DOWN;

            rabbitTemplate.convertAndSend(
                    RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.Exchanges.WEBSOCKET_EVENTS_EXCHANGE,
                    routingKey,
                    mqMsg
            );
            log.info("已发送WebSocket 集群生命周期事件消息:{}", mqMsg);
        } catch (Exception e) {
            log.error("发送集群生命周期事件消息失败", e);
        }
    }
}
