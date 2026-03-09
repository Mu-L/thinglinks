package com.mqttsnet.thinglinks.card.manager.channel.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfoConfig;
import com.mqttsnet.thinglinks.card.manager.channel.CardChannelInfoConfigManager;
import com.mqttsnet.thinglinks.card.mapper.channel.CardChannelInfoConfigMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 物联卡渠道信息配置表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-27 00:06:12
 * @create [2024-06-27 00:06:12] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CardChannelInfoConfigManagerImpl extends SuperManagerImpl<CardChannelInfoConfigMapper, CardChannelInfoConfig> implements CardChannelInfoConfigManager {

}


