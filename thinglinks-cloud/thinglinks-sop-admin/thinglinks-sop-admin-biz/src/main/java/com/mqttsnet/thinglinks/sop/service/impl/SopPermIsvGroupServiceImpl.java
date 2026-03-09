package com.mqttsnet.thinglinks.sop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.thinglinks.sop.entity.SopPermIsvGroup;
import com.mqttsnet.thinglinks.sop.manager.SopPermIsvGroupManager;
import com.mqttsnet.thinglinks.sop.service.SopPermIsvGroupService;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * isv分组
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SopPermIsvGroupServiceImpl extends SuperServiceImpl<SopPermIsvGroupManager, Long, SopPermIsvGroup> implements SopPermIsvGroupService {
    @Override
    public List<Long> listByGroupId(Long isvId) {
        List<SopPermIsvGroup> list = list(Wraps.<SopPermIsvGroup>lbQ().eq(SopPermIsvGroup::getIsvId, isvId));
        return list.stream().map(SopPermIsvGroup::getGroupId).distinct().toList();
    }
}


