package com.mqttsnet.thinglinks.video.manager.media;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.video.entity.media.VideoMediaServer;
import com.mqttsnet.thinglinks.video.vo.query.media.VideoMediaServerPageQuery;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 流媒体服务器信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-03 17:56:38
 * @create [2024-07-03 17:56:38] [mqttsnet]
 */
public interface VideoMediaServerManager extends SuperManager<VideoMediaServer> {


    /**
     * 根据媒体唯一标识获取流媒体服务器详情
     *
     * @param mediaIdentification 流媒体唯一标识
     * @return {@link VideoMediaServer} 结果实体
     */
    VideoMediaServer getOneByMediaIdentification(String mediaIdentification);

    /**
     * 分页查询流媒体服务器列表
     * @param query 分页查询参数
     * @return {@link List<VideoMediaServer>} 流媒体服务器列表
     */
    List<VideoMediaServer> getVideoMediaServerList(VideoMediaServerPageQuery query);
}


