package com.mqttsnet.thinglinks.video.service.media.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.converter.Builder;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.database.mybatis.conditions.query.LbQueryWrap;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.empowerment.media.VideoStreamProxyTypeEnum;
import com.mqttsnet.thinglinks.video.entity.media.VideoStreamProxy;
import com.mqttsnet.thinglinks.video.manager.media.VideoStreamProxyManager;
import com.mqttsnet.thinglinks.video.service.anytenant.ZlmMediaServerOpenAnyTenantService;
import com.mqttsnet.thinglinks.video.service.media.VideoMediaServerService;
import com.mqttsnet.thinglinks.video.service.media.VideoStreamProxyService;
import com.mqttsnet.thinglinks.video.vo.result.media.VideoMediaServerResultVO;
import com.mqttsnet.thinglinks.video.vo.result.media.VideoStreamProxyResultVO;
import com.mqttsnet.thinglinks.video.vo.result.media.zlm.ZlmMediaServerStreamInfoResultVO;
import com.mqttsnet.thinglinks.video.vo.save.media.VideoStreamProxySaveVO;
import com.mqttsnet.thinglinks.video.vo.update.media.VideoStreamProxyUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.IntStream;

/**
 * <p>
 * 业务实现类
 * 视频拉流代理信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-05 22:32:06
 * @create [2024-07-05 22:32:06] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class VideoStreamProxyServiceImpl extends SuperServiceImpl<VideoStreamProxyManager, Long, VideoStreamProxy> implements VideoStreamProxyService {


    private final VideoMediaServerService videoMediaServerService;

    private final ZlmMediaServerOpenAnyTenantService zlmMediaServerOpenAnyTenantService;


    @Override
    public VideoStreamProxySaveVO saveStreamProxy(VideoStreamProxySaveVO saveVO) {
        log.info("saveStreamProxy saveVO:{}", saveVO);

        // 校验参数
        checkSaveVO(saveVO);

        // 启用状态需要添加流代理到 ZLM
        VideoStreamProxyResultVO videoStreamProxyResultVO = addStreamProxyToZlm(BeanPlusUtil.toBeanIgnoreError(saveVO, VideoStreamProxyResultVO.class));
        saveVO.setFfmpegCmdKey(videoStreamProxyResultVO.getFfmpegCmdKey());
        saveVO.setStreamKey(videoStreamProxyResultVO.getStreamKey());
        saveVO.setDstUrl(videoStreamProxyResultVO.getDstUrl());


        // 构建实体
        VideoStreamProxy entity = buildSaveVO(saveVO);


        // 保存实体
        superManager.save(entity);

        return BeanPlusUtil.toBeanIgnoreError(entity, VideoStreamProxySaveVO.class);
    }

    @Override
    public VideoStreamProxyUpdateVO updateStreamProxy(VideoStreamProxyUpdateVO updateVO) {
        log.info("updateStreamProxy updateVO:{}", updateVO);

        // 校验参数
        checkUpdateVO(updateVO);

        // 构建参数
        Builder<VideoStreamProxy> videoStreamProxyBuilder = builderVideoStreamProxyUpdateVO(updateVO);

        // 启用状态需要添加流代理到 ZLM
        if (updateVO.getStatus()) {
            addStreamProxyToZlm(BeanPlusUtil.toBeanIgnoreError(videoStreamProxyBuilder.build(), VideoStreamProxyResultVO.class));
        } else {
            removeStreamProxyToZlm(BeanPlusUtil.toBeanIgnoreError(updateVO, VideoStreamProxyResultVO.class));
        }


        // 更新
        superManager.updateById(videoStreamProxyBuilder.with(VideoStreamProxy::setId, updateVO.getId()).build());
        return updateVO;
    }

    @Override
    public boolean deleteStreamProxy(Long id) {
        VideoStreamProxy videoStreamProxy = superManager.getById(id);
        if (Objects.isNull(videoStreamProxy)) {
            throw BizException.wrap("Stream Proxy ID cannot be null");
        }

        //移除代理流
        try {
            removeStreamProxyToZlm(BeanPlusUtil.toBeanIgnoreError(videoStreamProxy, VideoStreamProxyResultVO.class));
        } catch (BizException e) {
            log.error("deleteStreamProxy removeStreamProxyToZlm error", e);
        }
        return superManager.removeById(id);
    }

    @Override
    public VideoStreamProxyResultVO getStreamProxyDetails(Long id) {
        if (id == null) {
            throw BizException.wrap("Stream Proxy ID cannot be null");
        }

        VideoStreamProxy videoStreamProxy = superManager.getById(id);
        if (videoStreamProxy == null) {
            throw BizException.wrap("Stream Proxy not exist");
        }

        return BeanPlusUtil.toBeanIgnoreError(videoStreamProxy, VideoStreamProxyResultVO.class);
    }

    @Override
    public VideoStreamProxyResultVO getPlayUrl(Long id) {
        VideoStreamProxyResultVO videoStreamProxyResultVO = getStreamProxyDetails(id);

        VideoMediaServerResultDTO videoMediaServerResultDTO = videoMediaServerService.getVideoMediaServerResultDTO(videoStreamProxyResultVO.getMediaIdentification());

        List<ZlmMediaServerStreamInfoResultVO> mediaServerStreamInfoList = zlmMediaServerOpenAnyTenantService.getMediaServerStreamInfoList(videoMediaServerResultDTO, videoStreamProxyResultVO.getAppId(), videoStreamProxyResultVO.getStreamIdentification());
        videoStreamProxyResultVO.setZlmMediaServerStreamInfoList(mediaServerStreamInfoList);

        return videoStreamProxyResultVO;
    }


    private Builder<VideoStreamProxy> builderVideoStreamProxyUpdateVO(VideoStreamProxyUpdateVO updateVO) {
        return Builder.of(VideoStreamProxy::new)
                .with(VideoStreamProxy::setAppId, updateVO.getAppId())
                .with(VideoStreamProxy::setProxyType, updateVO.getProxyType())
                .with(VideoStreamProxy::setProxyName, updateVO.getProxyName())
                .with(VideoStreamProxy::setStreamIdentification, updateVO.getStreamIdentification())
                .with(VideoStreamProxy::setUrl, updateVO.getUrl())
                .with(VideoStreamProxy::setSrcUrl, updateVO.getSrcUrl())
                .with(VideoStreamProxy::setDstUrl, updateVO.getDstUrl())
                .with(VideoStreamProxy::setTimeoutMs, updateVO.getTimeoutMs())
                .with(VideoStreamProxy::setFfmpegCmdKey, updateVO.getFfmpegCmdKey())
                .with(VideoStreamProxy::setRtpType, updateVO.getRtpType())
                .with(VideoStreamProxy::setGbIdentification, updateVO.getGbIdentification())
                .with(VideoStreamProxy::setMediaIdentification, updateVO.getMediaIdentification())
                .with(VideoStreamProxy::setEnableAudio, updateVO.getEnableAudio())
                .with(VideoStreamProxy::setEnableMp4, updateVO.getEnableMp4())
                .with(VideoStreamProxy::setStatus, updateVO.getStatus())
                .with(VideoStreamProxy::setEnableRemoveNoneReader, updateVO.getEnableRemoveNoneReader())
                .with(VideoStreamProxy::setStreamKey, updateVO.getStreamKey())
                .with(VideoStreamProxy::setEnableDisableNoneReader, updateVO.getEnableDisableNoneReader())
                .with(VideoStreamProxy::setExtendParams, updateVO.getExtendParams())
                .with(VideoStreamProxy::setRemark, updateVO.getRemark())
                .with(VideoStreamProxy::setCreatedOrgId, updateVO.getCreatedOrgId());
    }

    private void checkSaveVO(VideoStreamProxySaveVO saveVO) {
        ArgumentAssert.notBlank(saveVO.getAppId(), "应用ID不能为空");
        ArgumentAssert.notBlank(saveVO.getMediaIdentification(), "媒体唯一标识不能为空");

        if (VideoStreamProxyTypeEnum.fromValue(saveVO.getProxyType()).isEmpty()) {
            throw BizException.wrap("proxyType is not exist");
        }

        VideoMediaServerResultVO videoMediaServerResultVO = videoMediaServerService.getOneByMediaIdentification(saveVO.getMediaIdentification());

        if (Objects.isNull(videoMediaServerResultVO)) {
            throw BizException.wrap("该流媒体服务不存在");
        }


        // 校验相同拉流代理是否存在
        validateUniqueStreamProxy(saveVO.getAppId(), saveVO.getStreamIdentification(), saveVO.getMediaIdentification(), null);


    }

    private void validateUniqueStreamProxy(String appId, String streamIdentification, String mediaIdentification, Long excludeId) {
        LbQueryWrap<VideoStreamProxy> wrap = Wraps.lbQ();
        wrap.eq(VideoStreamProxy::getAppId, appId)
                .eq(VideoStreamProxy::getStreamIdentification, streamIdentification)
                .eq(VideoStreamProxy::getMediaIdentification, mediaIdentification);

        if (excludeId != null) {
            wrap.ne(VideoStreamProxy::getId, excludeId);
        }

        List<VideoStreamProxy> list = list(wrap);

        if (CollUtil.isNotEmpty(list)) {
            throw BizException.wrap("此拉流代理已存在，请勿重复添加");
        }
    }

    private void checkUpdateVO(VideoStreamProxyUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "唯一标识符不能为空");

        VideoStreamProxy videoStreamProxy = superManager.getById(updateVO.getId());

        if (null == videoStreamProxy) {
            throw BizException.wrap("videoStreamProxy is not exist");
        }
        ArgumentAssert.notNull(updateVO.getId(), "唯一标识符不能为空");
        ArgumentAssert.notBlank(updateVO.getAppId(), "应用ID不能为空");

        if (VideoStreamProxyTypeEnum.fromValue(updateVO.getProxyType()).isEmpty()) {
            throw BizException.wrap("proxyType is not exist");
        }

        VideoMediaServerResultVO videoMediaServerResultVO = videoMediaServerService.getOneByMediaIdentification(updateVO.getMediaIdentification());

        if (Objects.isNull(videoMediaServerResultVO)) {
            throw BizException.wrap("该流媒体服务不存在");
        }


        // 校验相同拉流代理是否存在
        validateUniqueStreamProxy(updateVO.getAppId(), updateVO.getStreamIdentification(), updateVO.getMediaIdentification(), updateVO.getId());
    }

    /**
     * 通用saveVO 构建
     *
     * @param saveVO 保存VO
     * @return {@link VideoStreamProxy} 保存DO
     */
    private VideoStreamProxy buildSaveVO(VideoStreamProxySaveVO saveVO) {
        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return BeanPlusUtil.toBeanIgnoreError(saveVO, VideoStreamProxy.class);
    }


    /**
     * 添加代理到zlm
     *
     * @param videoStreamProxyResultVO 流代理信息
     * @return {@link VideoStreamProxyResultVO}
     */
    public VideoStreamProxyResultVO addStreamProxyToZlm(VideoStreamProxyResultVO videoStreamProxyResultVO) {
        VideoMediaServerResultDTO videoMediaServerResultDTO = videoMediaServerService
                .getVideoMediaServerResultDTO(videoStreamProxyResultVO.getMediaIdentification());

        // 校验流是否准备完成
        boolean streamReady = Optional.ofNullable(zlmMediaServerOpenAnyTenantService
                        .isStreamReady(videoMediaServerResultDTO, videoStreamProxyResultVO.getAppId(), videoStreamProxyResultVO.getStreamIdentification()))
                .orElse(false);

        if (streamReady) {
            log.info("[拉流代理] 流已经准备完成，跳过添加操作: appId={}, streamIdentification={}",
                    videoStreamProxyResultVO.getAppId(), videoStreamProxyResultVO.getStreamIdentification());
            return videoStreamProxyResultVO;
        }

        String dstUrl = Optional.of(videoStreamProxyResultVO)
                .filter(vo -> VideoStreamProxyTypeEnum.FFMPEG.getValue().equals(vo.getProxyType()))
                .map(vo -> {
                    if (vo.getTimeoutMs() == null || vo.getTimeoutMs() == 0) {
                        vo.setTimeoutMs(15);
                    }

                    String ffmpegCmd = Optional.ofNullable(zlmMediaServerOpenAnyTenantService.getFfmpegCmd(videoMediaServerResultDTO))
                            .filter(StringUtils::isNotBlank)
                            .orElseThrow(() -> BizException.wrap("ffmpeg拉流代理无法获取ffmpeg cmd"));

                    vo.setFfmpegCmdKey(ffmpegCmd);

                    String schema = Optional.ofNullable(getSchemaFromFFmpegCmd(ffmpegCmd))
                            .filter(StringUtils::isNotBlank)
                            .orElseThrow(() -> BizException.wrap("ffmpeg拉流代理无法从ffmpeg cmd中获取到输出格式"));

                    Integer port;
                    String schemaForUri;

                    switch (schema.toLowerCase()) {
                        case "rtsp":
                            port = videoMediaServerResultDTO.getRtspPort();
                            schemaForUri = schema;
                            break;
                        case "flv":
                            port = videoMediaServerResultDTO.getRtmpPort();
                            schemaForUri = schema;
                            break;
                        default:
                            port = videoMediaServerResultDTO.getRtmpPort();
                            schemaForUri = schema;
                            break;
                    }

                    return String.format("%s://%s:%s/%s/%s", schemaForUri, videoMediaServerResultDTO.getStreamIp(), port, vo.getAppId(), vo.getStreamIdentification());
                })
                .orElseGet(() -> String.format("rtsp://%s:%s/%s/%s", videoMediaServerResultDTO.getStreamIp(), videoMediaServerResultDTO.getRtspPort(), videoStreamProxyResultVO.getAppId(), videoStreamProxyResultVO.getStreamIdentification()));

        log.info("[拉流代理] 输出地址为：{}", dstUrl);
        videoStreamProxyResultVO.setDstUrl(dstUrl);

        // Add the stream proxy
        /*String result;
        if (VideoStreamProxyTypeEnum.FFMPEG.getValue().equalsIgnoreCase(videoStreamProxyResultVO.getProxyType())) {
            result = zlmMediaServerOpenAnyTenantService.addFFmpegSource(videoMediaServerResultVO, videoStreamProxyResultVO.getSrcUrl().trim(), dstUrl,
                    videoStreamProxyResultVO.getTimeoutMs(), videoStreamProxyResultVO.getEnableAudio(), videoStreamProxyResultVO.getEnableMp4(),
                    videoStreamProxyResultVO.getFfmpegCmdKey());
        } else {
            result = zlmMediaServerOpenAnyTenantService.addStreamProxy(videoMediaServerResultVO, videoStreamProxyResultVO.getAppId(), videoStreamProxyResultVO.getStreamIdentification(), videoStreamProxyResultVO.getUrl().trim(),
                    videoStreamProxyResultVO.getEnableAudio(), videoStreamProxyResultVO.getEnableMp4(), videoStreamProxyResultVO.getRtpType());
        }

        Optional.ofNullable(result)
                .filter(res -> !res.isEmpty())
                .ifPresentOrElse(
                        key -> {
                            log.info("[拉流代理] 添加流成功: {}", key);
                            videoStreamProxyResultVO.setStreamKey(key);
                        },
                        () -> {
                            log.error("[拉流代理] 添加流失败: {}", result);
                            throw new BizException("拉流代理操作添加流失败");
                        }
                );*/

        return videoStreamProxyResultVO;
    }


    /**
     * 移除代理流
     *
     * @param videoStreamProxyResultVO vo
     * @return {@link Boolean} 是否移除成功
     */
    private Boolean removeStreamProxyToZlm(VideoStreamProxyResultVO videoStreamProxyResultVO) {
        VideoMediaServerResultDTO videoMediaServerResultDTO = videoMediaServerService
                .getVideoMediaServerResultDTO(videoStreamProxyResultVO.getMediaIdentification());

        // 校验流是否准备完成并关闭流
        boolean streamReady = Optional.ofNullable(zlmMediaServerOpenAnyTenantService
                        .isStreamReady(videoMediaServerResultDTO, videoStreamProxyResultVO.getAppId(), videoStreamProxyResultVO.getStreamIdentification()))
                .orElse(false);

        if (streamReady) {
            Boolean closeStreamsFlag = zlmMediaServerOpenAnyTenantService
                    .closeStreams(videoMediaServerResultDTO, videoStreamProxyResultVO.getAppId(), videoStreamProxyResultVO.getStreamIdentification());
            log.info("removeStreamProxyToZlm 正在执行关断单个流，关闭是否成功:{}", closeStreamsFlag);
            if (closeStreamsFlag) {
                return true;
            }
        }

        // 根据代理类型删除流
        return Optional.of(videoStreamProxyResultVO)
                .filter(vo -> VideoStreamProxyTypeEnum.FFMPEG.getValue().equals(vo.getProxyType()))
                .map(vo -> zlmMediaServerOpenAnyTenantService.delFFmpegSource(videoMediaServerResultDTO, videoStreamProxyResultVO.getStreamKey()))
                .orElseGet(() -> zlmMediaServerOpenAnyTenantService.delStreamProxy(videoMediaServerResultDTO, videoStreamProxyResultVO.getStreamKey()));
    }


    private String getSchemaFromFFmpegCmd(String ffmpegCmd) {
        // 去除多余的空格，确保参数之间只有一个空格
        String sanitizedCmd = ffmpegCmd.replaceAll("\\s+", " ");
        String[] paramArray = sanitizedCmd.split(" ");

        // 使用Optional和Stream查找 "-f" 参数的值
        return IntStream.range(0, paramArray.length)
                .filter(i -> "-f".equalsIgnoreCase(paramArray[i]))
                .mapToObj(i -> (i + 1 < paramArray.length) ? paramArray[i + 1] : null)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(StrPool.EMPTY);
    }


}


