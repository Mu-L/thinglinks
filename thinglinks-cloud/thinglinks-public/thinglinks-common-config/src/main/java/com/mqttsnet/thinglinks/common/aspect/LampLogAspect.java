package com.mqttsnet.thinglinks.common.aspect;


import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.thinglinks.common.properties.SystemProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * 操作日志使用spring event异步入库
 *
 * @author mqttsnet
 * @date 2019-07-01 15:15
 */
@Slf4j
@Aspect
@RequiredArgsConstructor
public class LampLogAspect extends BaseLogAspect {
    private final SystemProperties systemProperties;

    /***
     * 定义controller切入点拦截规则：拦截标记SysLog注解和指定包下的方法
     * 2个表达式加起来才能拦截所有Controller 或者继承了BaseController的方法
     *
     * execution(public * com.mqttsnet.thinglinks.*.*(..)) 解释：
     * 第一个* 任意返回类型
     * 第二个* com.mqttsnet.thinglinks 包下的所有类
     * 第三个* 类下的所有方法
     * ()中间的.. 任意参数
     *
     */
    @Pointcut("execution(* com.mqttsnet.thinglinks..controller..*.*(..))) || execution(* com.mqttsnet.thinglinks..service..*.*(..))) " +
              "|| execution(* com.mqttsnet.thinglinks..biz..*.*(..)))")
    public void lampLogAspect() {

    }

    @Around("lampLogAspect()")
    public Object invoke(ProceedingJoinPoint joinPoint) throws Throwable {
        String logTraceId = ContextUtil.getLogTraceId() == null ? StrPool.EMPTY : ContextUtil.getLogTraceId();
        String types = getParamTypes(joinPoint);

        outArgsLog(joinPoint, logTraceId, types, joinPoint.getArgs(), systemProperties.getRecordLampArgs());
        long start = System.currentTimeMillis();
        try {
            Object retVal = joinPoint.proceed();
            outResultLog(joinPoint, logTraceId, types, start, retVal, systemProperties.getRecordLampResult());
            return retVal;
        } catch (Exception e) {
            log.error("<<<< [traceId:{}] {}.{}({}) end... {} ms", logTraceId, joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName(), types, System.currentTimeMillis() - start, e);
            throw e;
        }
    }

}
