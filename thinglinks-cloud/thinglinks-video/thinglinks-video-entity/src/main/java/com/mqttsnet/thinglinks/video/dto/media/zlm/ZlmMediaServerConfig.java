package com.mqttsnet.thinglinks.video.dto.media.zlm;

import com.alibaba.fastjson2.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * -----------------------------------------------------------------------------
 * File Name: MediaServerConfig
 * -----------------------------------------------------------------------------
 * Description:
 * MediaServerConfig
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/6       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/6 17:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(description = "流媒体服务器信息表")
public class ZlmMediaServerConfig implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @JSONField(name = "api.apiDebug")
    @Schema(description = "API调试模式")
    private String apiDebug;

    @JSONField(name = "api.defaultSnap")
    @Schema(description = "默认截图路径")
    private String apiDefaultSnap;

    @JSONField(name = "api.downloadRoot")
    @Schema(description = "下载根路径")
    private String apiDownloadRoot;

    @JSONField(name = "api.secret")
    @Schema(description = "API密钥")
    private String apiSecret;

    @JSONField(name = "api.snapRoot")
    @Schema(description = "截图根路径")
    private String apiSnapRoot;

    @JSONField(name = "cluster.origin_url")
    @Schema(description = "集群原始URL")
    private String clusterOriginUrl;

    @JSONField(name = "cluster.retry_count")
    @Schema(description = "集群重试次数")
    private String clusterRetryCount;

    @JSONField(name = "cluster.timeout_sec")
    @Schema(description = "集群超时时间（秒）")
    private String clusterTimeoutSec;

    @JSONField(name = "ffmpeg.bin")
    @Schema(description = "FFmpeg二进制路径")
    private String ffmpegBin;

    @JSONField(name = "ffmpeg.cmd")
    @Schema(description = "FFmpeg命令")
    private String ffmpegCmd;

    @JSONField(name = "ffmpeg.log")
    @Schema(description = "FFmpeg日志路径")
    private String ffmpegLog;

    @JSONField(name = "ffmpeg.restart_sec")
    @Schema(description = "FFmpeg重启间隔（秒）")
    private String ffmpegRestartSec;

    @JSONField(name = "ffmpeg.snap")
    @Schema(description = "FFmpeg截图命令")
    private String ffmpegSnap;

    @JSONField(name = "general.broadcast_player_count_changed")
    @Schema(description = "广播播放器计数变化")
    private String generalBroadcastPlayerCountChanged;

    @JSONField(name = "general.check_nvidia_dev")
    @Schema(description = "检查Nvidia设备")
    private String generalCheckNvidiaDev;

    @JSONField(name = "general.enableVhost")
    @Schema(description = "启用虚拟主机")
    private String generalEnableVhost;

    @JSONField(name = "general.enable_ffmpeg_log")
    @Schema(description = "启用FFmpeg日志")
    private String generalEnableFfmpegLog;

    @JSONField(name = "general.flowThreshold")
    @Schema(description = "流量阈值")
    private String generalFlowThreshold;

    @JSONField(name = "general.maxStreamWaitMS")
    @Schema(description = "最大流等待时间（毫秒）")
    private String generalMaxStreamWaitMS;

    @JSONField(name = "general.mediaServerId")
    @Schema(description = "媒体服务器ID")
    private String generalMediaServerId;

    @JSONField(name = "general.mergeWriteMS")
    @Schema(description = "合并写入时间（毫秒）")
    private String generalMergeWriteMS;

    @JSONField(name = "general.resetWhenRePlay")
    @Schema(description = "重新播放时重置")
    private String generalResetWhenRePlay;

    @JSONField(name = "general.streamNoneReaderDelayMS")
    @Schema(description = "无读取器流延迟（毫秒）")
    private String generalStreamNoneReaderDelayMS;

    @JSONField(name = "general.unready_frame_cache")
    @Schema(description = "未准备好帧缓存")
    private String generalUnreadyFrameCache;

    @JSONField(name = "general.wait_add_track_ms")
    @Schema(description = "等待添加轨道时间（毫秒）")
    private String generalWaitAddTrackMs;

    @JSONField(name = "general.wait_track_ready_ms")
    @Schema(description = "等待轨道准备时间（毫秒）")
    private String generalWaitTrackReadyMs;

    @JSONField(name = "hls.broadcastRecordTs")
    @Schema(description = "广播记录TS")
    private String hlsBroadcastRecordTs;

    @JSONField(name = "hls.deleteDelaySec")
    @Schema(description = "删除延迟时间（秒）")
    private String hlsDeleteDelaySec;

    @JSONField(name = "hls.fastRegister")
    @Schema(description = "快速注册")
    private String hlsFastRegister;

    @JSONField(name = "hls.fileBufSize")
    @Schema(description = "文件缓冲区大小")
    private String hlsFileBufSize;

    @JSONField(name = "hls.segDelay")
    @Schema(description = "分段延迟")
    private String hlsSegDelay;

    @JSONField(name = "hls.segDur")
    @Schema(description = "分段持续时间")
    private String hlsSegDur;

    @JSONField(name = "hls.segKeep")
    @Schema(description = "分段保持")
    private String hlsSegKeep;

    @JSONField(name = "hls.segNum")
    @Schema(description = "分段数量")
    private String hlsSegNum;

    @JSONField(name = "hls.segRetain")
    @Schema(description = "分段保留")
    private String hlsSegRetain;

    @JSONField(name = "hook.alive_interval")
    @Schema(description = "存活间隔")
    private String hookAliveInterval;

    @JSONField(name = "hook.enable")
    @Schema(description = "启用钩子")
    private String hookEnable;

    @JSONField(name = "hook.on_flow_report")
    @Schema(description = "流报告钩子")
    private String hookOnFlowReport;

    @JSONField(name = "hook.on_http_access")
    @Schema(description = "HTTP访问钩子")
    private String hookOnHttpAccess;

    @JSONField(name = "hook.on_play")
    @Schema(description = "播放钩子")
    private String hookOnPlay;

    @JSONField(name = "hook.on_publish")
    @Schema(description = "发布钩子")
    private String hookOnPublish;

    @JSONField(name = "hook.on_record_mp4")
    @Schema(description = "MP4记录钩子")
    private String hookOnRecordMp4;

    @JSONField(name = "hook.on_record_ts")
    @Schema(description = "TS记录钩子")
    private String hookOnRecordTs;

    @JSONField(name = "hook.on_rtp_server_timeout")
    @Schema(description = "RTP服务器超时钩子")
    private String hookOnRtpServerTimeout;

    @JSONField(name = "hook.on_rtsp_auth")
    @Schema(description = "RTSP认证钩子")
    private String hookOnRtspAuth;

    @JSONField(name = "hook.on_rtsp_realm")
    @Schema(description = "RTSP领域钩子")
    private String hookOnRtspRealm;

    @JSONField(name = "hook.on_send_rtp_stopped")
    @Schema(description = "停止发送RTP钩子")
    private String hookOnSendRtpStopped;

    @JSONField(name = "hook.on_server_exited")
    @Schema(description = "服务器退出钩子")
    private String hookOnServerExited;

    @JSONField(name = "hook.on_server_keepalive")
    @Schema(description = "服务器保活钩子")
    private String hookOnServerKeepalive;

    @JSONField(name = "hook.on_server_started")
    @Schema(description = "服务器启动钩子")
    private String hookOnServerStarted;

    @JSONField(name = "hook.on_shell_login")
    @Schema(description = "Shell登录钩子")
    private String hookOnShellLogin;

    @JSONField(name = "hook.on_stream_changed")
    @Schema(description = "流改变钩子")
    private String hookOnStreamChanged;

    @JSONField(name = "hook.on_stream_none_reader")
    @Schema(description = "无读取器流钩子")
    private String hookOnStreamNoneReader;

    @JSONField(name = "hook.on_stream_not_found")
    @Schema(description = "流未找到钩子")
    private String hookOnStreamNotFound;

    @JSONField(name = "hook.retry")
    @Schema(description = "钩子重试")
    private String hookRetry;

    @JSONField(name = "hook.retry_delay")
    @Schema(description = "钩子重试延迟")
    private String hookRetryDelay;

    @JSONField(name = "hook.stream_changed_schemas")
    @Schema(description = "流改变架构")
    private String hookStreamChangedSchemas;

    @JSONField(name = "hook.timeoutSec")
    @Schema(description = "钩子超时时间（秒）")
    private String hookTimeoutSec;

    @JSONField(name = "http.allow_cross_domains")
    @Schema(description = "允许跨域")
    private String httpAllowCrossDomains;

    @JSONField(name = "http.allow_ip_range")
    @Schema(description = "允许IP范围")
    private String httpAllowIpRange;

    @JSONField(name = "http.charSet")
    @Schema(description = "字符集")
    private String httpCharSet;

    @JSONField(name = "http.dirMenu")
    @Schema(description = "目录菜单")
    private String httpDirMenu;

    @JSONField(name = "http.forbidCacheSuffix")
    @Schema(description = "禁止缓存后缀")
    private String httpForbidCacheSuffix;

    @JSONField(name = "http.forwarded_ip_header")
    @Schema(description = "转发IP头")
    private String httpForwardedIpHeader;

    @JSONField(name = "http.keepAliveSecond")
    @Schema(description = "保持活动时间（秒）")
    private String httpKeepAliveSecond;

    @JSONField(name = "http.maxReqSize")
    @Schema(description = "最大请求大小")
    private String httpMaxReqSize;

    @JSONField(name = "http.notFound")
    @Schema(description = "404未找到页面")
    private String httpNotFound;

    @JSONField(name = "http.port")
    @Schema(description = "HTTP端口")
    private String httpPort;

    @JSONField(name = "http.rootPath")
    @Schema(description = "根路径")
    private String httpRootPath;

    @JSONField(name = "http.sendBufSize")
    @Schema(description = "发送缓冲区大小")
    private String httpSendBufSize;

    @JSONField(name = "http.sslport")
    @Schema(description = "HTTPS端口")
    private String httpSslport;

    @JSONField(name = "http.virtualPath")
    @Schema(description = "虚拟路径")
    private String httpVirtualPath;

    @JSONField(name = "multicast.addrMax")
    @Schema(description = "多播最大地址")
    private String multicastAddrMax;

    @JSONField(name = "multicast.addrMin")
    @Schema(description = "多播最小地址")
    private String multicastAddrMin;

    @JSONField(name = "multicast.udpTTL")
    @Schema(description = "多播UDP TTL")
    private String multicastUdpTTL;

    @JSONField(name = "protocol.add_mute_audio")
    @Schema(description = "添加静音音频")
    private String protocolAddMuteAudio;

    @JSONField(name = "protocol.auto_close")
    @Schema(description = "自动关闭")
    private String protocolAutoClose;

    @JSONField(name = "protocol.continue_push_ms")
    @Schema(description = "持续推送时间（毫秒）")
    private String protocolContinuePushMs;

    @JSONField(name = "protocol.enable_audio")
    @Schema(description = "启用音频")
    private String protocolEnableAudio;

    @JSONField(name = "protocol.enable_fmp4")
    @Schema(description = "启用FMP4")
    private String protocolEnableFmp4;

    @JSONField(name = "protocol.enable_hls")
    @Schema(description = "启用HLS")
    private String protocolEnableHls;

    @JSONField(name = "protocol.enable_hls_fmp4")
    @Schema(description = "启用HLS FMP4")
    private String protocolEnableHlsFmp4;

    @JSONField(name = "protocol.enable_mp4")
    @Schema(description = "启用MP4")
    private String protocolEnableMp4;

    @JSONField(name = "protocol.enable_rtmp")
    @Schema(description = "启用RTMP")
    private String protocolEnableRtmp;

    @JSONField(name = "protocol.enable_rtsp")
    @Schema(description = "启用RTSP")
    private String protocolEnableRtsp;

    @JSONField(name = "protocol.enable_ts")
    @Schema(description = "启用TS")
    private String protocolEnableTs;

    @JSONField(name = "protocol.fmp4_demand")
    @Schema(description = "FMP4需求")
    private String protocolFmp4Demand;

    @JSONField(name = "protocol.hls_demand")
    @Schema(description = "HLS需求")
    private String protocolHlsDemand;

    @JSONField(name = "protocol.hls_save_path")
    @Schema(description = "HLS保存路径")
    private String protocolHlsSavePath;

    @JSONField(name = "protocol.modify_stamp")
    @Schema(description = "修改时间戳")
    private String protocolModifyStamp;

    @JSONField(name = "protocol.mp4_as_player")
    @Schema(description = "MP4作为播放器")
    private String protocolMp4AsPlayer;

    @JSONField(name = "protocol.mp4_max_second")
    @Schema(description = "MP4最大秒数")
    private String protocolMp4MaxSecond;

    @JSONField(name = "protocol.mp4_save_path")
    @Schema(description = "MP4保存路径")
    private String protocolMp4SavePath;

    @JSONField(name = "protocol.paced_sender_ms")
    @Schema(description = "节奏发送器时间（毫秒）")
    private String protocolPacedSenderMs;

    @JSONField(name = "protocol.rtmp_demand")
    @Schema(description = "RTMP需求")
    private String protocolRtmpDemand;

    @JSONField(name = "protocol.rtsp_demand")
    @Schema(description = "RTSP需求")
    private String protocolRtspDemand;

    @JSONField(name = "protocol.ts_demand")
    @Schema(description = "TS需求")
    private String protocolTsDemand;

    @JSONField(name = "record.appName")
    @Schema(description = "记录应用名称")
    private String recordAppName;

    @JSONField(name = "record.enableFmp4")
    @Schema(description = "启用FMP4记录")
    private String recordEnableFmp4;

    @JSONField(name = "record.fastStart")
    @Schema(description = "快速启动记录")
    private String recordFastStart;

    @JSONField(name = "record.fileBufSize")
    @Schema(description = "记录文件缓冲区大小")
    private String recordFileBufSize;

    @JSONField(name = "record.fileRepeat")
    @Schema(description = "记录文件重复")
    private String recordFileRepeat;

    @JSONField(name = "record.sampleMS")
    @Schema(description = "记录采样时间（毫秒）")
    private String recordSampleMs;

    @JSONField(name = "rtc.datachannel_echo")
    @Schema(description = "RTC数据通道回显")
    private String rtcDatachannelEcho;

    @JSONField(name = "rtc.externIP")
    @Schema(description = "RTC外部IP")
    private String rtcExternIp;

    @JSONField(name = "rtc.maxNackMS")
    @Schema(description = "RTC最大NACK时间（毫秒）")
    private String rtcMaxNackMs;

    @JSONField(name = "rtc.max_bitrate")
    @Schema(description = "RTC最大比特率")
    private String rtcMaxBitrate;

    @JSONField(name = "rtc.min_bitrate")
    @Schema(description = "RTC最小比特率")
    private String rtcMinBitrate;

    @JSONField(name = "rtc.nackIntervalRatio")
    @Schema(description = "RTC NACK间隔比率")
    private String rtcNackIntervalRatio;

    @JSONField(name = "rtc.nackMaxCount")
    @Schema(description = "RTC最大NACK计数")
    private String rtcNackMaxCount;

    @JSONField(name = "rtc.nackMaxMS")
    @Schema(description = "RTC最大NACK时间（毫秒）")
    private String rtcNackMaxMs;

    @JSONField(name = "rtc.nackMaxSize")
    @Schema(description = "RTC最大NACK大小")
    private String rtcNackMaxSize;

    @JSONField(name = "rtc.nackRtpSize")
    @Schema(description = "RTC NACK RTP大小")
    private String rtcNackRtpSize;

    @JSONField(name = "rtc.port")
    @Schema(description = "RTC端口")
    private String rtcPort;

    @JSONField(name = "rtc.preferredCodecA")
    @Schema(description = "RTC首选音频编解码器")
    private String rtcPreferredCodecA;

    @JSONField(name = "rtc.preferredCodecV")
    @Schema(description = "RTC首选视频编解码器")
    private String rtcPreferredCodecV;

    @JSONField(name = "rtc.rembBitRate")
    @Schema(description = "RTC REMB比特率")
    private String rtcRembBitRate;

    @JSONField(name = "rtc.rtpCacheCheckInterval")
    @Schema(description = "RTC RTP缓存检查间隔")
    private String rtcRtpCacheCheckInterval;

    @JSONField(name = "rtc.start_bitrate")
    @Schema(description = "RTC起始比特率")
    private String rtcStartBitrate;

    @JSONField(name = "rtc.tcpPort")
    @Schema(description = "RTC TCP端口")
    private String rtcTcpPort;

    @JSONField(name = "rtc.timeoutSec")
    @Schema(description = "RTC超时时间（秒）")
    private String rtcTimeoutSec;

    @JSONField(name = "rtmp.directProxy")
    @Schema(description = "RTMP直接代理")
    private String rtmpDirectProxy;

    @JSONField(name = "rtmp.enhanced")
    @Schema(description = "增强的RTMP")
    private String rtmpEnhanced;

    @JSONField(name = "rtmp.handshakeSecond")
    @Schema(description = "RTMP握手时间（秒）")
    private String rtmpHandshakeSecond;

    @JSONField(name = "rtmp.keepAliveSecond")
    @Schema(description = "RTMP保持活动时间（秒）")
    private String rtmpKeepAliveSecond;

    @JSONField(name = "rtmp.port")
    @Schema(description = "RTMP端口")
    private String rtmpPort;

    @JSONField(name = "rtmp.sslport")
    @Schema(description = "RTMP SSL端口")
    private String rtmpSslport;

    @JSONField(name = "rtp.audioMtuSize")
    @Schema(description = "RTP音频MTU大小")
    private String rtpAudioMtuSize;

    @JSONField(name = "rtp.h264_stap_a")
    @Schema(description = "RTP H264 STAP-A")
    private String rtpH264StapA;

    @JSONField(name = "rtp.lowLatency")
    @Schema(description = "RTP低延迟")
    private String rtpLowLatency;

    @JSONField(name = "rtp.rtpMaxSize")
    @Schema(description = "RTP最大大小")
    private String rtpRtpMaxSize;

    @JSONField(name = "rtp.videoMtuSize")
    @Schema(description = "RTP视频MTU大小")
    private String rtpVideoMtuSize;

    @JSONField(name = "rtp_proxy.dumpDir")
    @Schema(description = "RTP代理转储目录")
    private String rtpProxyDumpDir;

    @JSONField(name = "rtp_proxy.gop_cache")
    @Schema(description = "RTP代理GOP缓存")
    private String rtpProxyGopCache;

    @JSONField(name = "rtp_proxy.h264_pt")
    @Schema(description = "RTP代理H264 PT")
    private String rtpProxyH264Pt;

    @JSONField(name = "rtp_proxy.h265_pt")
    @Schema(description = "RTP代理H265 PT")
    private String rtpProxyH265Pt;

    @JSONField(name = "rtp_proxy.opus_pt")
    @Schema(description = "RTP代理Opus PT")
    private String rtpProxyOpusPt;

    @JSONField(name = "rtp_proxy.port")
    @Schema(description = "RTP代理端口")
    private String rtpProxyPort;

    @JSONField(name = "rtp_proxy.port_range")
    @Schema(description = "RTP代理端口范围")
    private String rtpProxyPortRange;

    @JSONField(name = "rtp_proxy.ps_pt")
    @Schema(description = "RTP代理PS PT")
    private String rtpProxyPsPt;

    @JSONField(name = "rtp_proxy.rtp_g711_dur_ms")
    @Schema(description = "RTP代理G711持续时间（毫秒）")
    private String rtpProxyRtpG711DurMs;

    @JSONField(name = "rtp_proxy.timeoutSec")
    @Schema(description = "RTP代理超时时间（秒）")
    private String rtpProxyTimeoutSec;

    @JSONField(name = "rtp_proxy.udp_recv_socket_buffer")
    @Schema(description = "RTP代理UDP接收缓冲区")
    private String rtpProxyUdpRecvSocketBuffer;

    @JSONField(name = "rtsp.authBasic")
    @Schema(description = "RTSP基本认证")
    private String rtspAuthBasic;

    @JSONField(name = "rtsp.directProxy")
    @Schema(description = "RTSP直接代理")
    private String rtspDirectProxy;

    @JSONField(name = "rtsp.handshakeSecond")
    @Schema(description = "RTSP握手时间（秒）")
    private String rtspHandshakeSecond;

    @JSONField(name = "rtsp.keepAliveSecond")
    @Schema(description = "RTSP保持活动时间（秒）")
    private String rtspKeepAliveSecond;

    @JSONField(name = "rtsp.lowLatency")
    @Schema(description = "RTSP低延迟")
    private String rtspLowLatency;

    @JSONField(name = "rtsp.port")
    @Schema(description = "RTSP端口")
    private String rtspPort;

    @JSONField(name = "rtsp.rtpTransportType")
    @Schema(description = "RTSP RTP传输类型")
    private String rtspRtpTransportType;

    @JSONField(name = "rtsp.sslport")
    @Schema(description = "RTSP SSL端口")
    private String rtspSslport;

    @JSONField(name = "shell.maxReqSize")
    @Schema(description = "Shell最大请求大小")
    private String shellMaxReqSize;

    @JSONField(name = "shell.port")
    @Schema(description = "Shell端口")
    private String shellPort;

    @JSONField(name = "srt.latencyMul")
    @Schema(description = "SRT延迟乘数")
    private String srtLatencyMul;

    @JSONField(name = "srt.pktBufSize")
    @Schema(description = "SRT包缓冲区大小")
    private String srtPktBufSize;

    @JSONField(name = "srt.port")
    @Schema(description = "SRT端口")
    private String srtPort;

    @JSONField(name = "srt.timeoutSec")
    @Schema(description = "SRT超时时间（秒）")
    private String srtTimeoutSec;

}
