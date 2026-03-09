package com.mqttsnet.thinglinks.rule.facade;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptExecuteScriptParam;
import com.mqttsnet.thinglinks.vo.result.script.GroovyScriptEngineExecutorResultVO;

/**
 * ============================================================================
 * Description:
 * Rule 所有用户开放API
 * ============================================================================
 *
 * @author Sun Shihuan
 * @version 1.0.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2025/4/14      Sun Shihuan        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2025/4/14 14:56
 */
public interface RuleOpenAnyUserFacade {
    /**
     * 直接执行Groovy脚本
     *
     * @param param Groovy脚本执行参数
     * @return {@link R < GroovyScriptEngineExecutorResultVO >} 包含执行结果的响应对象
     */
    R<GroovyScriptEngineExecutorResultVO> executeScript(RuleGroovyScriptExecuteScriptParam param);

}
