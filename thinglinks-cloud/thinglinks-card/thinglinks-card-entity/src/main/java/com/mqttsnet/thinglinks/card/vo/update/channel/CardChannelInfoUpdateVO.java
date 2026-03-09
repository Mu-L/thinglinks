package com.mqttsnet.thinglinks.card.vo.update.channel;

import com.mqttsnet.basic.base.entity.SuperEntity;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 表单修改方法VO
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
@EqualsAndHashCode
@Builder
@Schema(description = "物联卡渠道表")
public class CardChannelInfoUpdateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "渠道id")
    @NotNull(message = "请填写渠道id", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 渠道商名称（如:中国移动）
     */
    @Schema(description = "渠道商名称（如:中国移动）")
    @Size(max = 50, message = "渠道商名称（如:中国移动）长度不能超过{max}")
    private String channelName;
    /**
     * 密钥集合
     */
    @Schema(description = "密钥集合")
    @Size(max = 1000, message = "密钥集合长度不能超过{max}")
    private String keyParameter;
    /**
     * 是否直属官方平台(如直接对接是移动onelink  0不是  1是)
     */
    @Schema(description = "是否直属官方平台(如直接对接是移动onelink  0不是  1是)")
    private Integer officialFlag;
    /**
     * token是否需要重复刷新 true是 false否 默认是: false
     */
    @Schema(description = "token是否需要重复刷新 true是 false否 默认是: false")
    private Integer refreshFlag;
    /**
     * 所属运营商(1移动、2电信 、3联通）
     */
    @Schema(description = "所属运营商(1移动、2电信 、3联通）")
    private Integer operatorType;
    /**
     * 省份名称
     */
    @Schema(description = "省份名称")
    @Size(max = 100, message = "省份名称长度不能超过{max}")
    private String provinceName;
    /**
     * 省份编码
     */
    @Schema(description = "省份编码")
    @Size(max = 100, message = "省份编码长度不能超过{max}")
    private String provinceCode;
    /**
     * 公共应用键
     */
    @Schema(description = "公共应用键")
    @Size(max = 64, message = "公共应用键长度不能超过{max}")
    private String appKey;
    /**
     * 公共密钥
     */
    @Schema(description = "公共密钥")
    @Size(max = 64, message = "公共密钥长度不能超过{max}")
    private String secret;
    /**
     * 公共code
     */
    @Schema(description = "公共code")
    @Size(max = 100, message = "公共code长度不能超过{max}")
    private String code;
    /**
     * 客户appid
     */
    @Schema(description = "客户appid")
    @Size(max = 64, message = "客户appid长度不能超过{max}")
    private String appId;
    /**
     * 密匙
     */
    @Schema(description = "密匙")
    @Size(max = 64, message = "密匙长度不能超过{max}")
    private String password;
    /**
     * 渠道状态(0启用、1停用)
     */
    @Schema(description = "渠道状态(0启用、1停用)")
    @NotNull(message = "请填写渠道状态(0启用、1停用)")
    private Integer status;
    /**
     * 渠道类别(如:山东移动)
     */
    @Schema(description = "渠道类别(如:山东移动)")
    private Integer channelType;
    /**
     * 扩展参数
     */
    @Schema(description = "扩展参数")
    @Size(max = 2147483647, message = "扩展参数长度不能超过{max}")
    private String extendParams;
    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 250, message = "备注长度不能超过{max}")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
