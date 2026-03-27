package com.mqttsnet.thinglinks.video.media.server.event.publisher;

import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.dto.media.event.MediaServerOfflineEvent;
import com.mqttsnet.thinglinks.video.dto.media.event.MediaServerOnlineEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description: Event事件通知推送器，支持推送在线事件、离线事件
 * @author: mqttsnet
 */
@Component
@Slf4j
public class MediaEventPublisher {

    public void mediaServerOfflineEventPublish(VideoMediaServerResultDTO mediaServer) {
        MediaServerOfflineEvent outEvent = new MediaServerOfflineEvent(this);
        outEvent.setMediaServer(mediaServer);
        SpringUtils.publishEvent(outEvent);
    }

    public void mediaServerOnlineEventPublish(VideoMediaServerResultDTO mediaServer) {
        MediaServerOnlineEvent outEvent = new MediaServerOnlineEvent(this);
        outEvent.setMediaServer(mediaServer);
        SpringUtils.publishEvent(outEvent);
    }


}
