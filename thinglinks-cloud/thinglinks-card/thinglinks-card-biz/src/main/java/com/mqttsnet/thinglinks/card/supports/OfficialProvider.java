package com.mqttsnet.thinglinks.card.supports;

import com.mqttsnet.thinglinks.card.entity.auto.IotCardAuthToken;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfo;
import com.mqttsnet.thinglinks.card.enumeration.OperatorTypeEnum;

import java.io.IOException;
import java.util.Map;

/**
 * 定义官方提供商操作的接口。
 *
 * @Author: shisen
 * @Date: 2024/06/27 10:56
 */
public interface OfficialProvider {

    /**
     * 获取提供商的支持ID。
     *
     * @return 表示提供商支持ID的OperatorTypeEnum。
     */
    OperatorTypeEnum getSupportId();

    /**
     * 获取提供商的名称。
     *
     * @return 表示提供商名称的字符串。
     */
    String getName();

    /**
     * 获取认证所需的关键参数。
     *
     * @return 包含关键参数的Map。
     */
    Map<String, String> getKeyParameters();

    /**
     * 进行认证并获取令牌。
     *
     * @param channelInfo 渠道信息。
     * @param iotToken    IOT卡令牌。
     * @return 含有认证详情的IotCardToken。
     */
    IotCardAuthToken authenticate(CardChannelInfo channelInfo, IotCardAuthToken iotToken) throws IOException;
}