package com.mqttsnet.thinglinks.video.media.zlm.event.listener;

import com.mqttsnet.thinglinks.video.dto.media.event.MediaServerDeleteEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Description:
 *
 * @author Sun ShiHuan
 * @version 1.0.0
 * @since 2025/5/13
 */
@Slf4j
@Component
public class MediaServerDeleteEventListener {


    @EventListener
    public void onApplicationEvent(MediaServerDeleteEvent event) {
        if (event.getMediaServer() == null) {
            return;
        }
        log.info("[ZLM-节点被移除] ID：" + event.getMediaServer().getMediaIdentification());
    }
}
