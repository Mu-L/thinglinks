package com.mqttsnet.thinglinks.video.gb28181.transmit;

import com.mqttsnet.thinglinks.video.config.gb28181.SipConfig;
import com.mqttsnet.thinglinks.video.gb28181.SipLayer;
import com.mqttsnet.thinglinks.video.gb28181.event.sip.SipEvent;
import com.mqttsnet.thinglinks.video.gb28181.event.subscribe.SipSubscribe;
import gov.nist.javax.sip.SipProviderImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.sip.SipException;
import javax.sip.header.CSeqHeader;
import javax.sip.header.CallIdHeader;
import javax.sip.header.ViaHeader;
import javax.sip.message.Message;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.text.ParseException;

/**
 * 发送SIP消息
 *
 * @author mqttsnet
 */
@Slf4j
@Component
public class SIPSender {

    @Autowired
    private SipLayer sipLayer;

    @Autowired
    private SipSubscribe sipSubscribe;
    @Autowired
    private SipConfig sipConfig;

    public void transmitRequest(String ip, Message message) throws SipException, ParseException {
        transmitRequest(ip, message, null, null, null);
    }

    public void transmitRequest(String ip, Message message, SipSubscribe.Event errorEvent) throws SipException, ParseException {
        transmitRequest(ip, message, errorEvent, null, null);
    }

    public void transmitRequest(String ip, Message message, SipSubscribe.Event errorEvent, SipSubscribe.Event okEvent) throws SipException {
        transmitRequest(ip, message, errorEvent, okEvent, null);
    }

    public void transmitRequest(String ip, Message message, SipSubscribe.Event errorEvent, SipSubscribe.Event okEvent, Long timeout) throws SipException {
        ViaHeader viaHeader = (ViaHeader) message.getHeader(ViaHeader.NAME);
        String transport = "UDP";
        if (viaHeader == null) {
            log.warn("[消息头缺失]： ViaHeader， 使用默认的UDP方式处理数据");
        } else {
            transport = viaHeader.getTransport();
        }

        CallIdHeader callIdHeader = (CallIdHeader) message.getHeader(CallIdHeader.NAME);
        CSeqHeader cSeqHeader = (CSeqHeader) message.getHeader(CSeqHeader.NAME);
        String key = callIdHeader.getCallId() + cSeqHeader.getSeqNumber();
        if (okEvent != null || errorEvent != null) {
            SipEvent sipEvent = SipEvent.getInstance(key, eventResult -> {
                sipSubscribe.removeSubscribe(key);
                if (okEvent != null) {
                    okEvent.response(eventResult);
                }
            }, (eventResult -> {
                sipSubscribe.removeSubscribe(key);
                if (errorEvent != null) {
                    errorEvent.response(eventResult);
                }
            }), timeout == null ? sipConfig.getTimeout() : timeout);
            sipSubscribe.addSubscribe(key, sipEvent);
        }
        try {
            if ("TCP".equals(transport)) {
                SipProviderImpl tcpSipProvider = sipLayer.getTcpSipProvider(ip);
                if (tcpSipProvider == null) {
                    log.error("[发送信息失败] 未找到tcp://{}的监听信息", ip);
                    return;
                }
                if (message instanceof Request) {
                    tcpSipProvider.sendRequest((Request) message);
                } else if (message instanceof Response) {
                    tcpSipProvider.sendResponse((Response) message);
                }

            } else if ("UDP".equals(transport)) {
                SipProviderImpl sipProvider = sipLayer.getUdpSipProvider(ip);
                if (sipProvider == null) {
                    log.error("[发送信息失败] 未找到udp://{}的监听信息", ip);
                    return;
                }
                if (message instanceof Request) {
                    sipProvider.sendRequest((Request) message);
                } else if (message instanceof Response) {
                    sipProvider.sendResponse((Response) message);
                }
            }
        } catch (SipException e) {
            sipSubscribe.removeSubscribe(key);
            throw e;
        }
    }

    public CallIdHeader getNewCallIdHeader(String ip, String transport) {
        if (ObjectUtils.isEmpty(transport)) {
            return sipLayer.getUdpSipProvider().getNewCallId();
        }
        SipProviderImpl sipProvider;
        if (ObjectUtils.isEmpty(ip)) {
            sipProvider = "TCP".equalsIgnoreCase(transport) ? sipLayer.getTcpSipProvider()
                    : sipLayer.getUdpSipProvider();
        } else {
            sipProvider = "TCP".equalsIgnoreCase(transport) ? sipLayer.getTcpSipProvider(ip)
                    : sipLayer.getUdpSipProvider(ip);
        }

        if (sipProvider == null) {
            sipProvider = sipLayer.getUdpSipProvider();
        }

        if (sipProvider != null) {
            return sipProvider.getNewCallId();
        } else {
            log.warn("[新建CallIdHeader失败]， ip={}, transport={}", ip, transport);
            return null;
        }
    }


}
