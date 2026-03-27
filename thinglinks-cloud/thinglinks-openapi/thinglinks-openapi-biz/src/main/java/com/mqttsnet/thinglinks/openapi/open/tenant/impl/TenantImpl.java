package com.mqttsnet.thinglinks.openapi.open.tenant.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotNull;

import com.gitee.sop.support.context.OpenContext;
import com.gitee.sop.support.dto.CommonFileData;
import com.gitee.sop.support.dto.FileData;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.thinglinks.base.entity.user.BaseEmployee;
import com.mqttsnet.thinglinks.base.service.user.BaseEmployeeService;
import com.mqttsnet.thinglinks.openapi.open.AbstractBaseApiImpl;
import com.mqttsnet.thinglinks.openapi.open.payment.req.ProductSaveRequest;
import com.mqttsnet.thinglinks.openapi.open.payment.resp.ProductResponse;
import com.mqttsnet.thinglinks.openapi.open.tenant.TenantApi;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Size;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.dubbo.config.annotation.DubboService;


/**
 * 开放接口实现
 *
 * @author 六如
 */
@DubboService
@Slf4j
public class TenantImpl extends AbstractBaseApiImpl implements TenantApi {

    @Resource
    private BaseEmployeeService baseEmployeeService;

    @Override
    public BaseEmployee getEmployeeById(@NotNull(message = "id必填") Long id,
                                        @Size(max = 20, min = 5) String name,
                                        OpenContext context) {
        log.info("appId={}, tenantId={}, traceid={} ", context.getAppId(), context.getTenantId(), context.getTraceId());
        ArgumentAssert.notNull(context.getTenantId(), "请传递租户ID");


        ContextUtil.setTenantId(context.getTenantId());
        BaseEmployee baseEmployee = baseEmployeeService.getById(id);
        this.sendNotifyTask(context, baseEmployee, "查询用户回调");
        log.info("baseEmployee={}", baseEmployee);
        return baseEmployee;
    }


    @Override
    public ProductResponse upload(ProductSaveRequest storySaveDTO, FileData file) {
        log.info("getName:{}", file.getName());
        log.info("getOriginalFilename:{}", file.getOriginalFilename());
        checkFile(List.of(file));

        ProductResponse storyResponse = new ProductResponse();
        storyResponse.setId(1);
        storyResponse.setName(file.getOriginalFilename());
        return storyResponse;
    }


    @Override
    public ProductResponse upload2(ProductSaveRequest storySaveDTO, FileData idCardFront, FileData idCardBack) {
        log.info("upload:{}", storySaveDTO);
        checkFile(List.of(idCardFront, idCardBack));

        ProductResponse storyResponse = new ProductResponse();
        storyResponse.setId(1);
        storyResponse.setName(storySaveDTO.getProductName());
        return storyResponse;
    }

    @Override
    public ProductResponse upload3(ProductSaveRequest storySaveDTO, List<FileData> files) {
        List<String> list = new ArrayList<>();
        list.add("upload:" + storySaveDTO);
        checkFile(files);

        ProductResponse storyResponse = new ProductResponse();
        storyResponse.setId(1);
        storyResponse.setName(storySaveDTO.getProductName());
        return storyResponse;
    }

    @Override
    @SneakyThrows
    public FileData download(Integer id) {
        CommonFileData fileData = new CommonFileData();
        String str = "abc,你好~!@#\n";

        fileData.setOriginalFilename("smart-doc.json");
        fileData.setData(IOUtils.toByteArray(IOUtils.toInputStream(str, StandardCharsets.UTF_8)));


        return fileData;
    }

    private void checkFile(List<FileData> fileDataList) {
        for (FileData file : fileDataList) {
            ArgumentAssert.notNull(file.getName());
            ArgumentAssert.notNull(file.getOriginalFilename());
            ArgumentAssert.notNull(file.getBytes());
            ArgumentAssert.isTrue(!file.isEmpty());
        }
    }

}
