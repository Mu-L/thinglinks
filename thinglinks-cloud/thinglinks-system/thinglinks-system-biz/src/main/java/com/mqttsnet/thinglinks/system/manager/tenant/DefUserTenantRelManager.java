package com.mqttsnet.thinglinks.system.manager.tenant;

import com.mqttsnet.basic.base.manager.SuperCacheManager;
import com.mqttsnet.thinglinks.system.entity.tenant.DefUserTenantRel;
import com.mqttsnet.thinglinks.system.vo.result.tenant.DefUserTenantRelResultVO;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 员工
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-27
 */
public interface DefUserTenantRelManager extends SuperCacheManager<DefUserTenantRel> {
    /**
     * 根据用户id查询员工
     *
     * @param userId 用户id
     * @return
     */
    List<DefUserTenantRelResultVO> listEmployeeByUserId(Long userId);
}
