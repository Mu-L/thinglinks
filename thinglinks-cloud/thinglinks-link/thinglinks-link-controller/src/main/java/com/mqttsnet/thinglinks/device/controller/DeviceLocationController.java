package com.mqttsnet.thinglinks.device.controller;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.base.controller.SuperController;
import com.mqttsnet.basic.interfaces.echo.EchoService;
import com.mqttsnet.thinglinks.device.entity.DeviceLocation;
import com.mqttsnet.thinglinks.device.service.DeviceLocationService;
import com.mqttsnet.thinglinks.device.vo.query.DeviceLocationPageQuery;
import com.mqttsnet.thinglinks.device.vo.result.DeviceLocationResultVO;
import com.mqttsnet.thinglinks.device.vo.save.DeviceLocationSaveVO;
import com.mqttsnet.thinglinks.device.vo.update.DeviceLocationUpdateVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * 设备位置表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-05-30 23:05:31
 * @create [2023-05-30 23:05:31] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Validated
@RestController
@RequestMapping("/deviceLocation")
@Tag(name = "设备位置")
public class DeviceLocationController extends SuperController<DeviceLocationService, Long, DeviceLocation, DeviceLocationSaveVO,
        DeviceLocationUpdateVO, DeviceLocationPageQuery, DeviceLocationResultVO> {
    private final EchoService echoService;

    @Override
    public EchoService getEchoService() {
        return echoService;
    }

    /**
     * 新增设备位置信息
     *
     * @param deviceLocationSaveVO 保存参数
     * @return 实体
     */
    @Operation(summary = "保存设备位置信息", description = "保存设备位置信息")
    @PostMapping("/saveDeviceLocation")
    public R<DeviceLocationSaveVO> saveDeviceLocation(@RequestBody DeviceLocationSaveVO deviceLocationSaveVO) {
        return R.success(superService.saveDeviceLocation(deviceLocationSaveVO));
    }


    /**
     * 修改设备位置信息
     *
     * @param deviceLocationUpdateVO 更新参数
     * @return 更新后的实体
     */
    @Operation(summary = "更新设备位置信息", description = "更新设备位置信息")
    @PutMapping("/updateDeviceLocation")
    public R<DeviceLocationUpdateVO> updateDeviceLocation(@RequestBody DeviceLocationUpdateVO deviceLocationUpdateVO) {
        return R.success(superService.updateDeviceLocation(deviceLocationUpdateVO));
    }

}


