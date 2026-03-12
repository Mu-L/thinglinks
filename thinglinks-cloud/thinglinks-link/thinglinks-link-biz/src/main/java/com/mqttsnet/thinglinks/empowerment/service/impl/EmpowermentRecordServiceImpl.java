package com.mqttsnet.thinglinks.empowerment.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.context.ContextConstants;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.tds.constant.TdsConstants;
import com.mqttsnet.basic.tds.enumeration.TdDataTypeEnum;
import com.mqttsnet.basic.tds.model.Fields;
import com.mqttsnet.basic.tds.model.FieldsVO;
import com.mqttsnet.basic.tds.model.SuperTableDTO;
import com.mqttsnet.basic.tds.utils.TdsUtils;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.cache.link.product.ProductModelSuperTableCacheKeyBuilder;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.empowerment.entity.EmpowermentRecord;
import com.mqttsnet.thinglinks.empowerment.enumeration.EmpowermentStatusEnum;
import com.mqttsnet.thinglinks.empowerment.enumeration.EmpowermentTypeEnum;
import com.mqttsnet.thinglinks.empowerment.manager.EmpowermentRecordManager;
import com.mqttsnet.thinglinks.empowerment.service.EmpowermentRecordService;
import com.mqttsnet.thinglinks.empowerment.vo.query.EmpowermentRecordPageQuery;
import com.mqttsnet.thinglinks.empowerment.vo.result.EmpowermentRecordResultVO;
import com.mqttsnet.thinglinks.empowerment.vo.save.EmpowermentRecordSaveVO;
import com.mqttsnet.thinglinks.empowerment.vo.save.ProductEmpowermentSaveVO;
import com.mqttsnet.thinglinks.empowerment.vo.update.EmpowermentRecordUpdateVO;
import com.mqttsnet.thinglinks.product.enumeration.ProductTypeEnum;
import com.mqttsnet.thinglinks.product.service.ProductService;
import com.mqttsnet.thinglinks.product.vo.param.ProductParamVO;
import com.mqttsnet.thinglinks.productservice.vo.param.ProductServiceParamVO;
import com.mqttsnet.thinglinks.tds.facade.TdsFacade;
import com.mqttsnet.thinglinks.tds.vo.result.SuperTableDescribeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 赋能记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-15 17:20:27
 * @create [2023-09-15 17:20:27] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class EmpowermentRecordServiceImpl extends SuperServiceImpl<EmpowermentRecordManager, Long, EmpowermentRecord> implements EmpowermentRecordService {

    private final CachePlusOps cachePlusOps;

    private final ProductService productService;

    private final TdsFacade tdsApi;

    /**
     * 根据赋能标识和赋能类型查询最新赋能信息
     *
     * @param empowermentIdentification 赋能标识
     * @param empowermentType           赋能类型
     * @return {@link EmpowermentRecordResultVO} 最新赋能记录
     */
    @Override
    public EmpowermentRecordResultVO selectLatestEmpowerment(String empowermentIdentification, Integer empowermentType) {
        ArgumentAssert.notBlank(empowermentIdentification, "赋能标识不能为空");
        ArgumentAssert.notNull(empowermentType, "赋能类型不能为空");
        EmpowermentRecordPageQuery empowermentRecordPageQuery = new EmpowermentRecordPageQuery();
        empowermentRecordPageQuery.setEmpowermentIdentification(empowermentIdentification);
        empowermentRecordPageQuery.setEmpowermentType(empowermentType);
        List<EmpowermentRecord> empowermentRecordList = superManager.getEmpowermentRecordList(empowermentRecordPageQuery);

        return CollUtil.isNotEmpty(empowermentRecordList) ? BeanPlusUtil.toBeanIgnoreError(empowermentRecordList.get(0), EmpowermentRecordResultVO.class) :
                new EmpowermentRecordResultVO();
    }

    /**
     * 保存赋能记录
     *
     * @param saveVO 保存参数
     * @return {@link EmpowermentRecordSaveVO} 实体
     */
    @Override
    public EmpowermentRecordSaveVO saveEmpowermentRecord(EmpowermentRecordSaveVO saveVO) {
        log.info("saveEmpowermentRecord saveVO:{}", saveVO);

        // 校验参数
        checkedEmpowermentRecordSaveVO(saveVO);

        // 构建参数
        EmpowermentRecord empowermentRecord = buildEmpowermentRecordFromSaveVO(saveVO);

        // 保存赋能记录
        superManager.save(empowermentRecord);

        return BeanPlusUtil.toBeanIgnoreError(empowermentRecord, EmpowermentRecordSaveVO.class);
    }

    /**
     * 校验新增参数
     *
     * @param saveVO 保存参数
     */
    private void checkedEmpowermentRecordSaveVO(EmpowermentRecordSaveVO saveVO) {
        // 赋能类型和状态都是Integer，如果您有相应的Enum，可以参考之前的方法校验他们
        // 例如：ArgumentAssert.notNull(saveVO.getEmpowermentType(), "EmpowermentType Cannot be null");

        // 应用ID
        ArgumentAssert.notBlank(saveVO.getAppId(), "appId Cannot be null");

        // 赋能标识
        ArgumentAssert.notBlank(saveVO.getEmpowermentIdentification(), "empowermentIdentification Cannot be null");

        // 版本
        ArgumentAssert.notBlank(saveVO.getVersion(), "version Cannot be null");

        // 您可以继续其他字段的校验...
    }

    /**
     * 根据保存的VO构建赋能记录实体
     *
     * @param saveVO 保存参数
     * @return EmpowermentRecord 实体
     */
    private EmpowermentRecord buildEmpowermentRecordFromSaveVO(EmpowermentRecordSaveVO saveVO) {
        EmpowermentRecord empowermentRecord = new EmpowermentRecord();
        BeanUtils.copyProperties(saveVO, empowermentRecord);
        return empowermentRecord;
    }


    /**
     * 修改赋能记录
     *
     * @param updateVO 更新参数
     * @return {@link EmpowermentRecordUpdateVO} 更新结果
     */
    @Override
    public EmpowermentRecordUpdateVO updateEmpowermentRecord(EmpowermentRecordUpdateVO updateVO) {
        log.info("updateEmpowermentRecord updateVO:{}", updateVO);

        //校验参数 (您可以在此处进行参数校验, 如果有需要)
        checkedEmpowermentRecordUpdateVO(updateVO);

        //构建参数 (您可以根据需求构建更新的参数)
        EmpowermentRecord empowermentRecord = builderEmpowermentRecordUpdateVO(updateVO);

        //更新赋能记录
        superManager.updateById(empowermentRecord);

        return updateVO;
    }

    /**
     * 保存产品赋能记录
     *
     * @param saveVO 产品赋能参数
     * @return {@link EmpowermentRecordSaveVO} 产品实体
     */
    @Override
    public EmpowermentRecordSaveVO saveProductEmpowerment(ProductEmpowermentSaveVO saveVO) {
        checkedProductEmpowermentSaveVO(saveVO);

        EmpowermentRecordSaveVO record = BeanPlusUtil.copyProperties(saveVO, EmpowermentRecordSaveVO.class);
        LocalDateTime startTime = LocalDateTime.now();
        record.setStartTime(startTime);
        List<String> feedbackList = new ArrayList<>();

        String productIdentification = "";
        String productName = "";
        String productVersion = "";

        // 查询产品信息，不存在会抛出异常
        ProductParamVO productParamVO = productService.selectFullProductByProductIdentification(saveVO.getEmpowermentIdentification());
        ProductTypeEnum productTypeEnum = ProductTypeEnum.valueOf(productParamVO.getProductType());
        record.setAppId(productParamVO.getAppId());
        record.setEmpowermentIdentification(productParamVO.getProductIdentification());
        record.setVersion(productParamVO.getProductVersion());
        productIdentification = productParamVO.getProductIdentification();
        productName = productParamVO.getProductName();
        productVersion = productParamVO.getProductVersion();

        productParamVO.getServices().forEach(service -> {
            String superTableName = TdsUtils.superTableName(String.valueOf(productTypeEnum.getDesc()), productParamVO.getProductIdentification(), service.getServiceCode());
            R<List<SuperTableDescribeVO>> superTableDescribeVOListR = tdsApi.describeSuperOrSubTable(superTableName);

            List<SuperTableDescribeVO> existingFields = Optional.ofNullable(superTableDescribeVOListR.getData()).orElse(Collections.emptyList());

            if (existingFields.isEmpty()) {
                String feedback = createNewSuperTableStructure(service, superTableName);
                feedbackList.add(feedback);
            } else {
                String feedback = updateSuperTableStructure(service, superTableName, existingFields);
                feedbackList.add(feedback);
            }

            //save to redis
            CacheKey superTableCacheKey = ProductModelSuperTableCacheKeyBuilder.build(
                    productParamVO.getProductIdentification(),
                    service.getServiceCode()
            );
            Optional<List<SuperTableDescribeVO>> superTableDescribeOpt = Optional.ofNullable(
                    tdsApi.describeSuperOrSubTable(superTableName).getData()
            );

            if (superTableDescribeOpt.isPresent() && !superTableDescribeOpt.get().isEmpty()) {
                cachePlusOps.del(superTableCacheKey);
                cachePlusOps.set(superTableCacheKey, superTableDescribeOpt.get());
            }
        });

        LocalDateTime endTime = LocalDateTime.now();
        record.setEndTime(endTime);

        List<Map<String, Object>> feedbacks = feedbackList.stream()
                .map(feedback -> {
                    Map<String, Object> map = new HashMap<>();
                    // Using epoch time in milliseconds as the timestamp
                    map.put("timestamp", System.currentTimeMillis());
                    map.put("message", feedback);
                    return map;
                })
                .collect(Collectors.toList());

        ObjectMapper mapper = new ObjectMapper();

        String jsonFeedback = "";
        try {
            jsonFeedback = mapper.writeValueAsString(feedbacks);
        } catch (JsonProcessingException e) {
            log.error("Error converting feedback to JSON. Using default empty value.", e);
        }

        record.setFeedback(jsonFeedback);


        record.setStatus(EmpowermentStatusEnum.COMPLETED.getValue());

        Duration duration = Duration.between(startTime, endTime);
        record.setOutcome(String.format("Processed product with identification: %s, name: %s, version: %s. Total time taken: %s seconds.",
                productIdentification, productName, productVersion, duration.getSeconds()));

        // save
        return saveEmpowermentRecord(record);
    }


    private String createNewSuperTableStructure(ProductServiceParamVO service, String superTableName) {
        StringBuilder feedback = new StringBuilder("Creating new super table: ").append(superTableName).append(". ");

        SuperTableDTO superTableDTO = new SuperTableDTO();
        superTableDTO.setSuperTableName(superTableName);

        List<FieldsVO> schemaFields = new ArrayList<>(Arrays.asList(
                new FieldsVO(TdsConstants.TS, TdDataTypeEnum.TIMESTAMP.getDataType(), null),
                new FieldsVO(TdsConstants.EVENT_TIME, TdDataTypeEnum.TIMESTAMP.getDataType(), null)
        ));

        service.getProperties().forEach(property -> {
            String fieldName = property.getPropertyCode();
            Integer size = Optional.ofNullable(property.getMaxlength()).map(Integer::parseInt).orElse(null);
            schemaFields.add(new FieldsVO(fieldName, TdDataTypeEnum.valueOfByDataType(property.getDatatype()).getDataType(), size));
        });

        List<FieldsVO> tagsFields = Collections.singletonList(new FieldsVO(TdsConstants.DEVICE_IDENTIFICATION, TdDataTypeEnum.BINARY.getDataType(), 64));

        superTableDTO.setSchemaFields(FieldsVO.toFieldsList(schemaFields));
        superTableDTO.setTagsFields(FieldsVO.toFieldsList(tagsFields));

        log.info("catch superTableDTO:{}", JsonUtil.toJson(superTableDTO));
        R superTableAndColumn = tdsApi.createSuperTableAndColumn(superTableDTO);
        if (Boolean.FALSE.equals(superTableAndColumn.getIsSuccess())) {
            feedback.append("Creation of field(s) failed with message: ").append(superTableAndColumn.getMsg()).append(ContextConstants.SEMICOLON);
        } else {
            feedback.append("Successfully created fields: ").append(FieldsVO.toFieldsList(schemaFields).stream().map(Fields::getFieldName).collect(Collectors.joining(ContextConstants.SEPARATOR))).append(ContextConstants.SEMICOLON);
        }
        return feedback.toString();
    }

    private String updateSuperTableStructure(ProductServiceParamVO service, String superTableName, List<SuperTableDescribeVO> existingFields) {
        StringBuilder feedback = new StringBuilder("Updating super table: ").append(superTableName).append(". ");
        SuperTableDTO superTableDTO = new SuperTableDTO();
        superTableDTO.setDataBaseName("");
        superTableDTO.setSuperTableName(superTableName);

        List<String> existingFieldNames = existingFields.stream()
                .filter(describeVO -> !Objects.equals(TdsConstants.TAG, describeVO.getNote()))
                .map(SuperTableDescribeVO::getField)
                .toList();

        service.getProperties().forEach(property -> {
            String fieldName = property.getPropertyCode();
            TdDataTypeEnum tdDataTypeEnum = TdDataTypeEnum.valueOfByDataType(property.getDatatype());
            Integer size = Optional.ofNullable(property.getMaxlength())
                    .map(Integer::parseInt)
                    .orElse(null);

            Optional<SuperTableDescribeVO> matchedFieldOpt = existingFields.stream()
                    .filter(f -> f.getField() != null && f.getField().equalsIgnoreCase(fieldName))
                    .findFirst();

            boolean isTypeMatch = matchedFieldOpt.map(field -> TdDataTypeEnum.valueOfByDataType(field.getType()))
                    .map(fieldTypeEnum -> tdDataTypeEnum.isTypeEqual(fieldTypeEnum.getDataType()))
                    .orElse(false);

            boolean isSizeMatch = matchedFieldOpt.map(SuperTableDescribeVO::getLength)
                    .map(length -> Objects.equals(length, size))
                    .orElse(false);

            // Check if type or size mismatch
            if (matchedFieldOpt.isPresent() && (!isTypeMatch || !isSizeMatch)) {
                // Delete the existing field first if type or size doesn't match
                superTableDTO.setFields(FieldsVO.toFields(new FieldsVO(fieldName, null, null)));
                tdsApi.dropSuperTableColumn(superTableDTO);
            }

            // Check if field is absent or type or size mismatch
            if (matchedFieldOpt.isEmpty() || !isTypeMatch || !isSizeMatch) {
                // Add or alter field if not matched or not matching in type or size
                FieldsVO fieldsVO = new FieldsVO(fieldName, tdDataTypeEnum.getDataType(), size);
                superTableDTO.setFields(FieldsVO.toFields(fieldsVO));
                R alterSuperTableColumn = tdsApi.alterSuperTableColumn(superTableDTO);
                if (Boolean.FALSE.equals(alterSuperTableColumn.getIsSuccess())) {
                    feedback.append("Alteration of field(s) failed with message: ").append(alterSuperTableColumn.getMsg()).append(ContextConstants.SEMICOLON);
                } else {
                    feedback.append("Successfully altered fields: ").append(fieldName).append(ContextConstants.SEMICOLON);
                }
            }
        });


        // Check for fields that should be deleted
        existingFieldNames.stream()
                .filter(existingFieldName -> !Arrays.asList(TdsConstants.TS, TdsConstants.EVENT_TIME).contains(existingFieldName))
                .filter(existingFieldName -> service.getProperties().stream().noneMatch(p -> Objects.equals(p.getPropertyCode(), existingFieldName)))
                .forEach(existingFieldName -> {
                    FieldsVO fieldsVO = new FieldsVO();
                    fieldsVO.setFieldName(existingFieldName);
                    superTableDTO.setFields(FieldsVO.toFields(fieldsVO));
                    R alterSuperTableColumn = tdsApi.dropSuperTableColumn(superTableDTO);
                    if (Boolean.FALSE.equals(alterSuperTableColumn.getIsSuccess())) {
                        feedback.append("Deletion of field: ").append(existingFieldName).append(" failed with message: ").append(alterSuperTableColumn.getMsg()).append(ContextConstants.SEMICOLON);
                    } else {
                        feedback.append("Successfully deleted field: ").append(existingFieldName).append(ContextConstants.SEMICOLON);
                    }
                });

        return feedback.toString();
    }


    /**
     * 校验产品赋能参数
     *
     * @param saveVO
     */
    private void checkedProductEmpowermentSaveVO(ProductEmpowermentSaveVO saveVO) {
        ArgumentAssert.notNull(saveVO, "save product empower saveVO is null");
        ArgumentAssert.isTrue(EmpowermentTypeEnum.PRODUCT.getValue().equals(saveVO.getEmpowermentType()), "save product empower saveVO.empowermentType is null");
        ArgumentAssert.notNull(saveVO.getEmpowermentIdentification(), "empowermentIdentification is null");
    }

    private void checkedEmpowermentRecordUpdateVO(EmpowermentRecordUpdateVO updateVO) {
        //在此处进行参数校验, 比如某些字段不能为空等
        // ...
    }

    private EmpowermentRecord builderEmpowermentRecordUpdateVO(EmpowermentRecordUpdateVO updateVO) {
        //在此处转换VO到实体类
        //可以使用工具如BeanUtils进行属性拷贝或手动赋值
        EmpowermentRecord empowermentRecord = new EmpowermentRecord();
        //empowermentRecord.set... (赋值操作)
        //...

        return empowermentRecord;
    }


}


