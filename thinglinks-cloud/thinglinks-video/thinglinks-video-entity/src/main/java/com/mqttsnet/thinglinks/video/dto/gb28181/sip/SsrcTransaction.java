package com.mqttsnet.thinglinks.video.dto.gb28181.sip;

import com.mqttsnet.thinglinks.video.dto.gb28181.SipTransactionInfo;
import com.mqttsnet.thinglinks.video.empowerment.stream.InviteSessionTypeEnum;
import gov.nist.javax.sip.message.SIPResponse;
import lombok.Data;

/**
 * 事务信息
 * @author mqttsnet
 */
@Data
public class SsrcTransaction {

    /**
     * 设备编号
     */
    private String deviceId;

    /**
     * 上级平台的编号
     */
    private String platformId;

    /**
     * 通道的数据库ID
     */
    private Integer channelId;

    /**
     * 会话的CALL ID
     */
    private String callId;

    /**
     * 关联的流应用名
     */
    private String app;

    /**
     * 关联的流ID
     */
    private String stream;

    /**
     * 使用的流媒体
     */
    private String mediaServerId;

    /**
     * 使用的SSRC
     */
    private String ssrc;

    /**
     * 事务信息
     */
    private SipTransactionInfo sipTransactionInfo;

    /**
     * 类型
     */
    private InviteSessionTypeEnum type;

    public SsrcTransaction() {
    }

    public static SsrcTransaction buildForDevice(String deviceId, Integer channelId, String callId, String app, String stream,
                                                 String ssrc, String mediaServerId, SIPResponse response, InviteSessionTypeEnum type) {
        SsrcTransaction ssrcTransaction = new SsrcTransaction();
        ssrcTransaction.setDeviceId(deviceId);
        ssrcTransaction.setChannelId(channelId);
        ssrcTransaction.setCallId(callId);
        ssrcTransaction.setApp(app);
        ssrcTransaction.setStream(stream);
        ssrcTransaction.setMediaServerId(mediaServerId);
        ssrcTransaction.setSsrc(ssrc);
        ssrcTransaction.setSipTransactionInfo(new SipTransactionInfo(response));
        ssrcTransaction.setType(type);
        return ssrcTransaction;
    }

    public static SsrcTransaction buildForPlatform(String platformId, Integer channelId, String callId, String app, String stream,
                                                   String ssrc, String mediaServerId, SIPResponse response, InviteSessionTypeEnum type) {
        SsrcTransaction ssrcTransaction = new SsrcTransaction();
        ssrcTransaction.setPlatformId(platformId);
        ssrcTransaction.setChannelId(channelId);
        ssrcTransaction.setCallId(callId);
        ssrcTransaction.setStream(stream);
        ssrcTransaction.setApp(app);
        ssrcTransaction.setMediaServerId(mediaServerId);
        ssrcTransaction.setSsrc(ssrc);
        ssrcTransaction.setSipTransactionInfo(new SipTransactionInfo(response));
        ssrcTransaction.setType(type);
        return ssrcTransaction;
    }
}
