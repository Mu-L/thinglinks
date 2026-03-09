package com.mqttsnet.thinglinks.msg.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.thinglinks.msg.entity.DefInterface;
import com.mqttsnet.thinglinks.msg.manager.DefInterfaceManager;
import com.mqttsnet.thinglinks.msg.mapper.DefInterfaceMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 接口
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-04 16:45:45
 * @create [2022-07-04 16:45:45] [mqttsnet] 
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DefInterfaceManagerImpl extends SuperManagerImpl<DefInterfaceMapper, DefInterface> implements DefInterfaceManager {
    @Override
    public DefInterface getByType(String type) {
        return getOne(Wraps.<DefInterface>lbQ().eq(DefInterface::getCode, type));
    }
}


