package com.mqttsnet.thinglinks.ota.vo.result;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

import cn.hutool.core.map.MapUtil;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.interfaces.echo.EchoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * <p>
 * 表单查询方法返回值VO
 * OTA升级目标表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-10-19 16:28:50
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(description = "OTA升级目标表")
public class OtaUpgradeTargetsResultVO extends Entity<Long> implements Serializable, EchoVO {
    @Serial
    private static final long serialVersionUID = 1L;
    @Builder.Default
    private final Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "主键")
    private Long id;

    /**
     * 任务ID
     */
    @Schema(description = "任务ID")
    private Long taskId;
    /**
     * 目标值(设备标识/分组ID/省市区域编码)
     */
    @Schema(description = "目标值(设备标识/分组ID/省市区域编码)")
    private String targetValue;
    /**
     * 目标状态(0:待推送,1:推送中,2:推送成功,3:推送失败)
     */
    @Schema(description = "目标状态(0:待推送,1:推送中,2:推送成功,3:推送失败)")
    private Integer targetStatus;
    /**
     * 描述
     */
    @Schema(description = "描述")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
