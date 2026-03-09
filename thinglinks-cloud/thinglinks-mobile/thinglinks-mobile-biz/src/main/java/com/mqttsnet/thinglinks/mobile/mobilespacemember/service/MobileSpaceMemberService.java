package com.mqttsnet.thinglinks.mobile.mobilespacemember.service;


import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.entity.MobileSpaceMember;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.result.MobileSpaceMemberResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.save.MobileSpaceMemberSaveVO;

import java.util.List;

/**
 * <p>
 * 业务接口
 * 空间人员绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 15:40:08
 * @create [2024-08-30 15:40:08] [mqttsnet]
 */
public interface MobileSpaceMemberService extends SuperService<Long, MobileSpaceMember> {


    // 判断空间下是否存在管理员
    boolean isAdminExistsBySpaceId(Long spaceId);

    // 删除空间下所有人员
    boolean removeBySpaceId(Long id);

    List<Long> selectMobileSpaceByMemberId(Long userId);

    List<MobileSpaceMemberResultVO> selectMobileSpaceMemberBySpaceId(Long spaceId);

    /**
     * 保存空间人员关系信息
     *
     * @param mobileSpaceMemberSaveVO 保存参数
     * @return {@link Boolean}
     */
    Boolean saveMobileSpaceMember(MobileSpaceMemberSaveVO mobileSpaceMemberSaveVO);


    /**
     * 查询空间与人员信息
     *
     * @param userId
     * @param spaceId
     * @return
     */
    MobileSpaceMemberResultVO findOneByUserIdAndSpaceId(Long userId, Long spaceId);

    Boolean removeBySpaceIdAndMemberId(Long spaceId, Long userId);
}


