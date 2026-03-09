package com.mqttsnet.thinglinks.video.constants;

/**
 * -----------------------------------------------------------------------------
 * File Name: ZlmMedia
 * -----------------------------------------------------------------------------
 * Description:
 * ZlmMedia 相关常量类
 * -----------------------------------------------------------------------------
 *
 * @author xiaonannet
 * @version 1.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2024/7/10       xiaonannet        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2024/7/10 15:00
 */
public class ZlmMediaConstants {


    /**
     * rtsp 协议
     */
    public static final String RTSP = "rtsp";


    /**
     * ffmpeg Cmd 模版
     */
    public static final String FFMPEG_CMD = "%s -re -i %s -c:a aac -strict -2 -ar 44100 -ab 48k -c:v libx264 -f flv %s";


}
