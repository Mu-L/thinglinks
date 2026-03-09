package com.mqttsnet.thinglinks.oauth.facade.impl;

import com.mqttsnet.thinglinks.model.constant.EchoApi;
import com.mqttsnet.thinglinks.oauth.api.OrgApi;
import com.mqttsnet.thinglinks.oauth.facade.OrgFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    @Autowired
    @Lazy  // 一定要延迟加载，否则thinglinks-gateway-server无法启动
    private OrgApi orgApi;

    @Override
    public Map<Serializable, Object> findByIds(Set<Serializable> ids) {
        return orgApi.findByIds(ids);
    }
}
