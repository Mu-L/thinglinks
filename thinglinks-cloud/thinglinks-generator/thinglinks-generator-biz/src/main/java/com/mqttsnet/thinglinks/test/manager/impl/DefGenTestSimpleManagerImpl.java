package com.mqttsnet.thinglinks.test.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.test.entity.DefGenTestSimple;
import com.mqttsnet.thinglinks.test.manager.DefGenTestSimpleManager;
import com.mqttsnet.thinglinks.test.mapper.DefGenTestSimpleMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 测试单表
 * </p>
 *
 * @author mqttsnet
 * @date 2022-04-15 15:36:45
 * @create [2022-04-15 15:36:45] [mqttsnet]
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefGenTestSimpleManagerImpl extends SuperManagerImpl<DefGenTestSimpleMapper, DefGenTestSimple> implements DefGenTestSimpleManager {

}


