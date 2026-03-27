package com.mqttsnet.thinglinks.device.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceAclRuleCacheVO;
import com.mqttsnet.thinglinks.device.entity.DeviceAclRule;
import com.mqttsnet.thinglinks.device.vo.query.DeviceAclRulePageQuery;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 设备访问控制(ACL)规则表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-06-11 19:57:46
 * @create [2025-06-11 19:57:46] [mqttsnet]
 */
public interface DeviceAclRuleManager extends SuperManager<DeviceAclRule> {

    /**
     * 刷新所有设备访问控制规则缓存
     *
     * @param pageParams 分页参数
     */
    void refreshAllDeviceAclRuleCache(PageParams<DeviceAclRulePageQuery> pageParams);

    /**
     * 刷新设备访问控制规则缓存
     *
     * @param deviceAclRules 设备访问控制规则列表
     */
    void refreshDeviceAclRuleCache(List<DeviceAclRule> deviceAclRules);

    /**
     * 获取指定租户的设备级和产品级ACL规则,并按优先级排序
     *
     * @param productIdentification 产品标识
     * @param deviceIdentification  设备标识
     * @return {@link List<DeviceAclRuleCacheVO>} 合并后的ACL规则列表, 按优先级排序
     */
    List<DeviceAclRuleCacheVO> getAclRules(String productIdentification, String deviceIdentification);
}


