package com.mqttsnet.thinglinks.video.dto.media.zlm.hook;


/**
 * zlm hook事件中的on_play事件的参数
 * @author mqttsnet
 */
public class OnServerKeepaliveHookParam extends HookParam {

    private ServerKeepaliveData data;

    public ServerKeepaliveData getData() {
        return data;
    }

    public void setData(ServerKeepaliveData data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "OnServerKeepaliveHookParam{" +
               "data=" + data +
               '}';
    }
}
