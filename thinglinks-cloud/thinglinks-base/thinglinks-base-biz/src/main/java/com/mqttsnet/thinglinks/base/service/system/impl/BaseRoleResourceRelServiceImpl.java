package com.mqttsnet.thinglinks.base.service.system.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.base.entity.system.BaseRoleResourceRel;
import com.mqttsnet.thinglinks.base.manager.system.BaseRoleResourceRelManager;
import com.mqttsnet.thinglinks.base.service.system.BaseRoleResourceRelService;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 角色的资源
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
public class BaseRoleResourceRelServiceImpl extends SuperServiceImpl<BaseRoleResourceRelManager, Long, BaseRoleResourceRel>
        implements BaseRoleResourceRelService {
}
