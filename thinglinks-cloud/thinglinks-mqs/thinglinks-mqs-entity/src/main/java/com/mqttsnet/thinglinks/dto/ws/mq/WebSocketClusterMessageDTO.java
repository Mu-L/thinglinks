package com.mqttsnet.thinglinks.dto.ws.mq;

import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.thinglinks.enumeration.WsMessageTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * ============================================================================
 * Description:
 * <p>
 *  webSocket 集群消息
 * ============================================================================
 *
 * @author mqttsnet
 * @version 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebSocketClusterMessageDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String messageId;

    private WsMessageTypeEnum messageType;

    private ClusterNode nodeInfo;

    private LocalDateTime sendTime = LocalDateTime.now();

    public static WebSocketClusterMessageDTO createInstanceEvent(boolean isUp) throws UnknownHostException {
        return WebSocketClusterMessageDTO.builder()
                .messageId(SnowflakeIdUtil.nextId())
                .messageType(isUp ? WsMessageTypeEnum.INSTANCE_UP : WsMessageTypeEnum.INSTANCE_DOWN)
                .nodeInfo(new ClusterNode(
                        InetAddress.getLocalHost().getHostAddress(),
                        new Date()))
                .build();
    }

    @Data
    @AllArgsConstructor
    public static class ClusterNode implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;
        private String serverIp;
        private Date registerTime;
    }
}
