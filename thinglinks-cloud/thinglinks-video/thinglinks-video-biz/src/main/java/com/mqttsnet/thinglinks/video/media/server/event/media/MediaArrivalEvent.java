package com.mqttsnet.thinglinks.video.media.server.event.media;

import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.dto.media.stream.StreamContent;
import com.mqttsnet.thinglinks.video.dto.media.zlm.MediaInfo;
import com.mqttsnet.thinglinks.video.dto.media.zlm.hook.OnStreamChangedHookParam;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * 流到来事件
 */

public class MediaArrivalEvent extends MediaEvent {
    @Getter
    @Setter
    private MediaInfo mediaInfo;
    @Getter
    @Setter
    private String callId;
    @Getter
    @Setter
    private StreamContent streamInfo;
    @Getter
    @Setter
    private Map<String, String> paramMap;
    @Getter
    @Setter
    private String serverId;

    public MediaArrivalEvent(Object source) {
        super(source);
    }

    public static MediaArrivalEvent getInstance(Object source, OnStreamChangedHookParam hookParam, VideoMediaServerResultDTO mediaServer, String serverId) {
        MediaArrivalEvent mediaArrivalEvent = new MediaArrivalEvent(source);
        mediaArrivalEvent.setMediaInfo(MediaInfo.getInstance(hookParam, mediaServer, serverId));
        mediaArrivalEvent.setApp(hookParam.getApp());
        mediaArrivalEvent.setStream(hookParam.getStream());
        mediaArrivalEvent.setMediaServer(mediaServer);
        mediaArrivalEvent.setSchema(hookParam.getSchema());
        mediaArrivalEvent.setSchema(hookParam.getSchema());
        mediaArrivalEvent.setParamMap(hookParam.getParamMap());
        return mediaArrivalEvent;
    }


}
