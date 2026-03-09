package com.mqttsnet.thinglinks.video.empowerment.device;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Optional;

/**
 * Description:
 * 支持的通道数据类型
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2025/5/9
 */
@Getter
@AllArgsConstructor
@Schema(title = "ChannelDataTypeEnum", description = "支持的通道数据类型")
public enum ChannelDataTypeEnum {

    GB28181(1, "国标28181"),
    STREAM_PUSH(2, "推流设备"),
    STREAM_PROXY(3, "拉流代理");

    private final Integer value;
    private final String desc;

    ChannelDataTypeEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static Optional<ChannelDataTypeEnum> fromValue(Integer value) {
        return Arrays.stream(values())
                .filter(type -> type.getValue().equals(value))
                .findFirst();
    }
}

