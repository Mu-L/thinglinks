package com.mqttsnet.thinglinks.oauth.event.listener;

import cn.hutool.core.bean.BeanUtil;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.oauth.event.LoginEvent;
import com.mqttsnet.thinglinks.oauth.event.model.LoginStatusDTO;
import com.mqttsnet.thinglinks.system.enumeration.system.LoginStatusEnum;
import com.mqttsnet.thinglinks.system.service.system.DefLoginLogService;
import com.mqttsnet.thinglinks.system.service.tenant.DefUserService;
import com.mqttsnet.thinglinks.system.vo.save.system.DefLoginLogSaveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 登录事件监听，用于记录登录日志
 *
 * @author mqttsnet
 * @date 2020年03月18日17:39:59
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class LoginListener {
    private final DefLoginLogService defLoginLogService;
    private final DefUserService defUserService;

    @Async
    @EventListener({LoginEvent.class})
    public void saveSysLog(LoginEvent event) {
        LoginStatusDTO loginStatus = (LoginStatusDTO) event.getSource();

        ContextUtil.setTenantId(loginStatus.getTenantId());
        if (LoginStatusEnum.SUCCESS.eq(loginStatus.getStatus())) {
            // 重置错误次数 和 最后登录时间
            this.defUserService.resetPassErrorNum(loginStatus.getUserId());
        } else if (LoginStatusEnum.PASSWORD_ERROR.eq(loginStatus.getStatus())) {
            // 密码错误
            this.defUserService.incrPasswordErrorNumById(loginStatus.getUserId());
        }
        DefLoginLogSaveVO saveVO = BeanUtil.toBean(loginStatus, DefLoginLogSaveVO.class);
        defLoginLogService.save(saveVO);
    }

}
