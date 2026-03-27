package com.mqttsnet.thinglinks.device.manager.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.database.mybatis.conditions.query.LbQueryWrap;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceAclRuleCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.device.DeviceAclRuleCacheKeyBuilder;
import com.mqttsnet.thinglinks.device.entity.DeviceAclRule;
import com.mqttsnet.thinglinks.device.enumeration.DeviceAclRuleLevelEnum;
import com.mqttsnet.thinglinks.device.manager.DeviceAclRuleManager;
import com.mqttsnet.thinglinks.device.mapper.DeviceAclRuleMapper;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAclRulePageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 通用业务实现类
 * 设备访问控制(ACL)规则表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-11 19:57:46
 * @create [2025-06-11 19:57:46] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceAclRuleManagerImpl extends SuperManagerImpl<DeviceAclRuleMapper, DeviceAclRule> implements DeviceAclRuleManager {

    private final DeviceAclRuleMapper deviceAclRuleMapper;

    private final CachePlusOps cachePlusOps;


    @Override
    public void refreshAllDeviceAclRuleCache(PageParams<DeviceAclRulePageQuery> pageParams) {
        IPage<DeviceAclRule> page = pageParams.buildPage(DeviceAclRule.class);
        LbQueryWrap<DeviceAclRule> wrap = Wraps.lbQ();
        IPage<DeviceAclRule> deviceAclRuleIPage = deviceAclRuleMapper.selectPage(page, wrap);

        // 按缓存键分组规则
        Map<CacheKey, Map<Object, Double>> cacheOperations = new HashMap<>();

        deviceAclRuleIPage.getRecords().forEach(deviceAclRule -> {
            Optional<DeviceAclRuleLevelEnum> ruleLevel = DeviceAclRuleLevelEnum.fromValue(deviceAclRule.getRuleLevel());
            CacheKey cacheKey = ruleLevel.filter(lvl -> lvl == DeviceAclRuleLevelEnum.PRODUCT_LEVEL)
                    .map(lvl -> DeviceAclRuleCacheKeyBuilder.builder(deviceAclRule.getProductIdentification()))
                    .orElseGet(() -> DeviceAclRuleCacheKeyBuilder.builder(
                            deviceAclRule.getProductIdentification(),
                            deviceAclRule.getDeviceIdentification()));

            // 转换为缓存对象并添加到分组
            DeviceAclRuleCacheVO cacheVO = BeanPlusUtil.toBeanIgnoreError(deviceAclRule, DeviceAclRuleCacheVO.class);
            cacheOperations.computeIfAbsent(cacheKey, k -> new HashMap<>())
                    .put(cacheVO, (double) deviceAclRule.getPriority());
        });

        // 批量执行缓存操作
        executeBatchCacheOperations(cacheOperations);
    }

    @Override
    public void refreshDeviceAclRuleCache(List<DeviceAclRule> deviceAclRules) {
        // 按缓存键分组规则
        Map<CacheKey, Map<Object, Double>> cacheOperations = new HashMap<>();

        deviceAclRules.forEach(deviceAclRule -> {
            Optional<DeviceAclRuleLevelEnum> ruleLevel = DeviceAclRuleLevelEnum.fromValue(deviceAclRule.getRuleLevel());
            CacheKey cacheKey = ruleLevel.filter(lvl -> lvl.equals(DeviceAclRuleLevelEnum.PRODUCT_LEVEL))
                    .map(lvl -> DeviceAclRuleCacheKeyBuilder.builder(deviceAclRule.getProductIdentification()))
                    .orElseGet(() -> DeviceAclRuleCacheKeyBuilder.builder(
                            deviceAclRule.getProductIdentification(),
                            deviceAclRule.getDeviceIdentification()));

            // 转换为缓存对象并添加到分组
            DeviceAclRuleCacheVO cacheVO = BeanPlusUtil.toBeanIgnoreError(deviceAclRule, DeviceAclRuleCacheVO.class);
            cacheOperations.computeIfAbsent(cacheKey, k -> new HashMap<>())
                    .put(cacheVO, (double) deviceAclRule.getPriority());
        });

        // 批量执行缓存操作
        executeBatchCacheOperations(cacheOperations);
    }

    /**
     * 批量执行缓存操作（先删除再批量添加）
     */
    private void executeBatchCacheOperations(Map<CacheKey, Map<Object, Double>> cacheOperations) {
        cacheOperations.forEach((cacheKey, scoreMembers) -> {
            try {
                // 1. 删除旧缓存
                cachePlusOps.del(cacheKey);

                // 2. 批量添加新数据
                if (!scoreMembers.isEmpty()) {
                    Long addedCount = cachePlusOps.zAdd(cacheKey, scoreMembers);
                    log.info("Refreshed cache for key {}: added {} new members",
                            cacheKey.getKey(), addedCount);
                    cachePlusOps.expire(cacheKey);
                }
            } catch (Exception e) {
                log.error("Failed to refresh cache for key {}", cacheKey.getKey(), e);
            }
        });
    }

    @Override
    public List<DeviceAclRuleCacheVO> getAclRules(String productIdentification, String deviceIdentification) {
        // 获取设备级ACL规则
        List<DeviceAclRuleCacheVO> deviceRules = getDeviceLevelAclRules(productIdentification, deviceIdentification);

        // 获取产品级ACL规则
        List<DeviceAclRuleCacheVO> productRules = getProductLevelAclRules(productIdentification);

        // 合并并按优先级排序
        return Stream.concat(deviceRules.stream(), productRules.stream())
                .sorted(Comparator.comparingInt(DeviceAclRuleCacheVO::getPriority))
                .limit(100)
                .collect(Collectors.toList());
    }

    protected List<DeviceAclRuleCacheVO> getDeviceLevelAclRules(String productIdentification, String deviceIdentification) {
        CacheKey cacheKey = DeviceAclRuleCacheKeyBuilder.builder(productIdentification, deviceIdentification);
        return getCachedRules(cacheKey);
    }

    protected List<DeviceAclRuleCacheVO> getProductLevelAclRules(String productIdentification) {
        CacheKey cacheKey = DeviceAclRuleCacheKeyBuilder.builder(productIdentification);
        return getCachedRules(cacheKey);
    }

    /**
     * 通用方法：从缓存获取规则
     */
    private List<DeviceAclRuleCacheVO> getCachedRules(CacheKey cacheKey) {
        Set<ZSetOperations.TypedTuple<Object>> cachedRules = cachePlusOps.zRangeWithScores(cacheKey, 0, -1);

        return cachedRules.stream()
                .map(tuple -> BeanPlusUtil.toBean(tuple.getValue(), DeviceAclRuleCacheVO.class))
                .collect(Collectors.toList());
    }


}


