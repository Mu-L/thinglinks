package com.mqttsnet.thinglinks.card.entity.sim;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
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
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("card_sim_info")
public class CardSimInfo extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 国际移动用户识别码
     */
    @TableField(value = "imsi", condition = LIKE)
    private String imsi;
    /**
     * SIM卡唯一识别码
     */
    @TableField(value = "iccid", condition = LIKE)
    private String iccid;
    /**
     * 卡号
     */
    @TableField(value = "card_number", condition = LIKE)
    private String cardNumber;
    /**
     * 卡类型 0 插拔卡 1 贴片IC卡
     */
    @TableField(value = "card_type", condition = EQUAL)
    private Integer cardType;
    /**
     * 渠道ID
     */
    @TableField(value = "channel_id", condition = EQUAL)
    private Long channelId;
    /**
     * 已用流量
     */
    @TableField(value = "flows_used", condition = EQUAL)
    private BigDecimal flowsUsed;
    /**
     * 总流量
     */
    @TableField(value = "flows_total", condition = EQUAL)
    private BigDecimal flowsTotal;
    /**
     * 本月剩余流量
     */
    @TableField(value = "flows_rest", condition = EQUAL)
    private BigDecimal flowsRest;
    /**
     * 虚拟已用流量
     */
    @TableField(value = "virtual_flows_used", condition = EQUAL)
    private BigDecimal virtualFlowsUsed;
    /**
     * 虚拟剩余流量
     */
    @TableField(value = "virtual_flows_rest", condition = EQUAL)
    private BigDecimal virtualFlowsRest;
    /**
     * 虚拟总流量
     */
    @TableField(value = "virtual_flows_total", condition = EQUAL)
    private BigDecimal virtualFlowsTotal;
    /**
     * 是否有短信 0 无 1 有
     */
    @TableField(value = "sms_flag", condition = EQUAL)
    private Integer smsFlag;
    /**
     * GPRS开关 0 关 1 开
     */
    @TableField(value = "gprs_flag", condition = EQUAL)
    private Integer gprsFlag;
    /**
     * 开卡时间
     */
    @TableField(value = "open_time", condition = EQUAL)
    private LocalDateTime openTime;
    /**
     * 最晚激活时间
     */
    @TableField(value = "last_open_time", condition = EQUAL)
    private LocalDateTime lastOpenTime;
    /**
     * 激活时间
     */
    @TableField(value = "start_time", condition = EQUAL)
    private LocalDateTime startTime;
    /**
     * 失效时间
     */
    @TableField(value = "end_time", condition = EQUAL)
    private LocalDateTime endTime;
    /**
     * 流量到期时间
     */
    @TableField(value = "flows_end_time", condition = EQUAL)
    private LocalDateTime flowsEndTime;
    /**
     * 运营商类型 1 移动 2 电信 3 联通
     */
    @TableField(value = "carrier_type", condition = EQUAL)
    private Integer carrierType;
    /**
     * 已发送短信数量
     */
    @TableField(value = "sms_count", condition = EQUAL)
    private Integer smsCount;
    /**
     * SIM卡状态 1 待激活 2 已激活 3 停机
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 使用类型 1 普卡 2 共享池 3 流量池
     */
    @TableField(value = "use_type", condition = EQUAL)
    private Integer useType;
    /**
     * APN名称
     */
    @TableField(value = "apn_name", condition = LIKE)
    private String apnName;
    /**
     * IP地址
     */
    @TableField(value = "ip_address", condition = LIKE)
    private String ipAddress;
    /**
     * 获取时间
     */
    @TableField(value = "gain_time", condition = EQUAL)
    private LocalDateTime gainTime;
    /**
     * 在线状态 0 不在线 1 在线
     */
    @TableField(value = "online_flag", condition = EQUAL)
    private Integer onlineFlag;
    /**
     * 停卡类型 1 系统停卡 2 人工停卡 0 正常
     */
    @TableField(value = "stop_card_type", condition = EQUAL)
    private Integer stopCardType;
    /**
     * 当月流量预警记录
     */
    @TableField(value = "monthly_warning", condition = LIKE)
    private String monthlyWarning;
    /**
     * 关联设备IMEI
     */
    @TableField(value = "imei", condition = LIKE)
    private String imei;
    /**
     * 流量限制阀值
     */
    @TableField(value = "limit_flow", condition = EQUAL)
    private Double limitFlow;
    /**
     * 流量阀值状态 0 未开启 1 开启
     */
    @TableField(value = "limit_flag", condition = EQUAL)
    private Integer limitFlag;
    /**
     * 流量限制状态 0 未限制 1 已限制
     */
    @TableField(value = "limit_status", condition = EQUAL)
    private Integer limitStatus;
    /**
     * 事务ID
     */
    @TableField(value = "offer_id", condition = EQUAL)
    private Long offerId;
    /**
     * API是否可查 0 不可查 1 可查
     */
    @TableField(value = "searchable_status", condition = EQUAL)
    private Integer searchableStatus;
    /**
     * 备注
     */
    @TableField(value = "remark", condition = LIKE)
    private String remark;
    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id", condition = EQUAL)
    private Long createdOrgId;


}
