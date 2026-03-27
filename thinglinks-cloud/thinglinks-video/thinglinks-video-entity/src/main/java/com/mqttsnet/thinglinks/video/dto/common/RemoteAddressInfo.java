package com.mqttsnet.thinglinks.video.dto.common;


import lombok.Data;

@Data
public class RemoteAddressInfo {
    private String ip;
    private int port;

    public RemoteAddressInfo(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

}
