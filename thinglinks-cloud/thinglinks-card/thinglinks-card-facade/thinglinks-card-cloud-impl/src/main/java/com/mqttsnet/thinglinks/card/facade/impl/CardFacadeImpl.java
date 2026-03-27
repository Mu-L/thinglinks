package com.mqttsnet.thinglinks.card.facade.impl;

import com.mqttsnet.thinglinks.card.CardFacade;
import com.mqttsnet.thinglinks.card.api.CardApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 *
 * @author tangyh
 * @since 2024/12/24 15:34
 */
@Service
public class CardFacadeImpl implements CardFacade {
    @Autowired
    @Lazy
    private CardApi cardApi;


}
