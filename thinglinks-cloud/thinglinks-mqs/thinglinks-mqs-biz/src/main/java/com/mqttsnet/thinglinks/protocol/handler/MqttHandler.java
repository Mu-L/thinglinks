package com.mqttsnet.thinglinks.protocol.handler;

import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;
import com.mqttsnet.thinglinks.protocol.event.publisher.ProtocolMessageEventPublisher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * -----------------------------------------------------------------------------
 * File Name: MqttHandler
 * -----------------------------------------------------------------------------
 * Description:
 * MQTT协议处理器
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
 * @date 2024/3/11 14:57
 */
@Component
@Slf4j
public class MqttHandler implements ProtocolHandler {

    @Autowired
    private ProtocolMessageEventPublisher eventPublisher;

    @Override
    public void processMessage(String message) {
        // MQTT协议特定的预处理逻辑
        eventPublisher.publishEvent(getProtocolTypeEnum(), message);
    }


    /**
     * 获取协议类型枚举
     *
     * @return 协议类型
     */
    @Override
    public ProtocolTypeEnum getProtocolTypeEnum() {
        return ProtocolTypeEnum.MQTT;
    }

}
