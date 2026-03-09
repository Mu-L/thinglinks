package com.mqttsnet.thinglinks.video.media.server.event.hook;

import com.mqttsnet.thinglinks.video.dto.media.zlm.hook.Hook;
import com.mqttsnet.thinglinks.video.empowerment.hook.HookTypeEnum;
import com.mqttsnet.thinglinks.video.media.server.event.media.MediaArrivalEvent;
import com.mqttsnet.thinglinks.video.media.server.event.media.MediaDepartureEvent;
import com.mqttsnet.thinglinks.video.media.server.event.media.MediaEvent;
import com.mqttsnet.thinglinks.video.media.server.event.media.MediaPublishEvent;
import com.mqttsnet.thinglinks.video.media.server.event.media.MediaRecordMp4Event;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * zlm hook事件的参数
 * @author mqttsnet
 */
@Component
public class HookSubscribe {

    /**
     * 订阅数据过期时间
     */
    private final long subscribeExpire = 5 * 60 * 1000;
    private final Map<String, Event> allSubscribes = new ConcurrentHashMap<>();
    private final Map<String, Hook> allHook = new ConcurrentHashMap<>();

    /**
     * 流到来的处理
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaArrivalEvent event) {
        if (event.getSchema() == null || "rtsp".equals(event.getSchema())) {
            sendNotify(HookTypeEnum.on_media_arrival, event);
        }

    }

    /**
     * 流结束事件
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaDepartureEvent event) {
        if (event.getSchema() == null || "rtsp".equals(event.getSchema())) {
            sendNotify(HookTypeEnum.on_media_departure, event);
        }

    }

    /**
     * 推流鉴权事件
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaPublishEvent event) {
        sendNotify(HookTypeEnum.on_publish, event);
    }

    /**
     * 生成录像文件事件
     */
    @Async("taskExecutor")
    @EventListener
    public void onApplicationEvent(MediaRecordMp4Event event) {
        sendNotify(HookTypeEnum.on_record_mp4, event);
    }

    private void sendNotify(HookTypeEnum hookType, MediaEvent event) {
        Hook paramHook = Hook.getInstance(hookType, event.getApp(), event.getStream());
        Event hookSubscribeEvent = allSubscribes.get(paramHook.toString());
        if (hookSubscribeEvent != null) {
            HookData data = HookData.getInstance(event);
            hookSubscribeEvent.response(data);
        }
    }

    public void addSubscribe(Hook hook, Event event) {
        if (hook.getExpireTime() == null) {
            hook.setExpireTime(System.currentTimeMillis() + subscribeExpire);
        }
        allSubscribes.put(hook.toString(), event);
        allHook.put(hook.toString(), hook);
    }

    public void removeSubscribe(Hook hook) {
        allSubscribes.remove(hook.toString());
        allHook.remove(hook.toString());
    }

    /**
     * 对订阅数据进行过期清理
     */
    @Scheduled(fixedRate = subscribeExpire)   //每5分钟执行一次
    public void execute() {
        long expireTime = System.currentTimeMillis();
        for (Hook hook : allHook.values()) {
            if (hook.getExpireTime() < expireTime) {
                allSubscribes.remove(hook.toString());
                allHook.remove(hook.toString());
            }
        }
    }

    public List<Hook> getAll() {
        return new ArrayList<>(allHook.values());
    }

    @FunctionalInterface
    public interface Event {
        void response(HookData data);
    }
}
