package com.mqttsnet.thinglinks.video.dto.media.event;

import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 流媒体服务变更事件
 * @author mqttsnet
 */
@Getter
public class MediaServerChangeEvent extends ApplicationEvent {

    private List<VideoMediaServerResultDTO> mediaServerItemList;

    public MediaServerChangeEvent(Object source) {
        super(source);
    }

    public void setMediaServerItemList(List<VideoMediaServerResultDTO> mediaServerItemList) {
        this.mediaServerItemList = mediaServerItemList;
    }

    public void setMediaServerItemList(VideoMediaServerResultDTO... mediaServerItemArray) {
        this.mediaServerItemList = new ArrayList<>();
        this.mediaServerItemList.addAll(Arrays.asList(mediaServerItemArray));
    }

    public void setMediaServerItem(List<VideoMediaServerResultDTO> mediaServerItemList) {
        this.mediaServerItemList = mediaServerItemList;
    }
}
