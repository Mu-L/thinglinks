package com.mqttsnet.thinglinks.card.supports;

import com.mqttsnet.thinglinks.card.abstrac.AbstractCard;
import com.mqttsnet.thinglinks.card.enumeration.OperatorTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理不同运营商的支持类。
 *
 * @Author: shisen
 * @Date: 2024/06/27 14:14
 */
@Component
@Slf4j
public class DefaultSupportManager {

    private final Map<OperatorTypeEnum, AbstractCard> providers = new ConcurrentHashMap<>();
    private final Map<OperatorTypeEnum, Map<String, String>> providersUrl = new ConcurrentHashMap<>();

    @Autowired
    public void postProcessAfterInitialization(AbstractCard... beans) throws BeansException {
        for (AbstractCard provider : beans) {
            providers.put(provider.getSupportId(), provider);
        }
    }

    public void postProvidersUrlAfterInitialization(OperatorTypeEnum supportId, Map<String, String> URL) throws BeansException {
        providersUrl.put(supportId, URL);
    }

    public AbstractCard getSpecificProviders(OperatorTypeEnum supportId) {
        return providers.get(supportId);
    }

    public Map<String, String> getProviderUrl(OperatorTypeEnum supportId) {
        return providersUrl.get(supportId);
    }

    public List<AbstractCard> getProviders() {
        return new ArrayList<>(providers.values());
    }
}
