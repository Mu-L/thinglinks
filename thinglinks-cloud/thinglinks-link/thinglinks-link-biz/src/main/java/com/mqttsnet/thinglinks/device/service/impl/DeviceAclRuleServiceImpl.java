package com.mqttsnet.thinglinks.device.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceAclRuleCacheVO;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.device.entity.DeviceAclRule;
import com.mqttsnet.thinglinks.device.enumeration.ClientAclActionTypeEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceAclRuleActionTypeEnum;
import com.mqttsnet.thinglinks.device.manager.DeviceAclRuleManager;
import com.mqttsnet.thinglinks.device.service.DeviceAclRuleService;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAclCheckQuery;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAclRulePageQuery;
import com.mqttsnet.thinglinks.device.vo.save.DeviceAclRuleSaveVO;
import com.mqttsnet.thinglinks.device.vo.update.DeviceAclRuleUpdateVO;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceAclCheckResultVO;
import com.mqttsnet.thinglinks.protocol.vo.result.DeviceInfoResultVO;
import com.mqttsnet.thinglinks.utils.acl.AclMatcherUtil;
import com.mqttsnet.thinglinks.utils.acl.AclTopicPatternPlaceholderReplacer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 设备访问控制(ACL)规则表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-11 19:57:46
 * @create [2025-06-11 19:57:46] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceAclRuleServiceImpl extends SuperServiceImpl<DeviceAclRuleManager, Long, DeviceAclRule> implements DeviceAclRuleService {

    private final LinkCacheDataHelper linkCacheDataHelper;

    @Override
    protected <UpdateVO> DeviceAclRule updateBefore(UpdateVO vo) {
        DeviceAclRuleUpdateVO updateVO = (DeviceAclRuleUpdateVO) vo;

        if (superManager.count(Wraps.<DeviceAclRule>lbQ()
                .eq(DeviceAclRule::getRuleLevel, updateVO.getRuleLevel())
                .eq(DeviceAclRule::getProductIdentification, updateVO.getProductIdentification())
                .eq(DeviceAclRule::getDeviceIdentification, updateVO.getDeviceIdentification())
                .eq(DeviceAclRule::getPriority, updateVO.getPriority())
                .ne(DeviceAclRule::getId, updateVO.getId())) > 0) {
            throw BizException.wrap("已存在有相同优先级的规则");
        }

        return super.updateBefore(updateVO);
    }

    @Override
    protected <SaveVO> DeviceAclRule saveBefore(SaveVO vo) {
        DeviceAclRuleSaveVO saveVO = (DeviceAclRuleSaveVO) vo;

        if (superManager.count(Wraps.<DeviceAclRule>lbQ()
                .eq(DeviceAclRule::getRuleLevel, saveVO.getRuleLevel())
                .eq(DeviceAclRule::getProductIdentification, saveVO.getProductIdentification())
                .eq(DeviceAclRule::getDeviceIdentification, saveVO.getDeviceIdentification())
                .eq(DeviceAclRule::getPriority, saveVO.getPriority())) > 0) {
            throw BizException.wrap("已存在有相同优先级的规则");
        }

        return super.saveBefore(saveVO);
    }


    @Override
    protected <SaveVO> void saveAfter(SaveVO saveVO, DeviceAclRule entity) {
        superManager.refreshDeviceAclRuleCache(Collections.singletonList(entity));
    }

    @Override
    protected <UpdateVO> void updateAfter(UpdateVO updateVO, DeviceAclRule entity) {
        superManager.refreshDeviceAclRuleCache(Collections.singletonList(entity));
    }


    @Override
    public DeviceAclCheckResultVO checkAclPermission(DeviceAclCheckQuery deviceAclCheckQuery) {
        Optional<DeviceCacheVO> deviceCacheVO = linkCacheDataHelper.getDeviceCacheVO(deviceAclCheckQuery.getClientIdentifier());
        if (deviceCacheVO.isEmpty()) {
            return DeviceAclCheckResultVO.builder()
                    .allowed(false)
                    .errorMessage("Device Not Found")
                    .echoMap(MapUtil.newHashMap())
                    .build();
        }
        DeviceInfoResultVO deviceInfoResultVO = BeanPlusUtil.toBean(deviceCacheVO.get(), DeviceInfoResultVO.class);
        List<DeviceAclRuleCacheVO> deviceAclRuleCacheVOList = getDeviceAclRuleCacheVOList(deviceAclCheckQuery.getClientIdentifier());
        if (CollectionUtil.isEmpty(deviceAclRuleCacheVOList)) {
            // 没有需要检查的规则，直接返回不允许
            return DeviceAclCheckResultVO.builder()
                    .allowed(false)
                    .errorMessage("Not ACL Rule")
                    .echoMap(MapUtil.newHashMap())
                    .build();
        }
        // 检查ACL规则
        Optional<ClientAclActionTypeEnum> clientAclActionTypeEnumOptional = ClientAclActionTypeEnum.fromValue(deviceAclCheckQuery.getActionType());

        if (clientAclActionTypeEnumOptional.isPresent()) {
            ClientAclActionTypeEnum aclActionTypeEnum = clientAclActionTypeEnumOptional.get();
            // 转换为设备ACL规则动作类型
            Optional<DeviceAclRuleActionTypeEnum> ruleActionType =
                    DeviceAclRuleActionTypeEnum.fromClientType(aclActionTypeEnum);

            // 获取要检查的主题
            String topic = deviceAclCheckQuery.getTopic();

            // 根据动作类型过滤规则（包括ALL类型）
            List<DeviceAclRuleCacheVO> filteredRules = deviceAclRuleCacheVOList.stream()
                    .filter(DeviceAclRuleCacheVO::getEnabled)
                    .filter(rule ->
                            rule.getActionType().equals(ruleActionType.get().getValue()) ||
                                    rule.getActionType().equals(DeviceAclRuleActionTypeEnum.ALL.getValue())
                    )
                    .collect(Collectors.toList());


            AclTopicPatternPlaceholderReplacer.replacePlaceholders(filteredRules, Optional.of(deviceInfoResultVO));
            // 使用ACL工具类进行决策
            boolean allowed = AclMatcherUtil.isTopicAllowed(topic, filteredRules);

            return DeviceAclCheckResultVO.builder()
                    .allowed(allowed)
                    .echoMap(MapUtil.newHashMap())
                    .build();
        } else {
            // 无效的操作类型,直接返回不允许
            return DeviceAclCheckResultVO.builder()
                    .allowed(false)
                    .errorMessage("Invalid Action Type")
                    .echoMap(MapUtil.newHashMap())
                    .build();
        }
    }


    @Override
    public List<DeviceAclRuleCacheVO> getDeviceAclRuleCacheVOList(String clientIdentifier) {
        // 获取设备缓存信息
        Optional<DeviceCacheVO> deviceCacheVOOptional = linkCacheDataHelper.getDeviceCacheVO(clientIdentifier);
        if (deviceCacheVOOptional.isEmpty()) {
            return Collections.emptyList();
        }
        DeviceCacheVO deviceCacheVO = deviceCacheVOOptional.get();
        //获取设备和产品级ACL规则（确保按优先级排序,优先级值越小优先级越高）
        List<DeviceAclRuleCacheVO> deviceAclRuleList = superManager.getAclRules(deviceCacheVO.getProductIdentification(), deviceCacheVO.getDeviceIdentification());
        return deviceAclRuleList.stream().filter(DeviceAclRuleCacheVO::getEnabled).toList();
    }

    @Override
    public void refreshAllDeviceAclRuleCache(Long tenantId) {
        PageParams<DeviceAclRulePageQuery> pageParams = new PageParams<>();
        pageParams.setSize(Long.MAX_VALUE);
        pageParams.setModel(DeviceAclRulePageQuery.builder().build());
        superManager.refreshAllDeviceAclRuleCache(pageParams);
    }

}


