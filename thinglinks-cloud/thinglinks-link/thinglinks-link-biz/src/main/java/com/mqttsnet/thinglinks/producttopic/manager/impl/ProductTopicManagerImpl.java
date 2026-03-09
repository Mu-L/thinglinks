package com.mqttsnet.thinglinks.producttopic.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.producttopic.entity.ProductTopic;
import com.mqttsnet.thinglinks.producttopic.manager.ProductTopicManager;
import com.mqttsnet.thinglinks.producttopic.mapper.ProductTopicMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 产品Topic信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet] 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class ProductTopicManagerImpl extends SuperManagerImpl<ProductTopicMapper, ProductTopic> implements ProductTopicManager {

}


