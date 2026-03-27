package com.mqttsnet.thinglinks.groovy.controller.script;

import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.annotation.log.WebLog;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.thinglinks.datascope.DataScopeHelper;
import com.mqttsnet.thinglinks.entity.script.RuleGroovyScript;
import com.mqttsnet.thinglinks.service.script.RuleGroovyScriptService;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptDirectCompileParam;
import com.mqttsnet.thinglinks.vo.query.script.RuleGroovyScriptPageQuery;
import com.mqttsnet.thinglinks.vo.result.script.GroovyScriptEngineExecutorResultVO;
import com.mqttsnet.thinglinks.vo.result.script.RuleGroovyScriptResultVO;
import com.mqttsnet.thinglinks.vo.save.script.RuleGroovyScriptSaveVO;
import com.mqttsnet.thinglinks.vo.update.script.RuleGroovyScriptUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 规则脚本表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-03-24 09:54:10
 * @create [2025-03-24 09:54:10] [mqttsnet] [代码生成器生成]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/groovyScript")
@Tag(name = "规则脚本")
public class RuleGroovyScriptController extends SuperController<RuleGroovyScriptService, Long, RuleGroovyScript
        , RuleGroovyScriptSaveVO, RuleGroovyScriptUpdateVO, RuleGroovyScriptPageQuery, RuleGroovyScriptResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    @Override
    public QueryWrap<RuleGroovyScript> handlerWrapper(RuleGroovyScript model, PageParams<RuleGroovyScriptPageQuery> params) {
        QueryWrap<RuleGroovyScript> queryWrap = super.handlerWrapper(model, params);
        // 开启数据权限
        DataScopeHelper.startDataScope("rule_groovy_script");
        return queryWrap;
    }


    /**
     * 新增规则脚本
     *
     * @param saveVO 保存参数
     * @return 实体
     */
    @Operation(summary = "保存规则脚本", description = "创建新的规则脚本")
    @PostMapping("/saveGroovyScript")
    @WebLog(value = "保存规则脚本", request = false)
    public R<RuleGroovyScriptSaveVO> saveGroovyScript(@RequestBody @Validated RuleGroovyScriptSaveVO saveVO) {
        try {
            return R.success(superService.saveGroovyScript(saveVO));
        } catch (BizException be) {
            return R.fail(be);
        } catch (Exception e) {
            log.error("规则脚本保存失败，系统异常: {}", e.getMessage(), e);
            return R.fail();
        }
    }

    /**
     * 修改规则脚本
     *
     * @param updateVO 更新参数
     * @return 更新结果
     */
    @Operation(summary = "修改规则脚本", description = "更新现有规则脚本")
    @PutMapping("/updateGroovyScript")
    @WebLog(value = "修改规则脚本", request = false)
    public R<RuleGroovyScriptUpdateVO> updateGroovyScript(@RequestBody @Validated RuleGroovyScriptUpdateVO updateVO) {
        try {
            return R.success(superService.updateGroovyScript(updateVO));
        } catch (BizException be) {
            return R.fail(be);
        } catch (Exception e) {
            log.error("规则脚本修改失败，系统异常: {}", e.getMessage(), e);
            return R.fail();
        }
    }


    /**
     * 删除规则脚本
     *
     * @param id 规则脚本ID
     * @return 删除结果
     */
    @Operation(summary = "删除规则脚本", description = "根据规则脚本ID删除规则脚本")
    @DeleteMapping("/deleteGroovyScript/{id}")
    @WebLog(value = "删除规则脚本", request = false)
    @Parameters({@Parameter(name = "id", description = "规则脚本ID", required = true),})
    public R<Boolean> deleteGroovyScript(@PathVariable("id") Long id) {
        log.info("deleteGroovyScript id:{}", id);
        try {
            return R.success(superService.deleteGroovyScript(id));
        } catch (BizException be) {
            return R.fail(be);
        } catch (Exception e) {
            log.error("删除规则脚本失败，系统异常: {}", e.getMessage(), e);
            return R.fail();
        }
    }

    /**
     * 直接编译执行脚本测试
     * <p>实时执行以下流程：</p>
     * <ol>
     *   <li>动态编译传入的脚本内容为Class</li>
     *   <li>绑定执行参数到脚本上下文</li>
     *   <li>执行脚本逻辑并返回结果</li>
     * </ol>
     *
     * @param param 包含脚本内容与执行参数的请求体
     * @return {@link R< GroovyScriptEngineExecutorResultVO >} 标准化执行结果包装
     */
    @Operation(summary = "直接编译执行测试", description = "实时编译脚本内容并执行，适用于动态脚本调试场景")
    @PostMapping("/runDirectCompile")
    public R<GroovyScriptEngineExecutorResultVO> runDirectCompile(@RequestBody RuleGroovyScriptDirectCompileParam param) {
        log.info("直接编译执行脚本测试，param：{}", JSONUtil.toJsonStr(param));
        ArgumentAssert.isTrue(JSONUtil.isTypeJSON(param.getExecuteParams()), "执行参数格式不正确");
        try {
            return R.success(superService.runDirectCompile(param));
        } catch (Exception e) {
            log.error("Failed to execute script", e);
            throw BizException.wrap("直接编译执行脚本失败", e.getMessage());
        }

    }


}


