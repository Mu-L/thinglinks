package com.mqttsnet.thinglinks.service.script;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.entity.script.RuleGroovyScript;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptDirectCompileParam;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptExecuteScriptParam;
import com.mqttsnet.thinglinks.vo.query.script.RuleGroovyScriptPageQuery;
import com.mqttsnet.thinglinks.vo.result.script.GroovyScriptEngineExecutorResultVO;
import com.mqttsnet.thinglinks.vo.result.script.RuleGroovyScriptResultVO;
import com.mqttsnet.thinglinks.vo.save.script.RuleGroovyScriptSaveVO;
import com.mqttsnet.thinglinks.vo.update.script.RuleGroovyScriptUpdateVO;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 规则脚本表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-03-24 09:54:10
 * @create [2025-03-24 09:54:10] [mqttsnet]
 */
public interface RuleGroovyScriptService extends SuperService<Long, RuleGroovyScript> {


    /**
     * 查询规则脚本VO列表
     *
     * @param query 查询参数
     * @return {@link List<RuleGroovyScriptResultVO>} 规则脚本VO列表
     */
    List<RuleGroovyScriptResultVO> getRuleGroovyScriptResultVOList(RuleGroovyScriptPageQuery query);


    /**
     * 刷新规则脚本缓存
     *
     * @return {@link Boolean} true:刷新成功 false:刷新失败
     */
    Boolean flushGroovyScriptCache();


    /**
     * 保存规则脚本数据
     *
     * @param saveVO 保存实体
     * @return {@link RuleGroovyScriptSaveVO} 保存实体
     */
    RuleGroovyScriptSaveVO saveGroovyScript(RuleGroovyScriptSaveVO saveVO);


    /**
     * 更新规则脚本数据
     *
     * @param updateVO 更新实体
     * @return {@link RuleGroovyScriptUpdateVO} 更新实体
     */
    RuleGroovyScriptUpdateVO updateGroovyScript(RuleGroovyScriptUpdateVO updateVO);

    /**
     * 删除规则脚本
     *
     * @param id 脚本ID
     * @return {@link Boolean} true:删除成功 false:删除失败
     */
    Boolean deleteGroovyScript(Long id);

    /**
     * 直接执行Groovy脚本
     *
     * @param param Groovy脚本执行参数
     * @return {@link GroovyScriptEngineExecutorResultVO} 执行结果
     */
    GroovyScriptEngineExecutorResultVO executeScript(RuleGroovyScriptExecuteScriptParam param) throws Exception;

    /**
     * 直接编译并执行Groovy脚本
     *
     * @param param Groovy脚本执行参数
     * @return {@link GroovyScriptEngineExecutorResultVO} 执行结果
     */
    GroovyScriptEngineExecutorResultVO runDirectCompile(RuleGroovyScriptDirectCompileParam param) throws Exception;
}


