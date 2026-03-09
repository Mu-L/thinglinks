package com.mqttsnet.thinglinks.gateway.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.gateway.config.ServletProperties;
import com.mqttsnet.thinglinks.model.vo.result.Option;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.handler.predicate.PredicateDefinition;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.reactive.result.view.Rendering;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


/**
 * 常用Controller
 *
 * @author mqttsnet
 * @date 2019-06-21 18:22
 */
@Controller
@RequiredArgsConstructor
@EnableConfigurationProperties({ServletProperties.class})
public class GeneratorController {
    private final ServletProperties servletProperties;
    @Autowired
    private DiscoveryClient discoveryClient;
    @Autowired
    private GatewayProperties gatewayProperties;
    @Value("${spring.application.name:}")
    private String application;

    /**
     * 兼容zuul 文档
     */
    @GetMapping("/gate/doc.html")
    public Rendering doc() {
        String uri = String.format("%s/doc.html", servletProperties.getContextPath());
        return Rendering.redirectTo(uri).build();
    }


    @ResponseBody
    @Operation(summary = "查询在线服务的前缀")
    @GetMapping("/gateway/findOnlineServicePrefix")
    public R<Map<String, String>> findOnlineServicePrefix() {
        List<String> services = discoveryClient.getServices();
        Set<String> excludedSet = new HashSet<>(Optional.ofNullable(servletProperties)
                .map(ServletProperties::getExcludedOnline)
                .map(ServletProperties.ExcludedOnline::getServices)
                .orElse(Collections.emptyList()));
        Map<String, String> map = MapUtil.newHashMap();
        map.put(application, "gateway");
        services.stream()
                .filter(service -> !excludedSet.contains(service))
                .distinct()
                .forEach(service ->
                        gatewayProperties.getRoutes().forEach(route -> {
                            if (StrUtil.equalsIgnoreCase(service, route.getUri().getHost())) {
                                if (CollUtil.isEmpty(route.getPredicates())) {
                                    return;
                                }
                                PredicateDefinition predicateDefinition = route.getPredicates().get(0);
                                predicateDefinition.getArgs().forEach((k, v) -> {
                                    map.put(service, StrUtil.subBetween(v, "/", "/**"));
                                });
                            }
                        })
                );
        return R.success(map);
    }

    @ResponseBody
    @Operation(summary = "查询在线服务")
    @GetMapping("/gateway/findOnlineService")
    public R<List<Option>> findOnlineService() {
        List<String> services = discoveryClient.getServices();
        Set<String> excludedSet = new HashSet<>(Optional.ofNullable(servletProperties)
                .map(ServletProperties::getExcludedOnline)
                .map(ServletProperties.ExcludedOnline::getServices)
                .orElse(Collections.emptyList()));

        List<Option> list = new ArrayList();
        services.stream()
                .filter(service -> !excludedSet.contains(service))
                .distinct()
                .forEach(service ->
                        gatewayProperties.getRoutes().forEach(route -> {
                            if (StrUtil.equalsIgnoreCase(service, route.getUri().getHost())) {
                                if (CollUtil.isEmpty(route.getPredicates())) {
                                    return;
                                }
                                PredicateDefinition predicateDefinition = route.getPredicates().get(0);
                                predicateDefinition.getArgs().forEach((k, v) -> {
                                    list.add(Option.builder()
                                            .value(StrUtil.subBetween(v, "/", "/**"))
                                            .remark(service)
                                            .label(service)
                                            .build());
                                });
                            }
                        })
                );
        return R.success(list);
    }
}
