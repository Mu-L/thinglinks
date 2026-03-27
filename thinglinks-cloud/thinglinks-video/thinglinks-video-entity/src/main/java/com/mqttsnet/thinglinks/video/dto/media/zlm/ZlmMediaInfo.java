package com.mqttsnet.thinglinks.video.dto.media.zlm;

import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
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

/**
 * -----------------------------------------------------------------------------
 * File Name: ZlmMediaInfo
 * -----------------------------------------------------------------------------
 * Description:
 * ZLM流信息实体
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
@Schema(description = "ZLM视频信息")
public class ZlmMediaInfo implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Schema(description = "应用名")
    private String app;
    @Schema(description = "流ID")
    private String stream;
    @Schema(description = "流媒体服务器信息")
    private VideoMediaServerResultDTO mediaServer;


    @Schema(description = "协议")
    private String schema;

    @Schema(description = "观看人数")
    private Integer readerCount;
    @Schema(description = "视频编码类型")
    private String videoCodec;
    @Schema(description = "视频宽度")
    private Integer width;
    @Schema(description = "视频高度")
    private Integer height;
    @Schema(description = "音频编码类型")
    private String audioCodec;
    @Schema(description = "音频通道数")
    private Integer audioChannels;
    @Schema(description = "音频采样率")
    private Integer audioSampleRate;
    @Schema(description = "音频采样率")
    private Long duration;
    @Schema(description = "在线")
    private Boolean online;
    @Schema(description = "unknown = 0,rtmp_push=1,rtsp_push=2,rtp_push=3,pull=4,ffmpeg_pull=5,mp4_vod=6,device_chn=7")
    private Integer originType;
    @Schema(description = "存活时间，单位秒")
    private Long aliveSecond;
    @Schema(description = "数据产生速度，单位byte/s")
    private Long bytesSpeed;
    @Schema(description = "鉴权参数")
    private String callId;


    public static String getVideoCodec(Integer codecId) {
        switch (codecId) {
            case 0:
                return "H264";
            case 1:
                return "H265";
            default:
                return null;
        }
    }

    public static String getAudioCodec(Integer codecId) {
        switch (codecId) {
            case 2:
                return "AAC";
            case 3:
                return "G711A";
            case 4:
                return "G711U";
            default:
                return null;
        }
    }

}
