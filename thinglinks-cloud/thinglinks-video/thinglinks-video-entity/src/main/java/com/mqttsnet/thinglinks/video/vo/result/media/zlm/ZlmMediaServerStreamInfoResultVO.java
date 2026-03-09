package com.mqttsnet.thinglinks.video.vo.result.media.zlm;

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
 * File Name: ZlmMediaServerStreamInfo
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
@Schema(description = "ZLM流媒体信息")
public class ZlmMediaServerStreamInfoResultVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    @Schema(description = "流媒体服务器信息表")
    private VideoMediaServerResultDTO mediaServer;


    @Schema(description = "HTTP-FLV流地址")
    private String flv;

    @Schema(description = "HTTPS-FLV流地址")
    private String httpsFlv;

    @Schema(description = "Websocket-FLV流地址")
    private String wsFlv;

    @Schema(description = "Websockets-FLV流地址")
    private String wssFlv;

    @Schema(description = "HTTP-FMP4流地址")
    private String fmp4;

    @Schema(description = "HTTPS-FMP4流地址")
    private String httpsFmp4;

    @Schema(description = "Websocket-FMP4流地址")
    private String wsFmp4;

    @Schema(description = "Websockets-FMP4流地址")
    private String wssFmp4;

    @Schema(description = "HLS流地址")
    private String hls;

    @Schema(description = "HTTPS-HLS流地址")
    private String httpsHls;

    @Schema(description = "Websocket-HLS流地址")
    private String wsHls;

    @Schema(description = "Websockets-HLS流地址")
    private String wssHls;

    @Schema(description = "HTTP-TS流地址")
    private String ts;

    @Schema(description = "HTTPS-TS流地址")
    private String httpsTs;

    @Schema(description = "Websocket-TS流地址")
    private String wsTs;

    @Schema(description = "Websockets-TS流地址")
    private String wssTs;

    @Schema(description = "RTMP流地址")
    private String rtmp;

    @Schema(description = "RTMPS流地址")
    private String rtmps;

    @Schema(description = "RTSP流地址")
    private String rtsp;

    @Schema(description = "RTC流地址")
    private String rtc;

    @Schema(description = "RTCS流地址")
    private String rtcs;


}
