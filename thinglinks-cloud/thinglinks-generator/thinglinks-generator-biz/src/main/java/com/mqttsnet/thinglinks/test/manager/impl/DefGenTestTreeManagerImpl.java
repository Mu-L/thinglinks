package com.mqttsnet.thinglinks.test.manager.impl;

import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.test.entity.DefGenTestTree;
import com.mqttsnet.thinglinks.test.manager.DefGenTestTreeManager;
import com.mqttsnet.thinglinks.test.mapper.DefGenTestTreeMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 测试树结构
 * </p>
 *
 * @author mqttsnet
 * @date 2022-04-20 00:28:30
 * @create [2022-04-20 00:28:30] [mqttsnet] 
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DefGenTestTreeManagerImpl extends SuperManagerImpl<DefGenTestTreeMapper, DefGenTestTree> implements DefGenTestTreeManager {

}


