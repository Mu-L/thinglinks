package com.mqttsnet.thinglinks.groovy.controller.script;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.groovy.entity.EngineExecutorResult;
import com.mqttsnet.basic.groovy.entity.ExecuteParams;
import com.mqttsnet.basic.groovy.entity.ScriptEntry;
import com.mqttsnet.basic.groovy.entity.ScriptQuery;
import com.mqttsnet.basic.groovy.executor.EngineExecutor;
import com.mqttsnet.basic.groovy.helper.RegisterScriptHelper;
import com.mqttsnet.basic.groovy.loader.ScriptLoader;
import com.mqttsnet.thinglinks.dto.script.OrderInfoDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * <p>
 * 规则脚本-测试性能controller
 *     <ol>
 *         <li>使用Redis-loader作为脚本数据来源</li>
 *         <li>测试每次都编译脚本为Class然后再执行脚本的效率和使用缓存缓存脚本的Class脚本效率对比</li>
 *         <li>测试使用了缓存机制的方法区Class数量和直接编译脚本的方法区Class数量对比</li>
 *     </ol>
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
@RequestMapping("/groovyScriptTestPerformance")
@Tag(name = "规则脚本测试性能")
public class RuleGroovyScriptTestPerformanceController extends RuleGroovyScriptBaseController {

    private final EngineExecutor engineExecutor;

    private final RegisterScriptHelper registerScriptHelper;

    private final ScriptLoader scriptLoader;

    private final AtomicLong testExecuteCount = new AtomicLong(0);
    private final AtomicLong compileExecuteCount = new AtomicLong(0);
    private final AtomicLong useCacheExecuteCount = new AtomicLong(0);

    /**
     * 简单性能测试接口
     * <p>用于基础性能基准测试，不涉及脚本编译</p>
     *
     * @return {@link R<OrderInfoDTO>} 固定成功响应
     */
    @Operation(summary = "基础性能测试", description = "执行简单业务逻辑测试，建立性能基准线")
    @GetMapping("/simpleTest")
    public R<OrderInfoDTO> simpleTest() {
        ExecuteParams executeParams = buildOrderParams();
        OrderInfoDTO orderInfo = changeOrderInfo2(executeParams.getValue("orderInfo"));
        log.info("[{}] 基础测试执行结果：{}", testExecuteCount.getAndIncrement(), orderInfo);
        return R.success(orderInfo);
    }

    /**
     * 编译模式性能测试
     * <p>每次执行都重新编译脚本，用于测试类加载性能</p>
     *
     * @param scriptUniqueKey 脚本唯一标识
     * @return {@link R<EngineExecutorResult>} 执行结果包装
     * @throws Exception 脚本加载或执行异常
     */
    @Operation(summary = "编译模式测试",
            description = "禁用缓存的直接编译模式测试，监控方法区类加载情况")
    @Parameters({@Parameter(name = "scriptUniqueKey", description = "脚本唯一标识", required = true),})
    @GetMapping("/compileDirect")
    public R<EngineExecutorResult> testCompileDirect(String scriptUniqueKey) throws Exception {

        ExecuteParams executeParams = buildOrderParams();
        ScriptEntry scriptEntry = scriptLoader.load(new ScriptQuery(scriptUniqueKey));

        EngineExecutorResult result = engineExecutor.execute(scriptEntry, executeParams);
        log.info("[{}] 编译模式执行结果：{}", compileExecuteCount.getAndIncrement(), result);

        return R.success(result);
    }

    /**
     * 缓存模式性能测试
     * <p>使用缓存脚本进行测试，对比编译模式的性能差异</p>
     *
     * @param scriptUniqueKey 脚本唯一标识
     * @return {@link R<EngineExecutorResult>} 执行结果包装
     */
    @Operation(summary = "缓存模式测试", description = "使用缓存脚本执行性能对比测试")
    @Parameters({@Parameter(name = "scriptUniqueKey", description = "脚本唯一标识", required = true),})
    @GetMapping("/useCache")
    public R<EngineExecutorResult> userCache(String scriptUniqueKey) {
        ExecuteParams executeParams = buildOrderParams();
        EngineExecutorResult result = engineExecutor.execute(new ScriptQuery(scriptUniqueKey), executeParams);
        log.info("[{}] 缓存模式执行结果：{}", useCacheExecuteCount.getAndIncrement(), result);
        return R.success(result);
    }

    /**
     * 修改订单金额业务逻辑
     * <p>模拟复杂业务规则判断，用于性能测试</p>
     *
     * @param orderInfoDTO 原始订单数据
     * @return 修改后的订单数据
     */
    private OrderInfoDTO changeOrderInfo2(OrderInfoDTO orderInfoDTO) {
        final String newOrderAmount = calculateOrderAmount(orderInfoDTO.getOrderId());
        log.info("订单金额修改 原金额:{} 新金额:{}", orderInfoDTO.getOrderAmount(), newOrderAmount);
        orderInfoDTO.setOrderAmount(newOrderAmount);
        return orderInfoDTO;
    }

    /**
     * 订单金额计算规则
     * <p>根据订单ID区间计算新金额</p>
     *
     * @param orderId 订单ID
     * @return 计算后的金额字符串
     */
    private String calculateOrderAmount(long orderId) {
        if (orderId < 10) {
            return "2000";
        }
        if (orderId < 20) {
            return "5000";
        }
        if (orderId < 30) {
            return "8000";
        }
        return "10000";
    }
}