package com.mqttsnet.thinglinks.system.service.application.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.system.entity.application.DefTenantApplicationRecord;
import com.mqttsnet.thinglinks.system.manager.application.DefTenantApplicationRecordManager;
import com.mqttsnet.thinglinks.system.service.application.DefTenantApplicationRecordService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 租户应用授权记录
 * </p>
 *
 * @author mqttsnet
 * @date 2021-09-15
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@DS(DsConstant.DEFAULTS)
public class DefTenantApplicationRecordServiceImpl extends SuperServiceImpl<DefTenantApplicationRecordManager, Long, DefTenantApplicationRecord>
        implements DefTenantApplicationRecordService {
}
