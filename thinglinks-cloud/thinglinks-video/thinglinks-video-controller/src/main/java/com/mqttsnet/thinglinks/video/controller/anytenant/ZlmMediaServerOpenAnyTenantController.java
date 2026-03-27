package com.mqttsnet.thinglinks.video.controller.anytenant;

import com.mqttsnet.thinglinks.video.service.anytenant.ZlmMediaServerOpenAnyTenantService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ZLM流媒体相关开放接口（anyTenant）
 * 接口必须携带TenantId 信息
 *
 * @author mqttsnet
 * @date 2024-07-08
 * @create [2024-07-08] [mqttsnet]
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/anyTenant/zlmMedia")
@Tag(name = "开放接口-ZLM流媒体相关")
public class ZlmMediaServerOpenAnyTenantController {

    private final ZlmMediaServerOpenAnyTenantService zlmMediaServerOpenAnyTenantService;


}
