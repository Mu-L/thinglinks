package com.mqttsnet.thinglinks.mobile.mobilespacedevice.manager.impl;

import cn.hutool.core.util.StrUtil;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.entity.MobileSpaceDevice;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.manager.MobileSpaceDeviceManager;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.mapper.MobileSpaceDeviceMapper;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.query.MobileSpaceDevicePageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * <p>
 * 通用业务实现类
 * 空间设备绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 14:11:40
 * @create [2024-08-30 14:11:40] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MobileSpaceDeviceManagerImpl extends SuperManagerImpl<MobileSpaceDeviceMapper, MobileSpaceDevice> implements MobileSpaceDeviceManager {

    private final MobileSpaceDeviceMapper mobileSpaceDeviceMapper;


    @Override
    public List<MobileSpaceDevice> getMobileSpaceDeviceList(MobileSpaceDevicePageQuery query) {
        QueryWrap<MobileSpaceDevice> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(null != query.getId(), MobileSpaceDevice::getId, query.getId());
        queryWrap.lambda().eq(null != query.getSpaceId(), MobileSpaceDevice::getSpaceId, query.getSpaceId());
        queryWrap.lambda().eq(StrUtil.isNotBlank(query.getProductIdentification()), MobileSpaceDevice::getProductIdentification, query.getProductIdentification());
        queryWrap.lambda().eq(StrUtil.isNotBlank(query.getDeviceIdentification()), MobileSpaceDevice::getDeviceIdentification, query.getDeviceIdentification());
        queryWrap.lambda().eq(null != query.getCreatedOrgId(), MobileSpaceDevice::getCreatedOrgId, query.getCreatedOrgId());
        return mobileSpaceDeviceMapper.selectList(queryWrap);

    }

    @Override
    public Long selectCountBySpaceId(Long spaceId) {
        QueryWrap<MobileSpaceDevice> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(null != spaceId, MobileSpaceDevice::getSpaceId, spaceId);
        return mobileSpaceDeviceMapper.selectCount(queryWrap);
    }

    @Override
    public MobileSpaceDevice getBySpaceIdAndProductIdentificationAndDeviceIdentification(MobileSpaceDevicePageQuery query) {
        QueryWrap<MobileSpaceDevice> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(null != query.getSpaceId(), MobileSpaceDevice::getSpaceId, query.getSpaceId());
        queryWrap.lambda().eq(StrUtil.isNotBlank(query.getProductIdentification()), MobileSpaceDevice::getProductIdentification, query.getProductIdentification());
        queryWrap.lambda().eq(StrUtil.isNotBlank(query.getDeviceIdentification()), MobileSpaceDevice::getDeviceIdentification, query.getDeviceIdentification());
        return mobileSpaceDeviceMapper.selectOne(queryWrap);

    }
}


