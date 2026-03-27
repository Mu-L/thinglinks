package com.mqttsnet.thinglinks.msg.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.msg.entity.ExtendNotice;
import com.mqttsnet.thinglinks.msg.manager.ExtendNoticeManager;
import com.mqttsnet.thinglinks.msg.mapper.ExtendNoticeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 通知表
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-04 15:51:37
 * @create [2022-07-04 15:51:37] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ExtendNoticeManagerImpl extends SuperManagerImpl<ExtendNoticeMapper, ExtendNotice> implements ExtendNoticeManager {

}


