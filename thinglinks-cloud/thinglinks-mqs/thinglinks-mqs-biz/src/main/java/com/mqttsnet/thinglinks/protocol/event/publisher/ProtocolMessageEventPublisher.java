package com.mqttsnet.thinglinks.protocol.event.publisher;

import java.util.Map;

import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.entity.protocol.HttpEvent;
import com.mqttsnet.thinglinks.entity.protocol.MqttEvent;
import com.mqttsnet.thinglinks.entity.protocol.TcpEvent;
import com.mqttsnet.thinglinks.entity.protocol.WebSocketEvent;
import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * -----------------------------------------------------------------------------
 * File Name: ProtocolMessageEventPublisher
 * -----------------------------------------------------------------------------
 * Description:
 * 协议消息事件发布器
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
 * @date 2024/3/11 15:00
 */
@Component
@Slf4j
public class ProtocolMessageEventPublisher {

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    /**
     * 根据协议类型发布对应的事件
     *
     * @param protocolType 协议类型
     * @param message      消息内容
     */
    public void publishEvent(ProtocolTypeEnum protocolType, String message) {
        Map<String, String> localMap = ContextUtil.getLocalMap();
        switch (protocolType) {
            case MQTT:
                eventPublisher.publishEvent(new MqttEvent(this, localMap, message));
                break;
            case HTTP:
                eventPublisher.publishEvent(new HttpEvent(this, localMap, message));
                break;
            case TCP:
                eventPublisher.publishEvent(new TcpEvent(this, localMap, message));
                break;
            case WEBSOCKET:
                eventPublisher.publishEvent(new WebSocketEvent(this, localMap, message));
                break;
            // ...为其他协议添加case语句...
            default:
                log.error("未知的协议类型: {}", protocolType);
                break;
        }
    }

}
