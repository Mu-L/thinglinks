package com.mqttsnet.thinglinks.device.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
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

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 设备位置表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-30 23:05:31
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@TableName(value = "device_location", autoResultMap = true)
public class DeviceLocation extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备标识
     */
    @TableField(value = "device_identification", condition = LIKE)
    private String deviceIdentification;
    /**
     * 纬度
     */
    @TableField(value = "latitude", condition = EQUAL)
    private BigDecimal latitude;
    /**
     * 经度
     */
    @TableField(value = "longitude", condition = EQUAL)
    private BigDecimal longitude;
    /**
     * 位置名称
     */
    @TableField(value = "full_name", condition = LIKE)
    private String fullName;
    /**
     * 省,直辖市编码
     */
    @TableField(value = "province_code", condition = LIKE)
    private String provinceCode;
    /**
     * 市编码
     */
    @TableField(value = "city_code", condition = LIKE)
    private String cityCode;
    /**
     * 区县
     */
    @TableField(value = "region_code", condition = LIKE)
    private String regionCode;
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
