package com.mqttsnet.thinglinks.mobile.mobilespacedevice.entity;

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
 * 空间设备绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 14:11:40
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("mobile_space_device")
public class MobileSpaceDevice extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 空间ID
     */
    @TableField(value = "space_id", condition = EQUAL)
    private Long spaceId;
    /**
     * 产品标识
     */
    @TableField(value = "product_identification", condition = LIKE)
    private String productIdentification;
    /**
     * 设备标识
     */
    @TableField(value = "device_identification", condition = LIKE)
    private String deviceIdentification;
    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id", condition = EQUAL)
    private Long createdOrgId;


}
