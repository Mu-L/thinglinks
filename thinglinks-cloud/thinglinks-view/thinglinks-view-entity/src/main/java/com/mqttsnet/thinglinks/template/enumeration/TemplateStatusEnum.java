package com.mqttsnet.thinglinks.template.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * <p>
 * 设备状态
 * </p>
 *
 * @author shihuan sun
 * @date 2023-04-14
 */
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "TemplateStatusEnum", description = "项目模板状态类型")
public enum TemplateStatusEnum {

    /**
     * 已发布
     */
    PUNISHED(1, "已发布"),

    /**
     * 未发布
     */
    UNPUNISHED(-1, "未发布");


    /**
     * 项目状态
     */
    public static final List<Integer> ALL_STATE_COLLECTION = List.of(PUNISHED.value, UNPUNISHED.value);
    private Integer value;
    private String desc;

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
