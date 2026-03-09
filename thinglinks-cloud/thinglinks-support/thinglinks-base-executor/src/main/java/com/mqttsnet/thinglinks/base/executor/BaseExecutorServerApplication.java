package com.mqttsnet.thinglinks.base.executor;

import com.mqttsnet.basic.validator.annotation.EnableFormValidator;
import com.mqttsnet.thinglinks.common.ServerApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.UnknownHostException;

import static com.mqttsnet.thinglinks.common.constant.BizConstant.BUSINESS_PACKAGE;
import static com.mqttsnet.thinglinks.common.constant.BizConstant.UTIL_PACKAGE;

/**
 * 基础执行器
 *
 * @author xiaonannet
 */
@SpringBootApplication
@EnableDiscoveryClient
@Configuration
@ComponentScan({
        UTIL_PACKAGE, BUSINESS_PACKAGE
})
@EnableFeignClients(value = {UTIL_PACKAGE, BUSINESS_PACKAGE})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Slf4j
@EnableAsync
@EnableFormValidator
public class BaseExecutorServerApplication extends ServerApplication {

    public static void main(String[] args) throws UnknownHostException {
        start(BaseExecutorServerApplication.class, args);
    }

}
