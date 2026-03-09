package com.mqttsnet.thinglinks.system.service.application;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.model.entity.system.SysUser;
import com.mqttsnet.thinglinks.system.entity.application.DefTenantApplicationRel;
import com.mqttsnet.thinglinks.system.vo.result.application.DefTenantApplicationRelResultVO;
import com.mqttsnet.thinglinks.system.vo.save.application.DefTenantApplicationRelSaveVO;
import com.mqttsnet.thinglinks.system.vo.update.application.DefTenantApplicationRelUpdateVO;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 租户的应用
 * </p>
 *
 * @author mqttsnet
 * @date 2021-09-15
 */
public interface DefTenantApplicationRelService extends SuperService<Long, DefTenantApplicationRel> {
    /**
     * 授权
     *
     * @param saveVO  saveVO
     * @param sysUser 系统用户信息
     * @return java.lang.Boolean
     * @author mqttsnet
     * @date 2021/9/27 5:44 下午
     * @create [2021/9/27 5:44 下午 ] [mqttsnet] [初始创建]
     */
    Boolean grant(DefTenantApplicationRelSaveVO saveVO, SysUser sysUser);

    /**
     * 取消授权
     *
     * @param ids     ids
     * @param sysUser 系统用户信息
     * @return java.lang.Boolean
     * @author mqttsnet
     * @date 2021/9/27 5:44 下午
     * @create [2021/9/27 5:44 下午 ] [mqttsnet] [初始创建]
     */
    Boolean cancel(List<Long> ids, SysUser sysUser);

    /**
     * 续期
     *
     * @param updateVO updateVO
     * @param sysUser  系统用户信息
     * @return java.lang.Boolean
     * @author mqttsnet
     * @date 2021/9/27 5:44 下午
     * @create [2021/9/27 5:44 下午 ] [mqttsnet] [初始创建]
     */
    Boolean renewal(DefTenantApplicationRelUpdateVO updateVO, SysUser sysUser);

    /**
     * 查询授权信息
     *
     * @param id id
     * @return com.mqttsnet.thinglinks.tenant.vo.result.tenant.DefTenantApplicationRelResultVO
     * @author mqttsnet
     * @date 2021/9/29 10:48 下午
     * @create [2021/9/29 10:48 下午 ] [mqttsnet] [初始创建]
     */
    DefTenantApplicationRelResultVO getDetailById(Long id);
}
