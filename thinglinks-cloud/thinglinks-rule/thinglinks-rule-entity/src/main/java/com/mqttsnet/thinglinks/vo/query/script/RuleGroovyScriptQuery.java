package com.mqttsnet.thinglinks.vo.query.script;

import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.mqttsnet.basic.utils.StrPool;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.IntStream;

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
@Schema(title = "GroovyScriptQuery", description = "规则脚本参数")
public class RuleGroovyScriptQuery {
    private static final Splitter KEY_SPLITTER = Splitter.on(StrPool.COLON).trimResults();
    /**
     * 5个基础字段 + 业务标识
     */
    private static final int REQUIRED_PARTS = 6;

    /**
     * 脚本唯一标识
     * 唯一键定义(命名空间:平台编码:产品编码:渠道编码:业务编码:业务标识)
     * 中间通过:分割 StrPool.COLON
     */
    @Schema(description = "脚本唯一标识")
    private String uniqueKey;


    /**
     * 命名空间
     */
    @Schema(description = "命名空间")
    private String namespace;

    /**
     * 平台编码
     */
    @Schema(description = "平台编码")
    private String platformCode;

    /**
     * 产品编码
     */
    @Schema(description = "产品编码")
    private String productCode;

    /**
     * 渠道编码
     */
    @Schema(description = "渠道编码")
    private String channelCode;

    /**
     * 业务编码
     */
    @Schema(description = "业务编码")
    private String businessCode;

    /**
     * 业务标识
     */
    @Schema(description = "业务标识")
    private String businessIdentification;


    public RuleGroovyScriptQuery(String uniqueKey) {
        validateUniqueKey(uniqueKey);
        List<String> parts = KEY_SPLITTER.splitToList(uniqueKey);
        this.namespace = parts.get(0);
        this.platformCode = parts.get(1);
        this.productCode = parts.get(2);
        this.channelCode = parts.get(3);
        this.businessCode = parts.get(4);
        this.businessIdentification = parts.get(5);
        this.uniqueKey = uniqueKey;
    }


    private void validateUniqueKey(String uniqueKey) {
        // 基础非空检查
        Preconditions.checkArgument(StringUtils.isNotBlank(uniqueKey), "脚本唯一键不能为空");

        List<String> parts = KEY_SPLITTER.splitToList(uniqueKey);

        // 字段数量验证
        Preconditions.checkArgument(parts.size() == REQUIRED_PARTS, "脚本唯一键格式错误，应有6个冒号分隔字段，实际收到%d个字段。输入值: %s", parts.size(), uniqueKey);

        // 逐个字段非空验证
        IntStream.range(0, REQUIRED_PARTS).forEach(i -> {
            String part = parts.get(i);
            Preconditions.checkArgument(StringUtils.isNotBlank(part), "第%d个字段不能为空或空白。字段位置说明：0=命名空间 1=平台编码 2=产品编码 3=渠道编码 4=业务编码 5=业务标识。问题字段索引: %d", i + 1, i);
        });
    }


}
