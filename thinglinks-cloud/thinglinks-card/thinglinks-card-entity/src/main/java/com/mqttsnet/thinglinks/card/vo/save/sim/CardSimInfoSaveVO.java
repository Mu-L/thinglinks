package com.mqttsnet.thinglinks.card.vo.save.sim;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 表单保存方法VO
 * 物联网卡信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 23:45:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@Schema(description = "物联网卡信息表")
public class CardSimInfoSaveVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 国际移动用户识别码
     */
    @Schema(description = "国际移动用户识别码")
    @Size(max = 50, message = "国际移动用户识别码长度不能超过{max}")
    private String imsi;
    /**
     * SIM卡唯一识别码
     */
    @Schema(description = "SIM卡唯一识别码")
    @NotEmpty(message = "请填写SIM卡唯一识别码")
    @Size(max = 50, message = "SIM卡唯一识别码长度不能超过{max}")
    private String iccid;
    /**
     * 卡号
     */
    @Schema(description = "卡号")
    @NotEmpty(message = "请填写卡号")
    @Size(max = 50, message = "卡号长度不能超过{max}")
    private String cardNumber;
    /**
     * 卡类型 0 插拔卡 1 贴片IC卡
     */
    @Schema(description = "卡类型 0 插拔卡 1 贴片IC卡")
    private Integer cardType;
    /**
     * 渠道ID
     */
    @Schema(description = "渠道ID")
    private Long channelId;
    /**
     * 已用流量
     */
    @Schema(description = "已用流量")
    private BigDecimal flowsUsed;
    /**
     * 总流量
     */
    @Schema(description = "总流量")
    private BigDecimal flowsTotal;
    /**
     * 本月剩余流量
     */
    @Schema(description = "本月剩余流量")
    private BigDecimal flowsRest;
    /**
     * 虚拟已用流量
     */
    @Schema(description = "虚拟已用流量")
    private BigDecimal virtualFlowsUsed;
    /**
     * 虚拟剩余流量
     */
    @Schema(description = "虚拟剩余流量")
    private BigDecimal virtualFlowsRest;
    /**
     * 虚拟总流量
     */
    @Schema(description = "虚拟总流量")
    private BigDecimal virtualFlowsTotal;
    /**
     * 是否有短信 0 无 1 有
     */
    @Schema(description = "是否有短信 0 无 1 有")
    private Integer smsFlag;
    /**
     * GPRS开关 0 关 1 开
     */
    @Schema(description = "GPRS开关 0 关 1 开")
    private Integer gprsFlag;
    /**
     * 开卡时间
     */
    @Schema(description = "开卡时间")
    private LocalDateTime openTime;
    /**
     * 最晚激活时间
     */
    @Schema(description = "最晚激活时间")
    private LocalDateTime lastOpenTime;
    /**
     * 激活时间
     */
    @Schema(description = "激活时间")
    private LocalDateTime startTime;
    /**
     * 失效时间
     */
    @Schema(description = "失效时间")
    private LocalDateTime endTime;
    /**
     * 流量到期时间
     */
    @Schema(description = "流量到期时间")
    private LocalDateTime flowsEndTime;
    /**
     * 运营商类型 1 移动 2 电信 3 联通
     */
    @Schema(description = "运营商类型 1 移动 2 电信 3 联通")
    private Integer carrierType;
    /**
     * 已发送短信数量
     */
    @Schema(description = "已发送短信数量")
    private Integer smsCount;
    /**
     * SIM卡状态 1 待激活 2 已激活 3 停机
     */
    @Schema(description = "SIM卡状态 1 待激活 2 已激活 3 停机")
    private Integer status;
    /**
     * 使用类型 1 普卡 2 共享池 3 流量池
     */
    @Schema(description = "使用类型 1 普卡 2 共享池 3 流量池")
    private Integer useType;
    /**
     * APN名称
     */
    @Schema(description = "APN名称")
    @Size(max = 100, message = "APN名称长度不能超过{max}")
    private String apnName;
    /**
     * IP地址
     */
    @Schema(description = "IP地址")
    @Size(max = 255, message = "IP地址长度不能超过{max}")
    private String ipAddress;
    /**
     * 获取时间
     */
    @Schema(description = "获取时间")
    private LocalDateTime gainTime;
    /**
     * 在线状态 0 不在线 1 在线
     */
    @Schema(description = "在线状态 0 不在线 1 在线")
    private Integer onlineFlag;
    /**
     * 停卡类型 1 系统停卡 2 人工停卡 0 正常
     */
    @Schema(description = "停卡类型 1 系统停卡 2 人工停卡 0 正常")
    @NotNull(message = "请填写停卡类型 1 系统停卡 2 人工停卡 0 正常")
    private Integer stopCardType;
    /**
     * 当月流量预警记录
     */
    @Schema(description = "当月流量预警记录")
    @Size(max = 32, message = "当月流量预警记录长度不能超过{max}")
    private String monthlyWarning;
    /**
     * 关联设备IMEI
     */
    @Schema(description = "关联设备IMEI")
    @Size(max = 32, message = "关联设备IMEI长度不能超过{max}")
    private String imei;
    /**
     * 流量限制阀值
     */
    @Schema(description = "流量限制阀值")
    private Double limitFlow;
    /**
     * 流量阀值状态 0 未开启 1 开启
     */
    @Schema(description = "流量阀值状态 0 未开启 1 开启")
    private Integer limitFlag;
    /**
     * 流量限制状态 0 未限制 1 已限制
     */
    @Schema(description = "流量限制状态 0 未限制 1 已限制")
    private Integer limitStatus;
    /**
     * 事务ID
     */
    @Schema(description = "事务ID")
    private Long offerId;
    /**
     * API是否可查 0 不可查 1 可查
     */
    @Schema(description = "API是否可查 0 不可查 1 可查")
    private Integer searchableStatus;
    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, message = "备注长度不能超过{max}")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
