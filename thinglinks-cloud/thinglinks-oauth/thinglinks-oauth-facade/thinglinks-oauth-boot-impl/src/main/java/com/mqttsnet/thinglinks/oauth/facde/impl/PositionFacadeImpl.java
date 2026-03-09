package com.mqttsnet.thinglinks.oauth.facde.impl;

import com.mqttsnet.thinglinks.base.service.user.BasePositionService;
import com.mqttsnet.thinglinks.model.constant.EchoApi;
import com.mqttsnet.thinglinks.oauth.facade.PositionFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

/**
 * 实现
 * @author tangyh
 * @since 2024/9/20 23:29
 */
@Service(EchoApi.POSITION_ID_CLASS)
@RequiredArgsConstructor
public class PositionFacadeImpl implements PositionFacade {
    private final BasePositionService basePositionService;

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {
        return basePositionService.findByIds(ids);
    }
}
