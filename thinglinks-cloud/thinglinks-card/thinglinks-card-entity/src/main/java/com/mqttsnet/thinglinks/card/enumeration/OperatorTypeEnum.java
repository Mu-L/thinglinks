package com.mqttsnet.thinglinks.card.enumeration;

import com.mqttsnet.basic.interfaces.BaseEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.stream.Stream;

/**
 * <p>
 * 实体注释中生成的类型枚举
 * 消息表
 * </p>
 *
 * @author mqttsnet
 * @date 2021-11-15
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "OperatorTypeEnum", description = "运营商类型")
public enum OperatorTypeEnum implements BaseEnum {

    CHINA_MOBILE(1, "中国移动"),

    CHINA_TELECOM(2, "中国电信"),

    CHINA_UNICOM(3, "中国联通"),
    ;
    @Schema(description = "编码")
    private int code;

    @Schema(description = "描述")
    private String desc;

    public static OperatorTypeEnum match(int code, OperatorTypeEnum def) {
        return Stream.of(values()).parallel().filter(item -> item.code == code).findAny().orElse(def);
    }

    public static OperatorTypeEnum get(int code) {
        return match(code, null);
    }

    public boolean eq(OperatorTypeEnum val) {
        return val != null && eq(val.name());
    }

    @Override
    @Schema(description = "编码", allowableValues = "TO_DO,NOTICE,EARLY_WARNING", example = "TO_DO")
    public String getCode() {
        return this.name();
    }

}
