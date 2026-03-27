package com.mqttsnet.thinglinks.consumer.rocketmq;


import java.util.Optional;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.mqttsnet.thinglinks.enumeration.MqttEventEnum;
import com.mqttsnet.thinglinks.mqtt.event.publisher.MqttEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;

/**
 * @Description: Mqtt Message Rocketmq模式 消费者（主要用于开源版本切换Pro过渡使用）
 * @ClassName: MqttMessageRocketmqConsumerHandler
 * @Date: 2023/04/28$ 16:11$
 * @author: ShiHuan Sun
 */
@Slf4j
//@Component
//@RocketMQMessageListener(consumerGroup = ConsumerGroupConstant.THINGLINKS_GROUP, topic = ConsumerTopicConstant.Mqtt.THINGLINKS_MQS_MQTT_MSG, messageModel = MessageModel.CLUSTERING)
public class MqttMessageRocketmqConsumerHandler implements RocketMQListener {

    @Autowired
    private MqttEventPublisher eventPublisher;

    @Autowired
    private LinkCacheDataHelper linkCacheDataHelper;

    @Async("systemDefaultExecutor")
    @Override
    public void onMessage(Object message) {
        if (null == message) {
            log.warn("message cannot be empty {}", message);
            return;
        }
        log.info("ThingLinks物联网平台数据消费-->Received message={}", message);
        try {
            JSONObject thinglinksMessage = JSON.parseObject(String.valueOf(message));
            String eventStr = Optional.ofNullable(thinglinksMessage.getString(CommonConstants.EVENT_TYPE))
                    .orElse("");
            Long tenantId = Optional.ofNullable(thinglinksMessage.getString(CommonConstants.TENANT_ID))
                    .filter(StringUtils::isNotBlank)
                    .map(Long::valueOf)
                    .orElse(null);
            if (StringUtils.isEmpty(eventStr) || tenantId == null) {
                log.warn("eventType or tenantId cannot be empty {}", eventStr);
                return;
            }
            ContextUtil.setTenantId(tenantId);
            linkCacheDataHelper.incrementUpLinkCounter();
            Optional<MqttEventEnum> optionalEvent = MqttEventEnum.getMqttEventEnum(thinglinksMessage.get("event").toString());
            optionalEvent.ifPresent(event -> {
                switch (event) {
                    case CONNECT:
                        eventPublisher.publishMqttConnectEvent(MqttEventEnum.CONNECT, thinglinksMessage.toJSONString());
                        break;
                    case CLOSE:
                        eventPublisher.publishMqttCloseEvent(MqttEventEnum.CLOSE, thinglinksMessage.toJSONString());
                        break;
                    case DISCONNECT:
                        eventPublisher.publishMqttDisconnectEvent(MqttEventEnum.DISCONNECT, thinglinksMessage.toJSONString());
                        break;
                    case PUBLISH:
                        eventPublisher.publishMqttPublishEvent(MqttEventEnum.PUBLISH, thinglinksMessage.toJSONString());
                        break;
                    case SUBSCRIBE:
                        eventPublisher.publishMqttSubscribeEvent(MqttEventEnum.SUBSCRIBE, thinglinksMessage.toJSONString());
                        break;
                    case UNSUBSCRIBE:
                        eventPublisher.publishMqttUnsubscribeEvent(MqttEventEnum.UNSUBSCRIBE, thinglinksMessage.toJSONString());
                        break;
                    case PING:
                        eventPublisher.publishMqttPingEvent(MqttEventEnum.PING, thinglinksMessage.toJSONString());
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception e) {
            log.error("ThingLinks物联网平台数据消费-->消费失败，失败原因：{}", e.getMessage());
        }
    }
}
