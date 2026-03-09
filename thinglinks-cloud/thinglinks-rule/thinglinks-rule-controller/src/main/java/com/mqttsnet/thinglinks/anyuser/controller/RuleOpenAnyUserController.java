package com.mqttsnet.thinglinks.anyuser.controller;

import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.thinglinks.service.linkage.RuleService;
import com.mqttsnet.thinglinks.service.plugin.PluginInfoService;
import com.mqttsnet.thinglinks.service.script.RuleGroovyScriptService;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptExecuteScriptParam;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.script.GroovyScriptEngineExecutorResultVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * -----------------------------------------------------------------------------
 * File Name: RuleOpenAnyUserController
 * -----------------------------------------------------------------------------
 * Description:
 * Rule 开放API
 * 请求中 需要携带TenantId，但 不需要携带Token(不需要登录) 和 不需要验证uri权限
 * 注意设置：ContextUtil.setTenantId(tenantId);
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/21       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/21 00:20
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/anyUser/ruleOpen")
@Tag(name = "Rule-OpenApi")
public class RuleOpenAnyUserController {


    private final RuleService ruleService;

    private final RuleGroovyScriptService ruleGroovyScriptService;

    private final PluginInfoService pluginInfoService;

    /**
     * Triggers the policy of a specific rule identified by the tenantId and the rule identification string.
     * <p>
     * This method is responsible for initiating the execution of a rule's policy for a given tenant.
     * It requires the tenant's ID and the rule's unique identification string. The method returns the details
     * of the rule after the policy has been triggered, which may include updated status or configuration information.
     * </p>
     *
     * @param tenantId           The ID of the tenant.
     * @param ruleIdentification The unique identification string of the rule.
     * @return {@link RuleDetailsResultVO} containing the updated details of the rule after triggering its policy.
     */
    @Operation(summary = "触发规则策略", description = "Triggers the policy of a specific rule for a given tenant.")
    @Parameters({
            @Parameter(name = "tenantId", description = "tenantId", required = true),
            @Parameter(name = "ruleIdentification", description = "Rule Identification", required = true),
    })
    @PostMapping("/triggerRulePolicy")
    public R<RuleDetailsResultVO> triggerRulePolicy(@RequestParam("tenantId") Long tenantId, @RequestParam("ruleIdentification") String ruleIdentification) {
        ArgumentAssert.notNull(tenantId, "tenantId  Cannot be null");
        ArgumentAssert.notEmpty(ruleIdentification, "ruleIdentification Cannot be null");
        log.info("Trigger Rule Policy - tenantId: {}, Rule Identification: {}", tenantId, ruleIdentification);
        try {
            RuleDetailsResultVO ruleDetailsResultVO = ruleService.triggerRulePolicy(tenantId, ruleIdentification);
            log.info("Successfully triggered rule policy - tenantId: {}, Rule Identification: {}, Result: {}", tenantId, ruleIdentification, ruleDetailsResultVO);
            return R.success(ruleDetailsResultVO);
        } catch (BizException bizException) {
            log.warn("Business exception while triggering rule policy - tenantId: {}, Rule Identification: {}, Message: {}", tenantId, ruleIdentification, bizException.getMessage(), bizException);
            return R.fail("Business error triggering rule policy: " + bizException.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while triggering rule policy - tenantId: {}, Rule Identification: {}", tenantId, ruleIdentification, e);
            return R.fail("Unexpected error triggering rule policy: " + e.getMessage());
        }
    }


    /**
     * 刷新Groovy脚本缓存
     *
     * @return {@link R<Boolean>} 包含刷新结果的布尔值
     */
    @Operation(summary = "刷新Groovy脚本缓存", description = "Flushes the Groovy script cache")
    @GetMapping("/flushGroovyScriptCache")
    public R<Boolean> flushGroovyScriptCache() {
        log.info("Flushing Groovy Script Cache - tenantId: {}", ContextUtil.getTenantIdStr());
        try {
            return R.success(ruleGroovyScriptService.flushGroovyScriptCache());
        } catch (BizException bizException) {
            log.warn("Business exception while flushing Groovy script cache - tenantId: {}, Message: {}", ContextUtil.getTenantIdStr(), bizException.getMessage(), bizException);
            return R.fail("Business error flushing Groovy script cache: " + bizException.getMessage());
        } catch (Exception e) {
            log.error("Unexpected error while flushing Groovy script cache - tenantId: {}", ContextUtil.getTenantIdStr(), e);
            return R.fail("Unexpected error flushing Groovy script cache: " + e.getMessage());
        }
    }


    /**
     * 安装插件并保存安装结果
     *
     * @param tenantId   租户ID
     * @param pluginId   插件ID
     * @param instanceId 实例ID
     * @return {@link R<?>} 包含安装结果的对象
     */
    @Operation(summary = "安装插件", description = "Installs a plugin based on pluginId and saves the result")
    @Parameter(name = "tenantId", description = "tenantId", required = true)
    @Parameter(name = "pluginId", description = "pluginId", required = true)
    @Parameter(name = "instanceId", description = "instanceId", required = true)
    @PostMapping("/installPlugin")
    public R<?> installPlugin(@RequestParam("tenantId") Long tenantId,
                              @RequestParam("pluginId") Long pluginId,
                              @RequestParam("instanceId") Long instanceId) {

        log.info("Initiating installation instanceId:{} for pluginId: {} under tenantId: {}", instanceId, pluginId, tenantId);
        try {
            ContextUtil.setTenantId(tenantId);
            Boolean result = pluginInfoService.installPlugin(pluginId, instanceId);
            return result ? R.success(true) : R.fail("Plugin installation failed.");
        } catch (Exception e) {
            log.error("Failed to install plugin for pluginId: {} under tenantId: {}. Error: {}", pluginId, tenantId, e.getMessage(), e);
            return R.fail("Failed to install plugin. Error: " + e.getMessage());
        }
    }

    /**
     * 卸载插件并保存卸载结果
     *
     * @param tenantId 租户ID
     * @param pluginId 插件ID
     * @return {@link R<?>} 包含卸载结果的对象
     */
    @Operation(summary = "卸载插件", description = "Uninstalls a plugin based on pluginId and saves the result")
    @Parameter(name = "tenantId", description = "tenantId", required = true)
    @Parameter(name = "pluginId", description = "pluginId", required = true)
    @Parameter(name = "instanceId", description = "instanceId", required = true)
    @DeleteMapping("/uninstallPlugin")
    public R<?> unInstallPlugin(@RequestParam("tenantId") Long tenantId,
                                @RequestParam("pluginId") Long pluginId,
                                @RequestParam("instanceId") Long instanceId) {

        log.info("Initiating uninstallation  instanceId:{} for pluginId: {} under tenantId: {}", instanceId, pluginId, tenantId);
        try {
            ContextUtil.setTenantId(tenantId);
            Boolean result = pluginInfoService.unInstallPlugin(pluginId, instanceId);
            return result ? R.success(true) : R.fail("Plugin uninstallation failed.");
        } catch (Exception e) {
            log.error("Failed to uninstall plugin for pluginId: {} under tenantId: {}. Error: {}", pluginId, tenantId, e.getMessage(), e);
            return R.fail("Failed to uninstall plugin. Error: " + e.getMessage());
        }
    }


    /**
     * 直接编译执行脚本
     * <p>实时执行以下流程：</p>
     * <ol>
     *   <li>动态编译传入的脚本内容为Class</li>
     *   <li>绑定执行参数到脚本上下文</li>
     *   <li>执行脚本逻辑并返回结果</li>
     * </ol>
     *
     * @param param 包含脚本内容与执行参数的请求体
     * @return {@link R<GroovyScriptEngineExecutorResultVO>} 标准化执行结果包装
     */
    @Operation(summary = "直接编译脚本", description = "实时编译脚本内容并执行，适用于动态脚本编译场景")
    @PostMapping("/executeScript")
    public R<GroovyScriptEngineExecutorResultVO> executeScript(@RequestBody RuleGroovyScriptExecuteScriptParam param) {
        log.info("Executing script param: {}", JSONUtil.toJsonStr(param));
        ArgumentAssert.isTrue(JSONUtil.isTypeJSON(param.getExecuteParams()), "执行参数格式不正确");
        try {
            return R.success(ruleGroovyScriptService.executeScript(param));
        } catch (Exception e) {
            log.error("Failed to execute script", e);
            throw BizException.wrap("编译执行脚本失败", e.getMessage());
        }
    }

}
