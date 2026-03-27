package com.mqttsnet.thinglinks.entity.protocol.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import lombok.experimental.SuperBuilder;

import java.io.Serial;
import java.io.Serializable;

/**
 * ============================================================================
 * Description:
 * <p>
 * 事件基类
 * ============================================================================
 *
 * @author mqttsnet
 * @version 1.0.0
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@Schema(title = "BaseEvent", description = "事件基类")
public abstract class BaseEvent implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tenantId;

    @Schema(description = "客户端ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private String clientId;

    @Schema(description = "操作结果状态", allowableValues = {"success", "fail"})
    private String success;

    @Schema(description = "事件类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String event;


    @Schema(description = "消息时间戳（毫秒）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long timestamp = System.currentTimeMillis();


}
