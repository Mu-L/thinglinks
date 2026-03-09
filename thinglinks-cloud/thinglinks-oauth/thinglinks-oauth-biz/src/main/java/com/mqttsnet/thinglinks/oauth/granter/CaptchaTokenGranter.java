package com.mqttsnet.thinglinks.oauth.granter;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.basic.utils.StrHelper;
import com.mqttsnet.thinglinks.oauth.event.LoginEvent;
import com.mqttsnet.thinglinks.oauth.event.model.LoginStatusDTO;
import com.mqttsnet.thinglinks.oauth.service.CaptchaService;
import com.mqttsnet.thinglinks.oauth.vo.param.LoginParamVO;
import com.mqttsnet.thinglinks.oauth.vo.result.LoginResultVO;
import com.mqttsnet.thinglinks.system.enumeration.system.LoginStatusEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.mqttsnet.thinglinks.oauth.granter.CaptchaTokenGranter.GRANT_TYPE;

/**
 * 验证码TokenGranter
 *
 * @author mqttsnet
 */
@Component(GRANT_TYPE)
@Slf4j
@RequiredArgsConstructor
public class CaptchaTokenGranter extends PasswordTokenGranter implements TokenGranter {

    public static final String GRANT_TYPE = "CAPTCHA";
    private final CaptchaService captchaService;

    @Override
    protected R<LoginResultVO> checkCaptcha(LoginParamVO loginParam) {
        if (systemProperties.getVerifyCaptcha()) {
            R<Boolean> check = captchaService.checkCaptcha(loginParam.getKey(), GRANT_TYPE, loginParam.getCode());
            if (!check.getIsSuccess()) {
                String msg = check.getMsg();
                SpringUtils.publishEvent(new LoginEvent(LoginStatusDTO.fail(null, loginParam.getUsername(), LoginStatusEnum.CAPTCHA_ERROR, msg)));
                throw BizException.validFail(check.getMsg());
            }
        }
        return R.success(null);
    }

    @Override
    public R<LoginResultVO> checkParam(LoginParamVO loginParam) {
        String username = loginParam.getUsername();
        String password = loginParam.getPassword();
        if (StrHelper.isAnyBlank(username, password)) {
            return R.fail("请输入用户名或密码");
        }
        if (StrHelper.isAnyBlank(loginParam.getCode(), loginParam.getKey())) {
            return R.fail("请输入验证码");
        }

        return R.success(null);
    }

}
