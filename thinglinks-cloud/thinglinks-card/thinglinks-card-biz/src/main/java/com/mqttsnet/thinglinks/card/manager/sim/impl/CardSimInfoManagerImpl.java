package com.mqttsnet.thinglinks.card.manager.sim.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.card.entity.sim.CardSimInfo;
import com.mqttsnet.thinglinks.card.manager.sim.CardSimInfoManager;
import com.mqttsnet.thinglinks.card.mapper.sim.CardSimInfoMapper;
import com.mqttsnet.thinglinks.card.vo.query.sim.CardSimInfoPageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通用业务实现类
 * 物联网卡信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 23:45:39
 * @create [2024-06-26 23:45:39] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class CardSimInfoManagerImpl extends SuperManagerImpl<CardSimInfoMapper, CardSimInfo> implements CardSimInfoManager {

    private final CardSimInfoMapper cardSimInfoMapper;

    @Override
    public List<CardSimInfo> getCardSimInfoList(CardSimInfoPageQuery query) {
        QueryWrap<CardSimInfo> queryWrap = new QueryWrap<>();
        queryWrap.lambda().eq(null != query.getId(), CardSimInfo::getId, query.getId());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getIccid()), CardSimInfo::getIccid, query.getIccid());
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(query.getCardNumber()), CardSimInfo::getCardNumber, query.getCardNumber());
        return cardSimInfoMapper.selectList(queryWrap);
    }
}


