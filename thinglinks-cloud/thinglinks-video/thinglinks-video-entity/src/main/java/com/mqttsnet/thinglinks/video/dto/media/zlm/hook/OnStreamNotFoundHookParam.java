package com.mqttsnet.thinglinks.video.dto.media.zlm.hook;

import lombok.Data;

/**
 * zlm hook事件中的on_stream_not_found事件的参数
 *
 * @author mqttsnet
 */
@Data
public class OnStreamNotFoundHookParam extends HookParam {
    private String id;
    private String app;
    private String stream;
    private String ip;
    private String params;
    private Integer port;
    private String schema;
    private String vhost;
}
