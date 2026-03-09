package com.mqttsnet.thinglinks.vo.result.script;

import com.mqttsnet.thinglinks.enumeration.script.ExecutionStatusEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * ============================================================================
 * Description:
 * 规则脚本执行结果
 * ============================================================================
 *
 * @author Sun Shihuan
 * @version 1.0.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2025/4/13      Sun Shihuan        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2025/4/13 15:19
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(title = "GroovyScriptEngineExecutorResultVO", description = "规则引擎脚本执行结果")
public class GroovyScriptEngineExecutorResultVO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 执行状态
     */
    @Schema(description = "执行状态（SUCCESS-成功, FAILED-失败, NO_SCRIPT-无脚本, PARAM_ERROR-参数错误）",
            example = "SUCCESS",
            implementation = ExecutionStatusEnum.class)
    private ExecutionStatusEnum executionStatus;

    /**
     * 返回内容
     */
    @Schema(description = "返回内容")
    private Object context;

    /**
     * 异常信息
     */
    @Schema(description = "异常信息")
    private Throwable exception;

    /**
     * 自定义异常描述
     */
    @Schema(description = "自定义异常描述")
    private String errorMessage;
}
