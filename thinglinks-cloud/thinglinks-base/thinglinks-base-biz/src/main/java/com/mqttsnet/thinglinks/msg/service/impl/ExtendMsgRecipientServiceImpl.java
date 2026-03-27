package com.mqttsnet.thinglinks.msg.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.msg.entity.ExtendMsgRecipient;
import com.mqttsnet.thinglinks.msg.manager.ExtendMsgRecipientManager;
import com.mqttsnet.thinglinks.msg.service.ExtendMsgRecipientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 消息接收人
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-10 11:41:17
 * @create [2022-07-10 11:41:17] [mqttsnet] 
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExtendMsgRecipientServiceImpl extends SuperServiceImpl<ExtendMsgRecipientManager, Long, ExtendMsgRecipient> implements ExtendMsgRecipientService {
    @Override
    public List<ExtendMsgRecipient> listByMsgId(Long id) {
        return superManager.listByMsgId(id);
    }

}


