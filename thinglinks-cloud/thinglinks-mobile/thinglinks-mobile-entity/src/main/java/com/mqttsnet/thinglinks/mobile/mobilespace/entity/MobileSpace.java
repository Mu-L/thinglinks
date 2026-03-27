package com.mqttsnet.thinglinks.mobile.mobilespace.entity;

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
 * 移动端-空间表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-29 10:17:46
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("mobile_space")
public class MobileSpace extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 空间名称
     */
    @TableField(value = "space_name", condition = LIKE)
    private String spaceName;
    /**
     * 空间经度
     */
    @TableField(value = "longitude", condition = LIKE)
    private String longitude;
    /**
     * 空间纬度
     */
    @TableField(value = "latitude", condition = LIKE)
    private String latitude;
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


}
