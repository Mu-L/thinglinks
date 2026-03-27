package com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.param;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;


/**
 * <p>
 * 空间二维码参数
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 15:40:08
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@Schema(description = "空间二维码参数")
public class MobileSpaceQrcodeParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 空间ID
     */
    @Schema(description = "空间ID")
    @NotNull(message = "请填写空间ID")
    private Long spaceId;
    /**
     * 人员类型( 0:成员 1:管理员  2:所有者)
     */
    @Schema(description = "人员类型( 0:成员 1:管理员  2:所有者)")
    @NotNull(message = "请填写人员类型( 0:成员 1:管理员  2:所有者)")
    private Integer memberType;


}
