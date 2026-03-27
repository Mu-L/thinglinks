package com.mqttsnet.thinglinks.mqtt.service;

import java.util.Optional;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.cache.utils.CacheUtil;
import com.mqttsnet.thinglinks.cache.vo.device.DeviceCacheVO;
import com.mqttsnet.thinglinks.common.cache.link.device.DeviceCacheKeyBuilder;
import com.mqttsnet.thinglinks.device.entity.DeviceAction;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionStatusEnum;
import com.mqttsnet.thinglinks.device.enumeration.DeviceActionTypeEnum;
import com.mqttsnet.thinglinks.device.vo.save.DeviceActionSaveVO;
import com.mqttsnet.thinglinks.link.facade.DeviceOpenAnyUserFacade;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: thinglinks-cloud
 * @description: MqttEventActionHandler
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-08-20 16:09
 **/
@Slf4j
@Service
@RequiredArgsConstructor
public class MqttEventActionService {

    @Autowired
    private DeviceOpenAnyUserFacade deviceOpenAnyUserApi;

    @Autowired
    private CacheUtil cacheUtil;

    /**
     * 保存MQTT事件动作
     *
     * @param eventMessage 事件消息
     * @param actionType   动作类型
     * @param describable  描述
     */
    public void saveMqttEventAction(String eventMessage, DeviceActionTypeEnum actionType, String describable) {
//        Gson gson = new Gson();
//        Map<Object, Object> map = new HashMap<>();
//        map = gson.fromJson(eventMessage, map.getClass());
        JSONObject map = JSONUtil.parseObj(eventMessage);
        String clientId = String.valueOf(map.get("clientId"));

        Optional<DeviceCacheVO> deviceCacheVOOptional = cacheUtil.getObjectFromCache(DeviceCacheKeyBuilder.build(clientId).getKey(), DeviceCacheVO.class);
        if (deviceCacheVOOptional.isEmpty()) {
            return;
        }

        // save device action
        DeviceActionSaveVO deviceActionSaveVO = new DeviceActionSaveVO();
        deviceActionSaveVO.setDeviceIdentification(deviceCacheVOOptional.get().getDeviceIdentification());
        deviceActionSaveVO.setActionType(actionType.getAction());
        deviceActionSaveVO.setMessage(eventMessage);
        deviceActionSaveVO.setStatus(DeviceActionStatusEnum.SUCCESSFUL.getValue());
        deviceActionSaveVO.setRemark(describable);
        R<DeviceAction> deviceActionR = deviceOpenAnyUserApi.saveDeviceAction(deviceActionSaveVO);
        if (Boolean.TRUE.equals(deviceActionR.getIsSuccess())) {
            log.info("Save device action success: deviceAction={}", deviceActionR.getData());
        } else {
            log.error("Save device action failed: deviceAction={}", deviceActionR.getData());
        }
    }
}