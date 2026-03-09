package com.mqttsnet.thinglinks.vo.result.alarm;

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

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 表单查询方法返回值VO
 * 告警规则详情VO
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:14:37
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(title = "RuleAlarmDetailsResultVO", description = "告警规则详情VO")
public class RuleAlarmDetailsResultVO extends Entity<Long> implements Serializable, EchoVO {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "主键")
    private Long id;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private String appId;
    /**
     * 告警名称
     */
    @Schema(description = "告警名称")
    private String alarmName;
    /**
     * 告警编码
     */
    @Schema(description = "告警编码")
    private String alarmIdentification;
    /**
     * 告警场景
     */
    @Schema(description = "告警场景")
    private String alarmScene;
    /**
     * 告警渠道ID集合
     */
    @Schema(description = "告警渠道ID集合")
    private String alarmChannelIds;
    /**
     * 告警级别
     */
    @Schema(description = "告警级别")
    private Integer level;
    /**
     * 启用状态
     */
    @Schema(description = "启用状态")
    private Integer status;
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


    @Schema(description = "告警渠道详情集合")
    private List<RuleAlarmChannelDetailsResultVO> ruleAlarmChannelDetailsResultVOList;


}
