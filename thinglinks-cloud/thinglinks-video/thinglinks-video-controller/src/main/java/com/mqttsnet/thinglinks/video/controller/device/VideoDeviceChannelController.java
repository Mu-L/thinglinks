package com.mqttsnet.thinglinks.video.controller.device;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.video.entity.device.VideoDeviceChannel;
import com.mqttsnet.thinglinks.video.service.device.VideoDeviceChannelService;
import com.mqttsnet.thinglinks.video.vo.query.device.VideoDeviceChannelPageQuery;
import com.mqttsnet.thinglinks.video.vo.result.device.VideoDeviceChannelResultVO;
import com.mqttsnet.thinglinks.video.vo.save.device.VideoDeviceChannelSaveVO;
import com.mqttsnet.thinglinks.video.vo.update.device.VideoDeviceChannelUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 流媒体设备通道表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:09:48
 * @create [2025-05-15 17:09:48] [mqttsnet] [代码生成器生成]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/videoDeviceChannel")
@Tag(name = "流媒体设备通道")
public class VideoDeviceChannelController extends SuperController<VideoDeviceChannelService, Long, VideoDeviceChannel
        , VideoDeviceChannelSaveVO, VideoDeviceChannelUpdateVO, VideoDeviceChannelPageQuery, VideoDeviceChannelResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


