package com.mqttsnet.thinglinks.video.media.zlm.event;

import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import org.springframework.context.ApplicationEvent;

/**
 * zlm 心跳事件
 */
public class HookZlmServerKeepaliveEvent extends ApplicationEvent {

    private VideoMediaServerResultDTO mediaServerItem;

    public HookZlmServerKeepaliveEvent(Object source) {
        super(source);
    }

    public VideoMediaServerResultDTO getMediaServerItem() {
        return mediaServerItem;
    }

    public void setMediaServerItem(VideoMediaServerResultDTO mediaServerItem) {
        this.mediaServerItem = mediaServerItem;
    }
}
