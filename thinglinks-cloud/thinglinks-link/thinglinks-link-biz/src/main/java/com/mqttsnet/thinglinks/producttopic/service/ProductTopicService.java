package com.mqttsnet.thinglinks.producttopic.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.product.enumeration.ProductTypeEnum;
import com.mqttsnet.thinglinks.producttopic.entity.ProductTopic;


/**
 * <p>
 * 业务接口
 * 产品Topic信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
public interface ProductTopicService extends SuperService<Long, ProductTopic> {


    /**
     * 初始化产品基础Topic
     *
     * @param productIdentification 产品标识
     * @param productTypeEnum       产品类型枚举
     * @param reInit                是否重新初始化
     */
    void initProductBaseTopics(String productIdentification, ProductTypeEnum productTypeEnum, Boolean reInit);


}


