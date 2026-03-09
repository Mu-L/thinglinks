package com.mqttsnet.thinglinks.service.alarm.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.model.Kv;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.common.constant.BizConstant;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.dto.alarm.channel.dingtalk.DingTalkMessageParamDTO;
import com.mqttsnet.thinglinks.dto.alarm.channel.fs.FeishuMessageParamDTO;
import com.mqttsnet.thinglinks.dto.alarm.channel.wechat.WeChatWorkMessageParamDTO;
import com.mqttsnet.thinglinks.entity.alarm.RuleAlarmRecord;
import com.mqttsnet.thinglinks.enumeration.alarm.AlarmChannelStatusEnum;
import com.mqttsnet.thinglinks.enumeration.alarm.AlarmChannelTypeEnum;
import com.mqttsnet.thinglinks.enumeration.alarm.AlarmRecordHandledStatusEnum;
import com.mqttsnet.thinglinks.manager.alarm.RuleAlarmRecordManager;
import com.mqttsnet.thinglinks.msg.facade.MsgFacade;
import com.mqttsnet.thinglinks.msg.vo.save.ExtendMsgRecipientSaveVO;
import com.mqttsnet.thinglinks.msg.vo.update.ExtendMsgSendVO;
import com.mqttsnet.thinglinks.protocol.vo.param.DeviceAlarmNotificationRequestParam;
import com.mqttsnet.thinglinks.service.alarm.RuleAlarmChannelService;
import com.mqttsnet.thinglinks.service.alarm.RuleAlarmRecordService;
import com.mqttsnet.thinglinks.service.alarm.RuleAlarmService;
import com.mqttsnet.thinglinks.vo.param.linkage.RuleAlarmRecordHandleParamVO;
import com.mqttsnet.thinglinks.vo.result.alarm.RuleAlarmChannelDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.alarm.RuleAlarmDetailsResultVO;
import com.mqttsnet.thinglinks.vo.result.alarm.RuleAlarmRecordDetailsResultVO;
import com.mqttsnet.thinglinks.vo.save.alarm.RuleAlarmRecordSaveVO;
import com.mqttsnet.thinglinks.vo.update.alarm.RuleAlarmRecordUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * 业务实现类
 * 告警记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-09 21:15:22
 * @create [2023-09-09 21:15:22] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleAlarmRecordServiceImpl extends SuperServiceImpl<RuleAlarmRecordManager, Long, RuleAlarmRecord> implements RuleAlarmRecordService {

    @Autowired
    private RuleAlarmService ruleAlarmService;

    @Autowired
    private RuleAlarmChannelService ruleAlarmChannelService;

    @Autowired
    private MsgFacade msgApi;

    /**
     * Save the alarm record.
     *
     * @param saveVO Save parameters
     * @return Saved entity
     */
    @Override
    public RuleAlarmRecordSaveVO saveAlarmRecord(RuleAlarmRecordSaveVO saveVO) {
        log.info("saveRuleAlarmRecord saveVO:{}", saveVO);

        // Validate parameters
        checkRuleAlarmRecordSaveVO(saveVO);

        // Construct parameters
        RuleAlarmRecord ruleAlarmRecord = buildRuleAlarmRecordSaveVO(saveVO);

        // Save alarm record
        superManager.save(ruleAlarmRecord);

        return BeanPlusUtil.toBeanIgnoreError(ruleAlarmRecord, RuleAlarmRecordSaveVO.class);
    }

    /**
     * Validate save parameters.
     *
     * @param saveVO Save parameters
     */
    private void checkRuleAlarmRecordSaveVO(RuleAlarmRecordSaveVO saveVO) {
        ArgumentAssert.notBlank(saveVO.getAppId(), "Application ID cannot be blank");
        // ... Continue with other checks based on your requirements
    }

    /**
     * Construct save parameters.
     *
     * @param saveVO Save parameters
     * @return Alarm record entity
     */
    private RuleAlarmRecord buildRuleAlarmRecordSaveVO(RuleAlarmRecordSaveVO saveVO) {
        // Make any necessary modifications or populate fields
        // Example:
        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());

        return BeanPlusUtil.copyProperties(saveVO, RuleAlarmRecord.class);
    }

    /**
     * Update the alarm record.
     *
     * @param updateVO Update parameters
     * @return Updated result
     */
    @Override
    public RuleAlarmRecordUpdateVO updateAlarmRecord(RuleAlarmRecordUpdateVO updateVO) {
        log.info("updateRuleAlarmRecord updateVO:{}", updateVO);

        // Validate parameters
        checkRuleAlarmRecordUpdateVO(updateVO);

        // Construct parameters
        RuleAlarmRecord ruleAlarmRecord = buildRuleAlarmRecordUpdateVO(updateVO);

        // Update alarm record
        superManager.updateById(ruleAlarmRecord);

        return updateVO;
    }

    /**
     * Validate update parameters.
     *
     * @param updateVO Update parameters
     */
    private void checkRuleAlarmRecordUpdateVO(RuleAlarmRecordUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "ID cannot be null");
        // ... Continue with other checks based on your requirements
    }

    /**
     * Construct update parameters.
     *
     * @param updateVO Update parameters
     * @return Alarm record entity
     */
    private RuleAlarmRecord buildRuleAlarmRecordUpdateVO(RuleAlarmRecordUpdateVO updateVO) {
        return BeanPlusUtil.copyProperties(updateVO, RuleAlarmRecord.class);
    }

    /**
     * Delete the alarm record.
     *
     * @param id ID
     * @return Boolean indicating success or failure
     */
    @Override
    public Boolean deleteAlarmRecord(Long id) {
        ArgumentAssert.notNull(id, "ID cannot be null");

        RuleAlarmRecord ruleAlarmRecord = superManager.getById(id);
        if (ruleAlarmRecord == null) {
            throw BizException.wrap("The rule alarm record does not exist");
        }

        // TODO Validate if the alarm record is being used elsewhere or has dependencies
        // if (isRuleAlarmRecordInUse(id)) {
        //     throw BizException.wrap("The rule alarm record is currently in use and cannot be deleted");
        // }

        return superManager.removeById(id);
    }

    /**
     * Get details of the alarm record.
     *
     * @param id ID
     * @return Alarm record details
     */
    @Override
    public RuleAlarmRecordDetailsResultVO getAlarmRecordDetails(Long id) {
        ArgumentAssert.notNull(id, "Rule alarm record ID cannot be null");

        RuleAlarmRecord ruleAlarmRecord = superManager.getById(id);
        if (ruleAlarmRecord == null) {
            throw BizException.wrap("Rule alarm record does not exist");
        }
        RuleAlarmRecordDetailsResultVO alarmRecordDetailsResultVO = BeanPlusUtil.toBeanIgnoreError(ruleAlarmRecord, RuleAlarmRecordDetailsResultVO.class);

        RuleAlarmDetailsResultVO ruleAlarmDetailsResultVO = ruleAlarmService.getRuleAlarmDetailsByAlarmIdentification(ruleAlarmRecord.getAlarmIdentification());
        if (Objects.nonNull(ruleAlarmDetailsResultVO)) {
            alarmRecordDetailsResultVO.setRuleAlarmDetailsResultVO(ruleAlarmDetailsResultVO);
        }

        return alarmRecordDetailsResultVO;
    }

    /**
     * handle or solve alarm record
     *
     * @param recordHandleParamVO parameters
     * @return {@link RuleAlarmRecordUpdateVO} updated alarm record entity
     */
    @Override
    public RuleAlarmRecordUpdateVO handleOrSolveAlarmRecord(RuleAlarmRecordHandleParamVO recordHandleParamVO) {
        log.info("handleAlarmRecord handleDTO: {}", recordHandleParamVO);

        // Validate parameters
        ArgumentAssert.notNull(recordHandleParamVO.getId(), "ID cannot be null");
        ArgumentAssert.notNull(recordHandleParamVO.getHandledStatus(), "Handle status cannot be null");
        ArgumentAssert.notEmpty(recordHandleParamVO.getHandleNotes(), "Handle notes cannot be empty");

        // Retrieve the existing alarm record
        RuleAlarmRecord existingRecord = superManager.getById(recordHandleParamVO.getId());
        if (existingRecord == null) {
            throw new RuntimeException("Alarm record with ID " + recordHandleParamVO.getId() + " not found.");
        }


        // Update the alarm record based on handleType
        switch (AlarmRecordHandledStatusEnum.valueOf(recordHandleParamVO.getHandledStatus())) {
            case IN_PROGRESS:
                existingRecord.setHandledTime(LocalDateTime.now());
                existingRecord.setHandlingNotes(recordHandleParamVO.getHandleNotes());
                existingRecord.setHandledStatus(AlarmRecordHandledStatusEnum.IN_PROGRESS.getValue());
                break;
            case RESOLVED:
                existingRecord.setResolvedTime(LocalDateTime.now());
                existingRecord.setResolutionNotes(recordHandleParamVO.getHandleNotes());
                existingRecord.setHandledStatus(AlarmRecordHandledStatusEnum.RESOLVED.getValue());
                break;
        }

        // Save the updated record
        superManager.updateById(existingRecord);

        // Convert and return updated entity
        return BeanPlusUtil.toBeanIgnoreError(existingRecord, RuleAlarmRecordUpdateVO.class);
    }


    @Override
    public boolean triggerDeviceAlarm(DeviceAlarmNotificationRequestParam requestParam) {
        // Get rule alarm details by alarm identification
        RuleAlarmDetailsResultVO ruleAlarmDetailsResultVO = ruleAlarmService.getRuleAlarmDetailsByAlarmIdentification(requestParam.getAlarmIdentification());

        ruleAlarmDetailsResultVO.getRuleAlarmChannelDetailsResultVOList().stream()
                .filter(item -> AlarmChannelStatusEnum.ACTIVATED.getValue().equals(item.getStatus()))
                .forEach(ruleAlarmChannelResultVO -> {
                    try {
                        sendAlarmNotification(requestParam, ruleAlarmDetailsResultVO, ruleAlarmChannelResultVO);
                    } catch (Exception e) {
                        log.error("Failed to send alarm notification for channel: {}, error: {}", ruleAlarmChannelResultVO.getChannelType(), e.getMessage(), e);
                    }
                });
        // Save the alarm record after sending notifications
        saveAlarmRecord(createAlarmRecordSaveVO(requestParam, ruleAlarmDetailsResultVO));
        return true;
    }

    private void sendAlarmNotification(DeviceAlarmNotificationRequestParam requestParam, RuleAlarmDetailsResultVO ruleAlarmDetailsResultVO, RuleAlarmChannelDetailsResultVO ruleAlarmChannelDetailsResultVO) {
        ExtendMsgSendVO extendMsgSendVO = new ExtendMsgSendVO();
        AlarmChannelTypeEnum alarmChannelTypeEnum = AlarmChannelTypeEnum.fromValue(ruleAlarmChannelDetailsResultVO.getChannelType()).orElse(null);

        if (alarmChannelTypeEnum == null) {
            log.error("Alarm channel type not found: {}", ruleAlarmChannelDetailsResultVO.getChannelType());
            return;
        }

        // Set template code
        extendMsgSendVO.setCode(alarmChannelTypeEnum.getChannelTemplateCode());

        // Parse channel configuration parameters
        List<Kv> configList = parseChannelConfig(ruleAlarmChannelDetailsResultVO.getChannelConfig(), alarmChannelTypeEnum);
        List<ExtendMsgRecipientSaveVO> recipientList = createRecipientList(requestParam);

        boolean isAtAll = recipientList.stream()
                .anyMatch(recipient -> BizConstant.ALL.equals(recipient.getRecipient()));

        if (isAtAll) {
            configList.add(Kv.builder().key("isAtAll").value(StrPool.YES).build());
        }

        // Set recipients and configuration
        if (CollUtil.isNotEmpty(recipientList)) {
            extendMsgSendVO.setRecipientList(recipientList);
        }
        if (CollUtil.isNotEmpty(configList)) {
            extendMsgSendVO.setConfigList(configList);
        }

        // Set alarm content
        extendMsgSendVO.setContent(requestParam.getContentData());
        extendMsgSendVO.setTitle(ruleAlarmDetailsResultVO.getAlarmName());

        // Send the alarm message
        Boolean booleanR = msgApi.sendByTemplate(extendMsgSendVO);
        log.info("Send alarm message result: {}", booleanR);
        if (!booleanR) {
            log.error("Failed to send alarm message: {}", booleanR);
        }
    }

    private List<ExtendMsgRecipientSaveVO> createRecipientList(DeviceAlarmNotificationRequestParam requestParam) {
        List<ExtendMsgRecipientSaveVO> recipientList = new ArrayList<>();
        String atPhone = requestParam.getAtPhone();

        if (StringUtils.isNotBlank(atPhone)) {
            Arrays.stream(atPhone.split(StrUtil.COMMA))
                    .map(String::trim)
                    .filter(phone -> !phone.isEmpty())
                    .distinct()
                    .forEach(phone -> {
                        ExtendMsgRecipientSaveVO recipient = new ExtendMsgRecipientSaveVO();
                        recipient.setRecipient(phone);
                        recipientList.add(recipient);
                    });
        }

        return recipientList;
    }

    private RuleAlarmRecordSaveVO createAlarmRecordSaveVO(DeviceAlarmNotificationRequestParam requestParam, RuleAlarmDetailsResultVO ruleAlarmDetailsResultVO) {
        RuleAlarmRecordSaveVO saveVO = new RuleAlarmRecordSaveVO();
        saveVO.setAppId(ruleAlarmDetailsResultVO.getAppId());
        saveVO.setAlarmIdentification(requestParam.getAlarmIdentification());
        saveVO.setOccurredTime(LocalDateTime.now());
        saveVO.setContentData(requestParam.getContentData());
        saveVO.setHandledStatus(AlarmRecordHandledStatusEnum.PENDING.getValue());
        saveVO.setRemark(Optional.ofNullable(requestParam.getRemark()).orElse("Alarm triggered and notification sent."));
        return saveVO;
    }

    private List<Kv> parseChannelConfig(String channelConfig, AlarmChannelTypeEnum alarmChannelTypeEnum) {
        if (StrUtil.isBlank(channelConfig)) {
            log.error("Channel config is empty: {}", channelConfig);
            return new ArrayList<>();
        }
        List<Kv> cofigList = new ArrayList<>();
        switch (alarmChannelTypeEnum) {
            case DING_TALK:
                Optional<DingTalkMessageParamDTO> dingTalkMessageParam = parseConfig(channelConfig, DingTalkMessageParamDTO.class);
                if (dingTalkMessageParam.isPresent()) {
                    cofigList.add(Kv.builder().key("msgType").value("MARKDOWN").build());
                    cofigList.add(Kv.builder().key("secret").value(dingTalkMessageParam.get().getSecret()).build());
                    cofigList.add(Kv.builder().key("token").value(dingTalkMessageParam.get().getToken()).build());
                } else {
                    log.error("Failed to parse DingTalkMessageParamDTO from channel config, channelConfig: {} ", channelConfig);
                }
                break;
            case ENTERPRISE_WECHAT:
                Optional<WeChatWorkMessageParamDTO> weChatWorkMessageParam = parseConfig(channelConfig, WeChatWorkMessageParamDTO.class);
                if (weChatWorkMessageParam.isPresent()) {
                    cofigList.add(Kv.builder().key("msgType").value("MARKDOWN").build());
                    cofigList.add(Kv.builder().key("token").value(weChatWorkMessageParam.get().getToken()).build());
                } else {
                    log.error("Failed to parse WeChatWorkMessageParamDTO from channel config, channelConfig: {} ", channelConfig);
                }
                break;
            case FS:
                Optional<FeishuMessageParamDTO> feishuMessageParam = parseConfig(channelConfig, FeishuMessageParamDTO.class);
                if (feishuMessageParam.isPresent()) {
                    cofigList.add(Kv.builder().key("appSecret").value(feishuMessageParam.get().getAppSecret()).build());
                    cofigList.add(Kv.builder().key("appId").value(feishuMessageParam.get().getAppId()).build());
                    cofigList.add(Kv.builder().key("token").value(feishuMessageParam.get().getToken()).build());
                } else {
                    log.error("Failed to parse FeishuMessageParamDTO from channel config, channelConfig: {} ", channelConfig);
                }
                break;
            default:
                log.error("Unsupported alarm channel type: {}", alarmChannelTypeEnum);
        }
        return cofigList;
    }

    private <T> Optional<T> parseConfig(String config, Class<T> clazz) {
        try {
            return Optional.ofNullable(JSON.parseObject(config, clazz));
        } catch (Exception e) {
            log.error("Failed to parse config to {}: {}", clazz.getSimpleName(), e.getMessage());
            return Optional.empty();
        }
    }

}


