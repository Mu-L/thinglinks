package com.mqttsnet.thinglinks.video.config.gb28181;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * sip 服务器配置
 *
 * @author mqttsnet
 */
@Component
@ConfigurationProperties(prefix = "sip", ignoreInvalidFields = true)
@Order(0)
@Data
public class SipConfig {

    Integer ptzSpeed = 50;
    Integer registerTimeInterval = 120;
    private String ip;
    private String showIp;
    private List<String> monitorIps;
    private Integer port;
    private String domain;
    private String id;
    private String password;
    private Boolean alarm = false;

    private Long timeout = 1000L;
}
