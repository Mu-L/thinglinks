package com.mqttsnet.thinglinks.oauth.controller;


import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.log.OptLogDTO;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.base.service.system.BaseOperationLogService;
import com.mqttsnet.thinglinks.base.vo.save.system.BaseOperationLogSaveVO;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 系统日志
 * </p>
 *
 * @author mqttsnet
 * @date 2019-07-22
 */
@Slf4j
@Validated
@RestController
@RequestMapping
@Tag(name = "系统访问日志")
@AllArgsConstructor
@Hidden
public class WebLogController {

    private final BaseOperationLogService baseOperationLogService;

    /**
     * 保存系统日志
     *
     * @param data 保存对象
     * @return 保存结果
     */
    @PostMapping("/optLog")
    @Operation(summary = "保存系统日志", description = "保存系统日志不为空的字段")
    public R<Boolean> save(@RequestBody OptLogDTO data) {
        try {
            if (!ContextUtil.isEmptyTenantId()) {
                BaseOperationLogSaveVO logSaveVO = null;
                try {
                    logSaveVO = BeanPlusUtil.toBeanIgnoreError(data, BaseOperationLogSaveVO.class);
                } catch (Exception e) {
                    log.error("转换日志数据时发生错误，日志数据：{}", data, e);
                }

                if (logSaveVO != null) {
                    baseOperationLogService.save(logSaveVO);
                }
            }
            return R.success(true);
        } catch (Exception e) {
            log.error("保存系统日志时发生错误，日志数据：{}", data, e);
            // 即使发生错误，也返回成功，避免影响业务
            return R.success(true);
        }
    }

}
