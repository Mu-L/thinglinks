package com.mqttsnet.thinglinks.service.alarm;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.entity.alarm.RuleAlarmChannel;
import com.mqttsnet.thinglinks.vo.query.alarm.RuleAlarmChannelPageQuery;
import com.mqttsnet.thinglinks.vo.result.alarm.RuleAlarmChannelDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.alarm.RuleAlarmChannelResultVO;
import com.mqttsnet.thinglinks.vo.save.alarm.RuleAlarmChannelSaveVO;
import com.mqttsnet.thinglinks.vo.update.alarm.RuleAlarmChannelUpdateVO;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 告警规则渠道表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:14:58
 * @create [2023-09-09 21:14:58] [mqttsnet]
 */
public interface RuleAlarmChannelService extends SuperService<Long, RuleAlarmChannel> {

    /**
     * Save alarm rule channel.
     *
     * @param saveVO Save VO
     * @return {@link RuleAlarmChannelResultVO} Saved entity
     */
    RuleAlarmChannelSaveVO saveAlarmChannel(RuleAlarmChannelSaveVO saveVO);

    /**
     * Update alarm rule channel.
     *
     * @param updateVO Update VO
     * @return {@link RuleAlarmChannelResultVO} Updated entity
     */
    RuleAlarmChannelUpdateVO updateAlarmChannel(RuleAlarmChannelUpdateVO updateVO);

    /**
     * Delete alarm rule channel.
     *
     * @param id ID
     * @return {@link Boolean} true: successful, false: failed
     */
    Boolean deleteAlarmChannel(Long id);

    /**
     * Get details of alarm rule channel.
     *
     * @param id ID
     * @return {@link RuleAlarmChannelResultVO} Details VO
     */
    RuleAlarmChannelDetailsResultVO getAlarmChannelDetails(Long id);


    /**
     * Retrieve a list of rule alarm channel VO.
     *
     * @param query Search parameters for rule alarm channels.
     * @return {@link List<RuleAlarmChannelResultVO>} List of rule alarm channel VO.
     */
    List<RuleAlarmChannelResultVO> getRuleAlarmChannelResultVOList(RuleAlarmChannelPageQuery query);


}


