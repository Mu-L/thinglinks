package com.mqttsnet.thinglinks.msg.facade.impl;


import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.msg.api.MsgApi;
import com.mqttsnet.thinglinks.msg.facade.MsgFacade;
import com.mqttsnet.thinglinks.msg.vo.update.ExtendMsgPublishVO;
import com.mqttsnet.thinglinks.msg.vo.update.ExtendMsgSendVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * 消息接口
 *
 * @author zuihou
 * @since 2024年09月20日10:37:50
 *
 */
@Service
public class MsgFacadeImpl implements MsgFacade {
    @Lazy
    @Autowired
    private MsgApi msgApi;

    /**
     * 根据模板发送消息
     *
     * @param data 发送内容
     * @return
     */
    @Override
    public Boolean sendByTemplate(ExtendMsgSendVO data) {
        R<Boolean> result = msgApi.sendByTemplate(data);
        return result.getIsSuccess() && result.getData();
    }

     /**
      * 发布站内信
      *
      * @param data 发送内容
      * @return
      */
    @Override
    public Boolean publish(ExtendMsgPublishVO data) {
        R<Boolean> result = msgApi.publish(data);
        return result.getIsSuccess() && result.getData();
    }
}
