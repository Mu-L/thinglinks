package com.mqttsnet.thinglinks.msg.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.msg.entity.ExtendInterfaceLog;
import com.mqttsnet.thinglinks.msg.entity.ExtendInterfaceLogging;
import com.mqttsnet.thinglinks.msg.manager.ExtendInterfaceLogManager;
import com.mqttsnet.thinglinks.msg.manager.ExtendInterfaceLoggingManager;
import com.mqttsnet.thinglinks.msg.service.ExtendInterfaceLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * <p>
 * 业务实现类
 * 接口执行日志
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-09 23:58:59
 * @create [2022-07-09 23:58:59] [mqttsnet] 
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExtendInterfaceLogServiceImpl extends SuperServiceImpl<ExtendInterfaceLogManager, Long, ExtendInterfaceLog> implements ExtendInterfaceLogService {
    private final ExtendInterfaceLoggingManager extendInterfaceLoggingManager;

    @Override
    public boolean removeByIds(Collection<Long> idList) {
        extendInterfaceLoggingManager.remove(Wraps.<ExtendInterfaceLogging>lbQ().in(
                ExtendInterfaceLogging::getLogId, idList
        ));
        return super.removeByIds(idList);
    }
}


