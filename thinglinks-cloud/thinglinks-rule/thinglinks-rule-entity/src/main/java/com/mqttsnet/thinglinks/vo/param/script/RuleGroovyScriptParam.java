package com.mqttsnet.thinglinks.vo.param.script;

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

/**
 * GroovyScript 查询实体
 *
 * @author mqttsnet 2025/03/18 12:42
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@Schema(title = "RuleGroovyScriptParam", description = "规则脚本参数")
public class RuleGroovyScriptParam {

    /**
     * 命名空间
     */
    @Schema(description = "命名空间")
    @NotEmpty(message = "请填写命名空间")
    @Size(max = 128, message = "命名空间长度不能超过{max}")
    private String namespace;
    /**
     * 平台编码
     */
    @Schema(description = "平台编码")
    @NotEmpty(message = "请填写平台编码")
    @Size(max = 128, message = "平台编码长度不能超过{max}")
    private String platformCode;
    /**
     * 产品编码
     */
    @Schema(description = "产品编码")
    @NotEmpty(message = "请填写产品编码")
    @Size(max = 128, message = "产品编码长度不能超过{max}")
    private String productCode;
    /**
     * 渠道编码
     */
    @Schema(description = "渠道编码")
    @NotEmpty(message = "请填写渠道编码")
    @Size(max = 128, message = "渠道编码长度不能超过{max}")
    private String channelCode;
    /**
     * 业务编码
     */
    @Schema(description = "业务编码")
    @NotEmpty(message = "请填写业务编码")
    @Size(max = 128, message = "业务编码长度不能超过{max}")
    private String businessCode;
    /**
     * 业务标识
     */
    @Schema(description = "业务标识")
    @NotEmpty(message = "请填写业务标识")
    @Size(max = 100, message = "业务标识长度不能超过{max}")
    private String businessIdentification;


    /**
     * 脚本内容
     */
    @Schema(description = "脚本内容")
    @NotEmpty(message = "请填写脚本内容")
    @Size(max = 2147483647, message = "脚本内容长度不能超过{max}")
    private String scriptContent;

}
