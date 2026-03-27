package com.mqttsnet.thinglinks.base.service.system.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.thinglinks.base.entity.system.BaseOperationLog;
import com.mqttsnet.thinglinks.base.entity.system.BaseOperationLogExt;
import com.mqttsnet.thinglinks.base.manager.system.BaseOperationLogManager;
import com.mqttsnet.thinglinks.base.mapper.system.BaseOperationLogExtMapper;
import com.mqttsnet.thinglinks.base.service.system.BaseOperationLogService;
import com.mqttsnet.thinglinks.base.vo.result.system.BaseOperationLogResultVO;
import com.mqttsnet.thinglinks.base.vo.save.system.BaseOperationLogSaveVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * <p>
 * 业务实现类
 * 操作日志
 * </p>
 *
 * @author mqttsnet
 * @date 2021-11-08
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@DS(DsConstant.BASE_TENANT)
public class BaseOperationLogServiceImpl extends SuperServiceImpl<BaseOperationLogManager, Long, BaseOperationLog> implements BaseOperationLogService {

    private final BaseOperationLogExtMapper baseOperationLogExtMapper;

    @Override
    public BaseOperationLogResultVO getDetail(Long id) {
        BaseOperationLog operationLog = superManager.getById(id);
        BaseOperationLogExt ext = baseOperationLogExtMapper.selectById(id);

        BaseOperationLogResultVO result = BeanUtil.toBean(ext, BaseOperationLogResultVO.class);
        BeanUtil.copyProperties(operationLog, result);
        return result;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean clearLog(LocalDateTime clearBeforeTime, Integer clearBeforeNum) {
        return superManager.clearLog(clearBeforeTime, clearBeforeNum) > 0;
    }

    @Override
    public <SaveVO> BaseOperationLog save(SaveVO saveVO) {
        BaseOperationLogSaveVO logSaveVO = (BaseOperationLogSaveVO) saveVO;
        if (ContextUtil.isEmptyBasePoolNameHeader()) {
            return null;
        }
        if (ContextUtil.isEmptyTenantId()) {
            return null;
        }

        BaseOperationLogExt baseOperationLogExt = BeanUtil.toBean(saveVO, BaseOperationLogExt.class);
        baseOperationLogExtMapper.insert(baseOperationLogExt);
        logSaveVO.setId(baseOperationLogExt.getId());
        return super.save(logSaveVO);
    }
}
