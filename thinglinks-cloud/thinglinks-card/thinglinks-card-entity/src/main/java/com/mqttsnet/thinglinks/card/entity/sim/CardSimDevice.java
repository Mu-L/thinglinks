package com.mqttsnet.thinglinks.card.entity.sim;

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

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;


/**
 * <p>
 * 实体类
 * 物联卡设备关系表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-27 00:10:22
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("card_sim_device")
public class CardSimDevice extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 物联卡ID
     */
    @TableField(value = "card_id", condition = EQUAL)
    private Long cardId;
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
     * 设备标识
     */
    @TableField(value = "device_identification", condition = LIKE)
    private String deviceIdentification;


}
