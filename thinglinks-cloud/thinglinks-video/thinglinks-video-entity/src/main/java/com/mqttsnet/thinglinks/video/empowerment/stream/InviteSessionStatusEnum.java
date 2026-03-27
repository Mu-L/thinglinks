package com.mqttsnet.thinglinks.video.empowerment.stream;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.stream.Stream;

/**
 * 标识invite消息发出后的各个状态，
 * 收到ok钱停止invite发送cancel，
 * 收到200ok后发送BYE停止invite
 *
 * @author mqttsnet
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(title = "InviteSessionStatusEnum", description = "invite消息状态枚举")
public enum InviteSessionStatusEnum {
    READY("ready", "ready"),
    OK("ok", "ok"),
    ;


    private String value;
    private String desc;


    /**
     * 根据value获取对应的枚举
     *
     * @param value 标识
     * @return 返回对应的枚举，如果没找到则返回 Optional.empty()
     */
    public static Optional<InviteSessionStatusEnum> fromValue(String value) {
        return Stream.of(InviteSessionStatusEnum.values())
                .filter(type -> type.getValue().equalsIgnoreCase(value))
                .findFirst();
    }
}
