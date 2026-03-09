package com.mqttsnet.thinglinks.mobile.mobilespacedevice.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.entity.MobileSpaceDevice;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.manager.MobileSpaceDeviceManager;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.service.MobileSpaceDeviceService;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.query.MobileSpaceDevicePageQuery;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.result.MobileSpaceDeviceResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.save.MobileSpaceDeviceSaveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 业务实现类
 * 空间设备绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 14:11:40
 * @create [2024-08-30 14:11:40] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class MobileSpaceDeviceServiceImpl extends SuperServiceImpl<MobileSpaceDeviceManager, Long, MobileSpaceDevice> implements MobileSpaceDeviceService {


    @Override
    public Boolean hasDevicesBySpaceId(Long spaceId) {
        // 封装条件
        MobileSpaceDevicePageQuery query = MobileSpaceDevicePageQuery.builder().spaceId(spaceId).build();
        List<MobileSpaceDevice> spaceDevices = superManager.getMobileSpaceDeviceList(query);
        return !spaceDevices.isEmpty();
    }

    @Override
    public List<MobileSpaceDeviceResultVO> getMobileSpaceDeviceList(MobileSpaceDevicePageQuery query) {
        List<MobileSpaceDevice> spaceDevices = superManager.getMobileSpaceDeviceList(query);
        return BeanPlusUtil.toBeanList(spaceDevices, MobileSpaceDeviceResultVO.class);
    }


    @Override
    public Long selectCountBySpaceId(Long spaceId) {
        return superManager.selectCountBySpaceId(spaceId);

    }

    @Override
    public MobileSpaceDeviceSaveVO saveMobileSpaceDevice(MobileSpaceDeviceSaveVO saveVO) {
        // 参数校验
        ArgumentAssert.notNull(saveVO.getSpaceId(), "The space ID cannot be empty");
        ArgumentAssert.notNull(saveVO.getProductIdentification(), "The ProductIdentification cannot be empty");
        ArgumentAssert.notNull(saveVO.getDeviceIdentification(), "The DeviceIdentification cannot be empty");

        // 构建查询对象
        MobileSpaceDevicePageQuery query = MobileSpaceDevicePageQuery.builder()
                .spaceId(saveVO.getSpaceId())
                .productIdentification(saveVO.getProductIdentification())
                .deviceIdentification(saveVO.getDeviceIdentification())
                .build();

        // 判断设备是否已存在
        MobileSpaceDevice existingDevice = superManager.getBySpaceIdAndProductIdentificationAndDeviceIdentification(query);
        if (existingDevice != null) {
            throw BizException.wrap(String.format("The device [%s] has been bound to the space [%s]",
                    saveVO.getDeviceIdentification(), saveVO.getSpaceId()));
        }

        // 保存设备信息
        MobileSpaceDevice mobileSpaceDevice = BeanPlusUtil.copyProperties(saveVO, MobileSpaceDevice.class);
        superManager.save(mobileSpaceDevice);

        return saveVO;
    }


}


