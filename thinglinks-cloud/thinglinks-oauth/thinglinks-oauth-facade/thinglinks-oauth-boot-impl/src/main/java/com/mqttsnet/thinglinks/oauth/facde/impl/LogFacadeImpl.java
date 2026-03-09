package com.mqttsnet.thinglinks.oauth.facde.impl;

import com.mqttsnet.basic.model.log.OptLogDTO;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.base.service.system.BaseOperationLogService;
import com.mqttsnet.thinglinks.base.vo.save.system.BaseOperationLogSaveVO;
import com.mqttsnet.thinglinks.oauth.facade.LogFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 操作日志保存 API
 *
 * @author zuihou
 * @date 2019/07/02
 */
@Service
@RequiredArgsConstructor
public class LogFacadeImpl implements LogFacade {
    private final BaseOperationLogService baseOperationLogService;

    /**
     * 保存日志
     *
     * @param data 操作日志
     * @return 操作日志
     */
    public void save(OptLogDTO data) {
        BaseOperationLogSaveVO bean = BeanPlusUtil.toBean(data, BaseOperationLogSaveVO.class);
        baseOperationLogService.save(bean);
    }

}
