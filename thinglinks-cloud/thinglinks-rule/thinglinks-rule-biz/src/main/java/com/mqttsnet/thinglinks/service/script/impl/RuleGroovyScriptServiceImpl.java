package com.mqttsnet.thinglinks.service.script.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.converter.Builder;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.groovy.entity.EngineExecutorResult;
import com.mqttsnet.basic.groovy.entity.ExecuteParams;
import com.mqttsnet.basic.groovy.entity.ScriptEntry;
import com.mqttsnet.basic.groovy.entity.ScriptQuery;
import com.mqttsnet.basic.groovy.executor.EngineExecutor;
import com.mqttsnet.basic.groovy.helper.RegisterScriptHelper;
import com.mqttsnet.basic.groovy.loader.ScriptLoader;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.entity.script.RuleGroovyScript;
import com.mqttsnet.thinglinks.enumeration.script.ExecutionStatusEnum;
import com.mqttsnet.thinglinks.manager.script.RuleGroovyScriptManager;
import com.mqttsnet.thinglinks.record.script.ScriptIdentifier;
import com.mqttsnet.thinglinks.service.script.RuleGroovyScriptService;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptDirectCompileParam;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptExecuteScriptParam;
import com.mqttsnet.thinglinks.vo.query.script.RuleGroovyScriptPageQuery;
import com.mqttsnet.thinglinks.vo.query.script.RuleGroovyScriptQuery;
import com.mqttsnet.thinglinks.vo.result.script.GroovyScriptEngineExecutorResultVO;
import com.mqttsnet.thinglinks.vo.result.script.RuleGroovyScriptResultVO;
import com.mqttsnet.thinglinks.vo.save.script.RuleGroovyScriptSaveVO;
import com.mqttsnet.thinglinks.vo.update.script.RuleGroovyScriptUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 业务实现类
 * 规则脚本表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-03-24 09:54:10
 * @create [2025-03-24 09:54:10] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class RuleGroovyScriptServiceImpl extends SuperServiceImpl<RuleGroovyScriptManager, Long, RuleGroovyScript> implements RuleGroovyScriptService {


    private final RegisterScriptHelper registerScriptHelper;

    private final EngineExecutor engineExecutor;

    private final ScriptLoader scriptLoader;


    @Override
    public List<RuleGroovyScriptResultVO> getRuleGroovyScriptResultVOList(RuleGroovyScriptPageQuery query) {
        List<RuleGroovyScript> scriptList = superManager.getRuleGroovyScriptList(query);
        return BeanPlusUtil.copyToList(scriptList, RuleGroovyScriptResultVO.class);
    }


    @Override
    public Boolean flushGroovyScriptCache() {
        RuleGroovyScriptPageQuery query = new RuleGroovyScriptPageQuery();
        query.setEnable(true);
        List<RuleGroovyScriptResultVO> ruleGroovyScriptResultVOList = getRuleGroovyScriptResultVOList(query);
        if (CollUtil.isEmpty(ruleGroovyScriptResultVOList)) {
            log.info("刷新规则脚本缓存，TenantId:{} 没有需要刷新的规则脚本数据", ContextUtil.getTenantIdStr());
            return true;
        }
        ruleGroovyScriptResultVOList.forEach(item -> {
            try {
                CacheKey cacheKey = item.getCacheKey();
                if (item.getEnable()) {
                    // 注册脚本
                    registerScriptHelper.flushGroovyScriptCache(cacheKey, item.getScriptContent(), true);
                } else {
                    //清理脚本
                    registerScriptHelper.clear(cacheKey);
                }
            } catch (Exception e) {
                log.error("刷新规则脚本缓存失败，TenantId:{}，规则脚本ID:{}", ContextUtil.getTenantIdStr(), item.getId(), e);
            }
        });
        return true;
    }


    /**
     * 保存规则脚本
     *
     * @param saveVO 保存参数
     * @return 保存结果
     */
    @Override
    public RuleGroovyScriptSaveVO saveGroovyScript(RuleGroovyScriptSaveVO saveVO) {
        log.info("保存规则脚本参数: {}", saveVO);

        // 参数校验
        checkSaveParams(saveVO);

        // 构建实体
        RuleGroovyScript script = buildScriptEntity(saveVO);

        // 保存操作
        superManager.save(script);

        if (script.getEnable()) {
            // 注册脚本
            registerScript(script);
        } else {
            //清理脚本
            clearScript(script);
        }


        return BeanPlusUtil.toBeanIgnoreError(script, RuleGroovyScriptSaveVO.class);
    }


    /**
     * 更新规则脚本
     *
     * @param updateVO 更新参数
     * @return 更新结果
     */
    @Override
    public RuleGroovyScriptUpdateVO updateGroovyScript(RuleGroovyScriptUpdateVO updateVO) {
        log.info("更新规则脚本参数: {}", updateVO);

        // 参数校验
        checkUpdateParams(updateVO);

        // 验证记录存在性
        RuleGroovyScript existingScript = superManager.getById(updateVO.getId());
        if (existingScript == null) {
            throw BizException.wrap("规则脚本不存在");
        }

        // 构建更新实体
        RuleGroovyScript script = buildUpdateEntity(updateVO, existingScript);

        // 执行更新
        superManager.updateById(script);

        if (script.getEnable()) {
            // 注册脚本
            registerScript(script);
        } else {
            //清理脚本
            clearScript(script);
        }

        return updateVO;
    }

    /**
     * 注册规则脚本
     *
     * @param script 脚本实体
     */
    private void registerScript(RuleGroovyScript script) {
        try {
            CacheKey cacheKey = ScriptIdentifier.buildCacheKey(BeanPlusUtil.toBeanIgnoreError(script, RuleGroovyScriptQuery.class));
            registerScriptHelper.registerScript(cacheKey, script.getScriptContent(), false);
        } catch (Exception e) {
            log.error("注册规则脚本失败，TenantId:{}，规则脚本ID:{}", ContextUtil.getTenantIdStr(), script.getId(), e);
            throw BizException.wrap("注册规则脚本失败,脚本不合法!");
        }
    }

    /**
     * 清理规则脚本
     *
     * @param script 脚本实体
     */
    private void clearScript(RuleGroovyScript script) {
        try {
            // 清理脚本
            CacheKey cacheKey = ScriptIdentifier.buildCacheKey(BeanPlusUtil.toBeanIgnoreError(script, RuleGroovyScriptQuery.class));
            registerScriptHelper.clear(cacheKey);
        } catch (Exception e) {
            log.error("清理规则脚本失败，TenantId:{}，规则脚本ID:{}", ContextUtil.getTenantIdStr(), script.getId(), e);
            throw BizException.wrap("清理规则脚本失败!");
        }
    }

    @Override
    public Boolean deleteGroovyScript(Long id) {
        ArgumentAssert.notNull(id, "id Cannot be null");
        RuleGroovyScript script = superManager.getById(id);
        if (Objects.isNull(script)) {
            throw BizException.wrap("脚本信息不存在!");
        }

        //清理脚本
        clearScript(script);
        return superManager.removeById(id);
    }

    @Override
    public GroovyScriptEngineExecutorResultVO executeScript(RuleGroovyScriptExecuteScriptParam param) throws Exception {
        ExecuteParams executeParams = JSONUtil.parseObj(param.getExecuteParams()).toBean(ExecuteParams.class);
        // 编译并执行
        ScriptQuery scriptQuery = new ScriptQuery(param.getScriptUniqueKey());
        ScriptEntry scriptEntry = scriptLoader.load(scriptQuery);
        EngineExecutorResult result = engineExecutor.execute(scriptEntry, executeParams);
        GroovyScriptEngineExecutorResultVO resultVO = BeanPlusUtil.toBeanIgnoreError(result, GroovyScriptEngineExecutorResultVO.class);
        resultVO.setExecutionStatus(
                ExecutionStatusEnum.fromValue(Integer.valueOf(result.getExecutionStatus().getCode())).orElse(ExecutionStatusEnum.FAILED)
        );
        return resultVO;
    }

    @Override
    public GroovyScriptEngineExecutorResultVO runDirectCompile(RuleGroovyScriptDirectCompileParam param) throws Exception {
        ExecuteParams executeParams = JSONUtil.parseObj(param.getExecuteParams()).toBean(ExecuteParams.class);
        // 编译并执行
        ScriptEntry scriptEntry = scriptLoader.compileScript(param.getScriptContent());
        EngineExecutorResult result = engineExecutor.execute(scriptEntry, executeParams);
        GroovyScriptEngineExecutorResultVO resultVO = BeanPlusUtil.toBeanIgnoreError(result, GroovyScriptEngineExecutorResultVO.class);
        resultVO.setExecutionStatus(
                ExecutionStatusEnum.fromValue(Integer.valueOf(result.getExecutionStatus().getCode())).orElse(ExecutionStatusEnum.FAILED)
        );
        return resultVO;
    }


    /**
     * 校验保存参数
     *
     * @param saveVO 保存参数
     */
    private void checkSaveParams(RuleGroovyScriptSaveVO saveVO) {
        // 校验唯一
        checkScriptUnique(saveVO.getNamespace(), saveVO.getPlatformCode(), saveVO.getProductCode(),
                saveVO.getChannelCode(), saveVO.getBusinessCode(), saveVO.getBusinessIdentification(),
                null);


    }

    /**
     * 校验更新参数
     *
     * @param updateVO 更新参数
     */
    private void checkUpdateParams(RuleGroovyScriptUpdateVO updateVO) {
        // 校验唯一
        checkScriptUnique(updateVO.getNamespace(), updateVO.getPlatformCode(), updateVO.getProductCode(),
                updateVO.getChannelCode(), updateVO.getBusinessCode(), updateVO.getBusinessIdentification(),
                updateVO.getId());
    }

    /**
     * 校验脚本唯一性
     *
     * @param namespace              命名空间
     * @param platformCode           平台编码
     * @param productCode            产品编码
     * @param channelCode            渠道编码
     * @param businessCode           业务编码
     * @param businessIdentification 业务标识
     * @param excludeId              排除的ID
     */
    private void checkScriptUnique(String namespace, String platformCode, String productCode,
                                   String channelCode, String businessCode, String businessIdentification,
                                   Long excludeId) {
        long count = superManager.count(Wraps.<RuleGroovyScript>lbQ()
                .eq(RuleGroovyScript::getNamespace, namespace)
                .eq(RuleGroovyScript::getPlatformCode, platformCode)
                .eq(RuleGroovyScript::getProductCode, productCode)
                .eq(RuleGroovyScript::getChannelCode, channelCode)
                .eq(RuleGroovyScript::getBusinessCode, businessCode)
                .eq(RuleGroovyScript::getBusinessIdentification, businessIdentification)
                .ne(excludeId != null, RuleGroovyScript::getId, excludeId));

        if (count > 0) {
            throw BizException.wrap("该组合配置已存在!");
        }
    }


    /**
     * 构建保存实体
     */
    private RuleGroovyScript buildScriptEntity(RuleGroovyScriptSaveVO saveVO) {
        return Builder.of(RuleGroovyScript::new)
                .with(RuleGroovyScript::setName, saveVO.getName())
                .with(RuleGroovyScript::setAppId, saveVO.getAppId())
                .with(RuleGroovyScript::setNamespace, saveVO.getNamespace())
                .with(RuleGroovyScript::setPlatformCode, saveVO.getPlatformCode())
                .with(RuleGroovyScript::setProductCode, saveVO.getProductCode())
                .with(RuleGroovyScript::setChannelCode, saveVO.getChannelCode())
                .with(RuleGroovyScript::setBusinessCode, saveVO.getBusinessCode())
                .with(RuleGroovyScript::setBusinessIdentification, saveVO.getBusinessIdentification())
                .with(RuleGroovyScript::setObjectVersion, saveVO.getObjectVersion())
                .with(RuleGroovyScript::setEnable, saveVO.getEnable())
                .with(RuleGroovyScript::setScriptContent, saveVO.getScriptContent())
                .with(RuleGroovyScript::setExtendParams, saveVO.getExtendParams())
                .with(RuleGroovyScript::setRemark, saveVO.getRemark())
                .with(RuleGroovyScript::setCreatedOrgId, ContextUtil.getCurrentDeptId())
                .build();
    }

    /**
     * 构建更新实体
     */
    private RuleGroovyScript buildUpdateEntity(RuleGroovyScriptUpdateVO updateVO,
                                               RuleGroovyScript existing) {
        return Builder.of(RuleGroovyScript::new)
                .with(RuleGroovyScript::setId, updateVO.getId())
                .with(RuleGroovyScript::setName, updateVO.getName())
                .with(RuleGroovyScript::setAppId, updateVO.getAppId())
                .with(RuleGroovyScript::setNamespace, updateVO.getNamespace())
                .with(RuleGroovyScript::setPlatformCode, updateVO.getPlatformCode())
                .with(RuleGroovyScript::setProductCode, updateVO.getProductCode())
                .with(RuleGroovyScript::setChannelCode, existing.getChannelCode())
                .with(RuleGroovyScript::setBusinessCode, existing.getBusinessCode())
                .with(RuleGroovyScript::setBusinessIdentification, existing.getBusinessIdentification())
                .with(RuleGroovyScript::setObjectVersion, updateVO.getObjectVersion())
                .with(RuleGroovyScript::setEnable, updateVO.getEnable())
                .with(RuleGroovyScript::setScriptContent, updateVO.getScriptContent())
                .with(RuleGroovyScript::setExtendParams, updateVO.getExtendParams())
                .with(RuleGroovyScript::setRemark, updateVO.getRemark())
                .with(RuleGroovyScript::setCreatedOrgId, ContextUtil.getCurrentDeptId())
                .build();
    }

}


