package com.mqttsnet.thinglinks.link.facade;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.thinglinks.cache.device.DeviceCacheService;
import com.mqttsnet.thinglinks.cache.product.ProductCacheService;
import com.mqttsnet.thinglinks.cache.product.ProductModelCacheService;
import com.mqttsnet.thinglinks.device.service.DeviceAclRuleService;
import com.mqttsnet.thinglinks.device.service.DeviceSyncAnyUserService;
import com.mqttsnet.thinglinks.ota.service.OtaUpgradeTaskExecutionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author tangyh
 * @since 2024/12/24 17:02
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LinkJobHandlerFacadeImpl implements LinkJobHandlerFacade {
    private final DeviceCacheService deviceCacheService;
    private final ProductCacheService productCacheService;
    private final ProductModelCacheService productModelCacheService;
    private final DeviceSyncAnyUserService deviceSyncAnyUserService;
    private final OtaUpgradeTaskExecutionService otaUpgradeTaskExecutionService;
    private final DeviceAclRuleService deviceAclRuleService;


    @Override
    public R<?> refreshDeviceCacheForTenant(Long tenantId) {
        ArgumentAssert.notNull(tenantId, "tenantId Cannot be null");
        log.info("Refreshing device cache for tenant ID: {}", tenantId);
        deviceCacheService.refreshDeviceCacheForTenant(tenantId);
        return R.success();
    }

    @Override
    public R<?> syncDeviceConnectionStatus(Long tenantId) {
        ArgumentAssert.notNull(tenantId, "tenantId cannot be null");
        log.info("Starting device connection status sync for tenantId: {}", tenantId);
        deviceSyncAnyUserService.syncDeviceConnectionStatus(tenantId);
        return R.success();
    }

    @Override
    public R<?> refreshProductCacheForTenant(Long tenantId) {
        ArgumentAssert.notNull(tenantId, "tenantId Cannot be null");
        log.info("Refreshing product cache for tenant ID: {}", tenantId);
        productCacheService.refreshProductCacheForTenant(tenantId);
        return R.success();
    }

    @Override
    public R<?> refreshProductModelCache(Long tenantId) {
        ArgumentAssert.notNull(tenantId, "tenantId Cannot be null");
        log.info("Refreshing product model cache for tenant ID: {}", tenantId);
        productModelCacheService.refreshProductModelCache(tenantId);
        return R.success();
    }

    @Override
    public R<?> otaUpgradeTasksExecute(Long tenantId) {
        ArgumentAssert.notNull(tenantId, "tenantId Cannot be null");
        log.info("Executing OTA Upgrade Tasks - Tenant ID: {}", tenantId);
        otaUpgradeTaskExecutionService.otaUpgradeTasksExecute(tenantId);
        return R.success();
    }

    @Override
    public R<?> refreshDeviceAclCache(Long tenantId) {
        ArgumentAssert.notNull(tenantId, "tenantId cannot be null");
        log.info("Refreshing device ACL cache for tenantId: {}", tenantId);
        deviceAclRuleService.refreshAllDeviceAclRuleCache(tenantId);
        return R.success();
    }
}
