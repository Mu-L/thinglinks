package com.mqttsnet.thinglinks.empowerment.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: thinglinks-cloud
 * @description: 赋能状态类型
 * @packagename: com.mqttsnet.thinglinks.empowerment.enumeration
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-09-18 23:38
 **/
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "EmpowermentStatus", description = "赋能状态类型")
public enum EmpowermentStatusEnum {

    /**
     * 计划中
     */
    PLANNING(0, "计划中"),

    /**
     * 执行中
     */
    IN_PROGRESS(1, "执行中"),

    /**
     * 执行完成
     */
    COMPLETED(2, "执行完成"),

    ;

    /**
     * 可选值
     */
    public static final List<Integer> STATE_COLLECTION = List.of(PLANNING.value, IN_PROGRESS.value, COMPLETED.value);
    private Integer value;
    private String desc;

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
