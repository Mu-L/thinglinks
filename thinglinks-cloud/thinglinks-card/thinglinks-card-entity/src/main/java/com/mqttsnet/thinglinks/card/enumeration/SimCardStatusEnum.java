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
 * File Name: SimCardStatusEnum
 * -----------------------------------------------------------------------------
 * Description:
 * SIM卡状态
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
 * @date 2024/7/13 22:51
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "SimCardStatusEnum", description = "SIM卡状态")
public enum SimCardStatusEnum {

    PENDING_ACTIVATION(1, "待激活"),
    ACTIVATED(2, "已激活"),
    DEACTIVATED(3, "停机");

    private Integer value;
    private String desc;

    /**
     * 根据value获取对应的枚举
     *
     * @param value SIM卡状态的标识
     * @return 返回对应的枚举，如果没找到则返回 Optional.empty()
     */
    public static Optional<SimCardStatusEnum> fromValue(Integer value) {
        return Stream.of(SimCardStatusEnum.values())
                .filter(type -> Objects.equals(type.getValue(), value))
                .findFirst();
    }
}
