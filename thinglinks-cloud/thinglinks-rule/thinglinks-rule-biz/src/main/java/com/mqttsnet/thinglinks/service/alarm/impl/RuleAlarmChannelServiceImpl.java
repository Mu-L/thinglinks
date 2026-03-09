package com.mqttsnet.thinglinks.service.alarm.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.alarm.RuleAlarmChannel;
import com.mqttsnet.thinglinks.manager.alarm.RuleAlarmChannelManager;
import com.mqttsnet.thinglinks.service.alarm.RuleAlarmChannelService;
import com.mqttsnet.thinglinks.vo.query.alarm.RuleAlarmChannelPageQuery;
import com.mqttsnet.thinglinks.vo.result.alarm.RuleAlarmChannelDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.alarm.RuleAlarmChannelResultVO;
import com.mqttsnet.thinglinks.vo.save.alarm.RuleAlarmChannelSaveVO;
import com.mqttsnet.thinglinks.vo.update.alarm.RuleAlarmChannelUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 告警规则渠道表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:14:58
 * @create [2023-09-09 21:14:58] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleAlarmChannelServiceImpl extends SuperServiceImpl<RuleAlarmChannelManager, Long, RuleAlarmChannel> implements RuleAlarmChannelService {


    /**
     * Save the alarm channel.
     *
     * @param saveVO Parameters for saving.
     * @return {@link RuleAlarmChannelSaveVO} Entity.
     */
    @Override
    public RuleAlarmChannelSaveVO saveAlarmChannel(RuleAlarmChannelSaveVO saveVO) {
        log.info("saveAlarmChannel saveVO:{}", saveVO);

        // Validate the parameters.
        checkedAlarmChannelSaveVO(saveVO);

        // Build the parameters.
        RuleAlarmChannel alarmChannel = builderAlarmChannelSaveVO(saveVO);

        // Persist the alarm channel.
        superManager.save(alarmChannel);

        return BeanPlusUtil.toBeanIgnoreError(alarmChannel, RuleAlarmChannelSaveVO.class);
    }

    /**
     * Validate the parameters for addition.
     *
     * @param saveVO Parameters for saving.
     */
    private void checkedAlarmChannelSaveVO(RuleAlarmChannelSaveVO saveVO) {
        ArgumentAssert.notBlank(saveVO.getChannelName(), "Alarm channel name cannot be blank.");
        // Depending on actual requirements, you can continue to add other parameter validations.
    }

    /**
     * Construct save parameters.
     *
     * @param saveVO Parameters for saving.
     * @return {@link RuleAlarmChannel} Alarm channel entity.
     */
    private RuleAlarmChannel builderAlarmChannelSaveVO(RuleAlarmChannelSaveVO saveVO) {
        return BeanPlusUtil.copyProperties(saveVO, RuleAlarmChannel.class);
    }

    /**
     * Update the alarm channel.
     *
     * @param updateVO Parameters for updating.
     * @return {@link RuleAlarmChannelUpdateVO} Update result.
     */
    @Override
    public RuleAlarmChannelUpdateVO updateAlarmChannel(RuleAlarmChannelUpdateVO updateVO) {
        log.info("updateAlarmChannel updateVO:{}", updateVO);

        // Validate the parameters.
        checkedAlarmChannelUpdateVO(updateVO);

        // Construct the parameters.
        RuleAlarmChannel alarmChannel = builderAlarmChannelUpdateVO(updateVO);

        // Update the alarm channel in database.
        superManager.updateById(alarmChannel);

        return updateVO;
    }

    /**
     * Delete the alarm channel.
     *
     * @param id ID.
     * @return {@link Boolean} Boolean value. true: successful, false: failed.
     */
    @Override
    public Boolean deleteAlarmChannel(Long id) {
        ArgumentAssert.notNull(id, "id cannot be null");

        RuleAlarmChannel alarmChannel = superManager.getById(id);

        if (null == alarmChannel) {
            throw BizException.wrap("The alarm channel does not exist");
        }

        // TODO Validate if the alarm channel is in use, e.g., if it has associated rules or records.
        // if (isAlarmChannelInUse(id)) {
        //     throw BizException.wrap("The alarm channel is currently in use and cannot be deleted");
        // }

        return superManager.removeById(id);
    }

    /**
     * Retrieve the details of the alarm channel.
     *
     * @param id ID.
     * @return {@link RuleAlarmChannelDetailsResultVO} Entity.
     */
    @Override
    public RuleAlarmChannelDetailsResultVO getAlarmChannelDetails(Long id) {
        if (id == null) {
            throw BizException.wrap("Alarm channel ID cannot be null");
        }

        RuleAlarmChannel alarmChannel = superManager.getById(id);
        if (alarmChannel == null) {
            throw new BizException("Alarm channel does not exist");
        }

        return BeanPlusUtil.toBeanIgnoreError(alarmChannel, RuleAlarmChannelDetailsResultVO.class);
    }

    /**
     * Retrieve a list of rule alarm channel VO.
     *
     * @param query Search parameters for rule alarm channels.
     * @return {@link List<RuleAlarmChannelResultVO>} List of rule alarm channel VO.
     */
    @Override
    public List<RuleAlarmChannelResultVO> getRuleAlarmChannelResultVOList(RuleAlarmChannelPageQuery query) {
        List<RuleAlarmChannel> ruleAlarmChannelList = superManager.getRuleAlarmChannelList(query);
        return BeanPlusUtil.toBeanList(ruleAlarmChannelList, RuleAlarmChannelResultVO.class);
    }

    /**
     * Validate the parameters for updating.
     *
     * @param updateVO Parameters for updating.
     */
    private void checkedAlarmChannelUpdateVO(RuleAlarmChannelUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "id cannot be null");

        RuleAlarmChannel existingChannel = superManager.getById(updateVO.getId());
        if (null == existingChannel) {
            throw BizException.wrap("Alarm channel does not exist");
        }

        ArgumentAssert.notBlank(updateVO.getChannelName(), "Alarm channel name cannot be blank.");
        // Depending on actual requirements, you can continue to add other parameter validations.
    }

    /**
     * Construct update parameters.
     *
     * @param updateVO Parameters for updating.
     * @return {@link RuleAlarmChannel} Alarm channel entity.
     */
    private RuleAlarmChannel builderAlarmChannelUpdateVO(RuleAlarmChannelUpdateVO updateVO) {
        return BeanPlusUtil.copyProperties(updateVO, RuleAlarmChannel.class);
    }

}


