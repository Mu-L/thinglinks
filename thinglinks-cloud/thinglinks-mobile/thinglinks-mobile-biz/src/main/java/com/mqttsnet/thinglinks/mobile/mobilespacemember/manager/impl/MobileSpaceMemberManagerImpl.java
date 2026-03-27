package com.mqttsnet.thinglinks.mobile.mobilespacemember.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.entity.MobileSpaceMember;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.enumeration.SpaceMemberTypeEnum;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.manager.MobileSpaceMemberManager;
import com.mqttsnet.thinglinks.mobile.mobilespacemember.mapper.MobileSpaceMemberMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


/**
 * <p>
 * 通用业务实现类
 * 空间人员绑定表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-30 15:40:08
 * @create [2024-08-30 15:40:08] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MobileSpaceMemberManagerImpl extends SuperManagerImpl<MobileSpaceMemberMapper, MobileSpaceMember> implements MobileSpaceMemberManager {

    @Override
    public MobileSpaceMember getAdminExistsBySpaceId(Long spaceId) {
        return this.getOne(Wrappers.<MobileSpaceMember>lambdaQuery()
                .eq(MobileSpaceMember::getSpaceId, spaceId)
                .eq(MobileSpaceMember::getMemberType, SpaceMemberTypeEnum.ADMIN.getValue()));

    }

    @Override
    public boolean removeBySpaceId(Long id) {
        return this.remove(Wrappers.<MobileSpaceMember>lambdaQuery().eq(MobileSpaceMember::getSpaceId, id));
    }

    @Override
    public List<Long> getSpaceIdsByMemberId(Long userId) {
        QueryWrap<MobileSpaceMember> query = new QueryWrap<>();
        query.lambda().eq(MobileSpaceMember::getMemberId, userId);
        List<MobileSpaceMember> list = this.list(query);
        // 判断列表是否为空
        return CollectionUtils.isNotEmpty(list)
                ? list.stream().map(MobileSpaceMember::getSpaceId).collect(Collectors.toList())
                : Collections.emptyList();
    }

    @Override
    public List<MobileSpaceMember> selectMobileSpaceMemberBySpaceId(Long spaceId) {
        QueryWrap<MobileSpaceMember> query = new QueryWrap<>();
        query.lambda().eq(MobileSpaceMember::getSpaceId, spaceId);
        return this.list(query);
    }

    @Override
    public MobileSpaceMember getByUserIdAndSpaceId(Long userId, Long id) {
        QueryWrap<MobileSpaceMember> query = new QueryWrap<>();
        query.lambda().eq(MobileSpaceMember::getMemberId, userId)
                .eq(MobileSpaceMember::getSpaceId, id);
        return this.getOne(query);

    }

    @Override
    public boolean removeBySpaceIdAndMemberId(Long id, Long userId) {
        QueryWrap<MobileSpaceMember> query = new QueryWrap<>();
        query.lambda().eq(MobileSpaceMember::getSpaceId, id)
                .eq(MobileSpaceMember::getMemberId, userId);
        return this.remove(query);
    }
}


