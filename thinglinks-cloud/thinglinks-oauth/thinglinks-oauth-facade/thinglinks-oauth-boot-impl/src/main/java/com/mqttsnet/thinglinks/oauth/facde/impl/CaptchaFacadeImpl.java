package com.mqttsnet.thinglinks.oauth.facde.impl;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.oauth.facade.CaptchaFacade;
import com.mqttsnet.thinglinks.oauth.service.CaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 *
 * @author tangyh
 * @since 2024/9/20 15:42
 */
@Service
@RequiredArgsConstructor
public class CaptchaFacadeImpl implements CaptchaFacade {

    private final CaptchaService captchaService;

    public Boolean check(String key, String code, String templateCode) {
        R<Boolean> result = captchaService.checkCaptcha(key, code, templateCode);
        return result.getData();
    }
}
