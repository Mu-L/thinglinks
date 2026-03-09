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
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 插件实例心跳表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:31:15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("plugin_instance_heartbeat")
public class PluginInstanceHeartbeat extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 实例唯一标识
     */
    @TableField(value = "instance_identification", condition = LIKE)
    private String instanceIdentification;
    /**
     * 插件运行所在的机器 IP 地址
     */
    @TableField(value = "machine_ip", condition = LIKE)
    private String machineIp;
    /**
     * 上次心跳时间
     */
    @TableField(value = "last_heartbeat_time", condition = EQUAL)
    private LocalDateTime lastHeartbeatTime;
    /**
     * 心跳间隔时间（秒）
     */
    @TableField(value = "heartbeat_interval", condition = EQUAL)
    private Integer heartbeatInterval;
    /**
     * 心跳状态：0-正常，1-异常
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 心跳详细信息
     */
    @TableField(value = "heartbeat_message", condition = LIKE)
    private String heartbeatMessage;
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


}
