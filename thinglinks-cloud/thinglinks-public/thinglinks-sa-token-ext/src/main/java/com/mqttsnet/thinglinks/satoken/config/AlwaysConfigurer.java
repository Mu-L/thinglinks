package com.mqttsnet.thinglinks.satoken.config;

import com.mqttsnet.thinglinks.common.properties.SystemProperties;
import com.mqttsnet.thinglinks.satoken.interceptor.NotAllowWriteInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 永远执行的配置
 *
 * @author zuihou
 * @date 2018/8/25
 */
@RequiredArgsConstructor
public class AlwaysConfigurer implements WebMvcConfigurer {
    private final SystemProperties systemProperties;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new NotAllowWriteInterceptor(systemProperties))
                .addPathPatterns("/**")
                .order(Integer.MAX_VALUE);
    }
}
