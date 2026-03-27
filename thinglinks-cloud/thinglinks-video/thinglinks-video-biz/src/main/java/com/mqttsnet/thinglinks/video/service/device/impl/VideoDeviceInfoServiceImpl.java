package com.mqttsnet.thinglinks.video.service.device.impl;

import java.util.Objects;

import cn.hutool.json.JSONUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.video.dto.device.VideoDeviceInfoResultDTO;
import com.mqttsnet.thinglinks.video.entity.device.VideoDeviceInfo;
import com.mqttsnet.thinglinks.video.manager.device.VideoDeviceInfoManager;
import com.mqttsnet.thinglinks.video.service.device.VideoDeviceInfoService;
import com.mqttsnet.thinglinks.video.vo.result.device.VideoDeviceInfoResultVO;
import com.mqttsnet.thinglinks.video.vo.save.device.VideoDeviceInfoSaveVO;
import com.mqttsnet.thinglinks.video.vo.update.device.VideoDeviceInfoUpdateVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 业务实现类
 * 流媒体设备信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:00:56
 * @create [2025-05-15 17:00:56] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class VideoDeviceInfoServiceImpl extends SuperServiceImpl<VideoDeviceInfoManager, Long, VideoDeviceInfo> implements VideoDeviceInfoService {


    @Override
    public VideoDeviceInfoResultVO getVideoDeviceInfoResultVO(String deviceIdentification) {
        ArgumentAssert.notBlank(deviceIdentification, "deviceIdentification Cannot be null");
        VideoDeviceInfo deviceInfo = superManager.getOneByDeviceIdentification(deviceIdentification);
        return BeanPlusUtil.toBeanIgnoreError(deviceInfo, VideoDeviceInfoResultVO.class);
    }

    @Override
    public VideoDeviceInfoResultDTO getVideoDeviceInfoResultDTO(String deviceIdentification) {
        ArgumentAssert.notBlank(deviceIdentification, "deviceIdentification Cannot be null");
        VideoDeviceInfo deviceInfo = superManager.getOneByDeviceIdentification(deviceIdentification);
        return BeanPlusUtil.toBeanIgnoreError(deviceInfo, VideoDeviceInfoResultDTO.class);
    }

    @Override
    public void updateDeviceInfo(VideoDeviceInfoUpdateVO updateVO) {
        log.info("更新设备信息: {}", JSONUtil.toJsonStr(updateVO));

        // 参数校验
        validateUpdateParams(updateVO);

        // 查询现有设备
        VideoDeviceInfo existingDevice = superManager.getById(updateVO.getId());
        if (existingDevice == null) {
            throw BizException.wrap("设备不存在 ID: {}", updateVO.getId());
        }

        // 使用Builder构建更新对象
        VideoDeviceInfo updatedDevice = buildUpdateDevice(updateVO, existingDevice);
        updatedDevice.setId(existingDevice.getId());

        // 更新设备信息
        superManager.updateById(updatedDevice);
    }

    @Override
    public void saveDeviceInfo(VideoDeviceInfoSaveVO saveVO) {
        log.info("新建设备信息: {}", JSONUtil.toJsonStr(saveVO));

        // 参数校验
        validateSaveParams(saveVO);

        // 使用Builder构建新设备
        VideoDeviceInfo newDevice = buildNewDevice(saveVO);

        // 保存设备信息
        superManager.save(newDevice);
    }

    /**
     * 构建更新设备对象
     */
    private VideoDeviceInfo buildUpdateDevice(VideoDeviceInfoUpdateVO updateVO,
                                              VideoDeviceInfo existingDevice) {
        return existingDevice.toBuilder()
                // 基础信息
                .deviceName(updateVO.getDeviceName())
                .customName(updateVO.getCustomName())

                // 网络配置
                .transport(updateVO.getTransport())
                .ip(updateVO.getIp())
                .port(updateVO.getPort())
                .wanIp(updateVO.getWanIp())
                .lanIp(updateVO.getLanIp())
                .hostAddress(updateVO.getHostAddress())

                // 媒体配置
                .streamMode(updateVO.getStreamMode())
                .mediaIdentification(updateVO.getMediaIdentification())
                .sdpIp(updateVO.getSdpIp())

                // 设备能力
                .geoCoordSys(updateVO.getGeoCoordSys())
                .positionCapability(updateVO.getPositionCapability())
                .ability(updateVO.getAbility())

                // 状态信息
                .onlineStatus(updateVO.getOnlineStatus())
                .expires(updateVO.getExpires())
                .keepaliveIntervalTime(updateVO.getKeepaliveIntervalTime())

                // 订阅配置
                .subscribeCycleForCatalog(updateVO.getSubscribeCycleForCatalog())
                .subscribeCycleForMobilePosition(updateVO.getSubscribeCycleForMobilePosition())
                .subscribeCycleForAlarm(updateVO.getSubscribeCycleForAlarm())

                // 安全配置
                .ssrcCheck(updateVO.getSsrcCheck())
                .broadcastPushAfterAck(updateVO.getBroadcastPushAfterAck())

                // 设备元信息
                .manufacturer(updateVO.getManufacturer())
                .model(updateVO.getModel())
                .firmware(updateVO.getFirmware())
                .charset(updateVO.getCharset())

                // 审计字段
                .remark(updateVO.getRemark())

                // 扩展字段
                .extendParams(updateVO.getExtendParams())
                .build();
    }

    /**
     * 构建新建设备对象
     */
    private VideoDeviceInfo buildNewDevice(VideoDeviceInfoSaveVO saveVO) {
        return VideoDeviceInfo.builder()
                // 唯一标识
                .deviceIdentification(saveVO.getDeviceIdentification())
                .deviceName(saveVO.getDeviceName())
                .customName(saveVO.getCustomName())

                // 网络配置
                .transport(saveVO.getTransport())
                .ip(saveVO.getIp())
                .port(saveVO.getPort())
                .wanIp(saveVO.getWanIp())
                .lanIp(saveVO.getLanIp())
                .hostAddress(saveVO.getHostAddress())

                // 媒体配置
                .streamMode(saveVO.getStreamMode())
                .mediaIdentification(saveVO.getMediaIdentification())
                .sdpIp(saveVO.getSdpIp())

                // 设备能力
                .geoCoordSys(saveVO.getGeoCoordSys())
                .positionCapability(saveVO.getPositionCapability())
                .ability(saveVO.getAbility())

                // 状态信息
                .onlineStatus(saveVO.getOnlineStatus())
                .expires(saveVO.getExpires())
                .keepaliveIntervalTime(saveVO.getKeepaliveIntervalTime())

                // 订阅配置
                .subscribeCycleForCatalog(saveVO.getSubscribeCycleForCatalog())
                .subscribeCycleForMobilePosition(saveVO.getSubscribeCycleForMobilePosition())
                .subscribeCycleForAlarm(saveVO.getSubscribeCycleForAlarm())

                // 安全配置
                .ssrcCheck(saveVO.getSsrcCheck())
                .broadcastPushAfterAck(saveVO.getBroadcastPushAfterAck())

                // 设备元信息
                .manufacturer(saveVO.getManufacturer())
                .model(saveVO.getModel())
                .firmware(saveVO.getFirmware())
                .charset(saveVO.getCharset())

                // 扩展字段
                .extendParams(saveVO.getExtendParams())
                .remark(saveVO.getRemark())
                .createdOrgId(ContextUtil.getCurrentDeptId())
                .build();
    }


    /**
     * 保存参数校验
     */
    private void validateSaveParams(VideoDeviceInfoSaveVO saveVO) {
        ArgumentAssert.notBlank(saveVO.getDeviceName(), "设备名称不能为空");
        ArgumentAssert.notNull(saveVO.getTransport(), "传输协议不能为空");
        ArgumentAssert.notBlank(saveVO.getPassword(), "设备密码不能为空");

        // 校验设备标识是已存在
        VideoDeviceInfo existingDeviceInfo = superManager.getOne(Wraps.<VideoDeviceInfo>lbQ().eq(VideoDeviceInfo::getDeviceIdentification, saveVO.getDeviceIdentification()));
        if (Objects.nonNull(existingDeviceInfo)) {
            throw BizException.validFail("该设备标识已存在!");
        }
    }

    /**
     * 更新参数校验
     */
    private void validateUpdateParams(VideoDeviceInfoUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "设备ID不能为空");
        ArgumentAssert.notBlank(updateVO.getDeviceIdentification(), "设备标识不能为空");
        // 校验设备标识是已存在
        VideoDeviceInfo existingDeviceInfo = superManager.getOne(Wraps.<VideoDeviceInfo>lbQ().eq(VideoDeviceInfo::getDeviceIdentification, updateVO.getDeviceIdentification())
                .ne(VideoDeviceInfo::getId, updateVO.getId()));
        if (Objects.nonNull(existingDeviceInfo)) {
            throw BizException.validFail("该设备标识已存在!");
        }
    }
}


