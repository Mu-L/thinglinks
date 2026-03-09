package com.mqttsnet.thinglinks.video.dto.media.zlm;

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

/**
 * -----------------------------------------------------------------------------
 * File Name: ZlmMediaListInfo
 * -----------------------------------------------------------------------------
 * Description:
 * ZLM流信息实体
 * 获取流列表(getMediaList)
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/8       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/8 00:39
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@Builder
@Schema(description = "ZLM流媒体列表信息表")
public class ZlmMediaListInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "存活时间，单位秒")
    private Long aliveSecond;

    @Schema(description = "应用名")
    private String app;

    @Schema(description = "字节速度")
    private Long bytesSpeed;

    @Schema(description = "创建时间戳")
    private Long createStamp;

    @Schema(description = "是否录制HLS")
    private Boolean isRecordingHLS;

    @Schema(description = "是否录制MP4")
    private Boolean isRecordingMP4;

    @Schema(description = "原始套接字信息")
    private OriginSock originSock;

    @Schema(description = "产生源类型")
    private Integer originType;

    @Schema(description = "产生源类型的字符串描述")
    private String originTypeStr;

    @Schema(description = "产生源的URL")
    private String originUrl;

    @Schema(description = "参数")
    private String params;

    @Schema(description = "读取器数量")
    private Integer readerCount;

    @Schema(description = "协议")
    private String schema;

    @Schema(description = "流ID")
    private String stream;

    @Schema(description = "总读取器数量")
    private Integer totalReaderCount;

    @Schema(description = "音视频轨道信息")
    private List<Track> tracks;

    @Schema(description = "虚拟主机")
    private String vhost;

    @Schema(description = "鉴权参数")
    private String callId;

    /**
     * 是否在线
     * 通过 isMediaOnline API 获取状态信息
     */
    @Schema(description = "是否在线")
    private Boolean online;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    @Schema(description = "原始套接字信息")
    public static class OriginSock {
        @Schema(description = "标识符")
        private String identifier;

        @Schema(description = "本地IP")
        private String localIp;

        @Schema(description = "本地端口")
        private Integer localPort;

        @Schema(description = "对端IP")
        private String peerIp;

        @Schema(description = "对端端口")
        private Integer peerPort;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    @Builder
    @Schema(description = "音视频轨道信息")
    public static class Track {
        @Schema(description = "声道数量")
        private Integer channels;

        @Schema(description = "编码ID")
        private Integer codecId;

        @Schema(description = "编码名称")
        private String codecIdName;

        @Schema(description = "编码类型")
        private Integer codecType;

        @Schema(description = "持续时间")
        private Long duration;

        @Schema(description = "帧数")
        private Long frames;

        @Schema(description = "丢失帧数")
        private Integer loss;

        @Schema(description = "是否准备好")
        private Boolean ready;

        @Schema(description = "采样位数")
        private Integer sampleBit;

        @Schema(description = "采样率")
        private Integer sampleRate;

        @Schema(description = "帧率")
        private Integer fps;

        @Schema(description = "GOP 间隔（毫秒）")
        private Long gopIntervalMs;

        @Schema(description = "GOP 大小")
        private Integer gopSize;

        @Schema(description = "视频高度")
        private Integer height;

        @Schema(description = "关键帧数")
        private Integer keyFrames;

        @Schema(description = "视频宽度")
        private Integer width;
    }
}