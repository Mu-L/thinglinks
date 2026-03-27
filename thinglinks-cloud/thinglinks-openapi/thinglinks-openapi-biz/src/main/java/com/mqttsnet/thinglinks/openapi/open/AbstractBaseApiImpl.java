package com.mqttsnet.thinglinks.openapi.open;

import cn.hutool.core.bean.BeanUtil;
import com.gitee.sop.support.context.OpenContext;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.sop.dto.NotifyRequest;
import com.mqttsnet.thinglinks.sop.facade.NotifyFacade;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;

/**
 * API实现类的抽象父类，提供通用的回调方法
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/12/11
 */
@Slf4j
public abstract class AbstractBaseApiImpl {

    @Resource
    protected NotifyFacade notifyFacade;

    /**
     * 发送回调通知
     *
     * @param openContext 上下文参数，包含认证信息和请求上下文
     * @param result      回调业务参数，泛型T表示业务结果类型
     * @param remark      回调备注
     * @param <T>         业务结果类型
     */
    protected <T> void sendNotifyTask(OpenContext openContext, T result, String remark) {
        // 回调
        NotifyRequest notifyRequest = new NotifyRequest();
        notifyRequest.setAppId(openContext.getAppId());
        notifyRequest.setApiName(openContext.getApiName());
        notifyRequest.setVersion(openContext.getVersion());
        notifyRequest.setClientIp(openContext.getClientIp());
        notifyRequest.setNotifyUrl(openContext.getNotifyUrl());
        notifyRequest.setCharset(openContext.getCharset());
        notifyRequest.setTenantId(openContext.getTenantId());
        notifyRequest.setAppAuthToken(openContext.getAppAuthToken());
        try {
            //回调业务参数
            notifyRequest.setBizParams(BeanUtil.beanToMap(result));
            notifyRequest.setRemark(remark);
            // 发送回调任务
            R<Long> notifyResponse = notifyFacade.notify(notifyRequest);
            log.info("回调返回,notifyResponse={}", notifyResponse);
        } catch (Exception e) {
            log.error("回调异常,notifyRequest={}", notifyRequest, e);
        }
    }
}