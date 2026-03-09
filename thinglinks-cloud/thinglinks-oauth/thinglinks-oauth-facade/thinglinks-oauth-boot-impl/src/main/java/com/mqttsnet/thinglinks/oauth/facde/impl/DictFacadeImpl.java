package com.mqttsnet.thinglinks.oauth.facde.impl;

import com.mqttsnet.thinglinks.model.constant.EchoApi;
import com.mqttsnet.thinglinks.oauth.facade.DictFacade;
import com.mqttsnet.thinglinks.oauth.service.DictService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 字典实现
 * @author tangyh
 * @since 2024/9/20 23:29
 */
@Service(EchoApi.DICTIONARY_ITEM_FEIGN_CLASS)
@RequiredArgsConstructor
public class DictFacadeImpl implements DictFacade {
    private final DictService dictService;

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {
        return dictService.findByIds(ids);
    }
}
