package com.mqttsnet.thinglinks.sop.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.sop.entity.SopIsvKeys;
import com.mqttsnet.thinglinks.sop.vo.update.SopIsvKeysUpdateVO;


/**
 * <p>
 * 业务接口
 * ISV秘钥管理
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopIsvKeysService extends SuperService<Long, SopIsvKeys> {
    Boolean saveKeys(SopIsvKeysUpdateVO updateVO);

    SopIsvKeys getByIsvId(Long isvId);
}


