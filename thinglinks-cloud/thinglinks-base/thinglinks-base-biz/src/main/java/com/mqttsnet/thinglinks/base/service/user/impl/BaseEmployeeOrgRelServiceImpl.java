package com.mqttsnet.thinglinks.base.service.user.impl;

import cn.hutool.core.convert.Convert;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.thinglinks.base.entity.user.BaseEmployeeOrgRel;
import com.mqttsnet.thinglinks.base.manager.user.BaseEmployeeOrgRelManager;
import com.mqttsnet.thinglinks.base.service.user.BaseEmployeeOrgRelService;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 员工所在部门
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-18
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@DS(DsConstant.BASE_TENANT)
public class BaseEmployeeOrgRelServiceImpl extends SuperServiceImpl<BaseEmployeeOrgRelManager, Long, BaseEmployeeOrgRel> implements BaseEmployeeOrgRelService {
    @Override
    public List<Long> findOrgIdListByEmployeeId(Long employeeId) {
        ArgumentAssert.notNull(employeeId, "员工id不能为空");
        return superManager.listObjs(Wraps.<BaseEmployeeOrgRel>lbQ().select(BaseEmployeeOrgRel::getOrgId).eq(BaseEmployeeOrgRel::getEmployeeId, employeeId), Convert::toLong);
    }
}
