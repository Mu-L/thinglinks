package com.mqttsnet.thinglinks.cache.product;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.cache.CacheSuperAbstract;
import com.mqttsnet.thinglinks.cache.vo.product.ProductModelCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.product.ProductModelCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.product.service.ProductService;
import com.mqttsnet.thinglinks.product.vo.param.ProductParamVO;
import com.mqttsnet.thinglinks.product.vo.query.ProductPageQuery;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * -----------------------------------------------------------------------------
 * File Name: ProductModelCacheService.java
 * -----------------------------------------------------------------------------
 * Description:
 * Service layer for Product Model cache management.
 * -----------------------------------------------------------------------------
 *
 * @author mqttsnet
 * @version 1.1
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * 2025-07-31    mqttsnet     1.1         优化日志记录和性能优化。
 * --------      --------     -------   --------------------
 * <p>
 * -----------------------------------------------------------------------------
 */
@DS(DsConstant.BASE_TENANT)
@Service
@RequiredArgsConstructor
@Slf4j
public class ProductModelCacheService extends CacheSuperAbstract {

    private final CachePlusOps cachePlusOps;
    private final ProductService productService;

    // 日志常量
    private static final String BATCH_LOG_FORMAT =
            "[产品物模型缓存-开始] 租户ID={} | 操作类型={} | 总产品数={} | 分页大小={} | 预计批次={}";
    private static final String BATCH_ITEM_LOG =
            "[产品物模型缓存-进度] 租户ID={} | 当前批次={}/{} | 本页产品={} | 成功累计={} | 失败累计={} | 耗时={}ms";
    private static final String BATCH_SUMMARY =
            "[产品物模型缓存-完成] 租户ID={} | 总耗时={}ms | 成功总数={} | 失败总数={} | 平均耗时={}ms/产品";
    private static final String MODEL_TRANSFORM_LOG =
            "[产品物模型缓存-转换] 租户ID={} | 产品标识={} | 状态={} | 耗时={}ms | 错误={}";
    private static final String MODEL_CACHE_LOG =
            "[产品物模型缓存-存储] 租户ID={} | 产品标识={} | 状态={} | 耗时={}ms | 错误={}";

    /**
     * 全量刷新指定租户的产品物模型缓存
     *
     * <p>该方法会分页查询所有产品，并逐个刷新其物模型缓存</p>
     *
     * @param tenantId 租户ID
     * @throws RuntimeException 当缓存刷新过程中出现异常时抛出
     * @see #fetchProductPage(int)
     * @see #processModelsBatch(Long, List, AtomicInteger, AtomicInteger)
     */
    public void refreshProductModelCache(Long tenantId) {
        long startTime = System.currentTimeMillis();
        AtomicInteger totalSuccess = new AtomicInteger();
        AtomicInteger totalFail = new AtomicInteger();

        int totalProducts = productService.findProductTotal().intValue();
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        log.info(BATCH_LOG_FORMAT, tenantId, "产品物模型全量刷新", totalProducts, PAGE_SIZE, totalPages);

        IntStream.rangeClosed(1, totalPages)
                .sequential()
                .forEach(currentPage -> {
                    long pageStartTime = System.currentTimeMillis();
                    List<ProductResultVO> products = fetchProductPage(currentPage);
                    processModelsBatch(tenantId, products, totalSuccess, totalFail);
                    log.info(BATCH_ITEM_LOG, tenantId, currentPage, totalPages, products.size(), totalSuccess.get(), totalFail.get(), System.currentTimeMillis() - pageStartTime);
                });

        log.info(BATCH_SUMMARY, tenantId, System.currentTimeMillis() - startTime, totalSuccess.get(), totalFail.get(), totalProducts > 0 ? (System.currentTimeMillis() - startTime) / totalProducts : 0);
    }

    /**
     * 获取分页产品数据
     *
     * @param currentPage 页码(从1开始)
     * @return 当前页的产品列表，如果查询失败返回空列表
     * @throws IllegalArgumentException 如果currentPage小于1
     * @see ProductService#getPage(PageParams)
     */
    private List<ProductResultVO> fetchProductPage(int currentPage) {
        ArgumentAssert.isTrue(currentPage >= 1, "currentPage must be greater than or equal to 1");
        PageParams<ProductPageQuery> params = new PageParams<>(currentPage, PAGE_SIZE);
        params.setModel(ProductPageQuery.builder().build());
        return Optional.ofNullable(productService.getPage(params))
                .map(IPage::getRecords)
                .orElse(Collections.emptyList());
    }

    /**
     * 处理产品物模型批次数据
     *
     * @param tenantId     租户ID
     * @param products     产品列表
     * @param totalSuccess 成功计数器
     * @param totalFail    失败计数器
     * @see #cacheProductModel(ProductModelCacheVO)
     */
    private void processModelsBatch(Long tenantId,
                                    List<ProductResultVO> products,
                                    AtomicInteger totalSuccess,
                                    AtomicInteger totalFail) {
        AtomicInteger pageSuccess = new AtomicInteger();
        AtomicInteger pageFail = new AtomicInteger();

        products.forEach(product -> {
            long start = System.currentTimeMillis();
            try {
                ProductModelCacheVO cacheVO = transformToModelCacheVO(tenantId, product);
                cacheProductModel(cacheVO);
                pageSuccess.incrementAndGet();
                log.info(MODEL_TRANSFORM_LOG, tenantId, product.getProductIdentification(), "成功", System.currentTimeMillis() - start, "");
            } catch (Exception e) {
                pageFail.incrementAndGet();
                log.error(MODEL_TRANSFORM_LOG, tenantId, product.getProductIdentification(), "失败", System.currentTimeMillis() - start, e.getClass().getSimpleName() + ":" + e.getMessage());
            }
        });
        totalSuccess.addAndGet(pageSuccess.get());
        totalFail.addAndGet(pageFail.get());
    }

    /**
     * 转换产品为物模型缓存对象
     *
     * @param tenantId 租户ID
     * @param product  产品实体
     * @return 物模型缓存VO
     * @throws RuntimeException 转换异常
     * @see ProductService#selectFullProductByProductIdentification(String)
     * @see BeanPlusUtil#toBeanIgnoreError(Object, Class)
     */
    private ProductModelCacheVO transformToModelCacheVO(Long tenantId, ProductResultVO product) {
        ProductParamVO productParamVO = productService.selectFullProductByProductIdentification(product.getProductIdentification());
        ProductModelCacheVO vo = BeanPlusUtil.toBeanIgnoreError(productParamVO, ProductModelCacheVO.class);
        vo.setTenantId(tenantId);
        return vo;
    }

    /**
     * 缓存单个产品物模型
     *
     * @param cacheVO 物模型缓存VO
     * @see ProductModelCacheKeyBuilder#build(String)
     */
    private void cacheProductModel(ProductModelCacheVO cacheVO) {
        long start = System.currentTimeMillis();
        try {
            CacheKey cacheKey = ProductModelCacheKeyBuilder.build(cacheVO.getProductIdentification());
            cachePlusOps.del(cacheKey);
            cachePlusOps.set(cacheKey, cacheVO);
            log.info(MODEL_CACHE_LOG, cacheVO.getTenantId(), cacheVO.getProductIdentification(), "成功", System.currentTimeMillis() - start, "");
        } catch (Exception e) {
            log.error(MODEL_CACHE_LOG, cacheVO.getTenantId(), cacheVO.getProductIdentification(), "失败", System.currentTimeMillis() - start, e.getClass().getSimpleName() + ":" + e.getMessage());
        }
    }

    /**
     * 刷新单个产品的物模型缓存
     *
     * @param productIdentification 产品标识
     * @return 是否刷新成功
     * @see #cacheProductModel(ProductModelCacheVO)
     */
    public boolean refreshProductModelCache(String productIdentification) {
        try {
            Long tenantId = ContextUtil.getTenantId();
            log.info("开始刷新{}物模型缓存: {}", tenantId, productIdentification);

            ProductResultVO product = productService.findOneByProductIdentification(productIdentification);
            if (product == null) {
                log.warn("未找到产品信息: {}", productIdentification);
                return false;
            }

            ProductModelCacheVO cacheVO = transformToModelCacheVO(tenantId, product);
            cacheProductModel(cacheVO);

            log.info("物模型缓存刷新成功: {}", productIdentification);
            return true;
        } catch (Exception e) {
            log.error("刷新物模型缓存失败: {}", productIdentification, e);
            return false;
        }
    }
}
