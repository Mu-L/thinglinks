package com.mqttsnet.thinglinks.card.manager.channel.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfo;
import com.mqttsnet.thinglinks.card.manager.channel.CardChannelInfoManager;
import com.mqttsnet.thinglinks.card.mapper.channel.CardChannelInfoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 物联卡渠道表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 21:55:13
 * @create [2024-06-26 21:55:13] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CardChannelInfoManagerImpl extends SuperManagerImpl<CardChannelInfoMapper, CardChannelInfo> implements CardChannelInfoManager {

}


