package com.mqttsnet.thinglinks.mobile.mobilespacemember.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.entity.MobileSpaceMember;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.manager.MobileSpaceMemberManager;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.service.MobileSpaceMemberService;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.result.MobileSpaceMemberResultVO;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.vo.save.MobileSpaceMemberSaveVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * <p>
 * 业务实现类
 * 空间人员绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 15:40:08
 * @create [2024-08-30 15:40:08] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class MobileSpaceMemberServiceImpl extends SuperServiceImpl<MobileSpaceMemberManager, Long, MobileSpaceMember> implements MobileSpaceMemberService {


    @Override
    public Boolean saveMobileSpaceMember(MobileSpaceMemberSaveVO saveVO) {
        // 校验参数
        ArgumentAssert.notNull(saveVO.getSpaceId(), "The space ID cannot be empty");
        ArgumentAssert.notNull(saveVO.getMemberId(), "The member ID cannot be empty");
        ArgumentAssert.notNull(saveVO.getMemberType(), "The memberType ID cannot be empty");

        MobileSpaceMember mobileSpaceMember = BeanPlusUtil.toBeanIgnoreError(saveVO, MobileSpaceMember.class);
        return superManager.save(mobileSpaceMember);
    }

    @Override
    public MobileSpaceMemberResultVO findOneByUserIdAndSpaceId(Long userId, Long spaceId) {
        return BeanPlusUtil.toBeanIgnoreError(superManager.getByUserIdAndSpaceId(userId, spaceId), MobileSpaceMemberResultVO.class);
    }

    @Override
    public Boolean removeBySpaceIdAndMemberId(Long spaceId, Long userId) {
        ArgumentAssert.notNull(spaceId, "The space ID cannot be empty");
        ArgumentAssert.notNull(userId, "The member ID cannot be empty");
        return superManager.removeBySpaceIdAndMemberId(spaceId, userId);
    }

    @Override
    public boolean isAdminExistsBySpaceId(Long spaceId) {
        return superManager.getAdminExistsBySpaceId(spaceId) != null;
    }


    @Override
    public boolean removeBySpaceId(Long id) {
        return superManager.removeBySpaceId(id);

    }

    @Override
    public List<Long> selectMobileSpaceByMemberId(Long userId) {
        return superManager.getSpaceIdsByMemberId(userId);
    }

    @Override
    public List<MobileSpaceMemberResultVO> selectMobileSpaceMemberBySpaceId(Long spaceId) {
        List<MobileSpaceMember> mobileSpaceMemberList = superManager.selectMobileSpaceMemberBySpaceId(spaceId);
        return BeanPlusUtil.toBeanList(mobileSpaceMemberList, MobileSpaceMemberResultVO.class);
    }

}


