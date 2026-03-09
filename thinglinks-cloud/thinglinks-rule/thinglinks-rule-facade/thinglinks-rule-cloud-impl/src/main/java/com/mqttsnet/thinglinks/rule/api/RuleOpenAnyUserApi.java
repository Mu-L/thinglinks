package com.mqttsnet.thinglinks.rule.api;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.constant.Constants;
import com.mqttsnet.thinglinks.rule.api.hystrix.RuleOpenAnyUserApiFallback;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptExecuteScriptParam;
import com.mqttsnet.thinglinks.vo.result.script.GroovyScriptEngineExecutorResultVO;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * ============================================================================
 * Description:
 * <p>
 * Rule AnyUser API
 * </p>
 * ============================================================================
 *
 * @author mqttsnet
 * @date 2025/4/14 14:59
 */
@FeignClient(name = "${" + Constants.PROJECT_PREFIX + ".feign.tenant-server:thinglinks-rule-server}", fallback = RuleOpenAnyUserApiFallback.class, path = "/anyUser/ruleOpen")
public interface RuleOpenAnyUserApi {


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
    R<GroovyScriptEngineExecutorResultVO> executeScript(@RequestBody RuleGroovyScriptExecuteScriptParam param);

}
