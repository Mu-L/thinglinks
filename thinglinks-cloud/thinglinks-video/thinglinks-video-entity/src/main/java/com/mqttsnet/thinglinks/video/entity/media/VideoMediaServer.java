package com.mqttsnet.thinglinks.video.entity.media;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 流媒体服务器信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-03 17:56:38
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("video_media_server")
public class VideoMediaServer extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;

    /**
     * 媒体唯一标识
     */
    @TableField(value = "media_identification", condition = EQUAL)
    private String mediaIdentification;
    /**
     * 服务器IP地址
     */
    @TableField(value = "ip", condition = LIKE)
    private String ip;
    /**
     * hook使用的IP（zlm访问客户端使用的IP）
     */
    @TableField(value = "hook_ip", condition = LIKE)
    private String hookIp;
    /**
     * SDP IP地址
     */
    @TableField(value = "sdp_ip", condition = LIKE)
    private String sdpIp;
    /**
     * 流IP地址
     */
    @TableField(value = "stream_ip", condition = LIKE)
    private String streamIp;
    /**
     * HTTP端口
     */
    @TableField(value = "http_port", condition = EQUAL)
    private Integer httpPort;
    /**
     * HTTPS端口
     */
    @TableField(value = "http_ssl_port", condition = EQUAL)
    private Integer httpSslPort;
    /**
     * RTMP端口
     */
    @TableField(value = "rtmp_port", condition = EQUAL)
    private Integer rtmpPort;
    /**
     * RTMP SSL端口
     */
    @TableField(value = "rtmp_ssl_port", condition = EQUAL)
    private Integer rtmpSslPort;
    /**
     * RTP代理端口（单端口模式）
     */
    @TableField(value = "rtp_proxy_port", condition = EQUAL)
    private Integer rtpProxyPort;
    /**
     * RTSP端口
     */
    @TableField(value = "rtsp_port", condition = EQUAL)
    private Integer rtspPort;
    /**
     * RTSP SSL端口
     */
    @TableField(value = "rtsp_ssl_port", condition = EQUAL)
    private Integer rtspSslPort;
    /**
     * FLV端口
     */
    @TableField(value = "flv_port", condition = EQUAL)
    private Integer flvPort;
    /**
     * FLV SSL端口
     */
    @TableField(value = "flv_ssl_port", condition = EQUAL)
    private Integer flvSslPort;
    /**
     * WebSocket FLV端口
     */
    @TableField(value = "ws_flv_port", condition = EQUAL)
    private Integer wsFlvPort;
    /**
     * WebSocket FLV SSL端口
     */
    @TableField(value = "ws_flv_ssl_port", condition = EQUAL)
    private Integer wsFlvSslPort;
    /**
     * 是否开启自动配置ZLM
     */
    @TableField(value = "auto_config", condition = EQUAL)
    private Boolean autoConfig;
    /**
     * ZLM鉴权参数
     */
    @TableField(value = "secret", condition = LIKE)
    private String secret;
    /**
     * 类型（zlm/abl）
     */
    @TableField(value = "type", condition = LIKE)
    private String type;
    /**
     * 是否启用多端口模式
     */
    @TableField(value = "rtp_enable", condition = EQUAL)
    private Boolean rtpEnable;
    /**
     * 多端口RTP收流端口范围
     */
    @TableField(value = "rtp_port_range", condition = LIKE)
    private String rtpPortRange;
    /**
     * RTP发流端口范围
     */
    @TableField(value = "send_rtp_port_range", condition = LIKE)
    private String sendRtpPortRange;
    /**
     * 录制辅助服务端口
     */
    @TableField(value = "record_assist_port", condition = EQUAL)
    private Integer recordAssistPort;
    /**
     * 是否是默认ZLM服务器
     */
    @TableField(value = "default_server", condition = EQUAL)
    private Boolean defaultServer;
    /**
     * 上次心跳时间
     */
    @TableField(value = "last_alive_time", condition = EQUAL)
    private LocalDateTime lastAliveTime;
    /**
     * keepalive hook触发间隔，单位秒
     */
    @TableField(value = "hook_alive_interval", condition = EQUAL)
    private Integer hookAliveInterval;
    /**
     * 录像存储路径
     */
    @TableField(value = "record_path", condition = LIKE)
    private String recordPath;
    /**
     * 录像存储时长（天）
     */
    @TableField(value = "record_day", condition = EQUAL)
    private Integer recordDay;
    /**
     * 转码的前缀
     */
    @TableField(value = "transcode_suffix", condition = LIKE)
    private String transcodeSuffix;
    /**
     * 备注
     */
    @TableField(value = "remark", condition = LIKE)
    private String remark;
    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id", condition = EQUAL)
    private Long createdOrgId;

    /**
     * 在线状态
     */
    @TableField(value = "online_status", condition = EQUAL)
    private Boolean onlineStatus;

    /**
     * 扩展参数
     */
    @TableField(value = "extend_params", condition = LIKE)
    private String extendParams;
    /**
     * 名称
     */
    @TableField(value = "name", condition = LIKE)
    private String name;


}
