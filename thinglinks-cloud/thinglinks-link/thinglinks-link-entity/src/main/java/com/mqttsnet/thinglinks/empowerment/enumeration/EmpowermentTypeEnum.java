package com.mqttsnet.thinglinks.empowerment.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @program: thinglinks-cloud
 * @description: 赋能类型
 * @packagename: com.mqttsnet.thinglinks.empowerment.enumeration
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-09-16 21:50
 **/
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "EmpowermentTypeEnum", description = "赋能类型")
public enum EmpowermentTypeEnum {

    /**
     * 未知
     */
    UNKNOWN(0, "未知"),

    /**
     * 产品
     */
    PRODUCT(1, "产品"),

    /**
     * 设备
     */
    DEVICE(2, "设备"),

    ;

    /**
     * 可选值
     */
    public static final List<Integer> TYPE_COLLECTION = List.of(UNKNOWN.value, PRODUCT.value, DEVICE.value);
    private Integer value;
    private String desc;

    public static EmpowermentTypeEnum valueOf(Integer value) {
        return Arrays.stream(values())
                .filter(type -> type.getValue().equals(Optional.ofNullable(value).orElse(-1)))
                .findFirst()
                .orElse(UNKNOWN);
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
