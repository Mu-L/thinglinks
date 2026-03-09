package com.mqttsnet.thinglinks.productproperty.entity;

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

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;


/**
 * <p>
 * 实体类
 * 产品模型服务属性表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "product_property", autoResultMap = true)
public class ProductProperty extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 服务ID
     */
    @TableField(value = "service_id", condition = EQUAL)
    private Long serviceId;
    /**
     * 指示属性编码
     */
    @TableField(value = "property_code", condition = LIKE)
    private String propertyCode;
    /**
     * 指示属性名称
     */
    @TableField(value = "property_name", condition = LIKE)
    private String propertyName;
    /**
     * 指示数据类型：取值范围：string、int、decimal（float和double都可以使用此类型）、DateTime、jsonObject上报数据时，复杂类型数据格式如下：•DateTime:yyyyMMdd’T’HHmmss’Z’如:20151212T121212Z•jsonObject：自定义json结构体，平台不理解只透传
     */
    @TableField(value = "datatype", condition = LIKE)
    private String datatype;
    /**
     * 属性描述，不影响实际功能，可配置为空字符串。
     */
    @TableField(value = "description", condition = LIKE)
    private String description;
    /**
     * 指示枚举值:如开关状态status可有如下取值enumList: [OPEN,CLOSE]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
     */
    @TableField(value = "enumlist", condition = LIKE)
    private String enumlist;
    /**
     * 指示最大值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑小于等于。
     */
    @TableField(value = "max", condition = LIKE)
    private String max;
    /**
     * 指示字符串长度。仅当dataType为string、DateTime时生效。
     */
    @TableField(value = "maxlength", condition = LIKE)
    private String maxlength;
    /**
     * 指示访问模式。R:可读；W:可写；E属性值更改时上报数据取值范围：R、RW、RE、RWE
     */
    @TableField(value = "method", condition = LIKE)
    private String method;
    /**
     * 指示最小值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑大于等于。
     */
    @TableField(value = "min", condition = LIKE)
    private String min;
    /**
     * 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。(字典值link_product_isRequired：0非必填 1必填)
     */
    @TableField(value = "required", condition = LIKE)
    private String required;
    /**
     * 指示步长。
     */
    @TableField(value = "step", condition = LIKE)
    private String step;
    /**
     * 指示单位。支持长度不超过50。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
     */
    @TableField(value = "unit", condition = LIKE)
    private String unit;
    /**
     * 图标png图
     */
    @TableField(value = "icon", condition = LIKE)
    private String icon;
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
