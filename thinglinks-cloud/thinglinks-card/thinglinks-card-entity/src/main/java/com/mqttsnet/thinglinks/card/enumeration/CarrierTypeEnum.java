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
 * File Name: CarrierTypeEnum
 * -----------------------------------------------------------------------------
 * Description:
 * 运营商类型
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
@Schema(title = "CarrierTypeEnum", description = "运营商类型")
public enum CarrierTypeEnum {

    MOBILE(1, "移动"),
    TELECOM(2, "电信"),
    UNICOM(3, "联通");

    private Integer value;
    private String desc;

    /**
     * 根据value获取对应的枚举
     *
     * @param value 运营商类型的标识
     * @return 返回对应的枚举，如果没找到则返回 Optional.empty()
     */
    public static Optional<CarrierTypeEnum> fromValue(Integer value) {
        return Stream.of(CarrierTypeEnum.values())
                .filter(type -> Objects.equals(type.getValue(), value))
                .findFirst();
    }
}
