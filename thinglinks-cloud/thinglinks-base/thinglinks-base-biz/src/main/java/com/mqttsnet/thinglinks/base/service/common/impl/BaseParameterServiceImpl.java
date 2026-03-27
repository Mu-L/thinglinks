package com.mqttsnet.thinglinks.base.service.common.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.base.entity.common.BaseParameter;
import com.mqttsnet.thinglinks.base.manager.common.BaseParameterManager;
import com.mqttsnet.thinglinks.base.service.common.BaseParameterService;
import com.mqttsnet.thinglinks.base.vo.save.common.BaseParameterSaveVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.model.enumeration.system.DataTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * 业务实现类
 * 个性参数
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
public class BaseParameterServiceImpl extends SuperServiceImpl<BaseParameterManager, Long, BaseParameter> implements BaseParameterService {
    @Override
    protected <SaveVO> BaseParameter saveBefore(SaveVO saveVO) {
        BaseParameterSaveVO baseParameterSaveVO = (BaseParameterSaveVO) saveVO;
        BaseParameter baseParameter = super.saveBefore(baseParameterSaveVO);
        baseParameter.setParamType(DataTypeEnum.SYSTEM.getCode());
        return baseParameter;
    }


}
