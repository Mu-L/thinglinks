package com.mqttsnet.thinglinks.video.service.media.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.video.entity.media.VideoStreamPush;
import com.mqttsnet.thinglinks.video.manager.media.VideoStreamPushManager;
import com.mqttsnet.thinglinks.video.service.anytenant.ZlmMediaServerOpenAnyTenantService;
import com.mqttsnet.thinglinks.video.service.media.VideoMediaServerService;
import com.mqttsnet.thinglinks.video.service.media.VideoStreamPushService;
import com.mqttsnet.thinglinks.video.vo.result.media.VideoStreamPushResultVO;
import com.mqttsnet.thinglinks.video.vo.save.media.VideoStreamPushSaveVO;
import com.mqttsnet.thinglinks.video.vo.update.media.VideoStreamPushUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 视频推流信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-07 19:19:57
 * @create [2024-07-07 19:19:57] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class VideoStreamPushServiceImpl extends SuperServiceImpl<VideoStreamPushManager, Long, VideoStreamPush> implements VideoStreamPushService {


    private final VideoMediaServerService videoMediaServerService;

    private final ZlmMediaServerOpenAnyTenantService zlmMediaServerOpenAnyTenantService;

    @Override
    public VideoStreamPushSaveVO saveStreamPush(VideoStreamPushSaveVO saveVO) {
        log.info("saveStreamPush saveVO:{}", saveVO);

        // 校验参数
        checkSaveVO(saveVO);

        // 构建实体
        VideoStreamPush entity = buildSaveVO(saveVO);

        // 保存实体
        superManager.save(entity);

        return BeanPlusUtil.toBeanIgnoreError(entity, VideoStreamPushSaveVO.class);
    }

    @Override
    public VideoStreamPushUpdateVO updateStreamPush(VideoStreamPushUpdateVO updateVO) {
        log.info("updateStreamPush updateVO:{}", updateVO);

        // 校验参数
        checkUpdateVO(updateVO);

        // 构建实体
        VideoStreamPush entity = buildUpdateVO(updateVO);

        // 更新
        superManager.updateById(entity);

        return updateVO;
    }

    @Override
    public boolean deleteStreamPush(Long id) {
        return superManager.removeById(id);
    }

    @Override
    public VideoStreamPushResultVO getStreamPushDetails(Long id) {
        if (id == null) {
            throw BizException.wrap("Stream Push ID cannot be null");
        }

        VideoStreamPush videoStreamPush = superManager.getById(id);
        if (videoStreamPush == null) {
            throw BizException.wrap("Stream Push not exist");
        }

        // 将VideoStreamPush转换为VideoStreamPushResultVO
        VideoStreamPushResultVO videoStreamPushResultVO =
                BeanPlusUtil.toBeanIgnoreError(videoStreamPush, VideoStreamPushResultVO.class);

        // Add more details to videoStreamPushResultVO if needed

        return videoStreamPushResultVO;
    }

    private void checkSaveVO(VideoStreamPushSaveVO saveVO) {
        ArgumentAssert.notBlank(saveVO.getAppId(), "应用ID不能为空");
        ArgumentAssert.notBlank(saveVO.getMediaIdentification(), "媒体唯一标识不能为空");
    }

    private void checkUpdateVO(VideoStreamPushUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "唯一标识符不能为空");
        ArgumentAssert.notBlank(updateVO.getAppId(), "应用ID不能为空");
        ArgumentAssert.notBlank(updateVO.getMediaIdentification(), "媒体唯一标识不能为空");
    }

    private VideoStreamPush buildSaveVO(VideoStreamPushSaveVO saveVO) {
        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return BeanPlusUtil.toBeanIgnoreError(saveVO, VideoStreamPush.class);
    }

    private VideoStreamPush buildUpdateVO(VideoStreamPushUpdateVO updateVO) {
        return BeanPlusUtil.toBeanIgnoreError(updateVO, VideoStreamPush.class);
    }

}


