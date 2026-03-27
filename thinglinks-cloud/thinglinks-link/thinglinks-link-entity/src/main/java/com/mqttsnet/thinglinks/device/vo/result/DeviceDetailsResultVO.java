package com.mqttsnet.thinglinks.device.vo.result;

import cn.hutool.core.map.MapUtil;
import com.mqttsnet.basic.annotation.echo.Echo;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.interfaces.echo.EchoVO;
import com.mqttsnet.thinglinks.model.constant.EchoApi;
import com.mqttsnet.thinglinks.model.constant.EchoDictType;
import com.mqttsnet.thinglinks.product.vo.result.ProductResultVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @program: thinglinks-cloud
 * @description: 设备详情结果VO
 * @packagename: com.mqttsnet.thinglinks.device.vo.result
 * @author: ShiHuan Sun
 * @e-mainl: 13733918655@163.com
 * @date: 2023-05-25 16:22
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(title = "DeviceDetailsResultVO", description = "设备详情结果VO")
public class DeviceDetailsResultVO extends Entity<Long> implements Serializable, EchoVO {

    @Serial
    private static final long serialVersionUID = 1L;
    @Builder.Default
    private Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "id")
    private Long id;

    /**
     * 客户端标识
     */
    @Schema(description = "客户端标识")
    private String clientId;
    /**
     * 用户名
     */
    @Schema(description = "用户名")
    private String userName;
    /**
     * 密码
     */
    @Schema(description = "密码")
    private String password;
    /**
     * 证书序列号
     */
    @Schema(description = "证书序列号")
    private String certSerialNumber;
    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private String appId;
    /**
     * 认证方式0-用户名密码，1-ssl证书
     */
    @Schema(description = "认证方式0-用户名密码，1-ssl证书")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_DEVICE_AUTH_MODE)
    private Integer authMode;
    /**
     * 加密密钥
     */
    @Schema(description = "加密密钥")
    private String encryptKey;
    /**
     * 加密向量
     */
    @Schema(description = "加密向量")
    private String encryptVector;
    /**
     * 签名密钥
     */
    @Schema(description = "签名密钥")
    private String signKey;
    /**
     * 传输协议的加密方式
     */
    @Schema(description = "传输协议的加密方式")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_DEVICE_ENCRYPT_METHOD)
    private Integer encryptMethod;
    /**
     * 设备标识
     */
    @Schema(description = "设备标识")
    private String deviceIdentification;
    /**
     * 设备名称
     */
    @Schema(description = "设备名称")
    private String deviceName;
    /**
     * 连接实例
     */
    @Schema(description = "连接实例")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_DEVICE_CONNECTOR)
    private String connector;
    /**
     * 设备描述
     */
    @Schema(description = "设备描述")
    private String description;
    /**
     * 设备状态
     */
    @Schema(description = "设备状态")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_DEVICE_STATUS)
    private Integer deviceStatus;
    /**
     * 连接状态
     */
    @Schema(description = "连接状态")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_DEVICE_CONNECT_STATUS)
    private Integer connectStatus;

    /**
     * 最新心跳时间
     */
    @Schema(description = "最新心跳时间")
    private LocalDateTime lastHeartbeatTime;

    /**
     * 设备标签
     */
    @Schema(description = "设备标签")
    private String deviceTags;
    /**
     * 产品标识
     */
    @Schema(description = "产品标识")
    private String productIdentification;
    /**
     * 软件版本
     */
    @Schema(description = "软件版本")
    private String swVersion;
    /**
     * 固件版本
     */
    @Schema(description = "固件版本")
    private String fwVersion;
    /**
     * sdk版本
     */
    @Schema(description = "sdk版本")
    private String deviceSdkVersion;
    /**
     * 网关设备id
     */
    @Schema(description = "网关设备id")
    private String gatewayId;
    /**
     * 设备类型
     */
    @Schema(description = "设备类型")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_DEVICE_NODE_TYPE)
    private Integer nodeType;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    @Echo(api = EchoApi.ORG_ID_CLASS)
    private Long createdOrgId;

    @Schema(description = "产品详情结果VO")
    private ProductResultVO productResultVO;

    @Schema(description = "子设备详情结果VO")
    private List<DeviceResultVO> subDeviceResultVOList;

    @Schema(description = "设备位置信息VO")
    private DeviceLocationResultVO deviceLocationResultVO;
}
