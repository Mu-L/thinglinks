package com.mqttsnet.thinglinks.oauth.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.mqttsnet.basic.cache.redis2.CacheResult;
import com.mqttsnet.basic.cache.repository.CacheOps;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.base.entity.user.BaseEmployee;
import com.mqttsnet.thinglinks.base.entity.user.BaseOrg;
import com.mqttsnet.thinglinks.base.service.user.BaseEmployeeService;
import com.mqttsnet.thinglinks.base.service.user.BaseOrgService;
import com.mqttsnet.thinglinks.common.cache.common.CaptchaCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.properties.SystemProperties;
import com.mqttsnet.thinglinks.oauth.service.UserInfoService;
import com.mqttsnet.thinglinks.oauth.vo.param.RegisterByEmailVO;
import com.mqttsnet.thinglinks.oauth.vo.param.RegisterByMobileVO;
import com.mqttsnet.thinglinks.oauth.vo.result.OrgResultVO;
import com.mqttsnet.thinglinks.oauth.vo.result.TenantResultVO;
import com.mqttsnet.thinglinks.system.entity.tenant.DefUser;
import com.mqttsnet.thinglinks.system.entity.tenant.DefUserTenantRel;
import com.mqttsnet.thinglinks.system.service.tenant.DefTenantService;
import com.mqttsnet.thinglinks.system.service.tenant.DefUserService;
import com.mqttsnet.thinglinks.system.service.tenant.DefUserTenantRelService;
import com.mqttsnet.thinglinks.system.vo.result.tenant.DefTenantResultVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author mqttsnet
 * @version v1.0
 * @date 2022/9/16 12:21 PM
 * @create [2022/9/16 12:21 PM ] [mqttsnet] [初始创建]
 */
@Service
@RequiredArgsConstructor
public class UserInfoServiceImpl implements UserInfoService {
    protected final BaseEmployeeService baseEmployeeService;
    protected final BaseOrgService baseOrgService;
    protected final DefUserTenantRelService defUserTenantRelService;
    protected final DefUserService defUserService;
    protected final DefTenantService defTenantService;
    protected final CacheOps cacheOps;
    protected final SystemProperties systemProperties;

    @Override
    public OrgResultVO findCompanyAndDept(Long tenantId) {
        Long userId = ContextUtil.getUserId();
        Long companyId = ContextUtil.getCurrentCompanyId();
        Long deptId = ContextUtil.getCurrentDeptId();
        List<DefTenantResultVO> defTenantList = defTenantService.listTenantByUserId(userId);
        if (CollUtil.isEmpty(defTenantList)) {
            return OrgResultVO.builder().build();
        }

        List<TenantResultVO> tenantList = BeanPlusUtil.toBeanList(defTenantList, TenantResultVO.class);

        if (tenantId == null) {
            return OrgResultVO.builder().tenantList(tenantList).build();
        }

        if (tenantList.stream().noneMatch(item -> item.getId().equals(tenantId))) {
            throw BizException.wrap("请选择正确的企业");
        }

        ContextUtil.setTenantBasePoolName(tenantId);
        DefUserTenantRel defUserTenantRel = defUserTenantRelService.getEmployeeByTenantAndUser(tenantId, userId);
        ArgumentAssert.notNull(defUserTenantRel, "用户不属于该企业");
        BaseEmployee baseEmployee = baseEmployeeService.getByIdCache(defUserTenantRel.getId());
        ArgumentAssert.notNull(baseEmployee, "用户不属于该企业");

        for (TenantResultVO tenant : tenantList) {
            ContextUtil.setTenantId(tenant.getId());
            List<BaseOrg> orgList = baseOrgService.findOrgByEmployeeId(tenant.getEmployeeId());
            tenant.setOrgList(orgList);
        }

        // 上次登录的单位
        Long currentCompanyId = companyId != null ? companyId : baseEmployee.getLastCompanyId();
        // 上次登录的部门
        Long currentDeptId = deptId != null ? deptId : baseEmployee.getLastDeptId();
        return OrgResultVO.builder()
                .tenantList(tenantList)
                .employeeId(baseEmployee.getId())
                .currentCompanyId(currentCompanyId)
                .currentDeptId(currentDeptId).build();
    }

    @Override
    public List<BaseOrg> findDeptByCompany(Long tenantId, Long companyId, Long employeeId) {
        ContextUtil.setTenantBasePoolName(tenantId);
        return baseOrgService.findDeptByEmployeeId(employeeId, companyId);
    }

    @Override
    public String registerByMobile(RegisterByMobileVO register) {
        if (systemProperties.getVerifyCaptcha()) {
//            短信验证码
            CacheKey cacheKey = new CaptchaCacheKeyBuilder().key(register.getMobile(), register.getKey());
            CacheResult<String> code = cacheOps.get(cacheKey);
            ArgumentAssert.equals(code.getValue(), register.getCode(), "验证码不正确");
        }
        ArgumentAssert.equals(register.getConfirmPassword(), register.getPassword(), "密码和确认密码不一致");
        DefUser defUser = BeanUtil.toBean(register, DefUser.class);

        defUserService.register(defUser);

        return defUser.getMobile();
    }

    @Override
    public String registerByEmail(RegisterByEmailVO register) {
        if (systemProperties.getVerifyCaptcha()) {
//            短信验证码
            CacheKey cacheKey = new CaptchaCacheKeyBuilder().key(register.getEmail(), register.getKey());
            CacheResult<String> code = cacheOps.get(cacheKey);
            ArgumentAssert.equals(code.getValue(), register.getCode(), "验证码不正确");
        }
        ArgumentAssert.equals(register.getConfirmPassword(), register.getPassword(), "密码和确认密码不一致");
        DefUser defUser = BeanUtil.toBean(register, DefUser.class);

        defUserService.registerByEmail(defUser);

        return defUser.getEmail();
    }
}
