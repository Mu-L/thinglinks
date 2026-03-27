package com.mqttsnet.thinglinks.protocol.event.listener;

import java.util.Optional;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.common.constant.CommonConstants;
import com.mqttsnet.thinglinks.entity.protocol.TcpEvent;
import com.mqttsnet.thinglinks.enumeration.TcpEventEnum;
import com.mqttsnet.thinglinks.tcp.event.publisher.TcpEventPublisher;
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
 * File Name: TcpEventListener
 * -----------------------------------------------------------------------------
 * Description:
 * TCP事件监听器
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
 * @date 2024/3/11 15:55
 */
@Component
@Slf4j
public class TcpEventListener {


    @Autowired
    private TcpEventPublisher eventPublisher;


    @Autowired
    private LinkCacheDataHelper linkCacheDataHelper;


    /**
     * 发布TCP事件
     *
     * @param event 事件消息
     */
    @EventListener
    @Async("systemDefaultExecutor")
    @Retryable(value = {Exception.class}, maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public void handleTcpEvent(TcpEvent event) {
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
            Optional<TcpEventEnum> optionalEvent = TcpEventEnum.getTcpEventEnum(thinglinksMessage.get(CommonConstants.EVENT_TYPE).toString());
            optionalEvent.ifPresent(eventEnum -> {
                switch (eventEnum) {
                    case CONNECT:
                        eventPublisher.publishTcpConnectEvent(TcpEventEnum.CONNECT, event.getMessage());
                        break;
                    case CLOSE:
                        eventPublisher.publishTcpCloseEvent(TcpEventEnum.CLOSE, event.getMessage());
                        break;
                    case DISCONNECT:
                        eventPublisher.publishTcpDisconnectEvent(TcpEventEnum.DISCONNECT, event.getMessage());
                        break;
                    case PUBLISH:
                        eventPublisher.publishTcpPublishEvent(TcpEventEnum.PUBLISH, event.getMessage());
                        break;
                    case PING:
                        eventPublisher.publishTcpPingEvent(TcpEventEnum.PING, event.getMessage());
                        break;
                    default:
                        break;
                }
            });
        } catch (Exception e) {
            log.error("处理TCP事件-->处理失败，失败原因：{}", e.getMessage());
        }
    }
}
