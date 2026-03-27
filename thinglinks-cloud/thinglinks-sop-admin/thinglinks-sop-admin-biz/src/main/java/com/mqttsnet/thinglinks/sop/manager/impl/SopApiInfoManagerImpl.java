package com.mqttsnet.thinglinks.sop.manager.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.thinglinks.sop.entity.SopApiInfo;
import com.mqttsnet.thinglinks.sop.manager.SopApiInfoManager;
import com.mqttsnet.thinglinks.sop.mapper.SopApiInfoMapper;
import com.mqttsnet.thinglinks.sop.vo.query.SopApiInfoPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopApiInfoResultVO;

/**
 * <p>
 * 通用业务实现类
 * 接口信息表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class SopApiInfoManagerImpl extends SuperManagerImpl<SopApiInfoMapper, SopApiInfo> implements SopApiInfoManager {
    @Override
    public IPage<SopApiInfoResultVO> groupPage(IPage<SopApiInfo> page,
                                               SopApiInfoPageQuery query) {
        Wrapper<SopApiInfo> wrapper = Wraps.<SopApiInfo>lbQ()
                .eq(SopApiInfo::getApplication, query.getApplication())
                .eq(SopApiInfo::getApiName, query.getApiName())
                .eq(SopApiInfo::getStatus, query.getStatus());
        return baseMapper.groupPage(page, wrapper, query);
    }
}


