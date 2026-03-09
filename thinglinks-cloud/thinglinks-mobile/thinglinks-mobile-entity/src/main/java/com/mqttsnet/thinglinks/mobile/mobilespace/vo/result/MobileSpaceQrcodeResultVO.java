package com.mqttsnet.thinglinks.mobile.mobilespace.vo.result;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * @author xiaonannet
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@Builder
@Schema(title = "MobileSpaceQrcodeResultVO", description = "空间二维码信息结果VO")
public class MobileSpaceQrcodeResultVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 场景(QrcodeConstant)
     */
    @Schema(description = "场景(spaceScene)")
    private String scene;

    @Schema(description = "空间ID")
    private Long spaceId;

    /**
     * 人员类型( 0:成员 1:管理员  2:所有者)
     */
    @Schema(description = "人员类型( 0:成员 1:管理员  2:所有者)")
    private Integer memberType;


}
