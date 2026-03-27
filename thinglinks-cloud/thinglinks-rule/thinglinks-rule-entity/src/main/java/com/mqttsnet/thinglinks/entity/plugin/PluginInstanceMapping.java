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
 * 插件与实例及端口管理表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:30:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("plugin_instance_mapping")
public class PluginInstanceMapping extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 插件唯一标识插件唯一标识
     */
    @TableField(value = "plugin_identification", condition = LIKE)
    private String pluginIdentification;
    /**
     * 实例唯一标识
     */
    @TableField(value = "instance_identification", condition = LIKE)
    private String instanceIdentification;
    /**
     * 插件在该实例上使用的端口号
     */
    @TableField(value = "port", condition = EQUAL)
    private Integer port;
    /**
     * 端口类型或用途（如 HTTP, HTTPS, 管理端口等）
     */
    @TableField(value = "port_type", condition = LIKE)
    private String portType;
    /**
     * 状态：0-正常，1-异常
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 备注
     */
    @TableField(value = "remark", condition = LIKE)
    private String remark;


}
