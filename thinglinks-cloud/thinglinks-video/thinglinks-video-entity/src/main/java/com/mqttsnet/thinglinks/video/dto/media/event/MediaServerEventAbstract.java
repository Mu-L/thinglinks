package com.mqttsnet.thinglinks.video.dto.media.event;

import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

import java.io.Serial;


/**
 * 流媒体服务事件抽象类
 *
 * @author mqttsnet
 */
@Setter
@Getter
public abstract class MediaServerEventAbstract extends ApplicationEvent {

    @Serial
    private static final long serialVersionUID = 1L;

    private VideoMediaServerResultDTO mediaServer;


    public MediaServerEventAbstract(Object source) {
        super(source);
    }

}
