package com.mqttsnet.thinglinks.sop.manager.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.thinglinks.sop.entity.SopPermIsvGroup;
import com.mqttsnet.thinglinks.sop.manager.SopPermIsvGroupManager;
import com.mqttsnet.thinglinks.sop.mapper.SopPermIsvGroupMapper;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 通用业务实现类
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
public class SopPermIsvGroupManagerImpl extends SuperManagerImpl<SopPermIsvGroupMapper, SopPermIsvGroup> implements SopPermIsvGroupManager {
    @Override
    public List<Long> listGroupIdByIsvId(Long isvId) {
        List<SopPermIsvGroup> list = this.list(Wrappers.<SopPermIsvGroup>lambdaQuery().eq(SopPermIsvGroup::getIsvId, isvId));
        return list.stream().map(SopPermIsvGroup::getGroupId).distinct().collect(Collectors.toList());
    }
}


