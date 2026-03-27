package com.mqttsnet.thinglinks.video.service.media;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.video.entity.media.VideoStreamProxy;
import com.mqttsnet.thinglinks.video.vo.result.media.VideoStreamProxyResultVO;
import com.mqttsnet.thinglinks.video.vo.save.media.VideoStreamProxySaveVO;
import com.mqttsnet.thinglinks.video.vo.update.media.VideoStreamProxyUpdateVO;


/**
 * <p>
 * 业务接口
 * 视频拉流代理信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-05 22:32:06
 * @create [2024-07-05 22:32:06] [mqttsnet]
 */
public interface VideoStreamProxyService extends SuperService<Long, VideoStreamProxy> {

    /**
     * 保存拉流代理信息
     *
     * @param saveVO 保存参数
     * @return {@link VideoStreamProxySaveVO} 实体
     */
    VideoStreamProxySaveVO saveStreamProxy(VideoStreamProxySaveVO saveVO);

    /**
     * 更新拉流代理信息
     *
     * @param updateVO 更新参数
     * @return {@link VideoStreamProxyUpdateVO} 实体
     */
    VideoStreamProxyUpdateVO updateStreamProxy(VideoStreamProxyUpdateVO updateVO);

    /**
     * 删除拉流代理信息
     *
     * @param id 拉流代理信息ID
     * @return {@link Boolean} 删除结果
     */
    boolean deleteStreamProxy(Long id);

    /**
     * 获取拉流代理详情
     *
     * @param id id
     * @return {@link VideoStreamProxyResultVO} 详情结果实体
     */
    VideoStreamProxyResultVO getStreamProxyDetails(Long id);

    /**
     * 获取流播放地址
     *
     * @param id 流代理信息ID
     * @return {@link VideoStreamProxyResultVO}
     */
    VideoStreamProxyResultVO getPlayUrl(Long id);
}


