package com.mqttsnet.thinglinks.product.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.device.vo.result.ProductOverviewResultVO;
import com.mqttsnet.thinglinks.product.entity.Product;
import com.mqttsnet.thinglinks.product.vo.param.ProductParamVO;
import com.mqttsnet.thinglinks.product.vo.query.ProductPageQuery;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.product.vo.save.ProductSaveVO;
import com.mqttsnet.thinglinks.product.vo.update.ProductUpdateVO;
import org.springframework.web.multipart.MultipartFile;


/**
 * <p>
 * 业务接口
 * 产品模型
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 * @create [2023-03-14 19:39:59] [mqttsnet]
 */
public interface ProductService extends SuperService<Long, Product> {

    /**
     * 分页查询产品信息
     *
     * @param params 分页参数
     * @return {@link IPage<ProductResultVO>} 分页数据
     */
    IPage<ProductResultVO> getPage(PageParams<ProductPageQuery> params);


    /**
     * 获取产品模型总量
     *
     * @return {@link Long} 产品模型数据总量
     */
    Long findProductTotal();

    /**
     * 保存产品模型
     *
     * @param saveVO
     * @return
     */
    ProductSaveVO saveProduct(ProductSaveVO saveVO);

    /**
     * 修改产品模型
     *
     * @param updateVO
     * @return
     */
    ProductUpdateVO updateProduct(ProductUpdateVO updateVO);

    /**
     * 删除产品模型
     *
     * @param id
     * @return
     */
    Boolean deleteProduct(Long id);

    /**
     * 查询产品管理完整信息（包含服务、属性、命令）
     *
     * @param productIdentification 产品标识
     * @return {@link ProductParamVO} 产品管理完整参数VO
     * @throws com.mqttsnet.basic.exception.BizException 如果产品不存在
     */
    ProductParamVO selectFullProductByProductIdentification(String productIdentification);

    /**
     * 产品模型导入
     *
     * @param file 产品模型JSON文件
     */
    /**
     * 导入产品模型JSON数据
     *
     * @param file  文件
     * @param appId 应用ID
     */
    void importProductJson(MultipartFile file, String appId);

    /**
     * 根据产品ID查询 产品详情
     *
     * @param productId 产品ID
     * @return {@link ProductResultVO} 产品详情
     */
    ProductResultVO findOneByProductId(Long productId);

    /**
     * 根据产品标识查询产品详情
     *
     * @param productIdentification 产品标识
     * @return {@link ProductResultVO} 产品详情
     */
    ProductResultVO findOneByProductIdentification(String productIdentification);

    /**
     * 根据产品标识查集合询产品详情
     *
     * @param productIdentificationList 产品标识集合
     * @return {@link ProductResultVO} 产品详情
     */
    List<ProductResultVO> findListByProductIdentificationList(List<String> productIdentificationList);

    /**
     * 快捷生成产品模型
     *
     * @param paramVO 产品模型参数
     */
    void generateProductJson(ProductParamVO paramVO);

    /**
     * 获取产品概况统计
     *
     * @return {@link ProductOverviewResultVO} 产品概况统计
     */
    ProductOverviewResultVO getProductOverview();

    /**
     * 初始化产品基础Topic
     *
     * @param productIdentification 产品标识
     * @param reInit                是否重新初始化
     * @return {@link Boolean} 初始化结果
     */
    Boolean initProductBaseTopics(String productIdentification, Boolean reInit);

    /**
     * 获取产品模型列表
     *
     * @param query 查询条件
     * @return {@link List<ProductResultVO>} 产品模型列表
     */
    List<ProductResultVO> getProductResultVOList(ProductPageQuery query);
}