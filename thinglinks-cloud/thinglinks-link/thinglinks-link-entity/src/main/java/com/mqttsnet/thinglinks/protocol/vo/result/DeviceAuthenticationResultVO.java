package com.mqttsnet.thinglinks.protocol.vo.result;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import cn.hutool.core.map.MapUtil;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.interfaces.echo.EchoVO;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceAclRuleCacheVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @program: thinglinks-cloud
 * @description: 设备认证结果VO
 * @packagename: com.mqttsnet.thinglinks.device.vo.result
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-07-13 23:17
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(title = "DeviceAuthenticationResultVO", description = "设备认证结果")
public class DeviceAuthenticationResultVO<T> extends Entity<Long> implements Serializable, EchoVO {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "认证结果")
    private Boolean certificationResult;

    @Schema(description = "认证失败原因")
    private String errorMessage;

    @Schema(description = "设备档案信息")
    private DeviceInfoResultVO deviceInfoResult;

    @Schema(description = "ACL授权规则信息")
    private List<DeviceAclRuleCacheVO> aclRuleListResult;

    @Schema(description = "租户ID")
    private Long tenantId;

}
