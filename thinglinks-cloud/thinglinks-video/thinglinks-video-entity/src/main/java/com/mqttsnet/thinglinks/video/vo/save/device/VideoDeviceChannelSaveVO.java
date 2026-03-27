package com.mqttsnet.thinglinks.video.vo.save.device;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 表单保存方法VO
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
@EqualsAndHashCode
@Builder
@Schema(title = "VideoDeviceChannelSaveVO", description = "流媒体设备通道")
public class VideoDeviceChannelSaveVO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 设备标识
     */
    @Schema(description = "设备标识")
    @NotEmpty(message = "请填写设备标识")
    @Size(max = 255, message = "设备标识长度不能超过{max}")
    private String deviceIdentification;
    /**
     * 通道标识
     */
    @Schema(description = "通道标识")
    @NotEmpty(message = "请填写通道标识")
    @Size(max = 255, message = "通道标识长度不能超过{max}")
    private String channelIdentification;
    /**
     * 码流标识
     */
    @Schema(description = "码流标识")
    @Size(max = 255, message = "码流标识长度不能超过{max}")
    private String streamIdentification;
    /**
     * 通道类型
     */
    @Schema(description = "通道类型")
    @NotNull(message = "请填写通道类型")
    private Integer channelType;
    /**
     * 码流类型
     */
    @Schema(description = "码流类型")
    @Size(max = 50, message = "码流类型长度不能超过{max}")
    private String streamType;
    /**
     * 通道名称
     */
    @Schema(description = "通道名称")
    @Size(max = 255, message = "通道名称长度不能超过{max}")
    private String channelName;
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
     * 警区
     */
    @Schema(description = "警区")
    @Size(max = 50, message = "警区长度不能超过{max}")
    private String block;
    /**
     * 省,直辖市编码
     */
    @Schema(description = "省,直辖市编码")
    @Size(max = 50, message = "省,直辖市编码长度不能超过{max}")
    private String provinceCode;
    /**
     * 市编码
     */
    @Schema(description = "市编码")
    @Size(max = 50, message = "市编码长度不能超过{max}")
    private String cityCode;
    /**
     * 行政区划编码
     */
    @Schema(description = "行政区划编码")
    @Size(max = 50, message = "行政区划编码长度不能超过{max}")
    private String regionCode;
    /**
     * 位置
     */
    @Schema(description = "位置")
    @Size(max = 500, message = "位置长度不能超过{max}")
    private String fullAddress;
    /**
     * 纬度
     */
    @Schema(description = "纬度")
    private BigDecimal latitude;
    /**
     * 经度
     */
    @Schema(description = "经度")
    private BigDecimal longitude;
    /**
     * 信令安全模式
     */
    @Schema(description = "信令安全模式")
    private Integer safetyWay;
    /**
     * 注册方式
     */
    @Schema(description = "注册方式")
    private Integer registerWay;
    /**
     * 证书序列号
     */
    @Schema(description = "证书序列号")
    @Size(max = 100, message = "证书序列号长度不能超过{max}")
    private String certNum;
    /**
     * 证书有效标识(0=有效,1=无效,2=过期,3=吊销)
     */
    @Schema(description = "证书有效标识(0=有效,1=无效,2=过期,3=吊销)")
    private Integer certStatus;
    /**
     * 证书无效原因码
     */
    @Schema(description = "证书无效原因码")
    @Size(max = 255, message = "证书无效原因码长度不能超过{max}")
    private String certInvalidReasonCode;
    /**
     * 证书有效期截止时间
     */
    @Schema(description = "证书有效期截止时间")
    private LocalDateTime certExpiryTime;
    /**
     * 保密属性(0-不涉密,1-涉密)
     */
    @Schema(description = "保密属性(0-不涉密,1-涉密)")
    private Integer secrecy;
    /**
     * 设备/系统IPv4/IPv6地址
     */
    @Schema(description = "设备/系统IPv4/IPv6地址")
    @Size(max = 50, message = "设备/系统IPv4/IPv6地址长度不能超过{max}")
    private String ipAddress;
    /**
     * 设备/系统端口
     */
    @Schema(description = "设备/系统端口")
    private Integer port;
    /**
     * 设备口令
     */
    @Schema(description = "设备口令")
    @Size(max = 255, message = "设备口令长度不能超过{max}")
    private String password;
    /**
     * 是否在线
     */
    @Schema(description = "是否在线")
    private Boolean onlineStatus;
    /**
     * 是否含有音频
     */
    @Schema(description = "是否含有音频")
    private Boolean hasAudio;
    /**
     * 摄像机结构类型
     */
    @Schema(description = "摄像机结构类型")
    private Integer ptzType;
    /**
     * 摄像机位置类型扩展
     */
    @Schema(description = "摄像机位置类型扩展")
    private Integer positionType;
    /**
     * 摄像机安装位置类型
     */
    @Schema(description = "摄像机安装位置类型")
    private Integer roomType;
    /**
     * 用途属性类型
     */
    @Schema(description = "用途属性类型")
    private Integer useType;
    /**
     * 摄像机补光属性类型
     */
    @Schema(description = "摄像机补光属性类型")
    private Integer supplyLightType;
    /**
     * 摄像机监视方位(光轴方向)属性类型
     */
    @Schema(description = "摄像机监视方位(光轴方向)属性类型")
    private Integer directionType;
    /**
     * 分辨率
     */
    @Schema(description = "分辨率")
    @Size(max = 50, message = "分辨率长度不能超过{max}")
    private String resolution;
    /**
     * 下载倍速
     */
    @Schema(description = "下载倍速")
    @Size(max = 50, message = "下载倍速长度不能超过{max}")
    private String downloadSpeed;
    /**
     * 空域编码能力
     */
    @Schema(description = "空域编码能力")
    private Integer svcSpaceSupportMod;
    /**
     * 时域编码能力
     */
    @Schema(description = "时域编码能力")
    private Integer svcTimeSupportMode;
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
