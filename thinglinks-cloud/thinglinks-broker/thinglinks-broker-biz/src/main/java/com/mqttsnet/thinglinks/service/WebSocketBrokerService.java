package com.mqttsnet.thinglinks.service;

import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.thinglinks.vo.query.PublishWebSocketMessageRequestVO;

/**
 * -----------------------------------------------------------------------------
 * File Name: WebSocketBrokerService.java
 * -----------------------------------------------------------------------------
 * Description:
 * WebSocket Broker API
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
 * @date 2023-10-31 19:43
 */
public interface WebSocketBrokerService {


    /**
     * Publishes a message to a specified topic and returns the content if successful.
     *
     * @param publishMessageRequestVO Object containing the required parameters for publishing.
     * @return The content of the published message.
     * @throws BizException If the publishing fails.
     */
    String publishMessage(PublishWebSocketMessageRequestVO publishMessageRequestVO);
}
