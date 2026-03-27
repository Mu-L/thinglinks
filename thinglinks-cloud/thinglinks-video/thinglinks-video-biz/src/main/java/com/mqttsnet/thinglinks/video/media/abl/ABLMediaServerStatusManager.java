package com.mqttsnet.thinglinks.video.media.abl;

import com.mqttsnet.thinglinks.video.cache.CacheSuperAbstract;
import com.mqttsnet.thinglinks.video.empowerment.media.VideoMediaServerTypeEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 管理abl流媒体节点的状态
 *
 * @author mqttsnet
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ABLMediaServerStatusManager extends CacheSuperAbstract {

    private final String mediaServerType = VideoMediaServerTypeEnum.ABL.getValue();


}
