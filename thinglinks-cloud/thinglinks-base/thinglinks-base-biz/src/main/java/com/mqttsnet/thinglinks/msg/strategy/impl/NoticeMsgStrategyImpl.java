package com.mqttsnet.thinglinks.msg.strategy.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mqttsnet.thinglinks.msg.entity.ExtendMsg;
import com.mqttsnet.thinglinks.msg.entity.ExtendMsgRecipient;
import com.mqttsnet.thinglinks.msg.entity.ExtendMsgTemplate;
import com.mqttsnet.thinglinks.msg.entity.ExtendNotice;
import com.mqttsnet.thinglinks.msg.enumeration.NoticeRemindModeEnum;
import com.mqttsnet.thinglinks.msg.manager.ExtendNoticeManager;
import com.mqttsnet.thinglinks.msg.strategy.MsgStrategy;
import com.mqttsnet.thinglinks.msg.strategy.domain.MsgParam;
import com.mqttsnet.thinglinks.msg.strategy.domain.MsgResult;
import com.mqttsnet.thinglinks.msg.ws.WebSocketSubject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mqttsnet
 * @date 2022/7/11 0011 10:29
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeMsgStrategyImpl implements MsgStrategy {
    private final ExtendNoticeManager extendNoticeManager;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public MsgResult exec(MsgParam msgParam) {
        ExtendMsg extendMsg = msgParam.getExtendMsg();
        List<ExtendMsgRecipient> recipientList = msgParam.getRecipientList();
        ExtendMsgTemplate extendMsgTemplate = msgParam.getExtendMsgTemplate();

        MsgResult msgResult = replaceVariable(extendMsg, extendMsgTemplate);

        List<ExtendNotice> list = new ArrayList<>();
        for (ExtendMsgRecipient extendMsgRecipient : recipientList) {
            ExtendNotice notice = new ExtendNotice();
            BeanUtil.copyProperties(extendMsgTemplate, notice);
            BeanUtil.copyProperties(extendMsg, notice);

            notice.setRecipientId(Long.valueOf(extendMsgRecipient.getRecipient()));
            notice.setTitle(msgResult.getTitle());
            notice.setContent(msgResult.getContent());
            notice.setIsHandle(false);
            notice.setIsRead(false);
            notice.setHandleTime(null);
            notice.setReadTime(null);
            notice.setId(null);
            notice.setRemindMode(NoticeRemindModeEnum.NOTICE.getCode());
            notice.setMsgId(extendMsg.getId());
            list.add(notice);

            WebSocketSubject subject = WebSocketSubject.Holder.getSubject(Long.valueOf(extendMsgRecipient.getRecipient()));
            // 通知客户端 接收消息
            subject.notify("1", null);
        }
        extendNoticeManager.saveBatch(list);
        return msgResult.setResult(true);
    }

    @Override
    public boolean isSuccess(MsgResult result) {
        return (boolean) result.getResult();
    }

}
