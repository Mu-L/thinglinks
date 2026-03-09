package com.mqttsnet.thinglinks.card.entity.channel;

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
 * 物联卡渠道信息配置表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-27 00:06:12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("card_channel_info_config")
public class CardChannelInfoConfig extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 渠道id
     */
    @TableField(value = "channel_id", condition = EQUAL)
    private Long channelId;
    /**
     * 请求类型编码
     */
    @TableField(value = "request_type_code", condition = LIKE)
    private String requestTypeCode;
    /**
     * 供应商接口地址
     */
    @TableField(value = "url", condition = LIKE)
    private String url;
    /**
     * 扩展参数
     */
    @TableField(value = "extend_params", condition = LIKE)
    private String extendParams;
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
