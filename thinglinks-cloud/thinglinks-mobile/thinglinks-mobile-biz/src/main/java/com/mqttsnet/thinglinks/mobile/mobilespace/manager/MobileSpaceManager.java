package com.mqttsnet.thinglinks.mobile.mobilespace.manager;


import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.mobile.mobilespace.entity.MobileSpace;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.query.MobileSpacePageQuery;

import java.util.List;

public interface MobileSpaceManager extends SuperManager<MobileSpace> {


    List<MobileSpace> getMobileSpaceList(MobileSpacePageQuery query);


    // 查询名称是否存在并且不等于当前id
    boolean doesSpaceNameExist(MobileSpacePageQuery query);

    List<MobileSpace> getSpaceInfoByIdList(List<Long> spaceList);


}
