package com.mqttsnet.thinglinks.mobile.mobilespace.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.mobile.mobilespace.entity.MobileSpace;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.query.MobileSpacePageQuery;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.result.MobileSpaceDetailsResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.result.MobileSpaceResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.save.MobileSpaceSaveVO;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.update.MobileSpaceUpdateVO;
import com.mqttsnet.thinglinks.mobile.mobilespacedevice.vo.save.MobileSpaceDeviceSaveVO;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.save.MobileSpaceMemberSaveVO;

import java.util.List;

public interface MobileSpaceService extends SuperService<Long, MobileSpace> {

    /**
     * 创建空间
     *
     * @param saveVO
     * @return
     */
    MobileSpaceSaveVO saveMobileSpace(MobileSpaceSaveVO saveVO);

    /**
     * 更新空间
     */
    MobileSpaceUpdateVO updateMobileSpace(MobileSpaceUpdateVO updateVO);

    /**
     * 删除空间
     */
    Boolean deleteMobileSpace(Long id, Long userId);


    /**
     * 查询空间列表
     */
    List<MobileSpaceResultVO> getMobileSpaceResultVOList(MobileSpacePageQuery query);


    /**
     * 查询空间详情
     *
     * @param spaceId 空间ID
     * @return {@link MobileSpaceDetailsResultVO} 空间详情VO
     */
    MobileSpaceDetailsResultVO getMobileSpaceById(Long spaceId);


    MobileSpaceMemberSaveVO saveMobileSpaceMember(MobileSpaceMemberSaveVO saveVO);

    MobileSpaceDeviceSaveVO updateMobileSpaceDevice(MobileSpaceDeviceSaveVO saveVO);
}
