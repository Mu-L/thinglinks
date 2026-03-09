package com.mqttsnet.thinglinks.video.entity.device;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 流媒体设备通道表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:09:48
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("video_device_channel")
public class VideoDeviceChannel extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备标识
     */
    @TableField(value = "device_identification", condition = LIKE)
    private String deviceIdentification;
    /**
     * 通道标识
     */
    @TableField(value = "channel_identification", condition = LIKE)
    private String channelIdentification;
    /**
     * 码流标识
     */
    @TableField(value = "stream_identification", condition = LIKE)
    private String streamIdentification;
    /**
     * 通道类型
     */
    @TableField(value = "channel_type", condition = EQUAL)
    private Integer channelType;
    /**
     * 码流类型
     */
    @TableField(value = "stream_type", condition = LIKE)
    private String streamType;
    /**
     * 通道名称
     */
    @TableField(value = "channel_name", condition = LIKE)
    private String channelName;
    /**
     * 厂商
     */
    @TableField(value = "manufacturer", condition = LIKE)
    private String manufacturer;
    /**
     * 型号
     */
    @TableField(value = "model", condition = LIKE)
    private String model;
    /**
     * 警区
     */
    @TableField(value = "block", condition = LIKE)
    private String block;
    /**
     * 省,直辖市编码
     */
    @TableField(value = "province_code", condition = LIKE)
    private String provinceCode;
    /**
     * 市编码
     */
    @TableField(value = "city_code", condition = LIKE)
    private String cityCode;
    /**
     * 行政区划编码
     */
    @TableField(value = "region_code", condition = LIKE)
    private String regionCode;
    /**
     * 位置
     */
    @TableField(value = "full_address", condition = LIKE)
    private String fullAddress;
    /**
     * 纬度
     */
    @TableField(value = "latitude", condition = EQUAL)
    private BigDecimal latitude;
    /**
     * 经度
     */
    @TableField(value = "longitude", condition = EQUAL)
    private BigDecimal longitude;
    /**
     * 信令安全模式
     */
    @TableField(value = "safety_way", condition = EQUAL)
    private Integer safetyWay;
    /**
     * 注册方式
     */
    @TableField(value = "register_way", condition = EQUAL)
    private Integer registerWay;
    /**
     * 证书序列号
     */
    @TableField(value = "cert_num", condition = LIKE)
    private String certNum;
    /**
     * 证书有效标识(0=有效,1=无效,2=过期,3=吊销)
     */
    @TableField(value = "cert_status", condition = EQUAL)
    private Integer certStatus;
    /**
     * 证书无效原因码
     */
    @TableField(value = "cert_invalid_reason_code", condition = LIKE)
    private String certInvalidReasonCode;
    /**
     * 证书有效期截止时间
     */
    @TableField(value = "cert_expiry_time", condition = EQUAL)
    private LocalDateTime certExpiryTime;
    /**
     * 保密属性(0-不涉密,1-涉密)
     */
    @TableField(value = "secrecy", condition = EQUAL)
    private Integer secrecy;
    /**
     * 设备/系统IPv4/IPv6地址
     */
    @TableField(value = "ip_address", condition = LIKE)
    private String ipAddress;
    /**
     * 设备/系统端口
     */
    @TableField(value = "port", condition = EQUAL)
    private Integer port;
    /**
     * 设备口令
     */
    @TableField(value = "password", condition = LIKE)
    private String password;
    /**
     * 是否在线
     */
    @TableField(value = "online_status", condition = EQUAL)
    private Boolean onlineStatus;
    /**
     * 是否含有音频
     */
    @TableField(value = "has_audio", condition = EQUAL)
    private Boolean hasAudio;
    /**
     * 摄像机结构类型
     */
    @TableField(value = "ptz_type", condition = EQUAL)
    private Integer ptzType;
    /**
     * 摄像机位置类型扩展
     */
    @TableField(value = "position_type", condition = EQUAL)
    private Integer positionType;
    /**
     * 摄像机安装位置类型
     */
    @TableField(value = "room_type", condition = EQUAL)
    private Integer roomType;
    /**
     * 用途属性类型
     */
    @TableField(value = "use_type", condition = EQUAL)
    private Integer useType;
    /**
     * 摄像机补光属性类型
     */
    @TableField(value = "supply_light_type", condition = EQUAL)
    private Integer supplyLightType;
    /**
     * 摄像机监视方位(光轴方向)属性类型
     */
    @TableField(value = "direction_type", condition = EQUAL)
    private Integer directionType;
    /**
     * 分辨率
     */
    @TableField(value = "resolution", condition = LIKE)
    private String resolution;
    /**
     * 下载倍速
     */
    @TableField(value = "download_speed", condition = LIKE)
    private String downloadSpeed;
    /**
     * 空域编码能力
     */
    @TableField(value = "svc_space_support_mod", condition = EQUAL)
    private Integer svcSpaceSupportMod;
    /**
     * 时域编码能力
     */
    @TableField(value = "svc_time_support_mode", condition = EQUAL)
    private Integer svcTimeSupportMode;
    /**
     * 扩展参数
     */
    @TableField(value = "extend_params", condition = LIKE)
    private String extendParams;
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


}
