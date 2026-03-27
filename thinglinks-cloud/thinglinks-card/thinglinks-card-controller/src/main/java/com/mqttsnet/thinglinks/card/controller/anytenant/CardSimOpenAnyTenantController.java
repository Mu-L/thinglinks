package com.mqttsnet.thinglinks.card.controller.anytenant;

import com.mqttsnet.basic.annotation.log.WebLog;
import com.mqttsnet.basic.base.R;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.jackson.JsonUtil;
import com.mqttsnet.thinglinks.card.entity.auto.IotCardAuthToken;
import com.mqttsnet.thinglinks.card.service.anytenant.CardSimOpenAnyTenantService;
import com.mqttsnet.thinglinks.card.vo.param.CardSimOpenApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物联卡相关开放接口（anyTenant）
 * 接口必须携带TenantId 信息
 *
 * @author mqttsnet
 * @date 2024-07-02
 * @create [2024-07-02] [mqttsnet]
 */
@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/anyTenant/cardSimOpen")
@Tag(name = "开放接口-物联卡相")
public class CardSimOpenAnyTenantController {

    private final CardSimOpenAnyTenantService cardSimOpenAnyTenantService;

    /**
     * 查询单卡基本信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡基本信息
     */
    @Operation(summary = "查询单卡基本信息", description = "CMIOT_API23S00-单卡基本信息查询")
    @PostMapping("/sim-basic-info")
    @WebLog(value = "CMIOT_API23S00-单卡基本信息查询", request = false)
    public R<IotCardAuthToken> simBasicInfo(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡基本信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simBasicInfo(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡基本信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡基本信息失败");
        }
    }

    /**
     * 查询单卡状态变更历史
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡状态变更历史
     */
    @Operation(summary = "查询单卡状态变更历史", description = "CMIOT_API23S02-单卡状态变更历史查询")
    @PostMapping("/sim-change-history")
    @WebLog(value = "CMIOT_API23S02-单卡状态变更历史查询", request = false)
    public R<IotCardAuthToken> simChangeHistory(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡状态变更历史，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simChangeHistory(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡状态变更历史失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡状态变更历史失败");
        }
    }

    /**
     * 查询单卡绑定IMEI实时信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡绑定IMEI信息
     */
    @Operation(summary = "查询单卡绑定IMEI实时信息", description = "CMIOT_API23S04-单卡绑定IMEI实时查询")
    @PostMapping("/sim-imei")
    @WebLog(value = "CMIOT_API23S04-单卡绑定IMEI实时查询", request = false)
    public R<IotCardAuthToken> simImei(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡绑定IMEI，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simImei(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡绑定IMEI失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡绑定IMEI失败");
        }
    }

    /**
     * 批量查询物联卡归属平台信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡归属平台信息
     */
    @Operation(summary = "批量查询物联卡归属平台信息", description = "CMIOT_API23S05-物联卡归属平台批量查询接口")
    @PostMapping("/sim-platform-batch")
    @WebLog(value = "CMIOT_API23S05-物联卡归属平台批量查询接口", request = false)
    public R<IotCardAuthToken> simPlatformBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量查询物联卡归属平台，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simPlatformBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量查询物联卡归属平台失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量查询物联卡归属平台失败");
        }
    }

    /**
     * 批量查询物联卡终端控制上行短信记录
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡终端控制上行短信记录信息
     */
    @Operation(summary = "批量查询物联卡终端控制上行短信记录", description = "API25C03-物联卡终端控制上行短信记录批量查")
    @PostMapping("/sim-mo-sms-batch")
    @WebLog(value = "API25C03-物联卡终端控制上行短信记录批量查", request = false)
    public R<IotCardAuthToken> simMoSmsBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量查询物联卡终端控制上行短信记录，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simMoSmsBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量查询物联卡终端控制上行短信记录失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量查询物联卡终端控制上行短信记录失败");
        }
    }

    /**
     * 查询单卡流量池内使用量实时信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡流量池内使用量实时信息
     */
    @Operation(summary = "查询单卡流量池内使用量实时信息", description = "CMIOT_API23U12-单卡流量池内使用量实时查询")
    @PostMapping("/sim-data-usage-inpool")
    @WebLog(value = "CMIOT_API23U12-单卡流量池内使用量实时查询", request = false)
    public R<IotCardAuthToken> simDataUsageInPool(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡流量池内使用量，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simDataUsageInPool(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡流量池内使用量失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡流量池内使用量失败");
        }
    }

    /**
     * 查询单卡本月流量累计使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡本月流量累计使用量
     */
    @Operation(summary = "查询单卡本月流量累计使用量", description = "CMIOT_API25U04-单卡本月流量累计使用量查询")
    @PostMapping("/sim-data-usage")
    @WebLog(value = "CMIOT_API25U04-单卡本月流量累计使用量查询", request = false)
    public R simDataUsage(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡本月流量累计使用量，租户ID:{}", cardSimOpenApiParam.getTenantId());
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simDataUsage(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡本月流量累计使用量失败，租户ID:{}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡本月流量累计使用量失败");
        }
    }


    /**
     * 查询单卡状态信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡状态信息
     */
    @Operation(summary = "查询单卡状态信息", description = "CMIOT_API25S04-单卡状态查询")
    @PostMapping("/sim-status")
    @WebLog(value = "CMIOT_API25S04-单卡状态查询", request = false)
    public R<IotCardAuthToken> simStatus(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡状态信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simStatus(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡状态信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡状态信息失败");
        }
    }

    /**
     * 查询单卡在线信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡在线信息
     */
    @Operation(summary = "查询单卡在线信息", description = "CMIOT_API25M01-单卡在线信息实时查询")
    @PostMapping("/sim-session")
    @WebLog(value = "CMIOT_API25M01-单卡在线信息实时查询", request = false)
    public R<IotCardAuthToken> simSession(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡在线信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simSession(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡在线信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡在线信息失败");
        }
    }

    /**
     * 查询单卡通信功能开通情况
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡通信功能开通情况
     */
    @Operation(summary = "查询单卡通信功能开通情况", description = "CMIOT_API23M08-单卡通信功能开通查询")
    @PostMapping("/sim-communication-function-status")
    @WebLog(value = "CMIOT_API23M08-单卡通信功能开通查询", request = false)
    public R<IotCardAuthToken> simCommunicationFunctionStatus(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡通信功能开通情况，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simCommunicationFunctionStatus(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡通信功能开通情况失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡通信功能开通情况失败");
        }
    }

    /**
     * 实时查询群组本月GPRS流量累计使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 群组本月GPRS流量累计使用量
     */
    @Operation(summary = "实时查询群组本月GPRS流量累计使用量", description = "CMIOT_API23U00-群组本月流量累计使用量实时查询")
    @PostMapping("/group-data-usage")
    @WebLog(value = "CMIOT_API23U00-群组本月流量累计使用量实时查询", request = false)
    public R<IotCardAuthToken> groupDataUsage(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("实时查询群组本月GPRS流量累计使用量，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.groupDataUsage(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("实时查询群组本月GPRS流量累计使用量失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("实时查询群组本月GPRS流量累计使用量失败");
        }
    }

    /**
     * 实时查询群组本月套餐内GPRS流量使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 群组本月套餐内GPRS流量使用量
     */
    @Operation(summary = "实时查询群组本月套餐内GPRS流量使用量", description = "CMIOT_API23U04-群组本月套餐内流量使用量实时查询")
    @PostMapping("/group-data-margin")
    @WebLog(value = "CMIOT_API23U04-群组本月套餐内流量使用量实时查询", request = false)
    public R<IotCardAuthToken> groupDataMargin(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("实时查询群组本月套餐内GPRS流量使用量，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.groupDataMargin(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("实时查询群组本月套餐内GPRS流量使用量失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("实时查询群组本月套餐内GPRS流量使用量失败");
        }
    }

    /**
     * 单卡APN功能开停
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡APN功能开停
     */
    @Operation(summary = "单卡APN功能开停", description = "CMIOT_API23M07-单卡APN功能开停")
    @PostMapping("/sim-apn-function")
    @WebLog(value = "CMIOT_API23M07-单卡APN功能开停", request = false)
    public R<IotCardAuthToken> simApnFunction(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("单卡APN功能开停，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simApnFunction(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("单卡APN功能开停失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("单卡APN功能开停失败");
        }
    }

    /**
     * 查询物联卡归属的群组信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡归属的群组信息
     */
    @Operation(summary = "查询物联卡归属的群组信息", description = "CMIOT_API23E02-成员归属群组查询")
    @PostMapping("/sim-group-by-member")
    @WebLog(value = "CMIOT_API23E02-成员归属群组查询", request = false)
    public R<IotCardAuthToken> simGroupByMember(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询物联卡归属的群组信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simGroupByMember(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询物联卡归属的群组信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询物联卡归属的群组信息失败");
        }
    }

    /**
     * 重置物联卡GPRS上网功能
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡GPRS上网功能重置
     */
    @Operation(summary = "重置物联卡GPRS上网功能", description = "CMIOT_API25M03-物联卡GPRS上网功能重置")
    @PostMapping("/sim-gprs-status-reset")
    @WebLog(value = "CMIOT_API25M03-物联卡GPRS上网功能重置", request = false)
    public R simGprsStatusReset(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("重置物联卡GPRS上网功能，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simGprsStatusReset(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("重置物联卡GPRS上网功能失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("重置物联卡GPRS上网功能失败");
        }
    }

    /**
     * 设置群组成员卡流量池内流量限额
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 群组成员卡流量池内流量限额设置
     */
    @Operation(summary = "设置群组成员卡流量池内流量限额", description = "CMIOT_API23E04-群组成员流量限额设置")
    @PostMapping("/limit-group-member-data-usage")
    @WebLog(value = "CMIOT_API23E04-群组成员流量限额设置", request = false)
    public R<IotCardAuthToken> groupMemberDataUsage(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("设置群组成员卡流量池内流量限额，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.groupMemberDataUsage(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("设置群组成员卡流量池内流量限额失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("设置群组成员卡流量池内流量限额失败");
        }
    }

    /**
     * 查询物联卡当前附网基站所在的经纬度信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡当前附网基站所在的经纬度信息
     */
    @Operation(summary = "查询物联卡当前附网基站所在的经纬度信息", description = "CMIOT_API25L00-物联卡实时位置经纬度查询")
    @PostMapping("/position-location-message")
    @WebLog(value = "CMIOT_API25L00-物联卡实时位置经纬度查询", request = false)
    public R<IotCardAuthToken> positionLocationMessage(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询物联卡当前附网基站所在的经纬度信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.positionLocationMessage(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询物联卡当前附网基站所在的经纬度信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询物联卡当前附网基站所在的经纬度信息失败");
        }
    }

    /**
     * 查询物联卡当前实时位置区号信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡当前实时位置区号信息
     */
    @Operation(summary = "查询物联卡当前实时位置区号信息", description = "CMIOT_API25L01-物联卡实时位置区号查询")
    @PostMapping("/district-position-location-message")
    @WebLog(value = "CMIOT_API25L01-物联卡实时位置区号查询", request = false)
    public R<IotCardAuthToken> districtPositionLocationMessage(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询物联卡当前实时位置区号信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.districtPositionLocationMessage(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询物联卡当前实时位置区号信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询物联卡当前实时位置区号信息失败");
        }
    }

    /**
     * 查询物联卡的最后上网基站所在的经纬度信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡最后上网基站所在的经纬度信息
     */
    @Operation(summary = "查询物联卡的最后上网基站所在的经纬度信息", description = "CMIOT_API25L03-物联卡最后上网位置经纬度批量查询")
    @PostMapping("/last-position-location")
    @WebLog(value = "CMIOT_API25L03-物联卡最后上网位置经纬度批量查询", request = false)
    public R<IotCardAuthToken> lastPositionLocation(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询物联卡的最后上网基站所在的经纬度信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.lastPositionLocation(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询物联卡的最后上网基站所在的经纬度信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询物联卡的最后上网基站所在的经纬度信息失败");
        }
    }

    /**
     * 查询经纬度对应的语义化地理位置信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 经纬度对应的语义化地理位置信息
     */
    @Operation(summary = "查询经纬度对应的语义化地理位置信息", description = "CMIOT_API25L42-经纬度逆地理解析")
    @PostMapping("/location-regeo")
    @WebLog(value = "CMIOT_API25L42-经纬度逆地理解析", request = false)
    public R<IotCardAuthToken> locationRegeo(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询经纬度对应的语义化地理位置信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.locationRegeo(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询经纬度对应的语义化地理位置信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询经纬度对应的语义化地理位置信息失败");
        }
    }

    /**
     * 变更物联卡的状态
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡状态变更结果
     */
    @Operation(summary = "变更物联卡的状态", description = "CMIOT_API23S03-单卡状态变更")
    @PostMapping("/change-sim-status")
    @WebLog(value = "CMIOT_API23S03-单卡状态变更", request = false)
    public R<IotCardAuthToken> changeSimStatus(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("变更物联卡的状态，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.changeSimStatus(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("变更物联卡的状态失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("变更物联卡的状态失败");
        }
    }

    /**
     * 批量变更物联卡的状态
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡状态批量变更结果
     */
    @Operation(summary = "批量变更物联卡的状态", description = "CMIOT_API23S06-物联卡状态变更批量办理")
    @PostMapping("/sim-status-batch")
    @WebLog(value = "CMIOT_API23S06-物联卡状态变更批量办理", request = false)
    public R simStatusBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量变更物联卡的状态，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simStatusBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量变更物联卡的状态失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量变更物联卡的状态失败");
        }
    }

    /**
     * 批量变更物联卡的节电参数
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡节电参数批量变更结果
     */
    @Operation(summary = "批量变更物联卡的节电参数", description = "CMIOT_API23M19-物联卡节电参数变更批量办理")
    @PostMapping("/sim-parameter-node-batch")
    @WebLog(value = "CMIOT_API23M19-物联卡节电参数变更批量办理", request = false)
    public R<IotCardAuthToken> simParameterNodeBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量变更物联卡的节电参数，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simParameterNodeBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量变更物联卡的节电参数失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量变更物联卡的节电参数失败");
        }
    }

    /**
     * 实时查询物联卡本月套餐内流量使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡本月套餐内流量使用量
     */
    @Operation(summary = "实时查询物联卡本月套餐内流量使用量", description = "CMIOT_API23U07-单卡本月套餐流量用量实时查询")
    @PostMapping("/sim-data-margin")
    @WebLog(value = "CMIOT_API23U07-单卡本月套餐流量用量实时查询", request = false)
    public R<IotCardAuthToken> simDataMargin(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("实时查询物联卡本月套餐内流量使用量，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simDataMargin(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("实时查询物联卡本月套餐内流量使用量失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("实时查询物联卡本月套餐内流量使用量失败");
        }
    }

    /**
     * 查询物联卡业务批量办理结果
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡业务批量办理结果
     */
    @Operation(summary = "查询物联卡业务批量办理结果", description = "CMIOT_API23M10-物联卡业务批量办理结果查询")
    @PostMapping("/sim-batch-result")
    @WebLog(value = "CMIOT_API23M10-物联卡业务批量办理结果查询", request = false)
    public R<IotCardAuthToken> simBatchResult(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询物联卡业务批量办理结果，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simBatchResult(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询物联卡业务批量办理结果失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询物联卡业务批量办理结果失败");
        }
    }

    /**
     * 批量查询物联卡某一天GPRS流量使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡某一天GPRS流量使用量
     */
    @Operation(summary = "批量查询物联卡某一天GPRS流量使用量", description = "CMIOT_API25U01-物联卡单日GPRS流量使用量批量查询")
    @PostMapping("/sim-data-usage-daily/batch")
    @WebLog(value = "CMIOT_API25U01-物联卡单日GPRS流量使用量批量查询", request = false)
    public R<IotCardAuthToken> simDataUsageDailyBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量查询物联卡某一天GPRS流量使用量，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simDataUsageDailyBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量查询物联卡某一天GPRS流量使用量失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量查询物联卡某一天GPRS流量使用量失败");
        }
    }

    /**
     * 批量查询集团客户下所属SIM卡的月数据使用情况
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 集团客户下所属SIM卡的月数据使用情况
     */
    @Operation(summary = "批量查询集团客户下所属SIM卡的月数据使用情况", description = "CMIOT_API25U03-物联卡单月GPRS流量使用量批量查询")
    @PostMapping("/sim-data-usage-monthly/batch")
    @WebLog(value = "CMIOT_API25U03-物联卡单月GPRS流量使用量批量查询", request = false)
    public R<IotCardAuthToken> simDataUsageMonthlyBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量查询集团客户下所属SIM卡的月数据使用情况，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simDataUsageMonthlyBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量查询集团客户下所属SIM卡的月数据使用情况失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量查询集团客户下所属SIM卡的月数据使用情况失败");
        }
    }

    /**
     * 查询物联卡机卡绑定状态
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡机卡绑定状态
     */
    @Operation(summary = "查询物联卡机卡绑定状态", description = "CMIOT_API23A04-物联卡机卡分离状态查询")
    @PostMapping("/card-bind-status")
    @WebLog(value = "CMIOT_API23A04-物联卡机卡分离状态查询", request = false)
    public R<IotCardAuthToken> cardBindStatus(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询物联卡机卡绑定状态，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.cardBindStatus(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询物联卡机卡绑定状态失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询物联卡机卡绑定状态失败");
        }
    }

    /**
     * 批量办理物联卡通信功能开停
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡通信功能开停批量办理结果
     */
    @Operation(summary = "批量办理物联卡通信功能开停", description = "CMIOT_API23M09-物联卡通信功能开停批量办理")
    @PostMapping("/sim-communication-function/batch")
    @WebLog(value = "CMIOT_API23M09-物联卡通信功能开停批量办理", request = false)
    public R<IotCardAuthToken> simCommunicationFunctionBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量办理物联卡通信功能开停，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simCommunicationFunctionBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量办理物联卡通信功能开停失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量办理物联卡通信功能开停失败");
        }
    }

    /**
     * 实时查询物联卡本月套餐内短信使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡本月套餐内短信使用量
     */
    @Operation(summary = "实时查询物联卡本月套餐内短信使用量", description = "CMIOT_API23U06-单卡本月套餐短信用量实时查询")
    @PostMapping("/sim-sms-margin")
    @WebLog(value = "CMIOT_API23U06-单卡本月套餐短信用量实时查询", request = false)
    public R<IotCardAuthToken> simSmsMargin(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("实时查询物联卡本月套餐内短信使用量，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simSmsMargin(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("实时查询物联卡本月套餐内短信使用量失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("实时查询物联卡本月套餐内短信使用量失败");
        }
    }

    /**
     * 批量查询物联卡月短信使用情况
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡月短信使用情况
     */
    @Operation(summary = "批量查询物联卡月短信使用情况", description = "CMIOT_API25U02-物联卡单月短信使用量批量查询")
    @PostMapping("/sim-sms-usage-monthly/batch")
    @WebLog(value = "CMIOT_API25U02-物联卡单月短信使用量批量查询", request = false)
    public R<IotCardAuthToken> simSmsUsageMonthlyBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量查询物联卡月短信使用情况，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simSmsUsageMonthlyBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量查询物联卡月短信使用情况失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量查询物联卡月短信使用情况失败");
        }
    }

    /**
     * 批量查询物联卡某一天短信使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡某一天短信使用量
     */
    @Operation(summary = "批量查询物联卡某一天短信使用量", description = "CMIOT_API25U00-物联卡单日短信使用量批量查询")
    @PostMapping("/sim-sms-usage-daily/batch")
    @WebLog(value = "CMIOT_API25U00-物联卡单日短信使用量批量查询", request = false)
    public R<IotCardAuthToken> simSmsUsageDailyBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("批量查询物联卡某一天短信使用量，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simSmsUsageDailyBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("批量查询物联卡某一天短信使用量失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("批量查询物联卡某一天短信使用量失败");
        }
    }

    /**
     * 查询物联卡终端控制下行短信记录
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 物联卡终端控制下行短信记录
     */
    @Operation(summary = "查询物联卡终端控制下行短信记录", description = "CMIOT_API25C02-物联卡终端控制下行短信记录批量查询")
    @PostMapping("/sim-mt-sms/batch")
    @WebLog(value = "CMIOT_API25C02-物联卡终端控制下行短信记录批量查询", request = false)
    public R<IotCardAuthToken> simMtSmsBatch(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询物联卡终端控制下行短信记录，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.simMtSmsBatch(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询物联卡终端控制下行短信记录失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询物联卡终端控制下行短信记录失败");
        }
    }

    /**
     * 查询终端开关机信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 终端开关机信息
     */
    @Operation(summary = "查询终端开关机信息", description = "CMIOT_API25M00-单卡开关机状态实时查询")
    @PostMapping("/on-off-status")
    @WebLog(value = "CMIOT_API25M00-单卡开关机状态实时查询", request = false)
    public R<IotCardAuthToken> onOffStatus(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询终端开关机信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.onOffStatus(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询终端开关机信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询终端开关机信息失败");
        }
    }

    /**
     * 查询单卡余额信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R<IotCardAuthToken>} 单卡余额信息
     */
    @Operation(summary = "查询单卡余额信息", description = "CMIOT_API23B01-单卡余额信息实时查询")
    @PostMapping("/balance-info")
    @WebLog(value = "CMIOT_API23B01-单卡余额信息实时查询", request = false)
    public R<IotCardAuthToken> balanceInfo(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询单卡余额信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.balanceInfo(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询单卡余额信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询单卡余额信息失败");
        }
    }

    /**
     * 查询集团客户账单
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R} 集团客户账单
     */
    @Operation(summary = "查询集团客户账单", description = "CMIOT_API23B00-集团客户账单实时查询")
    @PostMapping("/ec-bill")
    @WebLog(value = "CMIOT_API23B00-集团客户账单实时查询", request = false)
    public R ecBill(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询集团客户账单，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.ecBill(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询集团客户账单失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询集团客户账单失败");
        }
    }

    /**
     * 查询物联卡最后上网位置区号信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R} 物联卡最后上网位置区号信息
     */
    @Operation(summary = "查询物联卡最后上网位置区号信息", description = "CMIOT_API25L04-物联卡最后上网位置区号查询")
    @PostMapping("/last-district-position")
    @WebLog(value = "CMIOT_API25L04-物联卡最后上网位置区号查询", request = false)
    public R lastDistrictPosition(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("查询物联卡最后上网位置区号信息，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.lastDistrictPosition(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("查询物联卡最后上网位置区号信息失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("查询物联卡最后上网位置区号信息失败");
        }
    }


    /**
     * 实现各位置坐标系的经纬度转换
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link R} 各位置坐标系的经纬度转换结果
     */
    @Operation(summary = "实现各位置坐标系的经纬度转换", description = "CMIOT_API25L06-经纬度坐标系批量转换")
    @PostMapping("/location/translate/batch")
    @WebLog(value = "CMIOT_API25L06-经纬度坐标系批量转换", request = false)
    public R translate(@RequestBody @Valid CardSimOpenApiParam cardSimOpenApiParam) {
        try {
            log.info("实现各位置坐标系的经纬度转换，param: {}", JsonUtil.toJson(cardSimOpenApiParam));
            ContextUtil.setTenantId(cardSimOpenApiParam.getTenantId());
            return R.success(cardSimOpenAnyTenantService.translate(cardSimOpenApiParam));
        } catch (Exception e) {
            log.error("实现各位置坐标系的经纬度转换失败，租户ID: {}", cardSimOpenApiParam.getTenantId(), e);
            return R.fail("实现各位置坐标系的经纬度转换失败");
        }
    }


}

