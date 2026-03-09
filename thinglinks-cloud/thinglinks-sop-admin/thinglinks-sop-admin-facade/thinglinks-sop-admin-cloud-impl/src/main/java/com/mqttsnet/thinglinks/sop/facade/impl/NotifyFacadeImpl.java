package com.mqttsnet.thinglinks.sop.facade.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.sop.api.NotifyApi;
import com.mqttsnet.thinglinks.sop.dto.NotifyRequest;
import com.mqttsnet.thinglinks.sop.facade.NotifyFacade;

/**
 *
 * @author tangyh
 * @since 2025/12/18 00:10
 */
@Service
public class NotifyFacadeImpl implements NotifyFacade {
    @Autowired
    @Lazy
    private NotifyApi notifyApi;

    @Override
    public R<Long> notify(NotifyRequest request) {
        return notifyApi.notify(request);
    }
}
