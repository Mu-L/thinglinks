package com.mqttsnet.thinglinks.openapi.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 错误故障信息枚举
 *
 * @author mqttsnet
 */
@AllArgsConstructor
@Getter
public enum ErrorStoryMessageEnum {
    /**
     * 参数错误
     */
    ISV_PARAM_ERROR("isv.invalid-parameter", "参数错误,请检查参数是否正确", "请检查参数是否正确"),


    //-------------------------------------OTA任务相关错误-------------------------------------


    /**
     * OTA任务确认升级失败
     */
    OTA_CONFIRM_UPGRADE_FAILED("ota.confirm-upgrade-failed", "OTA任务确认升级失败...", "稍后请重试"),

    /**
     * OTA任务拒绝升级失败
     */
    OTA_REJECT_UPGRADE_FAILED("ota.reject-upgrade-failed", "OTA任务拒绝升级失败...", "稍后请重试"),


    //-------------------------------------设备相关错误-------------------------------------


    /**
     * 设备创建失败
     */
    DEVICE_CREATE_FAILED("device.create-failed", "设备创建失败", "请检查设备参数是否正确"),

    /**
     * 设备查询失败
     */
    DEVICE_QUERY_FAILED("device.query-failed", "设备查询失败", "请检查设备标识是否正确"),


    /**
     * 设备命令下发失败
     */
    DEVICE_COMMAND_ISSUE_FAILED("device.command.issue-failed", "设备命令下发失败", "请检查命令参数是否正确"),

    /**
     * 设备状态修改失败
     */
    DEVICE_UPDATE_STATUS_FAILED("device.update-status-failed", "设备状态修改失败", "请检查设备标识和状态值是否正确"),

    /**
     * 设备删除失败
     */
    DEVICE_DELETE_FAILED("device.delete-failed", "设备删除失败", "请检查设备标识是否正确"),

    /**
     * 设备数据上报失败
     */
    DEVICE_DATA_REPORT_FAILED("device.data-report-failed", "设备数据上报失败", "请检查数据参数是否正确"),

    //-------------------------------------OTA北向API相关错误-------------------------------------

    /**
     * OTA保存升级记录失败
     */
    OTA_SAVE_UPGRADE_RECORD_FAILED("ota.save-upgrade-record-failed", "OTA保存升级记录失败", "请检查参数是否正确"),

    /**
     * OTA拉取软固件信息失败
     */
    OTA_PULL_FAILED("ota.pull-failed", "OTA拉取软固件信息失败", "请检查设备标识和包类型是否正确"),

    /**
     * OTA上报软固件版本失败
     */
    OTA_REPORT_FAILED("ota.report-failed", "OTA上报软固件版本失败", "请检查参数是否正确"),

    /**
     * OTA读取响应失败
     */
    OTA_READ_RESPONSE_FAILED("ota.read-response-failed", "OTA读取响应失败", "请检查参数是否正确"),

//-------------------------------------产品相关错误-------------------------------------
    /**
     * 产品查询失败
     */
    PRODUCT_QUERY_FAILED("product.query-failed", "产品查询失败", "请检查产品标识是否正确"),

    /**
     * 产品物模型查询失败
     */
    PRODUCT_THING_MODEL_QUERY_FAILED("product.thing-model-query-failed", "产品物模型查询失败", "请检查产品标识是否正确"),


    //-------------------------------------其他错误-------------------------------------


    ;

    /**
     * 子错误码
     */
    private final String subCode;
    /**
     * 子错误信息
     */
    private final String subMsg;
    /**
     * 解决方案
     */
    private final String solution;
}
