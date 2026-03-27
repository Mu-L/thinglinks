package com.mqttsnet.thinglinks.video.media.zlm;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.thinglinks.common.cache.video.media.MediaServerHookCacheKeyBuilder;
import com.mqttsnet.thinglinks.video.cache.CacheSuperAbstract;
import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZLMServerConfig;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZlmRestfulResult;
import com.mqttsnet.thinglinks.video.empowerment.media.VideoMediaServerTypeEnum;
import com.mqttsnet.thinglinks.video.media.server.event.publisher.MediaEventPublisher;
import com.mqttsnet.thinglinks.video.service.media.VideoMediaServerService;
import com.mqttsnet.thinglinks.video.utils.zlm.ZlmRestfulUtils;
import com.mqttsnet.thinglinks.video.vo.query.media.VideoMediaServerPageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

/**
 * 管理zlm流媒体节点的状态
 *
 * @author mqttsnet
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ZLMMediaServerStatusManager extends CacheSuperAbstract {

    private final CachePlusOps cachePlusOps;
    private final String mediaServerType = VideoMediaServerTypeEnum.ZLM.getValue();
    @Autowired
    private ZlmRestfulUtils zlmRestfulUtils;
    @Autowired
    private VideoMediaServerService videoMediaServerService;
    @Autowired
    private MediaEventPublisher eventPublisher;
    @Value("${media.hook-domain-prefix:http://127.0.0.1:18760/video/zlmHook/index/hook}")
    private String mediaHookDomainPrefix;

    @Scheduled(fixedRate = 60 * 1000)
    public void execute() {
        loadTenant((tenant, param) -> {
            final Long tenantId = tenant.getId();
            try {
                // ✅ 设置上下文
                ContextUtil.setTenantId(tenantId);
                // ✅ 触发执行缓存刷新
                executeMediaServerCacheRefresh(ContextUtil.getTenantIdStr());
            } catch (Exception e) {
                log.warn("租户ID: {} ,执行缓存刷新异常", tenantId, e);
            }
        });
    }

    private void executeMediaServerCacheRefresh(String tenantId) {
        //获取所有媒体服务器
        VideoMediaServerPageQuery mediaServerPageQuery = new VideoMediaServerPageQuery()
                .setType(mediaServerType);
        List<VideoMediaServerResultDTO> mediaServerResultDTOList = videoMediaServerService.getVideoMediaServerResultDTOList(mediaServerPageQuery);

        if (CollUtil.isEmpty(mediaServerResultDTOList)) {
            log.info("[获取媒体服务器列表] tenantId: {} ,未找到媒体服务器", tenantId);
            return;
        }

        mediaServerResultDTOList.forEach(mediaServerItem -> {
            CacheKey cacheKey = MediaServerHookCacheKeyBuilder.build(mediaServerType, mediaServerItem.getMediaIdentification());
            if (cachePlusOps.exists(cacheKey)) {
                log.info("[ZLM-心跳检测成功] ID：{}, 地址： {}:{}", mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
                return;
            }

            log.info("[ZLM-正在进行心跳检测] ID：{}, 地址： {}:{}", mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
            ZlmRestfulResult<JSONArray> zlmRestfulResult = zlmRestfulUtils.getMediaServerConfig(mediaServerItem);
            JSONArray data = zlmRestfulResult.getData();
            if (!zlmRestfulResult.isSuccess() || data == null || data.isEmpty()) {
                log.info("[ZLM-尝试连接]失败, ID：{}, 地址： {}:{}", mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
                //离线处理
                handleOffline(mediaServerItem);
                return;
            }
            ZLMServerConfig zlmServerConfig = JSON.parseObject(JSON.toJSONString(data.get(0)), ZLMServerConfig.class);
            initZLMMediaServerPort(mediaServerItem, zlmServerConfig);
            handleOnline(mediaServerItem, zlmServerConfig);
            // 心跳信息存储
            cachePlusOps.set(cacheKey, mediaServerItem);
        });

    }


    /**
     * 在线处理
     *
     * @param mediaServerItem 媒体服务器
     * @param config          配置
     */
    private void handleOnline(VideoMediaServerResultDTO mediaServerItem, ZLMServerConfig config) {
        //如果当前状态为离线、则进行连接
        if (mediaServerItem.getOnlineStatus()) {
            log.info("[ZLM-心跳检测成功] ID：{}, 地址： {}:{}", mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
            return;
        }
        log.info("[ZLM-重新连接成功] ID：{}, 地址： {}:{}", mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
        mediaServerItem.setOnlineStatus(true);
        mediaServerItem.setHookAliveInterval(60);
        // 发送上线通知
        eventPublisher.mediaServerOnlineEventPublish(mediaServerItem);
        // 自动配置
        if (mediaServerItem.getAutoConfig()) {
            if (null == config) {
                ZlmRestfulResult<JSONArray> zlmRestfulResult = zlmRestfulUtils.getMediaServerConfig(mediaServerItem);
                if (!zlmRestfulResult.isSuccess()) {
                    log.info("[ZLM-尝试连接]失败, ID：{}, 地址： {}:{}", mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
                    return;
                }
                JSONArray data = zlmRestfulResult.getData();
                if (null != data && !data.isEmpty()) {
                    config = JSON.parseObject(JSON.toJSONString(data.get(0)), ZLMServerConfig.class);
                }
            }
            if (null != config) {
                initZLMMediaServerPort(mediaServerItem, config);
                setZLMConfig(mediaServerItem, "0".equals(config.getHookEnable()) || !Objects.equals(mediaServerItem.getHookAliveInterval(), config.getHookAliveInterval()));
            }
        }
    }


    /**
     * 离线处理
     *
     * @param mediaServerItem 媒体服务器
     */
    private void handleOffline(VideoMediaServerResultDTO mediaServerItem) {
        log.warn("[ZLM-心跳超时] 更新zlm状态为离线 ID：{}", mediaServerItem.getMediaIdentification());
        mediaServerItem.setOnlineStatus(false);
        // 发送离线通知
        eventPublisher.mediaServerOfflineEventPublish(mediaServerItem);
    }

    private void initZLMMediaServerPort(VideoMediaServerResultDTO mediaServerItem, ZLMServerConfig zlmServerConfig) {
        // 端口只会从配置中读取一次，一旦自己配置或者读取过了将不在配置
        if (mediaServerItem.getHttpSslPort() == 0) {
            mediaServerItem.setHttpSslPort(zlmServerConfig.getHttpSSLport());
        }
        if (mediaServerItem.getRtmpPort() == 0) {
            mediaServerItem.setRtmpPort(zlmServerConfig.getRtmpPort());
        }
        if (mediaServerItem.getRtmpSslPort() == 0) {
            mediaServerItem.setRtmpSslPort(zlmServerConfig.getRtmpSslPort());
        }
        if (mediaServerItem.getRtspPort() == 0) {
            mediaServerItem.setRtspPort(zlmServerConfig.getRtspPort());
        }
        if (mediaServerItem.getRtspSslPort() == 0) {
            mediaServerItem.setRtspSslPort(zlmServerConfig.getRtspSSlport());
        }
        if (mediaServerItem.getRtpProxyPort() == 0) {
            mediaServerItem.setRtpProxyPort(zlmServerConfig.getRtpProxyPort());
        }
        if (mediaServerItem.getFlvSslPort() == 0) {
            mediaServerItem.setFlvSslPort(zlmServerConfig.getHttpSSLport());
        }
        if (mediaServerItem.getWsFlvSslPort() == 0) {
            mediaServerItem.setWsFlvSslPort(zlmServerConfig.getHttpSSLport());
        }
        if (Objects.isNull(zlmServerConfig.getTranscodeSuffix())) {
            mediaServerItem.setTranscodeSuffix(null);
        } else {
            mediaServerItem.setTranscodeSuffix(zlmServerConfig.getTranscodeSuffix());
        }
        mediaServerItem.setHookAliveInterval(10);
    }

    public void setZLMConfig(VideoMediaServerResultDTO mediaServerItem, boolean restart) {
        log.info("[媒体服务节点] 正在设置 ：{} -> {}:{}",
                mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
        Map<String, Object> param = new HashMap<>();
        // -profile:v Baseline
        param.put("api.secret", mediaServerItem.getSecret());
        if (mediaServerItem.getRtspPort() != 0) {
            param.put("ffmpeg.snap", "%s -rtsp_transport tcp -i %s -y -f mjpeg -frames:v 1 %s");
        }
        param.put("hook.enable", "1");
        param.put("hook.on_flow_report", "");
        param.put("hook.on_play", String.format("%s/on_play", mediaHookDomainPrefix));
        param.put("hook.on_http_access", "");
        param.put("hook.on_publish", String.format("%s/on_publish", mediaHookDomainPrefix));
        param.put("hook.on_record_ts", "");
        param.put("hook.on_rtsp_auth", "");
        param.put("hook.on_rtsp_realm", "");
        param.put("hook.on_server_started", String.format("%s/on_server_started", mediaHookDomainPrefix));
        param.put("hook.on_shell_login", "");
        param.put("hook.on_stream_changed", String.format("%s/on_stream_changed", mediaHookDomainPrefix));
        param.put("hook.on_stream_none_reader", String.format("%s/on_stream_none_reader", mediaHookDomainPrefix));
        param.put("hook.on_stream_not_found", String.format("%s/on_stream_not_found", mediaHookDomainPrefix));
        param.put("hook.on_server_keepalive", String.format("%s/on_server_keepalive", mediaHookDomainPrefix));
        param.put("hook.on_send_rtp_stopped", String.format("%s/on_send_rtp_stopped", mediaHookDomainPrefix));
        param.put("hook.on_rtp_server_timeout", String.format("%s/on_rtp_server_timeout", mediaHookDomainPrefix));
        param.put("hook.on_record_mp4", String.format("%s/on_record_mp4", mediaHookDomainPrefix));
        param.put("hook.timeoutSec", "30");
        param.put("hook.alive_interval", mediaServerItem.getHookAliveInterval());
        // 推流断开后可以在超时时间内重新连接上继续推流，这样播放器会接着播放。
        // 置0关闭此特性(推流断开会导致立即断开播放器)
        // 此参数不应大于播放器超时时间
        // 优化此消息以更快的收到流注销事件
        param.put("protocol.continue_push_ms", "3000");
        // 最多等待未初始化的Track时间，单位毫秒，超时之后会忽略未初始化的Track, 设置此选项优化那些音频错误的不规范流，
        // 等zlm支持给每个rtpServer设置关闭音频的时候可以不设置此选项
        if (mediaServerItem.getRtpEnable() && !ObjectUtils.isEmpty(mediaServerItem.getRtpPortRange())) {
            param.put("rtp_proxy.port_range", mediaServerItem.getRtpPortRange().replace(",", "-"));
        } else {
            param.put("rtp_proxy.port", mediaServerItem.getRtpProxyPort());
        }

        if (!ObjectUtils.isEmpty(mediaServerItem.getRecordPath())) {
            File recordPathFile = new File(mediaServerItem.getRecordPath());
            param.put("protocol.mp4_save_path", recordPathFile.getParentFile().getPath());
            param.put("protocol.downloadRoot", recordPathFile.getParentFile().getPath());
            param.put("record.appName", recordPathFile.getName());
        }

        ZlmRestfulResult<JSONObject> zlmRestfulResult = zlmRestfulUtils.setServerConfig(mediaServerItem, param);

        if (zlmRestfulResult.isSuccess()) {
            if (restart) {
                log.info("[媒体服务节点] 设置成功,开始重启以保证配置生效 {} -> {}:{}",
                        mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
                zlmRestfulUtils.restartServer(mediaServerItem);
            } else {
                log.info("[媒体服务节点] 设置成功 {} -> {}:{}",
                        mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
            }
        } else {
            log.info("[媒体服务节点] 设置媒体服务节点失败 {} -> {}:{}",
                    mediaServerItem.getMediaIdentification(), mediaServerItem.getIp(), mediaServerItem.getHttpPort());
        }
    }

}
