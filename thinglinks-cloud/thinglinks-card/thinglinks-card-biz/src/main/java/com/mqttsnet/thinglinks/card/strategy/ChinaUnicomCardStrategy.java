package com.mqttsnet.thinglinks.card.strategy;

import com.alibaba.fastjson2.JSON;
import com.mqttsnet.thinglinks.card.abstrac.AbstractCard;
import com.mqttsnet.thinglinks.card.entity.auto.IotCardAuthToken;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfo;
import com.mqttsnet.thinglinks.card.enumeration.OperatorTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;


/**
 * 中国联通卡具体实现类。
 *
 * @Author: shisen
 * @Date: 2024/06/27 22:13
 */
@Slf4j
@Component
public class ChinaUnicomCardStrategy extends AbstractCard {

    @Autowired
    public ChinaUnicomCardStrategy(RestTemplate restTemplate) {
        super(restTemplate);
    }

    @Override
    public OperatorTypeEnum getSupportId() {
        return OperatorTypeEnum.CHINA_UNICOM;
    }

    @Override
    public String getName() {
        return OperatorTypeEnum.CHINA_UNICOM.getDesc();
    }


    /**
     * 新增卡源所需要填写的密钥参数,前端动态变更
     *
     * @return
     */
    @Override
    public Map<String, String> getKeyParameters() {
        Map keyMap = new HashMap();
        keyMap.put("APP_ID", "运营商提供 APP ID");
        keyMap.put("APP_SECRET", "运营商提供 APP SECRET");
        keyMap.put("open_ID", "运营商提供 创建者ID(openID)");
        return keyMap;
    }

    @Override
    public IotCardAuthToken authenticate(CardChannelInfo channelInfo, IotCardAuthToken iotToken) {
        // 实现中国联通卡的认证逻辑
        Map iotKeyParameterMap = JSON.parseObject(channelInfo.getKeyParameter(), Map.class);
        iotKeyParameterMap.put("access_number", iotToken.getCardNum());
        iotToken.setParameters(iotKeyParameterMap);
        return iotToken;
    }
}