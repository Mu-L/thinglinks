package com.mqttsnet.thinglinks.device.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.hutool.core.util.StrUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.tds.constant.TdsConstants;
import com.mqttsnet.basic.tds.utils.TdsUtils;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.DateUtils;
import com.mqttsnet.thinglinks.cache.helper.LinkCacheDataHelper;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.cache.vo.product.ProductCacheVO;
import com.mqttsnet.thinglinks.cache.vo.product.ProductModelCacheVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.device.service.DeviceShadowService;
import com.mqttsnet.thinglinks.device.vo.query.DeviceShadowPageQuery;
import com.mqttsnet.thinglinks.product.enumeration.ProductTypeEnum;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import com.mqttsnet.thinglinks.productproperty.vo.result.ProductPropertyResultVO;
import com.mqttsnet.thinglinks.productservice.vo.param.ProductServiceParamVO;
import com.mqttsnet.thinglinks.productservice.vo.result.ProductServiceResultVO;
import com.mqttsnet.thinglinks.tds.facade.TdsFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * -----------------------------------------------------------------------------
 * 文件名称: DeviceShadowServiceImpl.java
 * -----------------------------------------------------------------------------
 * 描述:
 * 设备影子业务层接口实现
 * -----------------------------------------------------------------------------
 *
 * @author ShiHuan Sun
 * @version 1.0
 * -----------------------------------------------------------------------------
 * 修改历史:
 * 日期           作者          版本        描述
 * --------      --------     -------   --------------------
 * <p>
 * -----------------------------------------------------------------------------
 * @email 13733918655@163.com
 * @date 2023-10-12 19:49
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceShadowServiceImpl implements DeviceShadowService {

    private final LinkCacheDataHelper linkCacheDataHelper;

    private final TdsFacade tdsApi;


    /**
     * 查询设备影子信息
     *
     * @param deviceShadowPageQuery 查询参数
     * @return {@link ProductResultVO} 设备影子信息
     */
    @Override
    public ProductResultVO queryDeviceShadow(DeviceShadowPageQuery deviceShadowPageQuery) {
        return linkCacheDataHelper.getDeviceCacheVO(deviceShadowPageQuery.getDeviceIdentification())
                .map(DeviceCacheVO::getProductCacheVO)
                .flatMap(productCacheVO -> buildProductResultVO(deviceShadowPageQuery, productCacheVO))
                .orElseGet(ProductResultVO::new);
    }

    private Optional<ProductResultVO> buildProductResultVO(DeviceShadowPageQuery deviceShadowPageQuery, ProductCacheVO productCacheVO) {
        return Optional.ofNullable(linkCacheDataHelper.getProductModelCacheVO(productCacheVO.getProductIdentification()))
                .map(productModelCacheVO -> {
                    List<ProductServiceResultVO> serviceResultVOs = buildServiceResults(deviceShadowPageQuery, productCacheVO, productModelCacheVO);
                    ProductResultVO result = BeanPlusUtil.toBeanIgnoreError(productModelCacheVO, ProductResultVO.class);
                    result.setServices(serviceResultVOs);
                    return result;
                });
    }

    private List<ProductServiceResultVO> buildServiceResults(DeviceShadowPageQuery deviceShadowPageQuery, ProductCacheVO productCacheVO, ProductModelCacheVO productModelCacheVO) {
        List<ProductServiceParamVO> services = Optional.ofNullable(productModelCacheVO)
                .map(ProductModelCacheVO::getServices)
                .orElseGet(Collections::emptyList);
        if (services.isEmpty()) {
            return Collections.emptyList();
        }
        // 判断是否提供了 serviceCode 参数
        if (StrUtil.isNotBlank(deviceShadowPageQuery.getServiceCode())) {
            // 如果指定了 serviceCode，则只查询指定服务的结果
            return services.stream()
                    .filter(Objects::nonNull)
                    .filter(service -> service.getServiceCode().equals(deviceShadowPageQuery.getServiceCode()))
                    .map(service -> buildServiceResultVO(deviceShadowPageQuery, productCacheVO, BeanPlusUtil.toBeanIgnoreError(service, ProductServiceResultVO.class)))
                    .collect(Collectors.toList());
        } else {
            // 如果没有指定 serviceCode，则查询所有服务
            return services.stream()
                    .filter(Objects::nonNull)
                    .map(service -> buildServiceResultVO(deviceShadowPageQuery, productCacheVO, BeanPlusUtil.toBeanIgnoreError(service, ProductServiceResultVO.class)))
                    .collect(Collectors.toList());
        }
    }

    private ProductServiceResultVO buildServiceResultVO(DeviceShadowPageQuery deviceShadowPageQuery, ProductCacheVO productCacheVO, ProductServiceResultVO service) {
        String stableName = TdsUtils.superTableName(ProductTypeEnum.valueOf(productCacheVO.getProductType()).getDesc(), productCacheVO.getProductIdentification(), service.getServiceCode());
        List<Map<String, Object>> dataList = fetchLastData(TdsUtils.subTableName(stableName, deviceShadowPageQuery.getDeviceIdentification()), deviceShadowPageQuery);

        ProductServiceResultVO serviceResultVO = BeanPlusUtil.toBeanIgnoreError(service, ProductServiceResultVO.class);
        serviceResultVO.setProperties(buildPropertyResults(service, dataList));
        serviceResultVO.setEchoList(dataList);
        return serviceResultVO;
    }

    private List<Map<String, Object>> fetchLastData(String subTableName, DeviceShadowPageQuery deviceShadowPageQuery) {
        return Optional.ofNullable(tdsApi.getDataInRangeOrLastRecord(subTableName, deviceShadowPageQuery.getStartTime(), deviceShadowPageQuery.getEndTime()))
                .filter(R::getIsSuccess)
                .map(R::getData)
                .orElse(Collections.emptyList());
    }

    private List<ProductPropertyResultVO> buildPropertyResults(ProductServiceResultVO productServiceResultVO, List<Map<String, Object>> dataList) {
        List<Map<String, Object>> firstDataList = Optional.ofNullable(dataList)
                .flatMap(list -> list.stream().findFirst())
                .map(Collections::singletonList)
                .orElse(Collections.singletonList(Collections.emptyMap()));

        return productServiceResultVO.getProperties().stream()
                .flatMap(property -> firstDataList.stream()
                        .map(data -> {
                            ProductPropertyResultVO resultVO = buildPropertyResultVO(property, data);
                            resultVO.setEchoMap(data);
                            return resultVO;
                        }))
                .collect(Collectors.toList());
    }

    private ProductPropertyResultVO buildPropertyResultVO(ProductPropertyResultVO propertyResultVO, Map<String, Object> data) {
        Optional.ofNullable(data.get(TdsConstants.TS))
                .map(ts -> DateUtils.date2LocalDateTime(DateUtils.parseDatetime(ts.toString())))
                .ifPresent(propertyResultVO::setCreatedTime);
        propertyResultVO.setPropertyValue(data.get(propertyResultVO.getPropertyCode()));
        return propertyResultVO;
    }


}
