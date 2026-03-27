package com.mqttsnet.thinglinks;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import com.mqttsnet.basic.validator.annotation.EnableFormValidator;
import com.mqttsnet.thinglinks.common.ServerApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import java.net.UnknownHostException;

import static com.mqttsnet.thinglinks.common.constant.BizConstant.BUSINESS_PACKAGE;
import static com.mqttsnet.thinglinks.common.constant.BizConstant.UTIL_PACKAGE;

/**
 * 开放接口启动类
 *
 * @author zuihou
 * @since 2025-05-18 17:37:42
 */
@SpringBootApplication
@EnableDiscoveryClient
@Configuration
@ComponentScan({UTIL_PACKAGE, BUSINESS_PACKAGE})
@EnableFeignClients(value = {UTIL_PACKAGE, BUSINESS_PACKAGE})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Slf4j
@EnableFormValidator
@EnableAsync
@EnableDubbo
public class OpenapiServerApplication extends ServerApplication {
    public static void main(String[] args) throws UnknownHostException {
        start(OpenapiServerApplication.class, args);
    }
}
