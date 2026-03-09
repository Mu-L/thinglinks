package com.mqttsnet.thinglinks.device.entity;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.mybatis.typehandler.EncryptTypeHandler;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * <p>
 * 实体类
 * 设备档案信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "device", autoResultMap = true)
public class Device extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 客户端标识
     */
    @TableField(value = "client_id", condition = EQUAL)
    private String clientId;
    /**
     * 用户名
     */
    @TableField(value = "user_name", condition = EQUAL, typeHandler = EncryptTypeHandler.class)
    private String userName;
    /**
     * 密码
     */
    @TableField(value = "password", condition = EQUAL, typeHandler = EncryptTypeHandler.class)
    private String password;
    /**
     * 证书序列号
     */
    @TableField(value = "cert_serial_number", condition = EQUAL)
    private String certSerialNumber;
    /**
     * 应用ID
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 认证方式0-用户名密码，1-ssl证书
     */
    @TableField(value = "auth_mode", condition = EQUAL)
    private Integer authMode;
    /**
     * 加密密钥
     */
    @TableField(value = "encrypt_key", condition = EQUAL, typeHandler = EncryptTypeHandler.class)
    private String encryptKey;
    /**
     * 加密向量
     */
    @TableField(value = "encrypt_vector", condition = EQUAL, typeHandler = EncryptTypeHandler.class)
    private String encryptVector;
    /**
     * 签名密钥
     */
    @TableField(value = "sign_key", condition = EQUAL, typeHandler = EncryptTypeHandler.class)
    private String signKey;
    /**
     * 传输协议的加密方式：0-明文传输、1-SM4、2-AES
     */
    @TableField(value = "encrypt_method", condition = EQUAL)
    private Integer encryptMethod;
    /**
     * 设备标识
     */
    @TableField(value = "device_identification", condition = EQUAL)
    private String deviceIdentification;
    /**
     * 设备名称
     */
    @TableField(value = "device_name", condition = EQUAL, typeHandler = EncryptTypeHandler.class)
    private String deviceName;
    /**
     * 连接实例
     */
    @TableField(value = "connector", condition = LIKE)
    private String connector;
    /**
     * 设备描述
     */
    @TableField(value = "description", condition = LIKE)
    private String description;
    /**
     * 设备状态:1启用ENABLE || 2禁用DISABLE||未激活NOTACTIVE 0
     */
    @TableField(value = "device_status", condition = EQUAL)
    private Integer deviceStatus;
    /**
     * 连接状态:在线：1ONLINE || 离线：2OFFLINE || 未连接：INIT 0
     */
    @TableField(value = "connect_status", condition = EQUAL)
    private Integer connectStatus;

    /**
     * 最新心跳时间
     */
    @TableField(value = "last_heartbeat_time", condition = EQUAL)
    private LocalDateTime lastHeartbeatTime;

    /**
     * 设备标签
     */
    @TableField(value = "device_tags", condition = LIKE)
    private String deviceTags;
    /**
     * 产品标识
     */
    @TableField(value = "product_identification", condition = LIKE)
    private String productIdentification;
    /**
     * 软件版本
     */
    @TableField(value = "sw_version", condition = LIKE)
    private String swVersion;
    /**
     * 固件版本
     */
    @TableField(value = "fw_version", condition = LIKE)
    private String fwVersion;
    /**
     * sdk版本
     */
    @TableField(value = "device_sdk_version", condition = LIKE)
    private String deviceSdkVersion;
    /**
     * 网关设备id
     */
    @TableField(value = "gateway_id", condition = LIKE)
    private String gatewayId;
    /**
     * 设备类型:0普通设备 || 1网关设备 || 2子设备
     */
    @TableField(value = "node_type", condition = EQUAL)
    private Integer nodeType;
    /**
     * 备注
     */
    @TableField(value = "remark", condition = LIKE)
    private String remark;
    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id", condition = EQUAL)
    private Long createdOrgId;
    /**
     * 逻辑删除标识:0-未删除 1-已删除
     */
    @TableLogic
    @TableField(value = "deleted", condition = EQUAL)
    private Integer deleted;


}
