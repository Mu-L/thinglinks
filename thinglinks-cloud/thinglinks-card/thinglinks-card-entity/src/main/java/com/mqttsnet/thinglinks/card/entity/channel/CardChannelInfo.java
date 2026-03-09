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

import java.io.Serial;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 物联卡渠道表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 21:55:13
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("card_channel_info")
public class CardChannelInfo extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 渠道商名称（如:中国移动）
     */
    @TableField(value = "channel_name", condition = LIKE)
    private String channelName;
    /**
     * 密钥集合
     */
    @TableField(value = "key_parameter", condition = LIKE)
    private String keyParameter;
    /**
     * 是否直属官方平台(如直接对接是移动onelink  0不是  1是)
     */
    @TableField(value = "official_flag", condition = EQUAL)
    private Integer officialFlag;
    /**
     * token是否需要重复刷新 true是 false否 默认是: false
     */
    @TableField(value = "refresh_flag", condition = EQUAL)
    private Integer refreshFlag;
    /**
     * 所属运营商(1移动、2电信 、3联通）
     */
    @TableField(value = "operator_type", condition = EQUAL)
    private Integer operatorType;
    /**
     * 省份名称
     */
    @TableField(value = "province_name", condition = LIKE)
    private String provinceName;
    /**
     * 省份编码
     */
    @TableField(value = "province_code", condition = LIKE)
    private String provinceCode;
    /**
     * 公共应用键
     */
    @TableField(value = "appKey", condition = LIKE)
    private String appKey;
    /**
     * 公共密钥
     */
    @TableField(value = "secret", condition = LIKE)
    private String secret;
    /**
     * 公共code
     */
    @TableField(value = "code", condition = LIKE)
    private String code;
    /**
     * 客户appid
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 密匙
     */
    @TableField(value = "password", condition = LIKE)
    private String password;
    /**
     * 渠道状态(0启用、1停用)
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 渠道类别(如:山东移动)
     */
    @TableField(value = "channel_type", condition = EQUAL)
    private Integer channelType;
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
