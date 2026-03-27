package com.mqttsnet.thinglinks.device.service;

import java.util.List;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceAclRuleCacheVO;
import com.mqttsnet.thinglinks.device.entity.DeviceAclRule;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAclCheckQuery;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAclCheckResultVO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


/**
 * <p>
 * 业务接口
 * 设备访问控制(ACL)规则表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-11 19:57:46
 * @create [2025-06-11 19:57:46] [mqttsnet]
 */
public interface DeviceAclRuleService extends SuperService<Long, DeviceAclRule> {


    /**
     * Checks the ACL (Access Control List) permission for a given device.
     *
     * @param deviceAclCheckQuery The query object containing the device ID and the user ID for which the ACL permission needs to be checked.
     * @return {@link DeviceAclCheckResultVO} The result of the ACL permission check.
     */
    DeviceAclCheckResultVO checkAclPermission(@Valid DeviceAclCheckQuery deviceAclCheckQuery);


    /**
     * Refreshes the cache for all device ACL rules.
     *
     * @param tenantId The identifier of the tenant for which the cache needs to be refreshed.
     */
    void refreshAllDeviceAclRuleCache(@NotNull Long tenantId);


    /**
     * Retrieves the ACL rules cache for a given product and device, sorted by priority.
     *
     * @param clientIdentifier clientId
     * @return {@link List<DeviceAclRuleCacheVO>} The list of ACL rules cache for the given product and device, sorted by priority.
     */
    List<DeviceAclRuleCacheVO> getDeviceAclRuleCacheVOList(@NotBlank String clientIdentifier);
}


