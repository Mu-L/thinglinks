package com.mqttsnet.thinglinks.video.entity.device;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * 流媒体设备信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2025-05-15 17:00:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder(toBuilder = true)
@TableName(value = "video_device_info", autoResultMap = true)
public class VideoDeviceInfo extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备标识
     */
    @TableField(value = "device_identification", condition = LIKE)
    private String deviceIdentification;
    /**
     * 设备名称
     */
    @TableField(value = "device_name", condition = LIKE)
    private String deviceName;
    /**
     * 自定义名称
     */
    @TableField(value = "custom_name", condition = LIKE)
    private String customName;
    /**
     * 媒体唯一标识
     */
    @TableField(value = "media_identification", condition = LIKE)
    private String mediaIdentification;
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
     * 固件版本
     */
    @TableField(value = "firmware", condition = LIKE)
    private String firmware;
    /**
     * 传输协议（UDP/TCP）
     */
    @TableField(value = "transport", condition = LIKE)
    private String transport;
    /**
     * 数据流传输模式
     */
    @TableField(value = "stream_mode", condition = LIKE)
    private String streamMode;
    /**
     * 是否在线
     */
    @TableField(value = "online_status", condition = EQUAL)
    private Boolean onlineStatus;
    /**
     * 注册时间
     */
    @TableField(value = "register_time", condition = LIKE)
    private String registerTime;
    /**
     * 心跳时间
     */
    @TableField(value = "keepalive_time", condition = LIKE)
    private String keepaliveTime;
    /**
     * IP
     */
    @TableField(value = "ip", condition = LIKE)
    private String ip;
    /**
     * 端口
     */
    @TableField(value = "port", condition = EQUAL)
    private Integer port;
    /**
     * 公网IP
     */
    @TableField(value = "wan_ip", condition = LIKE)
    private String wanIp;
    /**
     * 局域网IP
     */
    @TableField(value = "lan_ip", condition = LIKE)
    private String lanIp;
    /**
     * 访问地址
     */
    @TableField(value = "host_address", condition = LIKE)
    private String hostAddress;
    /**
     * 注册有效期
     */
    @TableField(value = "expires", condition = EQUAL)
    private Integer expires;
    /**
     * 目录订阅
     */
    @TableField(value = "subscribe_cycle_for_catalog", condition = EQUAL)
    private Boolean subscribeCycleForCatalog;
    /**
     * 移动设备位置订阅
     */
    @TableField(value = "subscribe_cycle_for_mobile_position", condition = EQUAL)
    private Boolean subscribeCycleForMobilePosition;
    /**
     * 报警订阅
     */
    @TableField(value = "subscribe_cycle_for_alarm", condition = EQUAL)
    private Boolean subscribeCycleForAlarm;
    /**
     * 移动设备位置信息上报时间间隔,单位:秒,默认值5
     */
    @TableField(value = "mobile_position_submission_interval", condition = EQUAL)
    private Integer mobilePositionSubmissionInterval;
    /**
     * 字符集
     */
    @TableField(value = "charset", condition = LIKE)
    private String charset;
    /**
     * ssrc校验
     */
    @TableField(value = "ssrc_check", condition = EQUAL)
    private Boolean ssrcCheck;
    /**
     * 地理坐标系
     */
    @TableField(value = "geo_coord_sys", condition = LIKE)
    private String geoCoordSys;
    /**
     * 收流IP
     */
    @TableField(value = "sdp_ip", condition = LIKE)
    private String sdpIp;
    /**
     * SIP交互IP（设备访问平台的IP）
     */
    @TableField(value = "local_ip", condition = LIKE)
    private String localIp;
    /**
     * 密码
     */
    @TableField(value = "password", condition = EQUAL, typeHandler = EncryptTypeHandler.class)
    private String password;
    /**
     * 是否作为消息通道
     */
    @TableField(value = "as_message_channel", condition = EQUAL)
    private Boolean asMessageChannel;
    /**
     * 心跳间隔
     */
    @TableField(value = "keepalive_interval_time", condition = EQUAL)
    private Integer keepaliveIntervalTime;
    /**
     * 心跳超时次数
     */
    @TableField(value = "keepalive_timeout_count", condition = EQUAL)
    private Integer keepaliveTimeoutCount;
    /**
     * 定位功能支持情况(0-不支持;1-支持GPS定位;2-支持北斗定位)
     */
    @TableField(value = "position_capability", condition = EQUAL)
    private Integer positionCapability;
    /**
     * 控制语音对讲流程，释放收到ACK后发流
     */
    @TableField(value = "broadcast_push_after_ack", condition = EQUAL)
    private Boolean broadcastPushAfterAck;
    /**
     * 能力
     */
    @TableField(value = "ability", condition = LIKE)
    private String ability;
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
