package com.mqttsnet.thinglinks.video.config.zlm;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.thinglinks.video.common.CommonCallback;
import com.mqttsnet.thinglinks.video.dto.gb28181.SendRtpInfo;
import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZlmRestfulResult;
import com.mqttsnet.thinglinks.video.utils.zlm.ZlmRestfulUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class ZLMServerFactory {
    @Autowired
    private ZlmRestfulUtils zlmRestfulUtils;


    /**
     * 开启rtpServer
     *
     * @param mediaServerItem zlm服务实例
     * @param streamId        流Id
     * @param ssrc            ssrc
     * @param port            端口， 0/null为使用随机
     * @param reUsePort       是否重用端口
     * @param tcpMode         0/null udp 模式，1 tcp 被动模式, 2 tcp 主动模式。
     * @return
     */
    public int createRTPServer(VideoMediaServerResultDTO mediaServerItem, String streamId, long ssrc, Integer port, Boolean onlyAuto, Boolean reUsePort, Integer tcpMode) {
        int result = -1;
        // 查询此rtp server 是否已经存在
        ZlmRestfulResult<JSONObject> zlmRestfulResult = zlmRestfulUtils.getRtpInfo(mediaServerItem, streamId);
        if (zlmRestfulResult.isSuccess()) {
            JSONObject rtpInfo = zlmRestfulResult.getData();
            if (rtpInfo.getBoolean("exist")) {
                result = rtpInfo.getInteger("local_port");
                if (result == 0) {
                    // 此时说明rtpServer已经创建但是流还没有推上来
                    // 此时重新打开rtpServer
                    Map<String, Object> param = new HashMap<>();
                    param.put("stream_id", streamId);
                    ZlmRestfulResult<JSONObject> closeRtpServerResult = zlmRestfulUtils.closeRtpServer(mediaServerItem, param);
                    if (closeRtpServerResult.isSuccess()) {
                        JSONObject data = closeRtpServerResult.getData();
                        if (data.getInteger("code") == 0) {
                            return createRTPServer(mediaServerItem, streamId, ssrc, port, onlyAuto, reUsePort, tcpMode);
                        } else {
                            log.warn("[开启rtpServer], 重启RtpServer错误");
                        }
                    }
                }
                return result;
            }
        } else if (zlmRestfulResult.getCode() == -2) {
            return result;
        }

        Map<String, Object> param = new HashMap<>();

        if (tcpMode == null) {
            tcpMode = 0;
        }
        param.put("tcp_mode", tcpMode);
        param.put("stream_id", streamId);
        if (reUsePort != null) {
            param.put("re_use_port", reUsePort ? "1" : "0");
        }
        // 推流端口设置0则使用随机端口
        if (port == null) {
            param.put("port", 0);
        } else {
            param.put("port", port);
        }
        if (onlyAuto != null) {
            param.put("only_audio", onlyAuto ? "1" : "0");
        }
        if (ssrc != 0) {
            param.put("ssrc", ssrc);
        }

        ZlmRestfulResult<JSONObject> openRtpServerResultJson = zlmRestfulUtils.openRtpServer(mediaServerItem, param);
        if (openRtpServerResultJson.isSuccess()) {
            result = openRtpServerResultJson.getData().getInteger("port");
        } else {
            //  检查ZLM状态
            log.error("创建RTP Server 失败 {}: 请检查ZLM服务,result:{}", param.get("port"), JSONObject.toJSONString(openRtpServerResultJson));
        }
        return result;
    }

    public boolean closeRtpServer(VideoMediaServerResultDTO serverItem, String streamId) {
        boolean result = false;
        if (serverItem != null) {
            Map<String, Object> param = new HashMap<>();
            param.put("stream_id", streamId);
            ZlmRestfulResult<JSONObject> objectZlmRestfulResult = zlmRestfulUtils.closeRtpServer(serverItem, param);
            log.info("关闭RTP Server {}", JSONObject.toJSONString(serverItem));
            if (objectZlmRestfulResult.isSuccess()) {
                JSONObject jsonObject = objectZlmRestfulResult.getData();
                if (jsonObject.getInteger("code") == 0) {
                    result = jsonObject.getInteger("hit") >= 1;
                } else {
                    log.error("关闭RTP Server 失败: " + jsonObject.getString("msg"));
                }
            } else {
                //  检查ZLM状态
                log.error("关闭RTP Server 失败: 请检查ZLM服务");
            }
        }
        return result;
    }

    public void closeRtpServer(VideoMediaServerResultDTO serverItem, String streamId, CommonCallback<Boolean> callback) {
        if (serverItem == null) {
            callback.run(false);
            return;
        }
        Map<String, Object> param = new HashMap<>();
        param.put("stream_id", streamId);
        zlmRestfulUtils.closeRtpServer(serverItem, param, result -> {
            if (result.isSuccess()) {
                JSONObject data = result.getData();
                if (data.getInteger("code") == 0) {
                    callback.run(data.getInteger("hit") == 1);
                    return;
                } else {
                    log.error("关闭RTP Server 失败: " + data.getString("msg"));
                }
            } else {
                //  检查ZLM状态
                log.error("关闭RTP Server 失败: 请检查ZLM服务");
            }
            callback.run(false);
        });


    }


    /**
     * 调用zlm RESTFUL API —— startSendRtp
     */
    public ZlmRestfulResult<JSONObject> startSendRtpStream(VideoMediaServerResultDTO mediaServerItem, Map<String, Object> param) {
        return zlmRestfulUtils.startSendRtp(mediaServerItem, param);
    }

    /**
     * 调用zlm RESTFUL API —— startSendRtpPassive
     */
    public ZlmRestfulResult<JSONObject> startSendRtpPassive(VideoMediaServerResultDTO mediaServerItem, Map<String, Object> param) {
        return zlmRestfulUtils.startSendRtpPassive(mediaServerItem, param);
    }

    public ZlmRestfulResult<JSONObject> startSendRtpPassive(VideoMediaServerResultDTO mediaServerItem, Map<String, Object> param, ZlmRestfulUtils.RequestCallback callback) {
        return zlmRestfulUtils.startSendRtpPassive(mediaServerItem, param, callback);
    }

    /**
     * 查询待转推的流是否就绪
     */
    public Boolean isStreamReady(VideoMediaServerResultDTO mediaServerItem, String app, String streamId) {
        ZlmRestfulResult<JSONArray> mediaInfo = zlmRestfulUtils.getMediaList(mediaServerItem, app, streamId);
        if (!mediaInfo.isSuccess()) {
            return null;
        }
        return (CollUtil.isNotEmpty(mediaInfo.getData()));
    }

    /**
     * 查询转推的流是否有其它观看者
     *
     * @param streamId
     * @return
     */
    public int totalReaderCount(VideoMediaServerResultDTO mediaServerItem, String app, String streamId) {
        ZlmRestfulResult<JSONObject> zlmRestfulResult = zlmRestfulUtils.getMediaInfo(mediaServerItem, app, "rtsp", streamId);
        if (!zlmRestfulResult.isSuccess()) {
            return 0;
        }
        JSONObject mediaInfo = zlmRestfulResult.getData();
        Integer code = mediaInfo.getInteger("code");
        if (code < 0) {
            log.warn("查询流({}/{})是否有其它观看者时得到： {}", app, streamId, mediaInfo.getString("msg"));
            return -1;
        }
        if (code == 0 && mediaInfo.getBoolean("online") != null && !mediaInfo.getBoolean("online")) {
            log.warn("查询流({}/{})是否有其它观看者时得到： {}", app, streamId, mediaInfo.getString("msg"));
            return -1;
        }
        return mediaInfo.getInteger("totalReaderCount");
    }

    public ZlmRestfulResult<JSONObject> startSendRtp(VideoMediaServerResultDTO mediaInfo, SendRtpInfo sendRtpItem) {
        String is_Udp = sendRtpItem.isTcp() ? "0" : "1";
        log.info("rtp/{}开始推流, 目标={}:{}，SSRC={}", sendRtpItem.getStream(), sendRtpItem.getIp(), sendRtpItem.getPort(), sendRtpItem.getSsrc());
        Map<String, Object> param = new HashMap<>(12);
        param.put("vhost", "__defaultVhost__");
        param.put("app", sendRtpItem.getApp());
        param.put("stream", sendRtpItem.getStream());
        param.put("ssrc", sendRtpItem.getSsrc());
        param.put("src_port", sendRtpItem.getLocalPort());
        param.put("pt", sendRtpItem.getPt());
        param.put("use_ps", sendRtpItem.isUsePs() ? "1" : "0");
        param.put("only_audio", sendRtpItem.isOnlyAudio() ? "1" : "0");
        if (!sendRtpItem.isTcp()) {
            // udp模式下开启rtcp保活
            param.put("udp_rtcp_timeout", sendRtpItem.isRtcp() ? "1" : "0");
        }

        if (mediaInfo == null) {
            return null;
        }
        // 如果是非严格模式，需要关闭端口占用
        ZlmRestfulResult<JSONObject> startSendRtpStreamResult = null;
        if (sendRtpItem.getLocalPort() != 0) {
            if (sendRtpItem.isTcpActive()) {
                startSendRtpStreamResult = startSendRtpPassive(mediaInfo, param);
            } else {
                param.put("is_udp", is_Udp);
                param.put("dst_url", sendRtpItem.getIp());
                param.put("dst_port", sendRtpItem.getPort());
                startSendRtpStreamResult = startSendRtpStream(mediaInfo, param);
            }
        } else {
            if (sendRtpItem.isTcpActive()) {
                startSendRtpStreamResult = startSendRtpPassive(mediaInfo, param);
            } else {
                param.put("is_udp", is_Udp);
                param.put("dst_url", sendRtpItem.getIp());
                param.put("dst_port", sendRtpItem.getPort());
                startSendRtpStreamResult = startSendRtpStream(mediaInfo, param);
            }
        }
        return startSendRtpStreamResult;
    }

    public Boolean updateRtpServerSSRC(VideoMediaServerResultDTO mediaServerItem, String streamId, String ssrc) {
        boolean result = false;
        ZlmRestfulResult<JSONObject> zlmRestfulResult = zlmRestfulUtils.updateRtpServerSSRC(mediaServerItem, streamId, ssrc);
        if (zlmRestfulResult.isSuccess()) {
            log.info("[更新RTPServer] 成功");
            result = true;
        } else {
            log.error("[更新RTPServer] 失败: 请检查ZLM服务 {}, streamId：{}，ssrc：{}->\r\n{}", zlmRestfulResult.getMsg(),
                    streamId, ssrc, zlmRestfulResult.getData());
        }
        return result;
    }

    public ZlmRestfulResult<JSONObject> stopSendRtpStream(VideoMediaServerResultDTO mediaServerItem, SendRtpInfo sendRtpItem) {
        Map<String, Object> param = new HashMap<>();
        param.put("vhost", "__defaultVhost__");
        param.put("app", sendRtpItem.getApp());
        param.put("stream", sendRtpItem.getStream());
        param.put("ssrc", sendRtpItem.getSsrc());
        return zlmRestfulUtils.stopSendRtp(mediaServerItem, param);
    }
}
