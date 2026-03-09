package com.mqttsnet.thinglinks.video.dto.gb28181.error;

public interface ErrorCallback<T> {

    void run(int code, String msg, T data);
}
