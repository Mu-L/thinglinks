package com.mqttsnet.thinglinks.video.vo.save.device;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

/**
 * <p>
 * 表单保存方法VO
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
@EqualsAndHashCode
@Builder
@Schema(title = "VideoDeviceInfoSaveVO", description = "流媒体设备信息")
public class VideoDeviceInfoSaveVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 设备标识
     */
    @Schema(description = "设备标识")
    @NotEmpty(message = "请填写设备标识")
    @Size(max = 255, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;
    /**
     * 设备名称
     */
    @Schema(description = "设备名称")
    @NotEmpty(message = "请填写设备名称")
    @Size(max = 255, message = "设备名称长度不能超过{max}")
    private String deviceName;
    /**
     * 自定义名称
     */
    @Schema(description = "自定义名称")
    @Size(max = 255, message = "自定义名称长度不能超过{max}")
    private String customName;
    /**
     * 媒体唯一标识
     */
    @Schema(description = "媒体唯一标识")
    @Size(max = 255, message = "媒体唯一标识长度不能超过{max}")
    private String mediaIdentification;
    /**
     * 厂商
     */
    @Schema(description = "厂商")
    @Size(max = 255, message = "厂商长度不能超过{max}")
    private String manufacturer;
    /**
     * 型号
     */
    @Schema(description = "型号")
    @Size(max = 255, message = "型号长度不能超过{max}")
    private String model;
    /**
     * 固件版本
     */
    @Schema(description = "固件版本")
    @Size(max = 100, message = "固件版本长度不能超过{max}")
    private String firmware;
    /**
     * 传输协议（UDP/TCP）
     */
    @Schema(description = "传输协议（UDP/TCP）")
    @Size(max = 50, message = "传输协议（UDP/TCP）长度不能超过{max}")
    private String transport;
    /**
     * 数据流传输模式
     */
    @Schema(description = "数据流传输模式")
    @Size(max = 50, message = "数据流传输模式长度不能超过{max}")
    private String streamMode;
    /**
     * 是否在线
     */
    @Schema(description = "是否在线")
    private Boolean onlineStatus;
    /**
     * 注册时间
     */
    @Schema(description = "注册时间")
    @Size(max = 50, message = "注册时间长度不能超过{max}")
    private String registerTime;
    /**
     * 心跳时间
     */
    @Schema(description = "心跳时间")
    @Size(max = 50, message = "心跳时间长度不能超过{max}")
    private String keepaliveTime;
    /**
     * IP
     */
    @Schema(description = "IP")
    @Size(max = 50, message = "IP长度不能超过{max}")
    private String ip;
    /**
     * 端口
     */
    @Schema(description = "端口")
    private Integer port;
    /**
     * 公网IP
     */
    @Schema(description = "公网IP")
    @Size(max = 50, message = "公网IP长度不能超过{max}")
    private String wanIp;
    /**
     * 局域网IP 
     */
    @Schema(description = "局域网IP")
    @Size(max = 50, message = "局域网IP长度不能超过{max}")
    private String lanIp;
    /**
     * 访问地址
     */
    @Schema(description = "访问地址")
    @Size(max = 50, message = "访问地址长度不能超过{max}")
    private String hostAddress;
    /**
     * 注册有效期
     */
    @Schema(description = "注册有效期")
    private Integer expires;
    /**
     * 目录订阅
     */
    @Schema(description = "目录订阅")
    private Boolean subscribeCycleForCatalog;
    /**
     * 移动设备位置订阅
     */
    @Schema(description = "移动设备位置订阅")
    private Boolean subscribeCycleForMobilePosition;
    /**
     * 报警订阅
     */
    @Schema(description = "报警订阅")
    private Boolean subscribeCycleForAlarm;
    /**
     * 移动设备位置信息上报时间间隔,单位:秒,默认值5
     */
    @Schema(description = "移动设备位置信息上报时间间隔,单位:秒,默认值5")
    private Integer mobilePositionSubmissionInterval;
    /**
     * 字符集
     */
    @Schema(description = "字符集")
    @Size(max = 50, message = "字符集长度不能超过{max}")
    private String charset;
    /**
     * ssrc校验
     */
    @Schema(description = "ssrc校验")
    private Boolean ssrcCheck;
    /**
     * 地理坐标系
     */
    @Schema(description = "地理坐标系")
    @Size(max = 50, message = "地理坐标系长度不能超过{max}")
    private String geoCoordSys;
    /**
     * 收流IP
     */
    @Schema(description = "收流IP")
    @Size(max = 50, message = "收流IP长度不能超过{max}")
    private String sdpIp;
    /**
     * SIP交互IP（设备访问平台的IP）
     */
    @Schema(description = "SIP交互IP（设备访问平台的IP）")
    @Size(max = 50, message = "SIP交互IP（设备访问平台的IP）长度不能超过{max}")
    private String localIp;
    /**
     * 设备密码
     */
    @Schema(description = "设备密码")
    @Size(max = 255, message = "设备密码长度不能超过{max}")
    private String password;
    /**
     * 是否作为消息通道
     */
    @Schema(description = "是否作为消息通道")
    private Boolean asMessageChannel;
    /**
     * 心跳间隔
     */
    @Schema(description = "心跳间隔")
    private Integer keepaliveIntervalTime;
    /**
     * 心跳超时次数
     */
    @Schema(description = "心跳超时次数")
    private Integer keepaliveTimeoutCount;
    /**
     * 定位功能支持情况(0-不支持;1-支持GPS定位;2-支持北斗定位)
     */
    @Schema(description = "定位功能支持情况(0-不支持")
    private Integer positionCapability;
    /**
     * 控制语音对讲流程，释放收到ACK后发流
     */
    @Schema(description = "控制语音对讲流程，释放收到ACK后发流")
    private Boolean broadcastPushAfterAck;
    /**
     * 能力
     */
    @Schema(description = "能力")
    @Size(max = 2147483647, message = "能力长度不能超过{max}")
    private String ability;
    /**
     * 扩展参数
     */
    @Schema(description = "扩展参数")
    @Size(max = 2147483647, message = "扩展参数长度不能超过{max}")
    private String extendParams;
    /**
     * 备注
     */
    @Schema(description = "备注")
    @Size(max = 500, message = "备注长度不能超过{max}")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


}
