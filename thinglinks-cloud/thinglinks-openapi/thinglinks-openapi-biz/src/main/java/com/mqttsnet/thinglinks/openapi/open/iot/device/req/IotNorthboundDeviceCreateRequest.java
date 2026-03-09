package com.mqttsnet.thinglinks.openapi.open.iot.device.req;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * Description:
 * 创建设备请求参数
 *
 * @author mqttsnet
 * @version 1.0.0
 * @apiNote 用于注册新设备到物联网平台，支持北向接口调用
 * @since 2025/6/22
 */
@Data
public class IotNorthboundDeviceCreateRequest {

    /**
     * 客户端标识
     * 定义必须以@TenantId结尾
     * @mock 100001@1
     */
    @NotEmpty(message = "请填写客户端标识")
    @Size(max = 255, message = "客户端标识长度不能超过{max}")
    private String clientId;
    /**
     * 用户名
     */
    @NotEmpty(message = "请填写用户名")
    @Size(max = 255, message = "用户名长度不能超过{max}")
    private String userName;
    /**
     * 密码
     */
    @NotEmpty(message = "请填写密码")
    @Size(max = 255, message = "密码长度不能超过{max}")
    private String password;
    /**
     * 应用ID
     */
    @NotEmpty(message = "请填写应用ID")
    @Size(max = 64, message = "应用ID长度不能超过{max}")
    private String appId;
    /**
     * 认证方式0-用户名密码，1-ssl证书
     *
     * @mock 0
     */
    @NotNull(message = "请填写认证方式0-用户名密码，1-ssl证书")
    private Integer authMode;
    /**
     * 加密密钥
     */
    @Size(max = 255, message = "加密密钥长度不能超过{max}")
    private String encryptKey;
    /**
     * 加密向量
     */
    @Size(max = 255, message = "加密向量长度不能超过{max}")
    private String encryptVector;
    /**
     * 签名密钥
     */
    @Size(max = 255, message = "签名密钥长度不能超过{max}")
    private String signKey;
    /**
     * 传输协议的加密方式：0-明文传输、1-SM4、2-AES
     *
     * @mock 0
     */
    @NotNull(message = "请填写传输协议的加密方式：0-明文传输、1-SM4、2-AES")
    private Integer encryptMethod;
    /**
     * 设备唯一标识（SN码/IMEI）
     * 必须保证唯一
     *
     * @mock "SN-2025-ABCD-5678"
     */
    @NotEmpty(message = "请填写设备标识")
    @Size(max = 255, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;
    /**
     * 设备名称
     */
    @NotEmpty(message = "请填写设备名称")
    @Size(max = 255, message = "设备名称长度不能超过{max}")
    private String deviceName;
    /**
     * 连接实例
     */
    @Size(max = 255, message = "连接实例长度不能超过{max}")
    private String connector;
    /**
     * 设备描述
     */
    @Size(max = 255, message = "设备描述长度不能超过{max}")
    private String description;
    /**
     * 设备状态:1-启用ENABLE || 2-禁用DISABLE||0-未激活NOTACTIVE
     */
    @NotNull(message = "请填写设备状态")
    private Integer deviceStatus;
    /**
     * 连接状态:在线：1-ONLINE || 离线：2-OFFLINE || 未连接：0-INIT
     */
    @NotNull(message = "请填写连接状态")
    private Integer connectStatus;
    /**
     * 设备标签
     */
    @Size(max = 255, message = "设备标签长度不能超过{max}")
    private String deviceTags;
    /**
     * 产品标识
     */
    @NotEmpty(message = "请填写产品标识")
    private String productIdentification;
    /**
     * 软件版本
     */
    @Size(max = 255, message = "软件版本长度不能超过{max}")
    private String swVersion;
    /**
     * 固件版本
     */
    @Size(max = 255, message = "固件版本长度不能超过{max}")
    private String fwVersion;
    /**
     * sdk版本
     */
    @Size(max = 255, message = "sdk版本长度不能超过{max}")
    private String deviceSdkVersion;
    /**
     * 网关设备id
     * 如果设备类型是子设备,必须填写父(网关)设备的设备标识
     */
    private String gatewayId;

    /**
     * 设备类型:0-普通设备 || 1-网关设备 || 2-子设备
     *
     * @mock 0
     */
    @NotNull(message = "设备类型不能为空")
    private Integer nodeType;

    /**
     * 备注
     */
    @Size(max = 500, message = "备注长度不能超过{max}")
    private String remark;

}
