package com.mqttsnet.thinglinks.video.dto.media.zlm.hook;

import lombok.Data;

/**
 * zlm hook事件中的on_send_rtp_stopped事件的参数
 * @author mqttsnet
 */
@Data
public class OnSendRtpStoppedHookParam extends HookParam {
    private String app;
    private String stream;


    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getStream() {
        return stream;
    }

    public void setStream(String stream) {
        this.stream = stream;
    }

    @Override
    public String toString() {
        return "OnSendRtpStoppedHookParam{" +
               "app='" + app + '\'' +
               ", stream='" + stream + '\'' +
               '}';
    }
}
