package com.mqttsnet.thinglinks.video.utils.zlm;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONException;
import com.alibaba.fastjson2.JSONObject;
import com.mqttsnet.thinglinks.video.dto.media.VideoMediaServerResultDTO;
import com.mqttsnet.thinglinks.video.dto.media.zlm.ZlmRestfulResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * -----------------------------------------------------------------------------
 * File Name: ZlmRestFulUtils
 * -----------------------------------------------------------------------------
 * Description:
 * ZLM Restful 工具类
 * 该类提供了对ZLM流媒体服务器的各种操作方法，如添加流、删除流、获取流信息等
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/6       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/6 17:16
 */
@Component
@Slf4j
public class ZlmRestfulUtils {

    private final RestTemplate restTemplate;

    public ZlmRestfulUtils() {
        this.restTemplate = new RestTemplate();
    }

    /**
     * 发送POST请求
     *
     * @param videoMediaServerResultDTOItem 流媒体服务器信息
     * @param api                          API接口名
     * @param param                        请求参数
     * @param callback                     回调函数
     * @param readTimeOut                  读取超时时间
     * @return ZlmRestfulResult对象
     */
    public <T> ZlmRestfulResult<T> sendPost(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String api, Map<String, Object> param, RequestCallback<T> callback, Integer readTimeOut, Class<T> responseType) {
        return Optional.ofNullable(videoMediaServerResultDTOItem).map(server -> {
            String url = String.format("http://%s:%s/index/api/%s", server.getIp(), server.getHttpPort(), api);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("secret", server.getSecret());
            if (param != null) {
                param.forEach((key, value) -> builder.queryParam(key, value.toString()));
            }

            ZlmRestfulResult<T> responseResult = new ZlmRestfulResult<>(-2, "流媒体调用失败");

            try {
                String responseStr = restTemplate.postForObject(builder.toUriString(), null, String.class);
                responseResult = parseResponse(responseStr, responseType);
                if (callback != null) {
                    callback.run(responseResult);
                }
            } catch (Exception e) {
                log.error(String.format("[ %s ]请求失败: %s", url, e.getMessage()), e);
                handleException(e, url);
            }
            return responseResult;
        }).orElseGet(() -> new ZlmRestfulResult<>(-1, "媒体服务器信息为空"));
    }

    private <T> ZlmRestfulResult<T> parseResponse(String responseStr, Class<T> responseType) {
        try {
            JSONObject responseJSON = JSON.parseObject(responseStr);
            Integer code = responseJSON.getIntValue("code");
            String msg = responseJSON.getString("msg");
            T responseData = null;

            try {
                if (responseJSON.containsKey("data")) {
                    responseData = JSON.parseObject(responseJSON.getString("data"), responseType);
                } else {
                    // If "data" key is not present, treat the entire response as data
                    responseData = JSON.parseObject(responseStr, responseType);
                    // Default to success code if we can parse the response data successfully
                    code = (code == null) ? 0 : code;
                    msg = (msg == null) ? "Success" : msg;
                }
            } catch (JSONException e) {
                log.error(String.format("数据解析失败: %s", e.getMessage()), e);
                // Data parsing failed, continue with responseData as null
            }

            return new ZlmRestfulResult<>(code, msg, responseData);
        } catch (JSONException e) {
            log.error(String.format("响应解析失败: %s", e.getMessage()), e);
            return new ZlmRestfulResult<>(-3, "响应解析失败");
        }
    }

    /**
     * 处理异常信息
     *
     * @param e   异常
     * @param url 请求URL
     */
    private void handleException(Exception e, String url) {
        if (e instanceof SocketTimeoutException) {
            log.error(String.format("读取ZLM数据超时失败: %s, %s", url, e.getMessage()), e);
        } else if (e instanceof ConnectException) {
            log.error(String.format("连接ZLM失败: %s, %s", url, e.getMessage()), e);
        }
    }

    public <T> ZlmRestfulResult<T> sendPost(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String api, Map<String, Object> param, RequestCallback<T> callback, Class<T> responseType) {
        return sendPost(videoMediaServerResultDTOItem, api, param, callback, 10, responseType);
    }

    public void sendGetForImg(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String api, Map<String, Object> params, String targetPath, String fileName) {
        Optional.ofNullable(videoMediaServerResultDTOItem).ifPresentOrElse(server -> {
            String url = String.format("http://%s:%s/index/api/%s", server.getIp(), server.getHttpPort(), api);
            UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url).queryParam("secret", server.getSecret());
            if (params != null) {
                params.forEach((key, value) -> builder.queryParam(key, value.toString()));
            }

            try {
                byte[] responseBytes = restTemplate.getForObject(builder.toUriString(), byte[].class);
                if (responseBytes != null) {
                    File snapFolder = new File(targetPath);
                    if (!snapFolder.exists() && !snapFolder.mkdirs()) {
                        log.warn("{}路径创建失败", snapFolder.getAbsolutePath());
                    }

                    File snapFile = new File(targetPath + File.separator + fileName);
                    try (FileOutputStream outStream = new FileOutputStream(snapFile)) {
                        outStream.write(responseBytes);
                    }
                } else {
                    log.error(String.format("[ %s ]请求失败: 没有收到数据", url));
                }
            } catch (Exception e) {
                log.error(String.format("[ %s ]请求失败: %s", url, e.getMessage()), e);
                handleException(e, url);
            }
        }, () -> log.error("媒体服务器信息为空，无法发送GET请求"));
    }

    public ZlmRestfulResult<JSONObject> isMediaOnline(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String app, String stream, String schema) {
        Map<String, Object> param = new HashMap<>();
        Optional.ofNullable(app).ifPresent(value -> param.put("app", value));
        Optional.ofNullable(stream).ifPresent(value -> param.put("stream", value));
        Optional.ofNullable(schema).ifPresent(value -> param.put("schema", value));
        param.put("vhost", "__defaultVhost__");
        return sendPost(videoMediaServerResultDTOItem, "isMediaOnline", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONArray> getMediaList(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String app, String stream, String schema, RequestCallback<JSONArray> callback) {
        Map<String, Object> param = new HashMap<>();
        Optional.ofNullable(app).ifPresent(value -> param.put("app", value));
        Optional.ofNullable(stream).ifPresent(value -> param.put("stream", value));
        Optional.ofNullable(schema).ifPresent(value -> param.put("schema", value));
        param.put("vhost", "__defaultVhost__");
        return sendPost(videoMediaServerResultDTOItem, "getMediaList", param, callback, JSONArray.class);
    }

    public ZlmRestfulResult<JSONArray> getMediaList(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String app, String stream) {
        return getMediaList(videoMediaServerResultDTOItem, app, stream, null, null);
    }

    public ZlmRestfulResult<JSONArray> getMediaList(VideoMediaServerResultDTO videoMediaServerResultDTOItem, RequestCallback<JSONArray> callback) {
        return sendPost(videoMediaServerResultDTOItem, "getMediaList", null, callback, JSONArray.class);
    }

    public ZlmRestfulResult<JSONObject> getMediaInfo(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String app, String schema, String stream) {
        Map<String, Object> param = new HashMap<>();
        param.put("app", app);
        param.put("schema", schema);
        param.put("stream", stream);
        param.put("vhost", "__defaultVhost__");
        return sendPost(videoMediaServerResultDTOItem, "getMediaInfo", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> getRtpInfo(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String stream_id) {
        Map<String, Object> param = new HashMap<>();
        param.put("stream_id", stream_id);
        return sendPost(videoMediaServerResultDTOItem, "getRtpInfo", param, null, JSONObject.class);
    }

    /**
     * 添加FFmpeg拉流代理(addFFmpegSource)
     *
     * @param videoMediaServerResultDTOItem 流媒体信息
     * @param src_url                      FFmpeg拉流地址,支持任意协议或格式(只要FFmpeg支持即可)
     * @param dst_url                      FFmpeg rtmp推流地址，一般都是推给自己，例如rtmp://127.0.0.1/live/stream_form_ffmpeg
     * @param timeout_ms                   FFmpeg推流成功超时时间,单位毫秒
     * @param enable_audio                 是否开启hls录制
     * @param enable_mp4                   是否开启mp4录制
     * @param ffmpeg_cmd_key
     * @return
     */
    public ZlmRestfulResult<JSONObject> addFFmpegSource(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String src_url, String dst_url, Integer timeout_ms,
                                                        boolean enable_audio, boolean enable_mp4, String ffmpeg_cmd_key) {
        log.info("addFFmpegSource src_url:{},dst_url:{}", src_url, dst_url);
        Map<String, Object> param = new HashMap<>();
        param.put("src_url", src_url);
        param.put("dst_url", dst_url);
        param.put("timeout_ms", timeout_ms);
        param.put("enable_mp4", enable_mp4);
        param.put("ffmpeg_cmd_key", ffmpeg_cmd_key);
        return sendPost(videoMediaServerResultDTOItem, "addFFmpegSource", param, null, JSONObject.class);
    }

    public ZlmRestfulResult delFFmpegSource(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String key) {
        Map<String, Object> param = new HashMap<>();
        param.put("key", key);
        return sendPost(videoMediaServerResultDTOItem, "delFFmpegSource", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> delStreamProxy(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String key) {
        Map<String, Object> param = new HashMap<>();
        param.put("key", key);
        return sendPost(videoMediaServerResultDTOItem, "delStreamProxy", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONArray> getMediaServerConfig(VideoMediaServerResultDTO videoMediaServerResultDTOItem) {
        return sendPost(videoMediaServerResultDTOItem, "getServerConfig", null, null, JSONArray.class);
    }

    public ZlmRestfulResult<JSONObject> setServerConfig(VideoMediaServerResultDTO videoMediaServerResultDTOItem, Map<String, Object> param) {
        return sendPost(videoMediaServerResultDTOItem, "setServerConfig", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> openRtpServer(VideoMediaServerResultDTO videoMediaServerResultDTOItem, Map<String, Object> param) {
        return sendPost(videoMediaServerResultDTOItem, "openRtpServer", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> closeRtpServer(VideoMediaServerResultDTO videoMediaServerResultDTOItem, Map<String, Object> param) {
        return sendPost(videoMediaServerResultDTOItem, "closeRtpServer", param, null, JSONObject.class);
    }

    public void closeRtpServer(VideoMediaServerResultDTO videoMediaServerResultDTOItem, Map<String, Object> param, RequestCallback<JSONObject> callback) {
        sendPost(videoMediaServerResultDTOItem, "closeRtpServer", param, callback, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> listRtpServer(VideoMediaServerResultDTO videoMediaServerResultDTOItem) {
        return sendPost(videoMediaServerResultDTOItem, "listRtpServer", null, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> startSendRtp(VideoMediaServerResultDTO videoMediaServerResultDTOItem, Map<String, Object> param) {
        return sendPost(videoMediaServerResultDTOItem, "startSendRtp", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> startSendRtpPassive(VideoMediaServerResultDTO videoMediaServerResultDTOItem, Map<String, Object> param) {
        return sendPost(videoMediaServerResultDTOItem, "startSendRtpPassive", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> startSendRtpPassive(VideoMediaServerResultDTO videoMediaServerResultDTOItem, Map<String, Object> param, RequestCallback<JSONObject> callback) {
        return sendPost(videoMediaServerResultDTOItem, "startSendRtpPassive", param, callback, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> stopSendRtp(VideoMediaServerResultDTO videoMediaServerResultDTOItem, Map<String, Object> param) {
        return sendPost(videoMediaServerResultDTOItem, "stopSendRtp", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> restartServer(VideoMediaServerResultDTO videoMediaServerResultDTOItem) {
        return sendPost(videoMediaServerResultDTOItem, "restartServer", null, null, JSONObject.class);
    }

    /**
     * 添加rtsp/rtmp/hls拉流代理(addStreamProxy)
     *
     * @param videoMediaServerResultDTOItem 流媒体信息
     * @param app                          添加的流的应用名，例如live
     * @param stream                       添加的流的id名，例如test
     * @param url                          拉流地址，例如rtmp://live.hkstv.hk.lxdns.com/live/hks2
     * @param enable_audio
     * @param enable_mp4
     * @param rtp_type
     * @return
     */
    public ZlmRestfulResult<JSONObject> addStreamProxy(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String app, String stream, String url, boolean enable_audio, boolean enable_mp4, String rtp_type) {
        Map<String, Object> param = new HashMap<>();
        param.put("vhost", "__defaultVhost__");
        param.put("app", app);
        param.put("stream", stream);
        param.put("url", url);
        param.put("enable_mp4", enable_mp4 ? 1 : 0);
        param.put("enable_audio", enable_audio ? 1 : 0);
        param.put("rtp_type", rtp_type);
        return sendPost(videoMediaServerResultDTOItem, "addStreamProxy", param, null, 20, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> closeStreams(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String app, String schema, String stream) {
        Map<String, Object> param = new HashMap<>();
        param.put("vhost", "__defaultVhost__");
        param.put("app", app);
        param.put("stream", stream);
        param.put("force", 1);
        param.put("schema", schema);
        return sendPost(videoMediaServerResultDTOItem, "close_streams", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> getAllSession(VideoMediaServerResultDTO videoMediaServerResultDTOItem) {
        return sendPost(videoMediaServerResultDTOItem, "getAllSession", null, null, JSONObject.class);
    }

    public void kickSessions(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String localPortSStr) {
        Map<String, Object> param = new HashMap<>();
        param.put("local_port", localPortSStr);
        sendPost(videoMediaServerResultDTOItem, "kick_sessions", param, null, JSONObject.class);
    }

    public void getSnap(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String streamUrl, int timeout_sec, int expire_sec, String targetPath, String fileName) {
        Map<String, Object> param = new HashMap<>(3);
        param.put("url", streamUrl);
        param.put("timeout_sec", timeout_sec);
        param.put("expire_sec", expire_sec);
        sendGetForImg(videoMediaServerResultDTOItem, "getSnap", param, targetPath, fileName);
    }

    public ZlmRestfulResult<JSONObject> pauseRtpCheck(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String streamId) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("stream_id", streamId);
        return sendPost(videoMediaServerResultDTOItem, "pauseRtpCheck", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> resumeRtpCheck(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String streamId) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("stream_id", streamId);
        return sendPost(videoMediaServerResultDTOItem, "resumeRtpCheck", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> connectRtpServer(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String dst_url, int dst_port, String stream_id) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("dst_url", dst_url);
        param.put("dst_port", dst_port);
        param.put("stream_id", stream_id);
        return sendPost(videoMediaServerResultDTOItem, "connectRtpServer", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> updateRtpServerSSRC(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String streamId, String ssrc) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("ssrc", ssrc);
        param.put("stream_id", streamId);
        return sendPost(videoMediaServerResultDTOItem, "updateRtpServerSSRC", param, null, JSONObject.class);
    }

    public ZlmRestfulResult<JSONObject> deleteRecordDirectory(VideoMediaServerResultDTO videoMediaServerResultDTOItem, String app, String stream, String date, String fileName) {
        Map<String, Object> param = new HashMap<>(1);
        param.put("vhost", "__defaultVhost__");
        param.put("app", app);
        param.put("stream", stream);
        param.put("period", date);
        param.put("name", fileName);
        return sendPost(videoMediaServerResultDTOItem, "deleteRecordDirectory", param, null, JSONObject.class);
    }

    public interface RequestCallback<T> {
        void run(ZlmRestfulResult<T> response);
    }


}
