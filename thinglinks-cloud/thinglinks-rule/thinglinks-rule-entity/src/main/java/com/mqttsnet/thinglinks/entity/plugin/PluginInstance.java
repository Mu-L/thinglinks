package com.mqttsnet.thinglinks.entity.plugin;

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

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 插件实例信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:02:17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("plugin_instance")
public class PluginInstance extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 实例唯一标识
     */
    @TableField(value = "instance_identification", condition = LIKE)
    private String instanceIdentification;
    /**
     * 实例名称，用于标识实例的友好名称
     */
    @TableField(value = "instance_name", condition = LIKE)
    private String instanceName;
    /**
     * 应用名称，SpringBoot应用名称
     */
    @TableField(value = "application_name", condition = LIKE)
    private String applicationName;
    /**
     * 实例运行所在的机器 IP 地址
     */
    @TableField(value = "machine_ip", condition = LIKE)
    private String machineIp;
    /**
     * 实例可用端口范围起始值
     */
    @TableField(value = "port_range_start", condition = EQUAL)
    private Integer portRangeStart;
    /**
     * 实例可用端口范围结束值
     */
    @TableField(value = "port_range_end", condition = EQUAL)
    private Integer portRangeEnd;
    /**
     * 扩展参数（预留）
     */
    @TableField(value = "extend_params", condition = LIKE)
    private String extendParams;
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
    /**
     * 实例的权重
     */
    @TableField(value = "weight", condition = EQUAL)
    private Integer weight;
    /**
     * 实例的健康状态
     */
    @TableField(value = "healthy", condition = EQUAL)
    private Boolean healthy;
    /**
     * 实例是否启用
     */
    @TableField(value = "enabled", condition = EQUAL)
    private Boolean enabled;
    /**
     * 实例是否为临时实例
     */
    @TableField(value = "ephemeral", condition = EQUAL)
    private Boolean ephemeral;
    /**
     * 实例所在集群名称
     */
    @TableField(value = "cluster_name", condition = LIKE)
    private String clusterName;
    /**
     * 实例心跳间隔时间(毫秒)
     */
    @TableField(value = "heart_beat_interval", condition = EQUAL)
    private Long heartBeatInterval;
    /**
     * 实例心跳超时时间(毫秒)
     */
    @TableField(value = "heart_beat_time_out", condition = EQUAL)
    private Long heartBeatTimeOut;
    /**
     * 实例IP删除超时时间(毫秒)
     */
    @TableField(value = "ip_delete_time_out", condition = EQUAL)
    private Long ipDeleteTimeOut;
    /**
     * 实例机器端口
     */
    @TableField(value = "machine_port", condition = LIKE)
    private String machinePort;

}
