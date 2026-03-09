package com.mqttsnet.thinglinks.video.dto.media.zlm.hook;

import lombok.Data;

@Data
public class OnStreamNoneReaderHookParam extends HookParam {

    private String schema;
    private String app;
    private String stream;
    private String vhost;
}
