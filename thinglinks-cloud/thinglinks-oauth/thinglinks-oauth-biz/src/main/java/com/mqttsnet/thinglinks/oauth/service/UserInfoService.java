package com.mqttsnet.thinglinks.oauth.service;

import com.mqttsnet.thinglinks.base.entity.user.BaseOrg;
import com.mqttsnet.thinglinks.oauth.vo.param.RegisterByEmailVO;
import com.mqttsnet.thinglinks.oauth.vo.param.RegisterByMobileVO;
import com.mqttsnet.thinglinks.oauth.vo.result.OrgResultVO;

import java.util.List;

/**
 * @author mqttsnet
 * @version v1.0
 * @date 2022/9/16 12:21 PM
 * @create [2022/9/16 12:21 PM ] [mqttsnet] [初始创建]
 */
public interface UserInfoService {
    /**
     * 根据单位ID查找部门
     *
     * @param tenantId  租户ID
     * @param employeeId  员工id
     * @param companyId 单位ID
     * @return java.util.List<com.mqttsnet.thinglinks.model.entity.base.SysOrg>
     * @author mqttsnet
     * @date 2022/9/29 11:18 PM
     * @create [2022/9/29 11:18 PM ] [mqttsnet] [初始创建]
     */
    List<BaseOrg> findDeptByCompany(Long tenantId, Long companyId, Long employeeId);


    /**
     * 查询单位和部门信息
     *
     * @param tenantId 租户ID
     * @return com.mqttsnet.thinglinks.oauth.vo.result.OrgResultVO
     * @author mqttsnet
     * @date 2022/9/15 2:37 PM
     * @create [2022/9/15 2:37 PM ] [mqttsnet] [初始创建]
     */
    OrgResultVO findCompanyAndDept(Long tenantId);


    /**
     * 注册
     *
     * @param register 注册
     * @return
     */
    String registerByMobile(RegisterByMobileVO register);

    /**
     * 注册
     *
     * @param register 注册
     * @return
     */
    String registerByEmail(RegisterByEmailVO register);
}
