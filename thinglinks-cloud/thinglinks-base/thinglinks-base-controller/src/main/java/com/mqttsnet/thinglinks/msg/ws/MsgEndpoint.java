package com.mqttsnet.thinglinks.msg.ws;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.thinglinks.msg.entity.ExtendNotice;
import com.mqttsnet.thinglinks.msg.enumeration.NoticeRemindModeEnum;
import com.mqttsnet.thinglinks.msg.service.ExtendNoticeService;
import com.mqttsnet.thinglinks.msg.vo.MyMsgResult;
import com.mqttsnet.thinglinks.msg.vo.result.ExtendNoticeResultVO;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * @author mqttsnet
 * @date 2021/8/4 23:47
 */
@ServerEndpoint("/myMsg/{tenantId}/{principal}")
@Component
@Slf4j
public class MsgEndpoint {


    /**
     * 连接成功
     *
     * @param session
     */
    @OnOpen
    public void onOpen(@PathParam("principal") String principal, Session session) {
        log.info("连接成功");
        WebSocketObserver observer = new WebSocketObserver(session);
        // get subject
        WebSocketSubject subject = WebSocketSubject.Holder.getSubject(principal);
        // register observer into subject
        subject.addObserver(observer);
    }

    /**
     * 连接关闭
     *
     * @param session
     */
    @OnClose
    public void onClose(@PathParam("principal") String principal, Session session) {
        log.info("连接关闭");
        // get subject
        WebSocketSubject subject = WebSocketSubject.Holder.getSubject(principal);

        // get observer
        WebSocketObserver observer = new WebSocketObserver(session);
        // delete observer from subject
        subject.deleteObserver(observer);

        // close session and close Web Socket connection
        try {
            if (session.isOpen()) {
                session.close();
            }
        } catch (IOException e) {
            throw new RuntimeException("close web socket session error.", e);
        }
    }

    /**
     * 接收客户端发送的消息， 并返回数据给客户端
     *
     * @param text
     */
    @OnMessage
    public String onMsg(@PathParam("tenantId") String tenantId, @PathParam("principal") String principal, String text) {
        if (StrUtil.isEmpty(text) || "ping".equals(text)) {
            return StrPool.EMPTY;
        }
        log.info("tenantId={}, employeeId={}, text={}", tenantId, principal, text);
        ContextUtil.setEmployeeId(principal);
        ContextUtil.setTenantId(tenantId);
        if (ContextUtil.isEmptyBasePool() || ContextUtil.isEmptyEmployeeId()) {
            return StrPool.EMPTY;
        }

        PageParams<ExtendNotice> params = new PageParams<>(1, 10);
        ExtendNoticeService superService = SpringUtils.getBean(ExtendNoticeService.class);

        IPage<ExtendNotice> todoList = params.buildPage(ExtendNotice.class);
        IPage<ExtendNotice> noticeList = params.buildPage(ExtendNotice.class);
        IPage<ExtendNotice> earlyWarningList = params.buildPage(ExtendNotice.class);
        superService.page(todoList, Wraps.<ExtendNotice>lbQ()
                .eq(ExtendNotice::getRemindMode, NoticeRemindModeEnum.TO_DO.getValue()).
                eq(ExtendNotice::getIsRead, false).eq(ExtendNotice::getRecipientId, ContextUtil.getEmployeeId()));
        superService.page(noticeList, Wraps.<ExtendNotice>lbQ()
                .eq(ExtendNotice::getRemindMode, NoticeRemindModeEnum.NOTICE.getValue()).
                eq(ExtendNotice::getIsRead, false).eq(ExtendNotice::getRecipientId, ContextUtil.getEmployeeId()));
        superService.page(earlyWarningList, Wraps.<ExtendNotice>lbQ()
                .eq(ExtendNotice::getRemindMode, NoticeRemindModeEnum.EARLY_WARNING.getValue()).
                eq(ExtendNotice::getIsRead, false).eq(ExtendNotice::getRecipientId, ContextUtil.getEmployeeId()));

        MyMsgResult result = MyMsgResult.builder()
                .todoList(BeanPlusUtil.toBeanPage(todoList, ExtendNoticeResultVO.class))
                .noticeList(BeanPlusUtil.toBeanPage(noticeList, ExtendNoticeResultVO.class))
                .earlyWarningList(BeanPlusUtil.toBeanPage(earlyWarningList, ExtendNoticeResultVO.class))
                .build();

        Map<String, Object> map = MapUtil.newHashMap();
        map.put("type", "2");
        map.put("data", result);
        return JsonUtil.toJson(map);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.info("连接error");
        throw new RuntimeException("web socket error.", error);
    }
}
