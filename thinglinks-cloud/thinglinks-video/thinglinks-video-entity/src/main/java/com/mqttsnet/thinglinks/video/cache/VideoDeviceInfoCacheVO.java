package com.mqttsnet.thinglinks.video.cache;

import cn.hutool.core.map.MapUtil;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.interfaces.echo.EchoVO;
import com.mqttsnet.thinglinks.video.dto.gb28181.SipTransactionInfo;
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
import java.util.Map;

/**
 * <p>
 * 流媒体设备信息缓存VO
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
@Schema(title = "VideoDeviceInfoCacheVO", description = "流媒体设备信息缓存VO")
public class VideoDeviceInfoCacheVO extends Entity<Long> implements Serializable, EchoVO {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "唯一标识符")
    private Long id;

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
     * 自定义名称
     */
    @Schema(description = "自定义名称")
    private String customName;
    /**
     * 媒体唯一标识
     */
    @Schema(description = "媒体唯一标识")
    private String mediaIdentification;
    /**
     * 厂商
     */
    @Schema(description = "厂商")
    private String manufacturer;
    /**
     * 型号
     */
    @Schema(description = "型号")
    private String model;
    /**
     * 固件版本
     */
    @Schema(description = "固件版本")
    private String firmware;
    /**
     * 传输协议（UDP/TCP）
     */
    @Schema(description = "传输协议（UDP/TCP）")
    private String transport;
    /**
     * 数据流传输模式
     */
    @Schema(description = "数据流传输模式")
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
    private String registerTime;
    /**
     * 心跳时间
     */
    @Schema(description = "心跳时间")
    private String keepaliveTime;
    /**
     * IP
     */
    @Schema(description = "IP")
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
    private String wanIp;
    /**
     * 局域网IP
     */
    @Schema(description = "局域网IP")
    private String lanIp;
    /**
     * 访问地址
     */
    @Schema(description = "访问地址")
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
    private String geoCoordSys;
    /**
     * 收流IP
     */
    @Schema(description = "收流IP")
    private String sdpIp;
    /**
     * SIP交互IP（设备访问平台的IP）
     */
    @Schema(description = "SIP交互IP（设备访问平台的IP）")
    private String localIp;
    /**
     * 设备密码
     */
    @Schema(description = "设备密码")
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
    private String ability;
    /**
     * 扩展参数
     */
    @Schema(description = "扩展参数")
    private String extendParams;
    /**
     * 备注
     */
    @Schema(description = "备注")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    private Long createdOrgId;


    /**
     * SIP 事务信息
     */
    @Schema(description = "SIP事务信息")
    private SipTransactionInfo sipTransactionInfo;

}
