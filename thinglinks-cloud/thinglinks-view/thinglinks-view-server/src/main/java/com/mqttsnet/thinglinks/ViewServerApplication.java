package com.mqttsnet.thinglinks;

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

/**
 * 可视化启动类
 *
 * @author mqttsnet
 * @date 2023-05-16 13:02:26
 */
@SpringBootApplication
@EnableDiscoveryClient
@Configuration
@EnableFeignClients(value = {"com.mqttsnet.thinglinks", "com.mqttsnet.basic"})
@ComponentScan(basePackages = {"com.mqttsnet.thinglinks", "com.mqttsnet.basic"})
@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Slf4j
@EnableFormValidator
@EnableAsync
public class ViewServerApplication extends ServerApplication {
    public static void main(String[] args) throws UnknownHostException {
        start(ViewServerApplication.class, args);
    }
}