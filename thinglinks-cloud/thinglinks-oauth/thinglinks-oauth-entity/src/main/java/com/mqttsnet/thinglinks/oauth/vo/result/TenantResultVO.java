package com.mqttsnet.thinglinks.oauth.vo.result;

import com.mqttsnet.thinglinks.base.entity.user.BaseOrg;
import com.mqttsnet.thinglinks.system.vo.result.tenant.DefTenantResultVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 租户信息
 *
 * @author mqttsnet
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@Schema(description = "租户扩展")
public class TenantResultVO extends DefTenantResultVO {
    @Schema(description = "当前租户下，所属单位或部门")
    private List<BaseOrg> orgList;
}
