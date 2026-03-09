package com.mqttsnet.thinglinks.mobile.mobilespacedevice.manager;


import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.entity.MobileSpaceDevice;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.query.MobileSpaceDevicePageQuery;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 空间设备绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 14:11:40
 * @create [2024-08-30 14:11:40] [mqttsnet]
 */
public interface MobileSpaceDeviceManager extends SuperManager<MobileSpaceDevice> {

    List<MobileSpaceDevice> getMobileSpaceDeviceList(MobileSpaceDevicePageQuery query);

    Long selectCountBySpaceId(Long spaceId);

    MobileSpaceDevice getBySpaceIdAndProductIdentificationAndDeviceIdentification(MobileSpaceDevicePageQuery query);
}


