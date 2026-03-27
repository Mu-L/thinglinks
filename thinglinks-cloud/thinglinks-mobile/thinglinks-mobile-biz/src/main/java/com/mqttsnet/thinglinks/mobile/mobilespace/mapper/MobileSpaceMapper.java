package com.mqttsnet.thinglinks.mobile.mobilespace.mapper;

import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.mobile.mobilespace.entity.MobileSpace;
import org.springframework.stereotype.Repository;

@Repository  // 声明为 Spring 的 IOC 容器中的组件
public interface MobileSpaceMapper extends SuperMapper<MobileSpace> {


}
