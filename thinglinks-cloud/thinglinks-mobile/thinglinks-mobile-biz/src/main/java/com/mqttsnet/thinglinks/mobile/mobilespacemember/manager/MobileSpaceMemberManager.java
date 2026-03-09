package com.mqttsnet.thinglinks.mobile.mobilespacemember.manager;


import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.entity.MobileSpaceMember;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 空间人员绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 15:40:08
 * @create [2024-08-30 15:40:08] [mqttsnet]
 */
public interface MobileSpaceMemberManager extends SuperManager<MobileSpaceMember> {

    // 空间ID下管理员信息
    MobileSpaceMember getAdminExistsBySpaceId(Long spaceId);

    /**
     * 删除空间ID下的所有成员
     * @param id
     * @return
     */
    boolean removeBySpaceId(Long id);

    List<Long> getSpaceIdsByMemberId(Long userId);

    List<MobileSpaceMember> selectMobileSpaceMemberBySpaceId(Long spaceId);

    MobileSpaceMember getByUserIdAndSpaceId(Long userId, Long id);

    boolean removeBySpaceIdAndMemberId(Long id, Long userId);
}


