package com.mqttsnet.thinglinks.empowerment.controller;

import com.mqttsnet.basic.annotation.log.WebLog;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.datascope.DataScopeHelper;
import com.mqttsnet.thinglinks.empowerment.entity.EmpowermentRecord;
import com.mqttsnet.thinglinks.empowerment.service.EmpowermentRecordService;
import com.mqttsnet.thinglinks.empowerment.vo.query.EmpowermentRecordPageQuery;
import com.mqttsnet.thinglinks.empowerment.vo.result.EmpowermentRecordResultVO;
import com.mqttsnet.thinglinks.empowerment.vo.save.EmpowermentRecordSaveVO;
import com.mqttsnet.thinglinks.empowerment.vo.save.ProductEmpowermentSaveVO;
import com.mqttsnet.thinglinks.empowerment.vo.update.EmpowermentRecordUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 赋能记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-15 17:20:27
 * @create [2023-09-15 17:20:27] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/empowermentRecord")
@Tag(name = "赋能记录表")
public class EmpowermentRecordController extends SuperController<EmpowermentRecordService, Long, EmpowermentRecord, EmpowermentRecordSaveVO,
        EmpowermentRecordUpdateVO, EmpowermentRecordPageQuery, EmpowermentRecordResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    @Override
    public QueryWrap<EmpowermentRecord> handlerWrapper(EmpowermentRecord model, PageParams<EmpowermentRecordPageQuery> params) {
        QueryWrap<EmpowermentRecord> queryWrap = super.handlerWrapper(model, params);
        // 开启数据权限
        DataScopeHelper.startDataScope("empowerment_record");
        return queryWrap;
    }


    /**
     * 根据赋能标识和赋能类型查询最新赋能信息
     *
     * @param empowermentIdentification 赋能标识
     * @param empowermentType           赋能类型
     * @return {@link EmpowermentRecordResultVO} 最新的赋能记录
     */
    @Operation(summary = "根据赋能标识和赋能类型查询最新赋能信息", description = "此接口需要赋能标识和赋能类型两个参数，并会返回符合条件的最新赋能记录")
    @Parameters({
            @Parameter(name = "empowermentIdentification", description = "赋能标识", required = true),
            @Parameter(name = "empowermentType", description = "赋能类型", required = true)
    })
    @GetMapping(value = "/getLatestEmpowermentInfo/{empowermentIdentification}/{empowermentType}")
    public R getLatestEmpowermentInfoByType(@PathVariable("empowermentIdentification") @NotBlank String empowermentIdentification, @PathVariable("empowermentType") @NotNull Integer empowermentType) {

        EmpowermentRecordResultVO latestEmpowermentRecord = superService.selectLatestEmpowerment(empowermentIdentification, empowermentType);
        return R.success(latestEmpowermentRecord);
    }

    /**
     * 新增 赋能记录信息
     *
     * @param saveVO 保存参数
     * @return 实体
     */
    @Operation(summary = "保存赋能记录", description = "保存赋能记录")
    @PostMapping("/saveEmpowermentRecord")
    @WebLog(value = "保存赋能记录", request = false)
    public R<EmpowermentRecordSaveVO> saveEmpowermentRecord(@RequestBody EmpowermentRecordSaveVO saveVO) {
        return R.success(superService.saveEmpowermentRecord(saveVO));
    }


    /**
     * 修改 赋能记录信息
     *
     * @param updateVO 更新参数
     * @return 实体
     */
    @Operation(summary = "修改赋能记录", description = "修改赋能记录")
    @PutMapping("/updateEmpowermentRecord")
    @WebLog(value = "修改赋能记录", request = false)
    public R<EmpowermentRecordUpdateVO> updateEmpowermentRecord(@RequestBody EmpowermentRecordUpdateVO updateVO) {
        return R.success(superService.updateEmpowermentRecord(updateVO));
    }


    /**
     * 新增 产品赋能信息
     *
     * @param saveVO 保存参数
     * @return 实体
     */
    @Operation(summary = "保存产品赋能", description = "保存产品赋能信息")
    @PostMapping("/saveProductEmpowerment")
    @WebLog(value = "保存产品赋能", request = false)
    public R<EmpowermentRecordSaveVO> saveProductEmpowerment(@RequestBody ProductEmpowermentSaveVO saveVO) {
        return R.success(superService.saveProductEmpowerment(saveVO));
    }


}


