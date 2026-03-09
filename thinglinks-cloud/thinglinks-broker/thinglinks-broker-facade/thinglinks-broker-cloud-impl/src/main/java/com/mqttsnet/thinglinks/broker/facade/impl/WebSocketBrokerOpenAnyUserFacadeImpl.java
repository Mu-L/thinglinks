package com.mqttsnet.thinglinks.broker.facade.impl;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.broker.WebSocketBrokerOpenAnyUserFacade;
import com.mqttsnet.thinglinks.broker.api.WebSocketBrokerOpenAnyUserApi;
import com.mqttsnet.thinglinks.vo.query.PublishWebSocketMessageRequestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * @author tangyh
 * @since 2024/12/24 15:54
 */
@Service
public class WebSocketBrokerOpenAnyUserFacadeImpl implements WebSocketBrokerOpenAnyUserFacade {
    @Autowired
    @Lazy
    private WebSocketBrokerOpenAnyUserApi webSocketBrokerOpenAnyUserApi;

    @Override
    public R<?> sendMessage(PublishWebSocketMessageRequestVO publishMessageRequestVO) {
        return webSocketBrokerOpenAnyUserApi.sendMessage(publishMessageRequestVO);
    }
}
