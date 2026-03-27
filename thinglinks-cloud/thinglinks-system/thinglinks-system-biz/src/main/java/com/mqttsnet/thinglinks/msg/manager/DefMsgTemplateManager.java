package com.mqttsnet.thinglinks.msg.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.msg.entity.DefMsgTemplate;

/**
 * <p>
 * 通用业务接口
 * 消息模板
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-04 15:51:37
 * @create [2022-07-04 15:51:37] [mqttsnet] 
 */
public interface DefMsgTemplateManager extends SuperManager<DefMsgTemplate> {
    /**
     * 根据消息模板编码查询消息模板
     *
     * @param code
     * @return
     */
    DefMsgTemplate getByCode(String code);
}


