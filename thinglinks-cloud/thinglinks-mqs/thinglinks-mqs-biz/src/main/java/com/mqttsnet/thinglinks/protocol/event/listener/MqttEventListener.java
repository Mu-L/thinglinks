package com.mqttsnet.thinglinks.protocol.event.listener;

import java.util.Optional;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.mqttsnet.thinglinks.entity.protocol.MqttEvent;
import com.mqttsnet.thinglinks.enumeration.MqttEventEnum;
import com.mqttsnet.thinglinks.mqtt.event.publisher.MqttEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * -----------------------------------------------------------------------------
 * File Name: MqttEventListener
 * -----------------------------------------------------------------------------
 * Description:
 * MQTT事件监听器
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/3/11       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email 13733918655@163.com
 * @date 2024/3/11 15:47
 */

@Component
@Slf4j
public class MqttEventListener {


    @Autowired
    private MqttEventPublisher eventPublisher;

    @Autowired
    private LinkCacheDataHelper linkCacheDataHelper;

    /**
     * 发布MQTT事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Async("mqsMqttExecutor")
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void handleMqttEvent(MqttEvent event) {
        // 设置本地上下文信息, @Async 注解（异步处理）可能会导致线程上下文参数丢失, 所以需要手动重新设置
        ContextUtil.setLocalMap(event.getContextUtilLocalMap());
        try {
            JSONObject thinglinksMessage = JSON.parseObject(event.getMessage());
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
            Optional<MqttEventEnum> optionalEvent = MqttEventEnum.getMqttEventEnum(thinglinksMessage.get(CommonConstants.EVENT_TYPE).toString());
            optionalEvent.ifPresent(eventEnum -> {
                switch (eventEnum) {
                    case CONNECT:
                        eventPublisher.publishMqttConnectEvent(MqttEventEnum.CONNECT, event.getMessage());
                        break;
                    case CLOSE:
                        eventPublisher.publishMqttCloseEvent(MqttEventEnum.CLOSE, event.getMessage());
                        break;
                    case DISCONNECT:
                        eventPublisher.publishMqttDisconnectEvent(MqttEventEnum.DISCONNECT, event.getMessage());
                        break;
                    case PUBLISH:
                        eventPublisher.publishMqttPublishEvent(MqttEventEnum.PUBLISH, event.getMessage());
                        break;
                    case SUBSCRIBE:
                        eventPublisher.publishMqttSubscribeEvent(MqttEventEnum.SUBSCRIBE, event.getMessage());
                        break;
                    case UNSUBSCRIBE:
                        eventPublisher.publishMqttUnsubscribeEvent(MqttEventEnum.UNSUBSCRIBE, event.getMessage());
                        break;
                    case PING:
                        eventPublisher.publishMqttPingEvent(MqttEventEnum.PING, event.getMessage());
                        break;
                    case ERROR:
                        eventPublisher.publishMqttErrorEvent(MqttEventEnum.ERROR, event.getMessage());
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception e) {
            log.error("处理MQTT事件-->处理失败，失败原因：{}", e.getMessage(), e);
        }
    }

}
