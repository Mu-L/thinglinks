package com.mqttsnet.thinglinks.service;

import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;

/**
 * ============================================================================
 * Description:
 * 协议规则脚本Service 接口
 * ============================================================================
 *
 * @author Sun Shihuan
 * @version 1.0.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2025/4/13      Sun Shihuan        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2025/4/13 14:03
 */
public interface ProtocolGroovyScriptService {


    /**
     * Datas Topic 消息转换
     *
     * @param protocolTypeEnum      协议类型
     * @param deviceIdentification  设备标识
     * @param productIdentification 产品标识
     * @param originBody            原始消息体
     * @return {@link String} 转换后的消息体
     */
    String datasTopicBodyTransformed(ProtocolTypeEnum protocolTypeEnum, String deviceIdentification, String productIdentification, String originBody);
}
