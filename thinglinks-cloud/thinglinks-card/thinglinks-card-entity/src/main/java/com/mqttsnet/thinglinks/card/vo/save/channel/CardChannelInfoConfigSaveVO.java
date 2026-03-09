package com.mqttsnet.thinglinks.card.vo.save.channel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
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
 * 表单保存方法VO
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
@EqualsAndHashCode
@Builder
@Schema(description = "物联卡渠道信息配置表")
public class CardChannelInfoConfigSaveVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 渠道id
     */
    @Schema(description = "渠道id")
    private Long channelId;
    /**
     * 请求类型编码
     */
    @Schema(description = "请求类型编码")
    @NotEmpty(message = "请填写请求类型编码")
    @Size(max = 50, message = "请求类型编码长度不能超过{max}")
    private String requestTypeCode;
    /**
     * 供应商接口地址
     */
    @Schema(description = "供应商接口地址")
    @Size(max = 255, message = "供应商接口地址长度不能超过{max}")
    private String url;
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
    @Size(max = 500, message = "备注长度不能超过{max}")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
