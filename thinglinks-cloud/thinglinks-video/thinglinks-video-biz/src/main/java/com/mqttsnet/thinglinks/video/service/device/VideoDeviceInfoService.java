package com.mqttsnet.thinglinks.video.service.device;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.video.dto.device.VideoDeviceInfoResultDTO;
import com.mqttsnet.thinglinks.video.entity.device.VideoDeviceInfo;
import com.mqttsnet.thinglinks.video.vo.result.device.VideoDeviceInfoResultVO;
import com.mqttsnet.thinglinks.video.vo.save.device.VideoDeviceInfoSaveVO;
import com.mqttsnet.thinglinks.video.vo.update.device.VideoDeviceInfoUpdateVO;


/**
 * <p>
 * 业务接口
 * 流媒体设备信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:00:56
 * @create [2025-05-15 17:00:56] [mqttsnet]
 */
public interface VideoDeviceInfoService extends SuperService<Long, VideoDeviceInfo> {


    /**
     * 根据设备标识查询设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link VideoDeviceInfoResultVO} 设备信息
     */
    VideoDeviceInfoResultVO getVideoDeviceInfoResultVO(String deviceIdentification);

    /**
     * 根据设备标识查询设备信息
     *
     * @param deviceIdentification 设备标识
     * @return {@link VideoDeviceInfoResultDTO} 设备信息
     */
    VideoDeviceInfoResultDTO getVideoDeviceInfoResultDTO(String deviceIdentification);


    /**
     * 更新设备信息
     *
     * @param updateVO 更新VO
     */
    void updateDeviceInfo(VideoDeviceInfoUpdateVO updateVO);

    /**
     * 保存设备信息
     *
     * @param saveVO 保存VO
     */
    void saveDeviceInfo(VideoDeviceInfoSaveVO saveVO);
}


