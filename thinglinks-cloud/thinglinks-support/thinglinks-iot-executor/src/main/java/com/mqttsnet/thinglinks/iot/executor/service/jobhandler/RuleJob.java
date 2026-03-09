package com.mqttsnet.thinglinks.iot.executor.service.jobhandler;

import com.fasterxml.jackson.core.type.TypeReference;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.context.ContextConstants;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.thinglinks.iot.executor.service.AbstractTenantJob;
import com.mqttsnet.thinglinks.rule.facade.RuleJobHandlerFacade;
import com.mqttsnet.thinglinks.vo.result.linkage.RuleDetailsResultVO;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;

/**
 * Job handler for rule tasks.
 *
 * @author mqttsnet
 */
@Component
@Slf4j
public class RuleJob extends AbstractTenantJob {

    private final RuleJobHandlerFacade ruleJobHandlerApi;

    public RuleJob(RuleJobHandlerFacade ruleJobHandlerApi) {
        this.ruleJobHandlerApi = ruleJobHandlerApi;
    }


    /**
     * 执行场景联动规则调度任务。
     * 该方法通过XXL-JOB调度框架触发特定场景规则的执行，根据任务参数中指定的租户和规则标识进行联动操作。
     *
     * <p><strong>任务参数规范：</strong>
     * <pre>
     * {
     *   "TenantId": "租户ID(必填)",
     *   "ruleldentification": "规则唯一标识(必填)"
     * }
     * </pre>
     *
     * @XxlJob 标注为XXL-JOB调度任务，任务处理器名称为sceneLinkageRuleJobHandler
     * @see RuleJobHandlerFacade#triggerRulePolicy(Long, String) 实际执行规则的方法
     *
     * <p><strong>示例配置：</strong>
     * <table border="1">
     *   <tr><td>运行模式</td><td>BEAN</td></tr>
     *   <tr><td>JobHandler</td><td>sceneLinkageRuleJobHandler</td></tr>
     *   <tr><td>任务参数</td><td>{"TenantId":"1","ruleldentification":"3477307037634560"}</td></tr>
     * </table>
     */
    @XxlJob("sceneLinkageRuleJobHandler")
    public void sceneLinkageRuleJobHandler() {
        try {
            final String param = XxlJobHelper.getJobParam();
            ArgumentAssert.notBlank(param, "Job parameter cannot be empty");
            Map<String, String> params = JsonUtil.parse(param, new TypeReference<>() {
            });
            ArgumentAssert.notNull(params.get(ContextConstants.TENANT_ID_HEADER), "Tenant ID 缺失");
            ArgumentAssert.notNull(params.get("ruleIdentification"), "规则标识缺失");
            String tenantId = params.get(ContextConstants.TENANT_ID_HEADER);
            ContextUtil.setTenantId(tenantId);
            executeRule(Long.parseLong(tenantId), params.get("ruleIdentification"));
        } catch (Exception e) {
            XxlJobHelper.log("sceneLinkageRuleJobHandler 任务失败: " + e.getMessage());
            log.error("sceneLinkageRuleJobHandler XXL-JOB执行异常", e);
        }
    }

    public void executeRule(Long tenantId, String ruleId) {
        try {
            R<RuleDetailsResultVO> result = ruleJobHandlerApi.triggerRulePolicy(tenantId, ruleId);
            log.info("租户[{}]规则[{}]执行结果: {}", tenantId, ruleId, result.getData());
            XxlJobHelper.log("租户[{}]规则[{}]执行结果: {}", tenantId, ruleId, result.getData());
        } catch (Exception e) {
            log.error("租户[{}]规则[{}]执行异常: {}", tenantId, ruleId, e.getMessage());
            XxlJobHelper.log("租户[{}]规则[{}]执行异常: {}", tenantId, ruleId, e.getMessage());
        }
    }


    /**
     * 执行全租户Groovy脚本缓存刷新任务。
     * 该任务遍历所有租户上下文，为每个租户执行脚本缓存刷新操作。
     *
     * <p><strong>功能说明：</strong>
     * <ul>
     *   <li>通过XXL-JOB调度框架实现分布式任务调度</li>
     *   <li>采用租户级联处理模式保证多租户隔离</li>
     *   <li>异步执行机制避免阻塞主调度线程</li>
     * </ul>
     *
     * <p><strong>调度策略：</strong>
     * <ul>
     *   <li>默认执行频率：每5分钟执行一次</li>
     *   <li>重试策略：失败后自动重试3次，间隔5分钟</li>
     *   <li>路由策略：优先选择上次执行节点</li>
     * </ul>
     *
     * @XxlJob 标记为XXL-JOB分布式任务
     * @see RuleJobHandlerFacade#flushGroovyScriptCache() 实际执行缓存刷新的服务方法
     */
    @XxlJob("flushAnyTenantGroovyScriptCacheJobHandler")
    public void flushAnyTenantGroovyScriptCacheJobHandler() {
        XxlJobHelper.log("[刷新Groovy脚本缓存]开始全租户任务开始");
        loadTenant((tenant, param) -> {
            final Long tenantId = tenant.getId();

            ContextUtil.setTenantId(tenantId);

            flushScriptCache(tenantId);
        });
    }

    /**
     * 执行具体租户的脚本缓存刷新操作
     *
     * @param tenantId 租户标识符
     */
    protected void flushScriptCache(Long tenantId) {
        log.info("开始Groovy脚本缓存刷新 tenantId={}", tenantId);
        XxlJobHelper.log("开始Groovy脚本缓存刷新 tenantId={}", tenantId);
        Instant start = Instant.now();

        try {
            R<Boolean> result = ruleJobHandlerApi.flushGroovyScriptCache();
            Duration duration = Duration.between(start, Instant.now());

            if (result.getIsSuccess()) {
                log.info("成功刷新Groovy脚本缓存 tenantId={} | 耗时:{}ms", tenantId, duration.toMillis());
                XxlJobHelper.log("成功刷新Groovy脚本缓存 tenantId={} | 耗时:{}ms", tenantId, duration.toMillis());
            } else {
                log.warn("刷新Groovy脚本缓存异常 tenantId={} | 耗时:{}ms | 错误码:{}", tenantId, duration.toMillis(), result.getCode());
                XxlJobHelper.log("刷新Groovy脚本缓存异常 tenantId={} | 错误码:{}", tenantId, result.getCode());
            }
        } catch (Exception e) {
            log.error("Groovy脚本缓存刷新异常 tenantId={} | error= {}", tenantId, e.getMessage(), e);
            XxlJobHelper.log("Groovy脚本缓存刷新异常 tenantId={} | error= {}", tenantId, e.getMessage());
        }
    }


}
