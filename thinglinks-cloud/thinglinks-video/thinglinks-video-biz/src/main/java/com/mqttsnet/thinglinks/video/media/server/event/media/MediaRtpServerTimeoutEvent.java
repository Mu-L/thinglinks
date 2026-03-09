package com.mqttsnet.thinglinks.video.media.server.event.media;


import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.dto.media.zlm.hook.OnStreamChangedHookParam;

/**
 * RtpServer收流超时事件
 */
public class MediaRtpServerTimeoutEvent extends MediaEvent {
    public MediaRtpServerTimeoutEvent(Object source) {
        super(source);
    }

    public static MediaRtpServerTimeoutEvent getInstance(Object source, OnStreamChangedHookParam hookParam, VideoMediaServerResultDTO mediaServer) {
        MediaRtpServerTimeoutEvent mediaDepartureEven = new MediaRtpServerTimeoutEvent(source);
        mediaDepartureEven.setApp(hookParam.getApp());
        mediaDepartureEven.setStream(hookParam.getStream());
        mediaDepartureEven.setSchema(hookParam.getSchema());
        mediaDepartureEven.setMediaServer(mediaServer);
        return mediaDepartureEven;
    }
}
