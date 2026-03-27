package com.mqttsnet.thinglinks.video.media.zlm.event.listener;

import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.media.zlm.event.HookZlmServerKeepaliveEvent;
import com.mqttsnet.thinglinks.video.service.media.VideoMediaServerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Description:
 * ZLMediaKit心跳事件监听器
 * @author Sun ShiHuan
 * @version 1.0.0
 * @since 2025/5/13
 */
@Slf4j
@Component
public class HookZlmServerKeepaliveEventListener {

    @Autowired
    private VideoMediaServerService videoMediaServerService;


    @EventListener
    public void onApplicationEvent(HookZlmServerKeepaliveEvent event) {
        if (event.getMediaServerItem() == null) {
            return;
        }
        VideoMediaServerResultDTO serverItem = videoMediaServerService.getVideoMediaServerResultDTO(event.getMediaServerItem().getMediaIdentification());
        if (serverItem == null) {
            return;
        }
        log.debug("[ZLM-HOOK事件-心跳] ID：" + event.getMediaServerItem().getMediaIdentification());
        // TODO 处理心跳事件
//        onlineCheck(serverItem, null);
    }
}
