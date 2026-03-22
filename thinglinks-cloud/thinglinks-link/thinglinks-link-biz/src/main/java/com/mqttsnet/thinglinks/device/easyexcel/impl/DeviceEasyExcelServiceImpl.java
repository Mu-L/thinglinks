package com.mqttsnet.thinglinks.device.easyexcel.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.idev.excel.annotation.ExcelProperty;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.easyexcel.ExcelCheckResult;
import com.mqttsnet.basic.easyexcel.ExcelImportErrDto;
import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.basic.utils.TenantUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.device.easyexcel.DeviceEasyExcelService;
import com.mqttsnet.thinglinks.device.easyexcel.DeviceImportData;
import com.mqttsnet.thinglinks.device.entity.Device;
import com.mqttsnet.thinglinks.device.enumeration.DeviceAuthModeEnum;
import com.mqttsnet.thinglinks.device.event.publisher.DeviceEventPublisher;
import com.mqttsnet.thinglinks.device.event.source.DeviceInfoUpdatedEventSource;
import com.mqttsnet.thinglinks.device.service.DeviceService;
import com.mqttsnet.thinglinks.device.vo.query.DevicePageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceResultVO;
import com.mqttsnet.thinglinks.product.service.ProductService;
import com.mqttsnet.thinglinks.product.vo.query.ProductPageQuery;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * -----------------------------------------------------------------------------
 * File Name: DeviceEasyExcelServiceImpl
 * -----------------------------------------------------------------------------
 * Description:
 * 设备档案导入导出业务接口实现类
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/6/20       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/6/20 18:50
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(rollbackFor = Exception.class)
public class DeviceEasyExcelServiceImpl implements DeviceEasyExcelService {

    private final DeviceService deviceService;
    private final ProductService productService;
    private final DeviceEventPublisher deviceEventPublisher;


    /**
     * Checks the imported Excel data for devices.
     *
     * @param deviceImportDataList the list of imported device data
     * @return the result of the Excel check, including any errors found
     */
    @Override
    public ExcelCheckResult<DeviceImportData> checkImportExcel(List<DeviceImportData> deviceImportDataList) {
        List<DeviceImportData> successList = new ArrayList<>();
        List<ExcelImportErrDto<DeviceImportData>> errList = new ArrayList<>();

        // Collect device and product identifiers
        List<String> deviceIdentificationCollect = deviceImportDataList.stream()
                .map(DeviceImportData::getDeviceIdentification)
                .collect(Collectors.toList());
        List<String> productIdentificationCollect = deviceImportDataList.stream()
                .map(DeviceImportData::getProductIdentification)
                .collect(Collectors.toList());

        // Retrieve existing devices and products
        Map<String, DeviceResultVO> existenceDeviceResultVOMap = getExistingDevices(deviceIdentificationCollect);
        Map<String, ProductResultVO> existenceProductResultVOMap = getExistingProducts(productIdentificationCollect);

        for (DeviceImportData item : deviceImportDataList) {
            Map<Integer, String> cellErrColMap = new HashMap<>();

            // Validate device identification
            validateDeviceIdentification(item, existenceDeviceResultVOMap, cellErrColMap);

            // Validate product identification
            validateProductIdentification(item, existenceProductResultVOMap, cellErrColMap);

            if (cellErrColMap.isEmpty()) {
                successList.add(item);
            } else {
                errList.add(new ExcelImportErrDto<>(item, cellErrColMap));
            }
        }

        // Log and save successful items
        if (CollUtil.isNotEmpty(successList)) {
            log.info("Saving {} successful items to the database", successList.size());
            saveSuccessList(successList);
        }

        return new ExcelCheckResult<>(successList, errList);
    }

    private Map<String, DeviceResultVO> getExistingDevices(List<String> deviceIdentificationCollect) {
        DevicePageQuery query = new DevicePageQuery();
        query.setDeviceIdentificationList(deviceIdentificationCollect);

        List<DeviceResultVO> deviceResultVOList = Optional.ofNullable(deviceService.getDeviceResultVOList(query))
                .orElse(Collections.emptyList());

        return deviceResultVOList.stream()
                .filter(d -> d.getDeviceIdentification() != null)
                .collect(Collectors.toMap(DeviceResultVO::getDeviceIdentification,
                        Function.identity(), (existing, replacement) -> existing));
    }

    private Map<String, ProductResultVO> getExistingProducts(List<String> productIdentificationCollect) {
        ProductPageQuery productQuery = new ProductPageQuery();
        productQuery.setProductIdentificationList(productIdentificationCollect);

        List<ProductResultVO> productResultVOList = Optional.ofNullable(productService.getProductResultVOList(productQuery))
                .orElse(Collections.emptyList());

        return productResultVOList.stream()
                .filter(p -> p.getProductIdentification() != null)
                .collect(Collectors.toMap(ProductResultVO::getProductIdentification,
                        Function.identity(), (existing, replacement) -> existing));
    }

    private void validateDeviceIdentification(DeviceImportData item, Map<String, DeviceResultVO> existenceDeviceResultVOMap, Map<Integer, String> cellErrColMap) {
        if (CollUtil.isNotEmpty(existenceDeviceResultVOMap) && existenceDeviceResultVOMap.containsKey(item.getDeviceIdentification())) {
            addValidationError(item, "deviceIdentification", "该设备标识符已存在", cellErrColMap);
        }
    }

    private void validateProductIdentification(DeviceImportData item, Map<String, ProductResultVO> existenceProductResultVOMap, Map<Integer, String> cellErrColMap) {
        if (CollUtil.isNotEmpty(existenceProductResultVOMap) && !existenceProductResultVOMap.containsKey(item.getProductIdentification())) {
            addValidationError(item, "productIdentification", "该产品标识符不存在", cellErrColMap);
        }
    }

    private void addValidationError(DeviceImportData item, String fieldName, String errorMessage, Map<Integer, String> cellErrColMap) {
        try {
            Field field = DeviceImportData.class.getDeclaredField(fieldName);
            ExcelProperty annotation = field.getAnnotation(ExcelProperty.class);
            int index = annotation.index();
            cellErrColMap.put(index, errorMessage);
        } catch (NoSuchFieldException e) {
            log.error("NoSuchFieldException during validation for field: {}", fieldName, e);
        }
    }

    private void saveSuccessList(List<DeviceImportData> successList) {
        if (CollUtil.isEmpty(successList)) {
            return;
        }
        List<Device> devicesToSave = successList.stream()
                .map(this::convertToDeviceEntity)
                .collect(Collectors.toList());
        log.info("Importing device files ,Saving {} count devices to the database", devicesToSave.size());
        boolean saveFlag = deviceService.saveBatch(devicesToSave);

        if (saveFlag) {
            log.info("Saving {} count devices to the database success", devicesToSave.size());
            deviceEventPublisher.publishDeviceInfoUpdatedEvent(DeviceInfoUpdatedEventSource.builder()
                    .deviceIdentificationList(devicesToSave.stream().map(Device::getDeviceIdentification).distinct().collect(Collectors.toList()))
                    .build());
        } else {
            log.error("Saving {} count devices to the database failed", devicesToSave.size());
        }

    }

    private Device convertToDeviceEntity(DeviceImportData data) {
        Device device = new Device();
        device.setAppId(data.getAppId());
        device.setDeviceIdentification(StrUtil.isNotBlank(data.getDeviceIdentification()) ? data.getDeviceIdentification() : SnowflakeIdUtil.nextId());
        device.setProductIdentification(data.getProductIdentification());
        device.setDeviceName(StrUtil.isNotBlank(data.getDeviceName()) ? data.getDeviceName() : RandomUtil.randomString(16));
        device.setConnector(data.getConnector());
        device.setUserName(StrUtil.isNotBlank(data.getUserName()) ? data.getUserName() : RandomUtil.randomString(32));
        String password = StrUtil.isNotBlank(data.getPassword()) ? data.getPassword() : RandomUtil.randomString(32);
        device.setPassword(password);
        device.setClientId(TenantUtil.buildOptionalItem(StrUtil.isNotBlank(data.getClientId()) ? data.getClientId() : SnowflakeIdUtil.nextId(), ContextUtil.getTenantIdStr()));
        device.setAuthMode(DeviceAuthModeEnum.ACCOUNT_MODE.getValue());
        device.setEncryptMethod(Integer.valueOf(data.getEncryptMethod()));
        device.setEncryptKey(data.getEncryptKey());
        device.setEncryptVector(data.getEncryptVector());
        device.setSignKey(data.getSignKey());
        device.setDeviceTags(data.getDeviceTags());
        device.setSwVersion(data.getSwVersion());
        device.setFwVersion(data.getFwVersion());
        device.setDeviceSdkVersion(data.getDeviceSdkVersion());
        device.setDeviceStatus(Integer.valueOf(data.getDeviceStatus()));
        device.setNodeType(Integer.valueOf(data.getNodeType()));
        // 设置其他需要的字段
        return device;
    }


}
