package com.mqttsnet.thinglinks.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.thinglinks.common.cache.rule.groovy.GroovyScriptCacheKeyBuilder;
import com.mqttsnet.thinglinks.product.enumeration.ProtocolTypeEnum;
import com.mqttsnet.thinglinks.record.script.ScriptIdentifier;
import com.mqttsnet.thinglinks.rule.facade.RuleOpenAnyUserFacade;
import com.mqttsnet.thinglinks.service.ProtocolGroovyScriptService;
import com.mqttsnet.thinglinks.vo.param.script.RuleGroovyScriptExecuteScriptParam;
import com.mqttsnet.thinglinks.vo.result.script.GroovyScriptEngineExecutorResultVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * ============================================================================
 * Description:
 * 协议规则脚本Service 接口实现类
 * ============================================================================
 *
 * @author Sun Shihuan
 * @version 1.0.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2025/4/13      Sun Shihuan        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2025/4/13 14:04
 */
@Slf4j
@Service
public class ProtocolGroovyScriptServiceImpl implements ProtocolGroovyScriptService {


    @Autowired
    private CachePlusOps cachePlusOps;

    @Autowired
    private RuleOpenAnyUserFacade ruleOpenAnyUserFacade;


    @Override
    public String datasTopicBodyTransformed(ProtocolTypeEnum protocolTypeEnum, String deviceIdentification, String productIdentification, String originBody) {
        if (StrUtil.isBlank(originBody)) {
            return originBody;
        }

        ScriptIdentifier scriptIdentifier;
        if (protocolTypeEnum.equals(ProtocolTypeEnum.MQTT)) {
            scriptIdentifier = ScriptIdentifier.forMqttToDatas(productIdentification);
        } else if (protocolTypeEnum.equals(ProtocolTypeEnum.WEBSOCKET)) {
            scriptIdentifier = ScriptIdentifier.forWebSocketToDatas(productIdentification);
        } else {
            log.info("{} 不支持脚本转换,不进行数据转换处理", protocolTypeEnum.getDesc());
            return originBody;
        }


        Boolean existsFlag = cachePlusOps.exists(GroovyScriptCacheKeyBuilder.builder(scriptIdentifier.buildCompositeKey()));
        if (!existsFlag) {
            log.info("无规则脚本配置，不进行数据转换处理");
            return originBody;
        }
        try {
            Map<String, Object> executeParams = new HashMap<>();
            executeParams.put("deviceIdentification", deviceIdentification);
            executeParams.put("productIdentification", productIdentification);
            executeParams.put("originBody", originBody);
            RuleGroovyScriptExecuteScriptParam param = new RuleGroovyScriptExecuteScriptParam();
            param.setScriptUniqueKey(scriptIdentifier.buildCompositeKey());
            param.setExecuteParams(JSONUtil.toJsonStr(executeParams));
            R<GroovyScriptEngineExecutorResultVO> resultVOR = ruleOpenAnyUserFacade.executeScript(param);
            log.info("脚本唯一标识：{} ,执行规则脚本结果：{}", scriptIdentifier.buildCompositeKey(), JSONUtil.toJsonStr(resultVOR));
            if (!resultVOR.getIsSuccess()) {
                log.warn("执行规则脚本失败,继续使用原数据执行后续业务逻辑：{}", JSONUtil.toJsonStr(resultVOR));
                return originBody;
            }
            return JSONUtil.toJsonStr(resultVOR.getData().getContext());
        } catch (Exception loadScriptException) {
            log.error("执行规则脚本异常：", loadScriptException);
            return originBody;
        }
    }

}
