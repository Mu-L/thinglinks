package com.mqttsnet.thinglinks.vo.result.plugin;

import cn.hutool.core.map.MapUtil;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.interfaces.echo.EchoVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Map;

/**
 * <p>
 * 表单查询方法返回值VO
 * 插件与实例及端口管理表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-27 16:30:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(description = "插件与实例及端口管理表")
public class PluginInstanceMappingResultVO extends Entity<Long> implements Serializable, EchoVO {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "主键")
    private Long id;

    /**
     * 插件唯一标识插件唯一标识
     */
    @Schema(description = "插件唯一标识插件唯一标识")
    private String pluginIdentification;
    /**
     * 实例唯一标识
     */
    @Schema(description = "实例唯一标识")
    private String instanceIdentification;
    /**
     * 插件在该实例上使用的端口号
     */
    @Schema(description = "插件在该实例上使用的端口号")
    private Integer port;
    /**
     * 端口类型或用途（如 HTTP, HTTPS, 管理端口等）
     */
    @Schema(description = "端口类型或用途（如 HTTP, HTTPS, 管理端口等）")
    private String portType;
    /**
     * 状态：0-正常，1-异常
     */
    @Schema(description = "状态：0-正常，1-异常")
    private Integer status;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;


}
