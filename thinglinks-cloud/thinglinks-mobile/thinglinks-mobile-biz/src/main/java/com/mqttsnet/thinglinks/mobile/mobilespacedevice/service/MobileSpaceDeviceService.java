package com.mqttsnet.thinglinks.mobile.mobilespacedevice.service;


import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.entity.MobileSpaceDevice;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.query.MobileSpaceDevicePageQuery;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.result.MobileSpaceDeviceResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.save.MobileSpaceDeviceSaveVO;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 空间设备绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 14:11:40
 * @create [2024-08-30 14:11:40] [mqttsnet]
 */
public interface MobileSpaceDeviceService extends SuperService<Long, MobileSpaceDevice> {


    /**
     * 通过空间id判断空间下是否有设备
     *
     * @param spaceId 空间ID
     * @return {@link Boolean}
     */
    Boolean hasDevicesBySpaceId(Long spaceId);

    /**
     * 空间设备列表
     *
     * @param query 查询条件
     * @return {@link List<MobileSpaceDeviceResultVO>}
     */
    List<MobileSpaceDeviceResultVO> getMobileSpaceDeviceList(MobileSpaceDevicePageQuery query);

    // 通过空间id查询设备数量
    Long selectCountBySpaceId(Long spaceId);

    MobileSpaceDeviceSaveVO saveMobileSpaceDevice(MobileSpaceDeviceSaveVO saveVO);
}


