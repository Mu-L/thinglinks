package com.mqttsnet.thinglinks.card.utils.http;

/**
 * 扩展http请求码
 */
public class HttpStatus extends cn.hutool.http.HttpStatus {

    /**
     * 移动、电信平台api返回成功标识
     */
    public static final String ONE_LINK_STATUS = "0";

    /**
     * 联通官方返回成功标识
     */
    public static final String DW_LT_STATUS = "0000";

}
