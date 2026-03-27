package com.mqttsnet.thinglinks.mobile.mobilespacemember.enumeration;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 空间人员类型
 * @author xiaonannet
 */

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "SpaceMemberTypeEnum", description = "空间人员类型枚举")
public enum SpaceMemberTypeEnum {

    /**
     * 普通成员
     */
    MEMBER(0, "普通成员"),

    /**
     * 管理员
     */
    ADMIN(1, "管理员"),

    /**
     * 所有者
     */
    OWNER(2, "所有者"),

    ;

    /**
     * 可选值
     */
    public static final List<Integer> STATE_COLLECTION = List.of(MEMBER.value, ADMIN.value, OWNER.value);
    private Integer value;
    private String desc;

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
