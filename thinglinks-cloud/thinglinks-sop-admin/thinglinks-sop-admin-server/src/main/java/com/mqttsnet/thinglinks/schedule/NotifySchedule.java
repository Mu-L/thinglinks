package com.mqttsnet.thinglinks.schedule;

import com.mqttsnet.thinglinks.sop.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class NotifySchedule {

    @Autowired
    private NotifyService notifyService;

    /**
     * 每分钟执行一次
     */
    @Scheduled(cron = "0 0/1 * * * ?")
    public void run() {
        notifyService.retry(LocalDateTime.now());
    }

}
