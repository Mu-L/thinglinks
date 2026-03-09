package com.mqttsnet.thinglinks.system.mapper.tenant;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.system.entity.tenant.DefTenant;
import com.mqttsnet.thinglinks.system.vo.result.tenant.DefTenantResultVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * 企业
 * </p>
 *
 * @author mqttsnet
 * @date 2021-09-13
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface DefTenantMapper extends SuperMapper<DefTenant> {
    /**
     * 查询用户的可用企业
     *
     * @param userId 用户id
     * @return
     */
    List<DefTenantResultVO> listTenantByUserId(@Param("userId") Long userId);
}
