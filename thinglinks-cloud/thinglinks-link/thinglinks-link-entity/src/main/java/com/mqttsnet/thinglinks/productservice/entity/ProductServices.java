package com.mqttsnet.thinglinks.productservice.entity;

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
 * 产品模型服务表
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
@TableName(value = "product_service", autoResultMap = true)
public class ProductServices extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    @TableField(value = "product_id", condition = EQUAL)
    private Long productId;
    /**
     * 服务编码:支持英文大小写、数字、下划线和中划线
     */
    @TableField(value = "service_code", condition = LIKE)
    private String serviceCode;
    /**
     * 服务名称
     */
    @TableField(value = "service_name", condition = LIKE)
    private String serviceName;
    /**
     * 服务类型
     */
    @TableField(value = "service_type", condition = LIKE)
    private String serviceType;
    /**
     * 状态(字典值：0启用  1停用)
     */
    @TableField(value = "service_status", condition = EQUAL)
    private Integer serviceStatus;
    /**
     * 服务的描述信息:文本描述，不影响实际功能，可配置为空字符串。
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
