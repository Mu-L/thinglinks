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
import com.mqttsnet.thinglinks.cache.vo.product.ProductCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.product.ProductCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.product.service.ProductService;
import com.mqttsnet.thinglinks.product.vo.query.ProductPageQuery;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * -----------------------------------------------------------------------------
 * File Name: ProductCacheService.java
 * -----------------------------------------------------------------------------
 * Description:
 * Service layer for Product cache management.
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
public class ProductCacheService extends CacheSuperAbstract {
    private final CachePlusOps cachePlusOps;
    private final ProductService productService;

    private static final String BATCH_LOG_FORMAT =
            "[产品缓存-开始] 租户ID={} | 操作类型={} | 总产品数={} | 分页大小={} | 预计批次={}";

    private static final String BATCH_ITEM_LOG =
            "[产品缓存-进度] 租户ID={} | 当前批次={}/{} | 本页产品={} | 成功累计={} | 失败累计={} | 耗时={}ms";

    private static final String BATCH_SUMMARY =
            "[产品缓存-完成] 租户ID={} | 总耗时={}ms | 成功总数={} | 失败总数={} | 平均耗时={}ms/产品";

    private static final String PRODUCT_DETAIL_LOG =
            "[产品缓存-详情] 租户ID={} | 产品标识={} | 状态={} | 耗时={}ms | 错误={}";

    /**
     * 刷新指定租户的产品缓存（全量）
     *
     * @param tenantId 租户ID，不能为null
     * @throws IllegalArgumentException 如果tenantId为null
     * @throws RuntimeException         当缓存刷新过程中出现异常时抛出
     * @see #processProductsBatch(Long, List, AtomicInteger, AtomicInteger)
     */
    public void refreshProductCacheForTenant(Long tenantId) {
        long startTime = System.currentTimeMillis();
        AtomicInteger totalSuccess = new AtomicInteger();
        AtomicInteger totalFail = new AtomicInteger();

        int totalProducts = productService.findProductTotal().intValue();
        int totalPages = (int) Math.ceil((double) totalProducts / PAGE_SIZE);

        log.info(BATCH_LOG_FORMAT, tenantId, "产品缓存全量刷新", totalProducts, PAGE_SIZE, totalPages);

        // 按页处理
        IntStream.rangeClosed(1, totalPages)
                .sequential()
                .forEach(currentPage -> {
                    long pageStartTime = System.currentTimeMillis();
                    // 查询当前页产品
                    List<ProductResultVO> products = fetchProductPage(currentPage);
                    // 处理当前页产品
                    processProductsBatch(tenantId, products, totalSuccess, totalFail);
                    log.info(BATCH_ITEM_LOG, tenantId, currentPage, totalPages, products.size(), totalSuccess.get(), totalFail.get(), System.currentTimeMillis() - pageStartTime);
                });

        // 批次总结
        long totalCost = System.currentTimeMillis() - startTime;
        log.info(BATCH_SUMMARY, tenantId, totalCost, totalSuccess.get(), totalFail.get(), totalProducts > 0 ? totalCost / totalProducts : 0);
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
     * 处理产品批次数据
     *
     * @param tenantId     租户ID，不能为null
     * @param products     当前批次的产品列表，不能为null
     * @param totalSuccess 成功计数器(累计)，不能为null
     * @param totalFail    失败计数器(累计)，不能为null
     * @throws IllegalArgumentException 如果任何参数为null
     * @see #transformToProductCacheVO(Long, ProductResultVO)
     * @see #cacheProduct(ProductCacheVO)
     */
    private void processProductsBatch(Long tenantId,
                                      List<ProductResultVO> products,
                                      AtomicInteger totalSuccess,
                                      AtomicInteger totalFail) {
        AtomicInteger pageSuccess = new AtomicInteger();
        AtomicInteger pageFail = new AtomicInteger();

        products.forEach(product -> {
            long productStart = System.currentTimeMillis();
            try {
                ProductCacheVO cacheVO = transformToProductCacheVO(tenantId, product);
                cacheProduct(cacheVO);
                pageSuccess.incrementAndGet();
            } catch (Exception e) {
                pageFail.incrementAndGet();
                log.error(PRODUCT_DETAIL_LOG, tenantId, product.getProductIdentification(), "失败", System.currentTimeMillis() - productStart, e.getClass().getSimpleName() + ":" + e.getMessage(), e);
            }
        });

        totalSuccess.addAndGet(pageSuccess.get());
        totalFail.addAndGet(pageFail.get());
    }

    /**
     * 转换产品结果为缓存对象
     *
     * @param tenantId 租户ID，不能为null
     * @param product  产品结果VO，不能为null
     * @return 产品缓存VO，不会返回null
     * @throws IllegalArgumentException 如果参数为null
     * @throws RuntimeException         当转换失败时抛出
     * @see BeanPlusUtil#toBeanIgnoreError(Object, Class)
     */
    private ProductCacheVO transformToProductCacheVO(Long tenantId, ProductResultVO product) {
        ProductCacheVO cacheVO = BeanPlusUtil.toBeanIgnoreError(product, ProductCacheVO.class);
        cacheVO.setTenantId(tenantId);
        return cacheVO;
    }

    /**
     * 缓存单个产品
     *
     * @param cacheVO 产品缓存VO，不能为null
     * @throws IllegalArgumentException 如果cacheVO为null
     * @see ProductCacheKeyBuilder#build(String)
     */
    private void cacheProduct(ProductCacheVO cacheVO) {
        long start = System.currentTimeMillis();
        try {
            CacheKey cacheKey = ProductCacheKeyBuilder.build(cacheVO.getProductIdentification());
            cachePlusOps.del(cacheKey);
            cachePlusOps.set(cacheKey, cacheVO);
        } catch (Exception e) {
            log.error(PRODUCT_DETAIL_LOG, cacheVO.getTenantId(), cacheVO.getProductIdentification(), "失败", System.currentTimeMillis() - start, e.getMessage());
        }
    }

    /**
     * 刷新单个产品的缓存
     *
     * @param productIdentification 产品标识，不能为空
     * @return 刷新是否成功
     * @throws IllegalArgumentException 如果productIdentification为空
     * @see ProductService#findOneByProductIdentification(String)
     * @see #transformToProductCacheVO(Long, ProductResultVO)
     * @see #cacheProduct(ProductCacheVO)
     */
    public boolean refreshProductCache(String productIdentification) {
        ArgumentAssert.notBlank(productIdentification, "productIdentification is null");
        try {
            Long tenantId = ContextUtil.getTenantId();
            log.info("开始刷新{}产品缓存: {}", tenantId, productIdentification);
            ProductResultVO product = productService.findOneByProductIdentification(productIdentification);
            if (product == null) {
                log.warn("未找到产品信息: {}", productIdentification);
                return false;
            }
            ProductCacheVO cacheVO = transformToProductCacheVO(tenantId, product);
            cacheProduct(cacheVO);
            log.info("产品缓存刷新成功: {}", productIdentification);
            return true;
        } catch (Exception e) {
            log.error("刷新产品缓存失败: {}", productIdentification, e);
            return false;
        }
    }

}

