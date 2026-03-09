package com.mqttsnet.thinglinks.product.event.handler;

import java.util.List;

import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.thinglinks.cache.product.ProductCacheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Description:
 * 产品信息更新缓存处理器
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/1/19
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductInfoUpdatedCacheHandler {

    private final ProductCacheService productCacheService;


    /**
     * 处理产品信息更新缓存
     *
     * <p>刷新指定产品标识集合的缓存</p>
     *
     * @param productIdentificationList 产品标识集合
     * @return 是否成功处理
     * @see ProductCacheService#refreshProductCache(String)
     */
    public boolean handleProductUpdatedCache(List<String> productIdentificationList) {
        try {
            ArgumentAssert.notEmpty(productIdentificationList, "productIdentificationList is null");
            log.info("处理产品信息更新缓存...productIdentificationList:{}", productIdentificationList);
            // 刷新产品缓存
            productIdentificationList.forEach(productCacheService::refreshProductCache);
            return true;
        } catch (Exception e) {
            log.error("处理产品信息更新缓存失败...productIdentificationList:{}", productIdentificationList, e);
            return false;
        }
    }

}
