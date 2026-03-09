package com.mqttsnet.thinglinks.card.manager.sim.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.card.entity.sim.CardSimDevice;
import com.mqttsnet.thinglinks.card.manager.sim.CardSimDeviceManager;
import com.mqttsnet.thinglinks.card.mapper.sim.CardSimDeviceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 物联卡设备关系表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-27 00:10:22
 * @create [2024-06-27 00:10:22] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CardSimDeviceManagerImpl extends SuperManagerImpl<CardSimDeviceMapper, CardSimDevice> implements CardSimDeviceManager {

}


