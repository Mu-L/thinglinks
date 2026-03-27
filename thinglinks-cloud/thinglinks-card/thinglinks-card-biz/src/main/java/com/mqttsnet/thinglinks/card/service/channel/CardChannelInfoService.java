package com.mqttsnet.thinglinks.card.service.channel;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfo;
import com.mqttsnet.thinglinks.card.vo.result.channel.CardChannelInfoResultVO;
import com.mqttsnet.thinglinks.card.vo.save.channel.CardChannelInfoSaveVO;
import com.mqttsnet.thinglinks.card.vo.update.channel.CardChannelInfoUpdateVO;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 物联卡渠道表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 21:55:13
 * @create [2024-06-26 21:55:13] [mqttsnet]
 */
public interface CardChannelInfoService extends SuperService<Long, CardChannelInfo> {

    /**
     * 新增渠道信息
     *
     * @param saveVO 保存参数
     * @return {@link CardChannelInfoSaveVO} 保存数据
     */
    CardChannelInfoSaveVO saveChannelInfo(CardChannelInfoSaveVO saveVO);

    /**
     * 更新渠道信息
     *
     * @param updateVO 更新参数
     * @return {@link CardChannelInfoUpdateVO} 更新数据
     */
    CardChannelInfoUpdateVO updateChannelInfo(CardChannelInfoUpdateVO updateVO);

    /**
     * 删除渠道信息
     *
     * @param id 渠道id
     * @return {@link Boolean} 是否删除成功
     */
    Boolean deleteChannelInfo(Long id);

    /**
     * 批量删除渠道信息
     *
     * @param ids 渠道id集合
     * @return {@link Boolean} 是否删除成功
     */
    Boolean deleteChannelInfos(List<Long> ids);


    /**
     * 获取渠道
     *
     * @param id id
     * @return {@link CardChannelInfoResultVO} 详情VO
     */
    CardChannelInfoResultVO getChannelInfoDetails(Long id);
}


