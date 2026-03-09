package com.mqttsnet.thinglinks.producttopic.entity;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;

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


/**
 * <p>
 * 实体类
 * 产品Topic信息表
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
@TableName(value = "product_topic", autoResultMap = true)
public class ProductTopic extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 产品标识
     */
    @TableField(value = "product_identification", condition = EQUAL)
    private String productIdentification;

    /**
     * 功能类型
     */
    @TableField(value = "function_type", condition = EQUAL)
    private Integer functionType;

    /**
     * Topic类型(0:基础Topic,1:自定义Topic)
     */
    @TableField(value = "topic_type", condition = EQUAL)
    private Integer topicType;
    /**
     * topic
     */
    @TableField(value = "topic", condition = LIKE)
    private String topic;
    /**
     * 发布者
     */
    @TableField(value = "publisher", condition = EQUAL)
    private Integer publisher;
    /**
     * 订阅者
     */
    @TableField(value = "subscriber", condition = EQUAL)
    private Integer subscriber;
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
