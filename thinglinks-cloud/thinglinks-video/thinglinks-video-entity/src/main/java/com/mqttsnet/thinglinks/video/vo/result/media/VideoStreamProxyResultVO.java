package com.mqttsnet.thinglinks.video.vo.result.media;

import cn.hutool.core.map.MapUtil;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.interfaces.echo.EchoVO;
import com.mqttsnet.thinglinks.video.vo.result.media.zlm.ZlmMediaServerStreamInfoResultVO;
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
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 表单查询方法返回值VO
 * 视频拉流代理信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-07-05 22:32:06
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(description = "视频拉流代理信息表")
public class VideoStreamProxyResultVO extends Entity<Long> implements Serializable, EchoVO {

    @Serial
    private static final long serialVersionUID = 1L;

    private Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "唯一标识符")
    private Long id;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private String appId;
    /**
     * 代理类型
     */
    @Schema(description = "代理类型")
    private String proxyType;
    /**
     * 代理名称
     */
    @Schema(description = "代理名称")
    private String proxyName;
    /**
     * 流唯一标识
     */
    @Schema(description = "流唯一标识")
    private String streamIdentification;
    /**
     * 拉流地址
     */
    @Schema(description = "拉流地址")
    private String url;
    /**
     * 源地址
     */
    @Schema(description = "源地址")
    private String srcUrl;
    /**
     * 目标地址
     */
    @Schema(description = "目标地址")
    private String dstUrl;
    /**
     * 超时时间（毫秒）
     */
    @Schema(description = "超时时间（毫秒）")
    private Integer timeoutMs;
    /**
     * FFmpeg模板KEY
     */
    @Schema(description = "FFmpeg模板KEY")
    private String ffmpegCmdKey;
    /**
     * RTSP拉流时的拉流方式（0：TCP，1：UDP，2：组播）
     */
    @Schema(description = "RTSP拉流时的拉流方式（0：TCP，1：UDP，2：组播）")
    private String rtpType;
    /**
     * 国标唯一标识
     */
    @Schema(description = "国标唯一标识")
    private String gbIdentification;
    /**
     * 媒体唯一标识
     */
    @Schema(description = "媒体唯一标识")
    private String mediaIdentification;
    /**
     * 是否启用音频
     */
    @Schema(description = "是否启用音频")
    private Boolean enableAudio;
    /**
     * 是否启用MP4
     */
    @Schema(description = "是否启用MP4")
    private Boolean enableMp4;
    /**
     * 状态
     */
    @Schema(description = "状态")
    private Boolean status;
    /**
     * 无人观看时是否删除
     */
    @Schema(description = "无人观看时是否删除")
    private Boolean enableRemoveNoneReader;
    /**
     * 拉流代理时ZLM返回的KEY，用于停止拉流代理
     */
    @Schema(description = "拉流代理时ZLM返回的KEY，用于停止拉流代理")
    private String streamKey;
    /**
     * 无人观看时是否自动停用
     */
    @Schema(description = "无人观看时是否自动停用")
    private Boolean enableDisableNoneReader;
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
     * ZLM流媒体信息集合
     */
    @Schema(description = "ZLM流媒体信息集合")
    private List<ZlmMediaServerStreamInfoResultVO> zlmMediaServerStreamInfoList;


}
