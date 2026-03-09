package com.mqttsnet.thinglinks.card.service.channel.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfoConfig;
import com.mqttsnet.thinglinks.card.manager.channel.CardChannelInfoConfigManager;
import com.mqttsnet.thinglinks.card.service.channel.CardChannelInfoConfigService;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 物联卡渠道信息配置表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-27 00:06:12
 * @create [2024-06-27 00:06:12] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class CardChannelInfoConfigServiceImpl extends SuperServiceImpl<CardChannelInfoConfigManager, Long, CardChannelInfoConfig> implements CardChannelInfoConfigService {


}


