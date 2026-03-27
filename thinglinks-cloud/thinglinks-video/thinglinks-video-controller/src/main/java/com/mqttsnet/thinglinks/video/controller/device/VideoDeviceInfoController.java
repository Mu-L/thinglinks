package com.mqttsnet.thinglinks.video.controller.device;

import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.video.entity.device.VideoDeviceInfo;
import com.mqttsnet.thinglinks.video.service.device.VideoDeviceInfoService;
import com.mqttsnet.thinglinks.video.vo.query.device.VideoDeviceInfoPageQuery;
import com.mqttsnet.thinglinks.video.vo.result.device.VideoDeviceInfoResultVO;
import com.mqttsnet.thinglinks.video.vo.save.device.VideoDeviceInfoSaveVO;
import com.mqttsnet.thinglinks.video.vo.update.device.VideoDeviceInfoUpdateVO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 流媒体设备信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:00:56
 * @create [2025-05-15 17:00:56] [mqttsnet] [代码生成器生成]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/videoDeviceInfo")
@Tag(name = "流媒体设备信息")
public class VideoDeviceInfoController extends SuperController<VideoDeviceInfoService, Long, VideoDeviceInfo
        , VideoDeviceInfoSaveVO, VideoDeviceInfoUpdateVO, VideoDeviceInfoPageQuery, VideoDeviceInfoResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

}


