package com.mqttsnet.thinglinks.card.service.sim.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.card.entity.sim.CardSimDevice;
import com.mqttsnet.thinglinks.card.manager.sim.CardSimDeviceManager;
import com.mqttsnet.thinglinks.card.service.sim.CardSimDeviceService;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 物联卡设备关系表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-27 00:10:22
 * @create [2024-06-27 00:10:22] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class CardSimDeviceServiceImpl extends SuperServiceImpl<CardSimDeviceManager, Long, CardSimDevice> implements CardSimDeviceService {


}


