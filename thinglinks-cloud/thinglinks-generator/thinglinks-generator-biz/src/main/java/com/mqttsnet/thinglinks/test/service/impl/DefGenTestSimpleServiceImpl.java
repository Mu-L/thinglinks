package com.mqttsnet.thinglinks.test.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.test.entity.DefGenTestSimple;
import com.mqttsnet.thinglinks.test.manager.DefGenTestSimpleManager;
import com.mqttsnet.thinglinks.test.service.DefGenTestSimpleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
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
@Transactional(readOnly = true)
@DS(DsConstant.DEFAULTS)
public class DefGenTestSimpleServiceImpl extends SuperServiceImpl<DefGenTestSimpleManager, Long, DefGenTestSimple> implements DefGenTestSimpleService {

}


