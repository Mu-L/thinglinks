package com.mqttsnet.thinglinks.video.vo.update.media;

import com.mqttsnet.basic.base.entity.SuperEntity;
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

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 表单修改方法VO
 * 视频推流信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-07 19:19:57
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
@Schema(description = "视频推流信息表")
public class VideoStreamPushUpdateVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "id")
    @NotNull(message = "请填写id", groups = SuperEntity.Update.class)
    private Long id;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    @NotEmpty(message = "请填写应用ID")
    @Size(max = 64, message = "应用ID长度不能超过{max}")
    private String appId;
    /**
     * 流唯一标识
     */
    @Schema(description = "流唯一标识")
    @Size(max = 255, message = "流唯一标识长度不能超过{max}")
    private String streamIdentification;
    /**
     * 观看总人数
     */
    @Schema(description = "观看总人数")
    private Integer totalReaderCount;
    /**
     * 产生源类型
     */
    @Schema(description = "产生源类型")
    private Integer originType;
    /**
     * 产生源的url
     */
    @Schema(description = "产生源的url")
    @Size(max = 255, message = "产生源的url长度不能超过{max}")
    private String originUrl;
    /**
     * 音视频轨道
     */
    @Schema(description = "音视频轨道")
    @Size(max = 255, message = "音视频轨道长度不能超过{max}")
    private String vhost;
    /**
     * 数据产生速度，单位byte/s
     */
    @Schema(description = "数据产生速度，单位byte/s")
    private Double bytesSpeed;
    /**
     * 存活时间，单位秒
     */
    @Schema(description = "存活时间，单位秒")
    private Long aliveSecond;
    /**
     * 媒体唯一标识
     */
    @Schema(description = "媒体唯一标识")
    @NotEmpty(message = "请填写媒体唯一标识")
    @Size(max = 255, message = "媒体唯一标识长度不能超过{max}")
    private String mediaIdentification;
    /**
     * 使用的服务ID
     */
    @Schema(description = "使用的服务ID")
    @Size(max = 50, message = "使用的服务ID长度不能超过{max}")
    private String serverId;
    /**
     * 推流时间
     */
    @Schema(description = "推流时间")
    private LocalDateTime pushTime;
    /**
     * 状态
     */
    @Schema(description = "状态")
    private Boolean status;
    /**
     * 是否正在推流
     */
    @Schema(description = "是否正在推流")
    private Boolean pushIng;
    /**
     * 是否自己平台的推流
     */
    @Schema(description = "是否自己平台的推流")
    private Boolean self;
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
