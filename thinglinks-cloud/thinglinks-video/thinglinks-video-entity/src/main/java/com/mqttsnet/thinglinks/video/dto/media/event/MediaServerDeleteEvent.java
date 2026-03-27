package com.mqttsnet.thinglinks.video.dto.media.event;

import lombok.Getter;

/**
 * 流媒体服务删除事件
 * @author mqttsnet
 */
@Getter
public class MediaServerDeleteEvent extends MediaServerEventAbstract {

    public MediaServerDeleteEvent(Object source) {
        super(source);
    }
}
