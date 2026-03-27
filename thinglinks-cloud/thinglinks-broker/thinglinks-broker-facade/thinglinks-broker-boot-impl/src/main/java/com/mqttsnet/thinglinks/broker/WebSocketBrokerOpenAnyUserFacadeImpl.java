package com.mqttsnet.thinglinks.broker;

import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.thinglinks.service.WebSocketBrokerService;
import com.mqttsnet.thinglinks.vo.query.PublishWebSocketMessageRequestVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tangyh
 * @since 2024/12/24 15:54
 */
@Service
@Slf4j
public class WebSocketBrokerOpenAnyUserFacadeImpl implements WebSocketBrokerOpenAnyUserFacade {
    @Autowired
    private WebSocketBrokerService webSocketBrokerService;

    @Override
    public R<?> sendMessage(PublishWebSocketMessageRequestVO publishMessageRequestVO) {
        log.info("Received request to send message.param {}", JSONUtil.toJsonStr(publishMessageRequestVO));
        try {
            return R.success(webSocketBrokerService.publishMessage(publishMessageRequestVO));
        } catch (BizException e) {
            log.error("Failed to send message. param: {}", JSONUtil.toJsonStr(publishMessageRequestVO), e);
            return R.fail(e.getMessage());
        }
    }
}
