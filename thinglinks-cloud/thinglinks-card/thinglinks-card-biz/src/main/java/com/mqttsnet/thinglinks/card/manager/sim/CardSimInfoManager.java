package com.mqttsnet.thinglinks.card.manager.sim;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.card.entity.sim.CardSimInfo;
import com.mqttsnet.thinglinks.card.vo.query.sim.CardSimInfoPageQuery;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 物联网卡信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 23:45:39
 * @create [2024-06-26 23:45:39] [mqttsnet]
 */
public interface CardSimInfoManager extends SuperManager<CardSimInfo> {


    /**
     * 获取 sim卡信息 列表
     *
     * @param query 查询条件
     * @return {@link List<CardSimInfo>} 列表结果
     */
    List<CardSimInfo> getCardSimInfoList(CardSimInfoPageQuery query);
}


