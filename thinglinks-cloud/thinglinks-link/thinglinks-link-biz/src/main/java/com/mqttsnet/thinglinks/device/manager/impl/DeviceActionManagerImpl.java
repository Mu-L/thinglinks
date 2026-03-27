package com.mqttsnet.thinglinks.device.manager.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.device.entity.DeviceAction;
import com.mqttsnet.thinglinks.device.manager.DeviceActionManager;
import com.mqttsnet.thinglinks.device.mapper.DeviceActionMapper;
import com.mqttsnet.thinglinks.device.vo.query.DeviceActionPageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceActionResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 通用业务实现类
 * 设备动作数据
 * </p>
 *
 * @author mqttsnet
 * @date 2023-06-10 16:38:09
 * @create [2023-06-10 16:38:09] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceActionManagerImpl extends SuperManagerImpl<DeviceActionMapper, DeviceAction> implements DeviceActionManager {

    private final DeviceActionMapper deviceActionMapper;

    /**
     * 查询设备动作数据VO列表
     *
     * @param query 查询参数
     * @return {@link List < DeviceActionResultVO >} 设备动作数据VO列表
     */
    @Override
    public List<DeviceActionResultVO> getDeviceActionResultVOList(DeviceActionPageQuery query) {
        QueryWrapper<DeviceAction> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Objects.nonNull(query.getId()), DeviceAction::getId, query.getId());
        queryWrapper.lambda().eq(StringUtils.isNotBlank(query.getDeviceIdentification()), DeviceAction::getDeviceIdentification, query.getDeviceIdentification());
        queryWrapper.lambda().eq(StringUtils.isNotBlank(query.getActionType()), DeviceAction::getActionType, query.getActionType());
        queryWrapper.lambda().eq(StringUtils.isNotNull(query.getStatus()), DeviceAction::getStatus, query.getStatus());

        List<DeviceAction> deviceActions = deviceActionMapper.selectList(queryWrapper);
        return BeanUtil.copyToList(deviceActions, DeviceActionResultVO.class);
    }

}


