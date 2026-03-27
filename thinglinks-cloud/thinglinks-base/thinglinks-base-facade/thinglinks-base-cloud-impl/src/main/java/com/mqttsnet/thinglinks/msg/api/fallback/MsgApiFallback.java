package com.mqttsnet.thinglinks.msg.api.fallback;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.msg.api.MsgApi;
import com.mqttsnet.thinglinks.msg.vo.update.ExtendMsgPublishVO;
import com.mqttsnet.thinglinks.msg.vo.update.ExtendMsgSendVO;
import org.springframework.stereotype.Component;

/**
 * 熔断
 *
 * @author zuihou
 * @date 2019/07/25
 */
@Component
public class MsgApiFallback implements MsgApi {
    @Override
    public R<Boolean> sendByTemplate(ExtendMsgSendVO data) {
        return R.timeout();
    }

     @Override
    public R<Boolean> publish(ExtendMsgPublishVO data) {
        return R.timeout();
    }
}
