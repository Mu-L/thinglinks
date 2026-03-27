//package com.mqttsnet.thinglinks.video.dto.media.event;
//
//import com.mqttsnet.thinglinks.video.dto.media.zlm.hook.OnStreamNotFoundHookParam;
//import com.mqttsnet.thinglinks.video.service.media.VideoMediaServerService;
//import org.springframework.context.ApplicationEvent;
//
///**
// * 发送流停止事件
// * @author mqttsnet
// */
//public class MediaServerSendRtpStoppedEvent extends ApplicationEvent {
//    public MediaServerSendRtpStoppedEvent(Object source) {
//        super(source);
//    }
//
//    private String app;
//
//    private String stream;
//
//    private VideoMediaServerService videoMediaServerService;
//
//    public static MediaServerSendRtpStoppedEvent getInstance(Object source, OnStreamNotFoundHookParam hookParam, VideoMediaServerService mediaServer) {
//        MediaServerSendRtpStoppedEvent mediaDepartureEven = new MediaServerSendRtpStoppedEvent(source);
//        mediaDepartureEven.setApp(hookParam.getApp());
//        mediaDepartureEven.setStream(hookParam.getStream());
//        mediaDepartureEven.setMediaServer(mediaServer);
//        return mediaDepartureEven;
//    }
//
//    public String getApp() {
//        return app;
//    }
//
//    public void setApp(String app) {
//        this.app = app;
//    }
//
//    public String getStream() {
//        return stream;
//    }
//
//    public void setStream(String stream) {
//        this.stream = stream;
//    }
//
//    public VideoMediaServerService getMediaServer() {
//        return videoMediaServerService;
//    }
//
//    public void setMediaServer(VideoMediaServerService mediaServer) {
//        this.videoMediaServerService = mediaServer;
//    }
//}
