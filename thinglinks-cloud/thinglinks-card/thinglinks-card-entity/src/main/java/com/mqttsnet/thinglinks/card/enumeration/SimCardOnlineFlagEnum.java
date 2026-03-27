package com.mqttsnet.thinglinks.card.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * -----------------------------------------------------------------------------
 * File Name: SimCardOnlineFlagEnum
 * -----------------------------------------------------------------------------
 * Description:
 * sim卡在线状态
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/13       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/13 22:54
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "SimCardOnlineFlagEnum", description = "在线状态")
public enum SimCardOnlineFlagEnum {
    OFFLINE(0, "不在线"),
    ONLINE(1, "在线");

    private Integer value;
    private String desc;

    /**
     * 根据value获取对应的枚举
     *
     * @param value 在线状态的标识
     * @return 返回对应的枚举，如果没找到则返回 Optional.empty()
     */
    public static Optional<SimCardOnlineFlagEnum> fromValue(Integer value) {
        return Stream.of(SimCardOnlineFlagEnum.values())
                .filter(type -> Objects.equals(type.getValue(), value))
                .findFirst();
    }
}
