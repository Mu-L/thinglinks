package com.mqttsnet.thinglinks.base.service.user.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.base.entity.user.BaseOrgRoleRel;
import com.mqttsnet.thinglinks.base.manager.user.BaseOrgRoleRelManager;
import com.mqttsnet.thinglinks.base.service.user.BaseOrgRoleRelService;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 组织的角色
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
public class BaseOrgRoleRelServiceImpl extends SuperServiceImpl<BaseOrgRoleRelManager, Long, BaseOrgRoleRel> implements BaseOrgRoleRelService {

}
