package com.mqttsnet.thinglinks.oauth.facde.impl;

import com.mqttsnet.thinglinks.base.service.user.BaseOrgService;
import com.mqttsnet.thinglinks.model.constant.EchoApi;
import com.mqttsnet.thinglinks.oauth.facade.OrgFacade;
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
@Service(EchoApi.ORG_ID_CLASS)
@RequiredArgsConstructor
public class OrgFacadeImpl implements OrgFacade {
    private final BaseOrgService baseOrgService;

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {
        return baseOrgService.findByIds(ids);
    }
}
