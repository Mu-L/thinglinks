package com.mqttsnet.thinglinks.card.vo.param;

import com.mqttsnet.thinglinks.card.validation.constraint.anytenant.ValidCardSimOpenApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
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
 * -----------------------------------------------------------------------------
 * File Name: CardSimOpenApiParam
 * -----------------------------------------------------------------------------
 * Description:
 * 物联卡公共开发api通用参数
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/15       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/15 23:02
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@ValidCardSimOpenApiParam
@Schema(description = "物联网卡OpenApi 通用参数")
public class CardSimOpenApiParam implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    /**
     * 租户ID
     */
    @Schema(description = "租户ID")
    private String tenantId;
    /**
     * SIM卡唯一识别码
     */
    @Schema(description = "SIM卡唯一识别码")
    private String iccid;
    /**
     * 卡号
     */
    @Schema(description = "卡号")
    private String cardNumber;
}
