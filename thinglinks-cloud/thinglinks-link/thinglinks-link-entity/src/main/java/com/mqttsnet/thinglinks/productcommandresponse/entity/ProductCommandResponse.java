package com.mqttsnet.thinglinks.productcommandresponse.entity;

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
 * 产品模型服务命令属性响应参数
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
@TableName(value = "product_command_response", autoResultMap = true)
public class ProductCommandResponse extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 命令ID
     */
    @TableField(value = "command_id", condition = EQUAL)
    private Long commandId;
    /**
     * 服务ID
     */
    @TableField(value = "service_id", condition = EQUAL)
    private Long serviceId;
    /**
     * 指示数据类型。取值范围：string、int、decimal
     */
    @TableField(value = "datatype", condition = LIKE)
    private String datatype;
    /**
     * 指示枚举值。如开关状态status可有如下取值enumList : [OPEN,CLOSE]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
     */
    @TableField(value = "enumlist", condition = LIKE)
    private String enumlist;
    /**
     * 指示最大值。仅当dataType为int、decimal时生效，逻辑小于等于。
     */
    @TableField(value = "max", condition = LIKE)
    private String max;
    /**
     * 指示字符串长度。仅当dataType为string时生效。
     */
    @TableField(value = "maxlength", condition = LIKE)
    private String maxlength;
    /**
     * 指示最小值。仅当dataType为int、decimal时生效，逻辑大于等于。
     */
    @TableField(value = "min", condition = LIKE)
    private String min;
    /**
     * 命令中参数的描述，不影响实际功能，可配置为空字符串。
     */
    @TableField(value = "parameter_description", condition = LIKE)
    private String parameterDescription;

    /**
     * 参数编码。
     */
    @TableField(value = "parameter_code", condition = LIKE)
    private String parameterCode;

    /**
     * 命令中参数的名字。
     */
    @TableField(value = "parameter_name", condition = LIKE)
    private String parameterName;
    /**
     * 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。
     */
    @TableField(value = "required", condition = LIKE)
    private String required;
    /**
     * 指示步长。
     */
    @TableField(value = "step", condition = LIKE)
    private String step;
    /**
     * 指示单位。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
     */
    @TableField(value = "unit", condition = LIKE)
    private String unit;
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
