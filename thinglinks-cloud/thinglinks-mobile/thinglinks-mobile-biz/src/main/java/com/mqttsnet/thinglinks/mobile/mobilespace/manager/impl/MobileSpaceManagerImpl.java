package com.mqttsnet.thinglinks.mobile.mobilespace.manager.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.mobile.mobilespace.entity.MobileSpace;
import com.mqttsnet.thinglinks.mobile.mobilespace.manager.MobileSpaceManager;
import com.mqttsnet.thinglinks.mobile.mobilespace.mapper.MobileSpaceMapper;
import com.mqttsnet.thinglinks.mobile.mobilespace.vo.query.MobileSpacePageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class MobileSpaceManagerImpl extends SuperManagerImpl<MobileSpaceMapper, MobileSpace> implements MobileSpaceManager {
    private final MobileSpaceMapper mobileSpaceMapper;

    @Override
    public List<MobileSpace> getMobileSpaceList(MobileSpacePageQuery query) {
        QueryWrap<MobileSpace> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(null != query.getCreatedUserId(), MobileSpace::getCreatedBy, query.getCreatedUserId());
        queryWrap.lambda().eq(null != query.getId(), MobileSpace::getId, query.getId());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getSpaceName()), MobileSpace::getSpaceName, query.getSpaceName());
        queryWrap.lambda().eq(null != query.getCreatedOrgId(), MobileSpace::getCreatedOrgId, query.getCreatedOrgId());
        return mobileSpaceMapper.selectList(queryWrap);
    }

    @Override
    public boolean doesSpaceNameExist(MobileSpacePageQuery query) {
        return this.getOne(Wrappers.<MobileSpace>lambdaQuery()
                .eq(null != query.getSpaceName(), MobileSpace::getSpaceName, query.getSpaceName())
                .ne(null != query.getId(), MobileSpace::getId, query.getId())) != null;
    }

    @Override
    public List<MobileSpace> getSpaceInfoByIdList(List<Long> spaceList) {
        QueryWrap<MobileSpace> queryWrap = new QueryWrap<>();
        queryWrap.lambda().in(null != spaceList, MobileSpace::getId, spaceList);
        return mobileSpaceMapper.selectList(queryWrap);
    }


}
