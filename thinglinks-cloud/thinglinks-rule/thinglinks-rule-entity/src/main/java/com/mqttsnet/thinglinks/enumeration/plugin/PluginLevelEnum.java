package com.mqttsnet.thinglinks.enumeration.plugin;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * -----------------------------------------------------------------------------
 * File Name: PluginLevelEnum
 * -----------------------------------------------------------------------------
 * Description:
 * PluginLevelEnum
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/8/30       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/8/30 17:37
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "PluginLevelEnum", description = "插件级别 枚举")
public enum PluginLevelEnum {
    SYSTEM(0, "系统级"),
    USER(1, "用户级");

    private Integer value;
    private String desc;

    public static Optional<PluginLevelEnum> fromValue(Integer value) {
        return Stream.of(PluginLevelEnum.values())
                .filter(level -> level.getValue().equals(value))
                .findFirst();
    }
}
