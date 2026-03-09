package com.mqttsnet.thinglinks.anytenant.controller;

import java.util.Optional;

import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson2.JSON;
import com.mqttsnet.basic.context.ContextConstants;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.TenantUtil;
import com.mqttsnet.thinglinks.device.enumeration.ClientAclActionTypeEnum;
import com.mqttsnet.thinglinks.device.service.DeviceAclRuleService;
import com.mqttsnet.thinglinks.device.service.DeviceService;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAclCheckQuery;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAuthenticationQuery;
import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAclCheckResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAuthenticationResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设备相关开放接口（anyTenant）
 * 请求中 不需要携带TenantId 且 不需要携带Token(不需要登录) 和 不需要验证uri权限
 *
 * @author mqttsnet
 * @date 2021-06-30
 * @create [2021-06-30] [mqttsnet]
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/anyTenant/deviceOpen")
@Tag(name = "anyTenant-设备相关API")
public class DeviceOpenAnyTenantController {


    private final DeviceService deviceService;

    private final DeviceAclRuleService deviceAclRuleService;


    @Operation(summary = "Device connection authentication", description = "If the authentication succeeds, the status code 200 is returned. If the authentication fails, the advanced status code is returned")
    @PostMapping(value = "/clientConnectionAuthentication")
    public ResponseEntity<DeviceAuthenticationResultVO> clientConnectionAuthentication(@RequestBody DeviceAuthenticationQuery deviceAuthenticationQuery) {
        log.info("clientConnectionAuthentication clientIdentifier: {} param：{}", deviceAuthenticationQuery.getClientIdentifier(), JSON.toJSONString(deviceAuthenticationQuery));
        ArgumentAssert.notBlank(deviceAuthenticationQuery.getProtocolType(), "The protocol type cannot be null");

        try {
            if (ProtocolTypeEnum.fromValue(deviceAuthenticationQuery.getProtocolType()).isPresent()) {
                // Check parameter
                ArgumentAssert.notBlank(deviceAuthenticationQuery.getClientIdentifier(), "clientIdentifier Cannot be null");
                ArgumentAssert.notBlank(deviceAuthenticationQuery.getUsername(), "username Cannot be null");
                ArgumentAssert.notBlank(deviceAuthenticationQuery.getPassword(), "password Cannot be null");

                // Resolve the tenant ID based on the device ID, for example, clientId: 1000000000000000001@tenantId
                String tenantId = TenantUtil.extractTenantIdWithDefault(deviceAuthenticationQuery.getClientIdentifier());

                ContextUtil.setTenantId(tenantId);

                DeviceAuthenticationResultVO authenticationResult = deviceService.authClient(deviceAuthenticationQuery);

                if (authenticationResult.getCertificationResult()) {
                    log.info("clientConnectionAuthentication {} Device connection authentication result：{}", deviceAuthenticationQuery.getClientIdentifier(), true);
                    // 认证成功,写入ACL权限
                    authenticationResult.setAclRuleListResult(deviceAclRuleService.getDeviceAclRuleCacheVOList(deviceAuthenticationQuery.getClientIdentifier()));
                    return ResponseEntity.ok().body(authenticationResult);
                } else {
                    log.warn("clientConnectionAuthentication {} The device connection authentication failed. Procedure：{}", deviceAuthenticationQuery.getClientIdentifier(), authenticationResult.getErrorMessage());
                    return ResponseEntity.status(HttpStatus.FORBIDDEN).body(authenticationResult);
                }
            }
        } catch (Exception e) {
            log.error("An exception occurred during authentication. Procedure: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new DeviceAuthenticationResultVO<>());
        }

        log.info("clientConnectionAuthentication skipped for unsupported protocol type: {}", deviceAuthenticationQuery.getProtocolType());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new DeviceAuthenticationResultVO<>());
    }


    /**
     * ACL权限校验接口
     *
     * @param deviceAclCheckQuery ACL权限校验请求参数
     * @return 权限校验结果
     */
    @Operation(summary = "Device ACL validation", description = "Check if the client has permission to perform the operation on the topic")
    @PostMapping(value = "/clientAclValidation")
    public ResponseEntity<DeviceAclCheckResultVO> clientAclValidation(@Valid @RequestBody DeviceAclCheckQuery deviceAclCheckQuery) {
        log.info("clientAclValidation ACL validation clientIdentifier: {} param：{}", deviceAclCheckQuery.getClientIdentifier(), JSON.toJSONString(deviceAclCheckQuery));

        try {
            ContextUtil.setTenantId(Optional.ofNullable(deviceAclCheckQuery.getTenantId()).orElse(ContextConstants.BUILT_IN_TENANT_ID_STR));

            Optional<ClientAclActionTypeEnum> clientAclActionTypeEnumOptional = ClientAclActionTypeEnum.fromValue(deviceAclCheckQuery.getActionType());

            if (clientAclActionTypeEnumOptional.isEmpty()) {
                DeviceAclCheckResultVO result = DeviceAclCheckResultVO.builder()
                        .allowed(false)
                        .errorMessage("Invalid action type")
                        .echoMap(MapUtil.newHashMap())
                        .build();
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
            }

            // 执行权限校验
            DeviceAclCheckResultVO result = deviceAclRuleService.checkAclPermission(deviceAclCheckQuery);

            // 返回结果
            if (result.getAllowed()) {
                log.info("ACL allowed: {}...{} -> {}", deviceAclCheckQuery.getClientIdentifier(), clientAclActionTypeEnumOptional.get().getDesc(), deviceAclCheckQuery.getTopic());
                return ResponseEntity.ok().body(result);
            } else {
                log.warn("ACL denied: {}...{} -> {}", deviceAclCheckQuery.getClientIdentifier(), clientAclActionTypeEnumOptional.get().getDesc(), deviceAclCheckQuery.getTopic());
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(result);
            }
        } catch (Exception e) {
            log.error("ACL validation error: {}", e.getMessage(), e);
            DeviceAclCheckResultVO result = DeviceAclCheckResultVO.builder()
                    .allowed(false)
                    .errorMessage("ACL validation error")
                    .echoMap(MapUtil.newHashMap())
                    .build();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }
    }

}
