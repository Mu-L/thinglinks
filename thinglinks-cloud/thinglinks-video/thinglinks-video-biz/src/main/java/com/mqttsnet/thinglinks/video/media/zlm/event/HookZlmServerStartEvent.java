package com.mqttsnet.thinglinks.video.media.zlm.event;

import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import org.springframework.context.ApplicationEvent;

/**
 * zlm server_start事件
 */
public class HookZlmServerStartEvent extends ApplicationEvent {

    private VideoMediaServerResultDTO mediaServerItem;

    public HookZlmServerStartEvent(Object source) {
        super(source);
    }

    public VideoMediaServerResultDTO getMediaServerItem() {
        return mediaServerItem;
    }

    public void setMediaServerItem(VideoMediaServerResultDTO mediaServerItem) {
        this.mediaServerItem = mediaServerItem;
    }
}
