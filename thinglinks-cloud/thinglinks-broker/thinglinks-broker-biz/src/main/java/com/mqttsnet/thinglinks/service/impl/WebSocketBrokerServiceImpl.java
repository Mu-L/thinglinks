package com.mqttsnet.thinglinks.service.impl;

import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.common.mq.RabbitmqConsumerConstant;
import com.mqttsnet.thinglinks.service.WebSocketBrokerService;
import com.mqttsnet.thinglinks.vo.query.PublishWebSocketMessageRequestVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

/**
 * -----------------------------------------------------------------------------
 * File Name: WebSocketBrokerServiceImpl.java
 * -----------------------------------------------------------------------------
 * Description:
 * WebSocketBroker API 实现类
 * -----------------------------------------------------------------------------
 *
 * @author ShiHuan Sun
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * <p>
 * -----------------------------------------------------------------------------
 * @email 13733918655@163.com
 * @date 2023-10-31 19:44
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WebSocketBrokerServiceImpl implements WebSocketBrokerService {


    private final LinkCacheDataHelper linkCacheDataHelper;

    private final RabbitTemplate rabbitTemplate;


    /**
     * Publishes a message to a specified topic and returns the content if successful.
     *
     * @param publishMessageRequestVO Object containing the required parameters for publishing.
     * @return The content of the published message.
     * @throws BizException If the publishing fails.
     */
    @Override
    public String publishMessage(PublishWebSocketMessageRequestVO publishMessageRequestVO) throws BizException {
        log.info("Preparing to publish webSocket message with topic: {}", publishMessageRequestVO.getTopic());
        linkCacheDataHelper.incrementDownLinkCounter();
        try {
            rabbitTemplate.convertAndSend(
                    RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.Exchanges.DEVICE_DIRECT_EXCHANGE,
                    RabbitmqConsumerConstant.Mqs.WebSocketClusterInfra.RouteKeys.DEVICE_COMMAND_SEND,
                    JsonUtil.toJson(publishMessageRequestVO)
            );
            log.info("Successfully sent webSocket message to RabbitMQ: command to {}", publishMessageRequestVO.getTopic());
            return JsonUtil.toJson(publishMessageRequestVO);
        } catch (AmqpException e) {
            log.error("RabbitMQ通信异常: {}", e.getMessage());
            throw new BizException("Message queue service unavailable");
        } catch (Exception e) {
            log.error("消息发送失败: {}", e.getMessage());
            throw new BizException("Message delivery failed: " + e.getMessage());
        }
    }

}
