package com.mqttsnet.thinglinks.groovy.controller.script;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.groovy.entity.EngineExecutorResult;
import com.mqttsnet.basic.groovy.entity.ExecuteParams;
import com.mqttsnet.basic.groovy.entity.ScriptQuery;
import com.mqttsnet.basic.groovy.exception.LoadScriptException;
import com.mqttsnet.basic.groovy.executor.EngineExecutor;
import com.mqttsnet.basic.groovy.helper.RegisterScriptHelper;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.record.script.ScriptIdentifier;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptParam;
import com.mqttsnet.thinglinks.vo.query.script.RuleGroovyScriptQuery;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotEmpty;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 规则脚本测试
 * </p>
 *
 * @author mqttsnet
 * @date 2025-03-24 09:54:10
 * @create [2025-03-24 09:54:10] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/groovyScriptTest")
@Tag(name = "规则脚本测试")
public class RuleGroovyScriptTestController extends RuleGroovyScriptBaseController {

    private final EngineExecutor engineExecutor;

    private final RegisterScriptHelper registerScriptHelper;


    /**
     * scriptUniqueKey 只要能唯一定位到脚本即可
     * 测试{@link EngineExecutor#execute(ScriptQuery, ExecuteParams)}
     *
     * @param scriptUniqueKey 脚本唯一键
     * @return {@link R<EngineExecutorResult>} 执行结果
     */
    @Operation(summary = "修改订单信息", description = "测试规则脚本——修改订单信息")
    @GetMapping("/changeOrderInfo")
    public R<EngineExecutorResult> changeOrderInfo(@NotEmpty(message = "脚本唯一键不能为空") String scriptUniqueKey) {
        // 构建参数
        ExecuteParams executeParams = buildOrderParams();
        EngineExecutorResult executorResult = null;
        try {
            // 执行脚本
            executorResult = engineExecutor.execute(new ScriptQuery(scriptUniqueKey), executeParams);
            String statusCode = executorResult.getExecutionStatus().getCode();
            if ("200".equals(statusCode)) {
                log.info("脚本执行成功......");
            } else {
                log.info("脚本执行失败......");
            }
            log.info("changeOrderInfo=========>>>>>>>>>>>执行结果：{}", executorResult);
        } catch (LoadScriptException loadScriptException) {
            log.error("加载脚本异常：", loadScriptException);
        }

        return R.success(executorResult);
    }


    /**
     * scriptUniqueKey 只要能唯一定位到脚本即可
     * 测试{@link EngineExecutor#execute(ScriptQuery, ExecuteParams)}
     *
     * @param scriptUniqueKey 脚本唯一键
     * @return {@link R<EngineExecutorResult>} 执行结果
     */
    @Operation(summary = "修改产品信息", description = "测试规则脚本——修改产品信息")
    @GetMapping("/changeProductInfo")
    public R<EngineExecutorResult> changeProductInfo(@NotEmpty(message = "脚本唯一键不能为空") String scriptUniqueKey) {
        // 构建参数
        ExecuteParams executeParams = buildProductParams();
        // 执行脚本中指定的方法 changeProduct
        EngineExecutorResult executorResult = engineExecutor.execute("changeProduct", new ScriptQuery(scriptUniqueKey), executeParams);
        log.info("使用groovy脚本来动态修改产品金额信息=========>>>>>>>>>>>执行结果：{}", executorResult);

        return R.success(executorResult);
    }


    /**
     * 获取脚本执行内容
     *
     * @param scriptUniqueKey 脚本唯一键
     * @return {@link R<EngineExecutorResult>} 执行结果
     */
    @Operation(summary = "获取脚本执行内容", description = "获取脚本执行内容")
    @GetMapping("/getContext")
    public R<EngineExecutorResult> getContext(@NotEmpty(message = "脚本唯一键不能为空") String scriptUniqueKey) {
        EngineExecutorResult executorResult = engineExecutor.execute(new ScriptQuery(scriptUniqueKey), null);
        log.info("getContext=========>>>>>>>>>>>执行结果：{}", executorResult);
        return R.success(executorResult);
    }


    /**
     * 测试运行中手动新增脚本
     */
    @Operation(summary = "注册脚本", description = "运行中手动注册脚本")
    @PostMapping("/registerScript")
    public R<Boolean> registerScript(@RequestBody RuleGroovyScriptParam param) throws Exception {
        RuleGroovyScriptQuery ruleGroovyScriptQuery = BeanPlusUtil.toBeanIgnoreError(param, RuleGroovyScriptQuery.class);
        CacheKey cacheKey = ScriptIdentifier.buildCacheKey(ruleGroovyScriptQuery);
        Boolean flag = registerScriptHelper.registerScript(cacheKey, param.getScriptContent(), false);
        log.info("注册结果:{}", flag);
        return R.success(flag);
    }
}


