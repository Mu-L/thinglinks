package com.mqttsnet.thinglinks.system.manager.application.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.system.entity.application.DefTenantApplicationRecord;
import com.mqttsnet.thinglinks.system.manager.application.DefTenantApplicationRecordManager;
import com.mqttsnet.thinglinks.system.mapper.application.DefTenantApplicationRecordMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 应用管理
 *
 * @author mqttsnet
 * @version v1.0
 * @date 2021/9/29 1:26 下午
 * @create [2021/9/29 1:26 下午 ] [mqttsnet] [初始创建]
 */
@RequiredArgsConstructor
@Service
public class DefTenantApplicationRecordManagerImpl extends SuperManagerImpl<DefTenantApplicationRecordMapper, DefTenantApplicationRecord> implements DefTenantApplicationRecordManager {

}
