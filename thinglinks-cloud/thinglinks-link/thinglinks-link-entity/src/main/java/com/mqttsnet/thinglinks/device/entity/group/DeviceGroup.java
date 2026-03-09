package com.mqttsnet.thinglinks.device.entity.group;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.TreeEntity;
import com.mqttsnet.basic.mybatis.typehandler.EncryptTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * <p>
 * 实体类
 * 设备分组表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-19 18:05:14
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "device_group", autoResultMap = true)
public class DeviceGroup extends TreeEntity<DeviceGroup, Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 分组名称
     */
    @TableField(value = "group_name", condition = EQUAL, typeHandler = EncryptTypeHandler.class)
    private String groupName;
    /**
     * 分组类型
     */
    @TableField(value = "type", condition = EQUAL)
    private Integer type;
    /**
     * 状态;[0-禁用 1-启用]
     */
    @TableField(value = "state", condition = EQUAL)
    private Boolean state;
    /**
     * 分组描述
     */
    @TableField(value = "description", condition = LIKE)
    private String description;
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
     * 逻辑删除标识:0-未删除 1-已删除
     */
    @TableLogic
    @TableField(value = "deleted", condition = EQUAL)
    private Integer deleted;
}
