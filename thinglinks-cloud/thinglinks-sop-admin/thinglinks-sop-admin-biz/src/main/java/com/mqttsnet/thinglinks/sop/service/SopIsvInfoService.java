package com.mqttsnet.thinglinks.sop.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.model.vo.save.AudioVO;
import com.mqttsnet.thinglinks.model.vo.save.StatusUpdateVO;
import com.mqttsnet.thinglinks.sop.entity.SopIsvInfo;
import com.mqttsnet.thinglinks.sop.utils.RSATool;
import com.mqttsnet.thinglinks.sop.vo.result.SopIsvKeysResultVO;
import com.mqttsnet.thinglinks.sop.vo.save.SopIsvInfoApplyForVO;
import com.mqttsnet.thinglinks.sop.vo.save.SopIsvInfoSubmitVO;
import com.mqttsnet.thinglinks.sop.vo.update.SopIsvInfoOpenUpdateVO;


/**
 * <p>
 * 业务接口
 * isv信息表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopIsvInfoService extends SuperService<Long, SopIsvInfo> {

    Boolean updateStatus(StatusUpdateVO param);

    Long examine(AudioVO param);

    RSATool.KeyStore createKeys(RSATool.KeyFormat keyFormat) throws Exception;

    SopIsvKeysResultVO getKeys(Long isvId);

    Long applyFor(SopIsvInfoApplyForVO param);

    Long submit(SopIsvInfoSubmitVO param);

    Long update(SopIsvInfoOpenUpdateVO param);
}


