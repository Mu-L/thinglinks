package com.mqttsnet.thinglinks.video.service.media.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.video.constants.ZlmMediaConstants;
import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZlmMediaInfo;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZlmMediaListInfo;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZlmMediaServerConfig;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZlmMediaServerStreamInfo;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZlmRestfulResult;
import com.mqttsnet.thinglinks.video.empowerment.media.VideoMediaServerTypeEnum;
import com.mqttsnet.thinglinks.video.service.media.MediaNodeServerService;
import com.mqttsnet.thinglinks.video.utils.zlm.ZlmRestfulUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * -----------------------------------------------------------------------------
 * File Name: MediaNodeServerServiceImpl
 * -----------------------------------------------------------------------------
 * Description:
 * 多媒体节点服务接口实现类
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
 * @date 2024/7/6 16:32
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class MediaNodeServerServiceImpl implements MediaNodeServerService {

    private final ZlmRestfulUtils zlmRestfulUtils;

    @Override
    public VideoMediaServerResultDTO checkMediaServerConfig(String ip, Integer port, String secret) {
        VideoMediaServerResultDTO videoMediaServerResultDTO = new VideoMediaServerResultDTO();
        videoMediaServerResultDTO.setIp(ip);
        videoMediaServerResultDTO.setHttpPort(port);
        videoMediaServerResultDTO.setFlvPort(port);
        videoMediaServerResultDTO.setWsFlvPort(port);
        videoMediaServerResultDTO.setSecret(secret);

        ZlmRestfulResult<JSONArray> result = zlmRestfulUtils.getMediaServerConfig(videoMediaServerResultDTO);
        if (result == null || !result.isSuccess()) {
            throw BizException.wrap("流媒体服务器连接失败: {}", Objects.nonNull(result) ? result.getMsg() : "未知错误");
        }

        JSONArray data = Optional.ofNullable(result.getData())
                .filter(d -> !d.isEmpty())
                .orElseThrow(() -> BizException.wrap("流媒体服务器读取配置失败"));

        ZlmMediaServerConfig zlmServerConfig = Optional.ofNullable(JSON.parseObject(JSON.toJSONString(data.get(0)), ZlmMediaServerConfig.class))
                .orElseThrow(() -> BizException.wrap("流媒体服务器读取配置失败"));

        videoMediaServerResultDTO.setMediaIdentification(zlmServerConfig.getGeneralMediaServerId());

        Optional.ofNullable(zlmServerConfig.getHttpPort())
                .map(this::safeParseInt)
                .ifPresent(videoMediaServerResultDTO::setHttpSslPort);

        Optional.ofNullable(zlmServerConfig.getHttpPort())
                .map(this::safeParseInt)
                .ifPresent(videoMediaServerResultDTO::setFlvSslPort);

        Optional.ofNullable(zlmServerConfig.getHttpPort())
                .map(this::safeParseInt)
                .ifPresent(videoMediaServerResultDTO::setWsFlvSslPort);

        Optional.ofNullable(zlmServerConfig.getRtmpPort())
                .map(this::safeParseInt)
                .ifPresent(videoMediaServerResultDTO::setRtmpPort);

        Optional.ofNullable(zlmServerConfig.getRtmpSslport())
                .map(this::safeParseInt)
                .ifPresent(videoMediaServerResultDTO::setRtmpSslPort);

        Optional.ofNullable(zlmServerConfig.getRtspPort())
                .map(this::safeParseInt)
                .ifPresent(videoMediaServerResultDTO::setRtspPort);

        Optional.ofNullable(zlmServerConfig.getRtspSslport())
                .map(this::safeParseInt)
                .ifPresent(videoMediaServerResultDTO::setRtspSslPort);

        Optional.ofNullable(zlmServerConfig.getRtpProxyPort())
                .map(this::safeParseInt)
                .ifPresent(videoMediaServerResultDTO::setRtpProxyPort);

        videoMediaServerResultDTO.setStreamIp(ip);
        videoMediaServerResultDTO.setHookIp("127.0.0.1");
        videoMediaServerResultDTO.setSdpIp(ip);
        videoMediaServerResultDTO.setType(VideoMediaServerTypeEnum.ZLM.getValue());

        return videoMediaServerResultDTO;
    }

    @Override
    public List<ZlmMediaServerStreamInfo> getMediaList(VideoMediaServerResultDTO videoMediaServerResultDTO, String appId, String streamIdentification, String callId) {
        ZlmRestfulResult<JSONArray> result = zlmRestfulUtils.getMediaList(videoMediaServerResultDTO, appId, streamIdentification);

        if (result == null || !result.isSuccess()) {
            throw BizException.wrap("流媒体服务器连接失败: {}", Objects.nonNull(result) ? result.getMsg() : "未知错误");
        }

        JSONArray data = Optional.ofNullable(result.getData())
                .orElseThrow(() -> BizException.wrap("流媒体服务器读取配置失败"));

        if (data.isEmpty()) {
            throw BizException.wrap("流媒体服务器读取配置失败");
        }

        List<ZlmMediaListInfo> zlmMediaListInfoList = Optional.ofNullable(JSON.parseArray(JSON.toJSONString(data), ZlmMediaListInfo.class))
                .orElseThrow(() -> BizException.wrap("流媒体服务器读取配置失败"));

        List<ZlmMediaServerStreamInfo> zlmMediaServerStreamInfoList = new ArrayList<>();

        for (ZlmMediaListInfo zlmMediaListInfo : zlmMediaListInfoList) {
            ZlmMediaServerStreamInfo streamInfo = buildZlmMediaServerStreamInfo(videoMediaServerResultDTO, zlmMediaListInfo, callId);
            zlmMediaServerStreamInfoList.add(streamInfo);
        }

        return zlmMediaServerStreamInfoList;
    }

    @Override
    public ZlmMediaServerStreamInfo getMediaInfo(VideoMediaServerResultDTO videoMediaServerResultDTO, String appId, String streamIdentification) {
        ZlmRestfulResult<JSONObject> result = zlmRestfulUtils.getMediaInfo(videoMediaServerResultDTO, appId, ZlmMediaConstants.RTSP, streamIdentification);

        if (result == null || !result.isSuccess()) {
            throw BizException.wrap("流媒体服务器连接失败: {}", Objects.nonNull(result) ? result.getMsg() : "未知错误");
        }

        ZlmMediaListInfo zlmMediaListInfo = Optional.ofNullable(JSON.parseObject(JSON.toJSONString(result.getData()), ZlmMediaListInfo.class))
                .orElseThrow(() -> BizException.wrap("流媒体服务器读取配置失败"));

        return buildZlmMediaServerStreamInfo(videoMediaServerResultDTO, zlmMediaListInfo, StringUtils.EMPTY);
    }

    @Override
    public Boolean closeStreams(VideoMediaServerResultDTO videoMediaServerResultDTO, String appId, String streamIdentification) {
        log.info("Attempting to close stream: appId={}, streamIdentification={}, serverId={}", appId, streamIdentification, videoMediaServerResultDTO.getMediaIdentification());
        ZlmRestfulResult<JSONObject> zlmRestfulResult = zlmRestfulUtils.closeStreams(videoMediaServerResultDTO, appId, ZlmMediaConstants.RTSP, streamIdentification);
        if (zlmRestfulResult == null) {
            log.error("Failed to close stream: appId={}, streamIdentification={}, serverId={}, reason=Connection to server failed", appId, streamIdentification, videoMediaServerResultDTO.getMediaIdentification());
            throw BizException.wrap("连接失败");
        }

        if (!zlmRestfulResult.isSuccess()) {
            log.error("Failed to close stream: appId={}, streamIdentification={}, serverId={}, reason={}", appId, streamIdentification, videoMediaServerResultDTO.getMediaIdentification(), JSON.toJSONString(zlmRestfulResult));
            return false;
        }

        log.info("Successfully closed stream: appId={}, streamIdentification={}, serverId={}", appId, streamIdentification, videoMediaServerResultDTO.getMediaIdentification());

        return true;
    }

    @Override
    public String getFfmpegCmd(VideoMediaServerResultDTO videoMediaServerResultDTO) {
        ZlmRestfulResult<JSONArray> result = zlmRestfulUtils.getMediaServerConfig(videoMediaServerResultDTO);
        if (result == null || !result.isSuccess()) {
            throw BizException.wrap("流媒体服务器连接失败: {}", Objects.nonNull(result) ? result.getMsg() : "未知错误");
        }

        JSONArray data = Optional.ofNullable(result.getData())
                .filter(d -> !d.isEmpty())
                .orElseThrow(() -> BizException.wrap("流媒体服务器读取配置失败"));

        ZlmMediaServerConfig zlmServerConfig = Optional.ofNullable(JSON.parseObject(JSON.toJSONString(data.get(0)), ZlmMediaServerConfig.class))
                .orElseThrow(() -> BizException.wrap("流媒体服务器读取配置失败"));

        return Optional.ofNullable(zlmServerConfig.getFfmpegCmd())
                .filter(cmd -> !cmd.isEmpty())
                .orElse(ZlmMediaConstants.FFMPEG_CMD);
    }

    @Override
    public Boolean delFFmpegSource(VideoMediaServerResultDTO videoMediaServerResultDTO, String streamKey) {
        ZlmRestfulResult zlmRestfulResult = zlmRestfulUtils.delFFmpegSource(videoMediaServerResultDTO, streamKey);
        return zlmRestfulResult.isSuccess();
    }

    @Override
    public Boolean delStreamProxy(VideoMediaServerResultDTO videoMediaServerResultDTO, String streamKey) {
        ZlmRestfulResult zlmRestfulResult = zlmRestfulUtils.delStreamProxy(videoMediaServerResultDTO, streamKey);
        return zlmRestfulResult.isSuccess();
    }

    @Override
    public String addFFmpegSource(VideoMediaServerResultDTO videoMediaServerResultDTO, String trim, String dstUrl, Integer timeoutMs, Boolean enableAudio, Boolean enableMp4, String ffmpegCmdKey) {
        ZlmRestfulResult<JSONObject> result = zlmRestfulUtils.addFFmpegSource(videoMediaServerResultDTO, trim, dstUrl, timeoutMs, enableAudio, enableMp4, ffmpegCmdKey);
        if (result == null || !result.isSuccess()) {
            throw BizException.wrap("流媒体服务器连接失败: {}", Objects.nonNull(result) ? result.getMsg() : "未知错误");
        }

        JSONObject jsonObject = Optional.ofNullable(result.getData())
                .orElseThrow(() -> BizException.wrap("流媒体服务器返回数据为空"));

        return Optional.ofNullable(jsonObject.getString("key"))
                .orElseThrow(() -> BizException.wrap("代理结果中缺少key"));

    }


    @Override
    public String addStreamProxy(VideoMediaServerResultDTO videoMediaServerResultDTO, String appId, String streamIdentification, String url, Boolean enableAudio, Boolean enableMp4, String rtpType) {
        ZlmRestfulResult<JSONObject> result = zlmRestfulUtils.addStreamProxy(videoMediaServerResultDTO, appId, streamIdentification, url, enableAudio, enableMp4, rtpType);
        if (result == null || !result.isSuccess()) {
            throw BizException.wrap("流媒体服务器连接失败: {}", Objects.nonNull(result) ? result.getMsg() : "未知错误");
        }

        JSONObject jsonObject = Optional.ofNullable(result.getData())
                .orElseThrow(() -> BizException.wrap("流媒体服务器返回数据为空"));

        return Optional.ofNullable(jsonObject.getString("key"))
                .orElseThrow(() -> BizException.wrap("代理结果中缺少key"));
    }

    private ZlmMediaServerStreamInfo buildZlmMediaServerStreamInfo(VideoMediaServerResultDTO videoMediaServerResultDTO, ZlmMediaListInfo zlmMediaListInfo, String callId) {
        ZlmMediaServerStreamInfo streamInfo = new ZlmMediaServerStreamInfo();
        streamInfo.setAppId(zlmMediaListInfo.getApp());
        streamInfo.setStreamIdentification(zlmMediaListInfo.getStream());
        streamInfo.setStreamIp(videoMediaServerResultDTO.getStreamIp());
        streamInfo.setMediaIdentification(videoMediaServerResultDTO.getMediaIdentification());
        streamInfo.setMediaServer(videoMediaServerResultDTO);
        streamInfo.setCallId(callId);
        streamInfo.setOriginType(zlmMediaListInfo.getOriginType());

        // 构建 ZlmMediaInfo 对象
        ZlmMediaInfo zlmMediaInfo = new ZlmMediaInfo();
        zlmMediaInfo.setApp(zlmMediaListInfo.getApp());
        zlmMediaInfo.setStream(zlmMediaListInfo.getStream());
        zlmMediaInfo.setMediaServer(videoMediaServerResultDTO);
        zlmMediaInfo.setSchema(zlmMediaListInfo.getSchema());
        zlmMediaInfo.setReaderCount(zlmMediaListInfo.getReaderCount());
        zlmMediaInfo.setVideoCodec(zlmMediaListInfo.getTracks().stream()
                .filter(track -> track.getCodecType() != null && track.getCodecType() == 0)
                .findFirst()
                .map(track -> ZlmMediaInfo.getVideoCodec(track.getCodecId()))
                .orElse(null));
        zlmMediaInfo.setWidth(zlmMediaListInfo.getTracks().stream()
                .map(ZlmMediaListInfo.Track::getWidth)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null));
        zlmMediaInfo.setHeight(zlmMediaListInfo.getTracks().stream()
                .map(ZlmMediaListInfo.Track::getHeight)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null));
        zlmMediaInfo.setAudioCodec(zlmMediaListInfo.getTracks().stream()
                .filter(track -> track.getCodecType() != null && track.getCodecType() == 1)
                .findFirst()
                .map(track -> ZlmMediaInfo.getAudioCodec(track.getCodecId()))
                .orElse(null));
        zlmMediaInfo.setAudioChannels(zlmMediaListInfo.getTracks().stream()
                .map(ZlmMediaListInfo.Track::getChannels)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null));
        zlmMediaInfo.setAudioSampleRate(zlmMediaListInfo.getTracks().stream()
                .map(ZlmMediaListInfo.Track::getSampleRate)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null));
        zlmMediaInfo.setDuration(zlmMediaListInfo.getTracks().stream()
                .map(ZlmMediaListInfo.Track::getDuration)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null));
        zlmMediaInfo.setOnline(zlmMediaListInfo.getOnline());
        zlmMediaInfo.setOriginType(zlmMediaListInfo.getOriginType());
        zlmMediaInfo.setAliveSecond(zlmMediaListInfo.getAliveSecond());
        zlmMediaInfo.setBytesSpeed(zlmMediaListInfo.getBytesSpeed());
        zlmMediaInfo.setCallId(callId);
        // 设置流媒体地址
        setStreamUrls(streamInfo, zlmMediaInfo, videoMediaServerResultDTO, callId);

        return streamInfo;
    }

    private void setStreamUrls(ZlmMediaServerStreamInfo streamInfo, ZlmMediaInfo zlmMediaInfo, VideoMediaServerResultDTO videoMediaServerResultDTO, String callId) {
        String addr = videoMediaServerResultDTO.getStreamIp();
        String callIdParam = StrUtil.isBlank(callId) ? "" : "?callId=" + callId;
        String app = zlmMediaInfo.getApp();
        String stream = zlmMediaInfo.getStream();
        streamInfo.setRtmp(addr, videoMediaServerResultDTO.getRtmpPort(), videoMediaServerResultDTO.getRtmpSslPort(), app, stream, callIdParam);
        streamInfo.setRtsp(addr, videoMediaServerResultDTO.getRtspPort(), videoMediaServerResultDTO.getRtspSslPort(), app, stream, callIdParam);

        String flvFile = String.format("%s/%s.live.flv%s", app, stream, callIdParam);
        streamInfo.setFlv(addr, videoMediaServerResultDTO.getHttpPort(), videoMediaServerResultDTO.getHttpSslPort(), flvFile);
        streamInfo.setWsFlv(addr, videoMediaServerResultDTO.getHttpPort(), videoMediaServerResultDTO.getHttpSslPort(), flvFile);

        streamInfo.setFmp4(addr, videoMediaServerResultDTO.getHttpPort(), videoMediaServerResultDTO.getHttpSslPort(), app, stream, callIdParam);
        streamInfo.setHls(addr, videoMediaServerResultDTO.getHttpPort(), videoMediaServerResultDTO.getHttpSslPort(), app, stream, callIdParam);
        streamInfo.setTs(addr, videoMediaServerResultDTO.getHttpPort(), videoMediaServerResultDTO.getHttpSslPort(), app, stream, callIdParam);
        streamInfo.setRtc(addr, videoMediaServerResultDTO.getHttpPort(), videoMediaServerResultDTO.getHttpSslPort(), app, stream, callIdParam, true);
    }


    private Integer safeParseInt(String value) {
        try {
            return Integer.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
