package com.mqttsnet.thinglinks;

import com.mqttsnet.basic.validator.annotation.EnableFormValidator;
import com.mqttsnet.thinglinks.common.ServerApplication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

import java.net.UnknownHostException;

import static com.mqttsnet.thinglinks.common.constant.BizConstant.BUSINESS_PACKAGE;
import static com.mqttsnet.thinglinks.common.constant.BizConstant.UTIL_PACKAGE;

/**
 * 认证服务 启动类
 *
 * @author mqttsnet
 * @date 2020年03月23日16:24:37
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan({UTIL_PACKAGE, BUSINESS_PACKAGE})
@EnableFeignClients(value = {UTIL_PACKAGE, BUSINESS_PACKAGE})
@Slf4j
@EnableFormValidator
public class OauthServerApplication extends ServerApplication {
    public static void main(String[] args) throws UnknownHostException {
        start(OauthServerApplication.class, args);
    }
}
