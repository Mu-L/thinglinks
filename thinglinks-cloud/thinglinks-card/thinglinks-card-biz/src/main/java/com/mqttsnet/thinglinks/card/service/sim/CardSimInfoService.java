package com.mqttsnet.thinglinks.card.service.sim;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.card.entity.sim.CardSimInfo;
import com.mqttsnet.thinglinks.card.vo.query.sim.CardSimInfoPageQuery;
import com.mqttsnet.thinglinks.card.vo.result.sim.CardSimInfoResultVO;
import com.mqttsnet.thinglinks.card.vo.result.sim.CardSimOverviewResultVO;
import com.mqttsnet.thinglinks.card.vo.save.sim.CardSimInfoSaveVO;
import com.mqttsnet.thinglinks.card.vo.update.sim.CardSimInfoUpdateVO;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 物联网卡信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 23:45:39
 * @create [2024-06-26 23:45:39] [mqttsnet]
 */
public interface CardSimInfoService extends SuperService<Long, CardSimInfo> {

    /**
     * 新增物联卡信息
     *
     * @param saveVO 保存实体
     * @return {@link CardSimInfoSaveVO}
     */
    CardSimInfoSaveVO saveCardSimInfo(CardSimInfoSaveVO saveVO);

    /**
     * 更新物联卡信息
     *
     * @param updateVO 更新实体
     * @return {@link CardSimInfoUpdateVO}
     */
    CardSimInfoUpdateVO updateCardSimInfo(CardSimInfoUpdateVO updateVO);

    /**
     * 获取物联卡详情
     *
     * @param id id
     * @return {@link CardSimInfoResultVO}
     */
    CardSimInfoResultVO getCardSimInfoDetails(Long id);

    /**
     * 删除物联卡信息
     *
     * @param id id
     * @return {@link Boolean}
     */
    Boolean deleteCardSimInfo(Long id);

    /**
     * 获取物联网卡概况统计信息
     *
     * @return {@link CardSimOverviewResultVO} 物联网卡概况统计信息VO
     */
    CardSimOverviewResultVO getCardSimOverview();


    /**
     * 查询SIM卡信息 VO列表
     *
     * @param query 查询参数
     * @return {@link List<CardSimInfoResultVO>} 结果集
     */
    List<CardSimInfoResultVO> getCardSimInfoResultVOList(CardSimInfoPageQuery query);
}


