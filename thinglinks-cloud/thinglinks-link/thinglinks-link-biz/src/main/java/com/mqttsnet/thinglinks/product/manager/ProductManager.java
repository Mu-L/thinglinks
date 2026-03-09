package com.mqttsnet.thinglinks.product.manager;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.thinglinks.product.entity.Product;
import com.mqttsnet.thinglinks.product.vo.query.ProductPageQuery;

/**
 * <p>
 * 通用业务接口
 * 产品模型
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
public interface ProductManager extends SuperManager<Product> {

    /**
     * 分页查询产品信息
     *
     * @param params 分页参数
     * @return {@link IPage<Product>} 分页数据
     */
    IPage<Product> getPage(PageParams<ProductPageQuery> params);

    /**
     * 根据产品模型ID查询信息
     *
     * @param productId
     * @return
     */
    Product findOneByProductId(Long productId);

    /**
     * 根据条件查询产品模型信息
     *
     * @param query 查询条件
     * @return Product 产品模型信息
     */
    List<Product> getProductList(ProductPageQuery query);

    /**
     * 根据产品标识查询信息
     *
     * @param productIdentification 产品标识
     * @return {@link List<Product>} 产品模型信息
     */
    Product findOneByProductIdentification(String productIdentification);

    /**
     * 根据产品标识集合查询信息
     *
     * @param productIdentificationList 产品标识集合
     * @return {@link List<Product>} 产品模型信息
     */
    List<Product> findListByProductIdentificationList(List<String> productIdentificationList);

    /**
     * 查询产品模型是否存在
     *
     * @param manufacturerId 厂商ID
     * @param model          产品型号
     * @param deviceType     设备类型
     * @return {@link Product} 产品模型信息
     */
    Product findOneByManufacturerIdAndModelAndDeviceType(String manufacturerId, String model, String deviceType);

    /**
     * 获取产品模型总量
     *
     * @return {@link Long} 产品模型数据总量
     */
    Long findProductTotal();
}


