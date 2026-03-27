package com.mqttsnet.thinglinks.mobile.mobilespacemember.entity;

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
 * 空间人员绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 15:40:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("mobile_space_member")
public class MobileSpaceMember extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 空间ID
     */
    @TableField(value = "space_id", condition = EQUAL)
    private Long spaceId;
    /**
     * 人员ID
     */
    @TableField(value = "member_id", condition = EQUAL)
    private Long memberId;
    /**
     * 成员类型人员类型( 0:成员 1:管理员  2:所有者)
     */
    @TableField(value = "member_type", condition = EQUAL)
    private Integer memberType;
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
