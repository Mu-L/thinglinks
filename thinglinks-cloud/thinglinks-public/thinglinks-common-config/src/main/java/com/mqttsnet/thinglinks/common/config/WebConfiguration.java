package com.mqttsnet.thinglinks.common.config;

import com.mqttsnet.basic.boot.config.BaseConfig;
import com.mqttsnet.basic.constant.Constants;
import com.mqttsnet.basic.log.event.SysLogListener;
import com.mqttsnet.thinglinks.oauth.facade.LogFacade;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 基础服务-Web配置
 *
 * @author zuihou
 * @date 2021-10-08
 */
@Configuration
public class WebConfiguration extends BaseConfig {

    /**
     *
     */
    @Bean
    @ConditionalOnExpression("${" + Constants.PROJECT_PREFIX + ".log.enabled:true} && 'DB'.equals('${" + Constants.PROJECT_PREFIX + ".log.type:LOGGER}')")
    public SysLogListener sysLogListener(LogFacade logApi) {
        return new SysLogListener(logApi::save);
    }
}
