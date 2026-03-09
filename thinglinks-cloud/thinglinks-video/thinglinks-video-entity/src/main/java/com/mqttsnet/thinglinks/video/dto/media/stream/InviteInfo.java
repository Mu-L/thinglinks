package com.mqttsnet.thinglinks.video.dto.media.stream;

import com.mqttsnet.thinglinks.video.empowerment.stream.InviteSessionStatusEnum;
import com.mqttsnet.thinglinks.video.empowerment.stream.InviteSessionTypeEnum;
import lombok.Data;

/**
 * 记录每次发送invite消息的状态
 * @author mqttsnet
 */
@Data
public class InviteInfo {

    private String deviceId;

    private Integer channelId;

    private String stream;

    private SSRCInfo ssrcInfo;

    private String receiveIp;

    private Integer receivePort;

    private String streamMode;

    private InviteSessionTypeEnum type;

    private InviteSessionStatusEnum status;

    private StreamInfo streamInfo;

    private String mediaServerId;

    private Long expirationTime;

    private Long createTime;

    private Boolean record;

    private String startTime;

    private String endTime;


    public static InviteInfo getInviteInfo(String deviceId, Integer channelId, String stream, SSRCInfo ssrcInfo, String mediaServerId,
                                           String receiveIp, Integer receivePort, String streamMode,
                                           InviteSessionTypeEnum type, InviteSessionStatusEnum status, Boolean record) {
        InviteInfo inviteInfo = new InviteInfo();
        inviteInfo.setDeviceId(deviceId);
        inviteInfo.setChannelId(channelId);
        inviteInfo.setStream(stream);
        inviteInfo.setSsrcInfo(ssrcInfo);
        inviteInfo.setReceiveIp(receiveIp);
        inviteInfo.setReceivePort(receivePort);
        inviteInfo.setStreamMode(streamMode);
        inviteInfo.setType(type);
        inviteInfo.setStatus(status);
        inviteInfo.setMediaServerId(mediaServerId);
        inviteInfo.setRecord(record);
        return inviteInfo;
    }

}
