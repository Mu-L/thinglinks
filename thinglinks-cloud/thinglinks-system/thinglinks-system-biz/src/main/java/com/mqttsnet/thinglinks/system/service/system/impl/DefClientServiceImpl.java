package com.mqttsnet.thinglinks.system.service.system.impl;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperCacheServiceImpl;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.system.entity.system.DefClient;
import com.mqttsnet.thinglinks.system.manager.system.DefClientManager;
import com.mqttsnet.thinglinks.system.service.system.DefClientService;
import com.mqttsnet.thinglinks.system.vo.save.system.DefClientSaveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 客户端
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-13
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@DS(DsConstant.DEFAULTS)
public class DefClientServiceImpl extends SuperCacheServiceImpl<DefClientManager, Long, DefClient>
        implements DefClientService {

    @Override
    protected <SaveVO> DefClient saveBefore(SaveVO saveVO) {
        DefClientSaveVO defClientSaveVO = (DefClientSaveVO) saveVO;
        DefClient defClient = super.saveBefore(defClientSaveVO);
        defClient.setClientId(RandomUtil.randomString(24));
        defClient.setClientSecret(RandomUtil.randomString(32));
        return defClient;
    }

    @Override
    public DefClient getClient(String clientId, String clientSecret) {
        return superManager.getClient(clientId, clientSecret);
    }
}
