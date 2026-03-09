package com.mqttsnet.thinglinks.card.constants;


/**
 * 物联网卡常量
 *
 * @author shisen
 */
public class IotCardConstants {

    /**
     * 通用成功标识
     */
    public static final int SUCCESS = 0;

    /**
     * 通用失败标识
     */
    public static final String FAIL = "1";

    /**
     * 令牌
     */
    public static final String TOKEN = "token";

    /**
     * url
     */
    public static final String URL = "url";

    /**
     * transid
     */
    public static final String TRANSID = "transid";

    /**
     * 应用id
     */
    public static final String APPID = "appid";

    /**
     * 认证密码key
     */
    public static final String PASSWORD = "password";

    /**
     * 登录用户 redis key
     */
    public static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /**
     * 参数管理 cache key
     */
    public static final String SYS_CONFIG_KEY = "sys_config:";

    /**
     * 微信支付成功标识
     */
    public static final String PAY_SUCCESS = "SUCCESS";

    /*
     * 支付成功回应码
     */
    public static final String RESSUCCESSXML = " <xml>" + "<return_code><![CDATA[SUCCESS]]></return_code><return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

    /**
     * 支付失败回应码
     */
    public static final String RESFAILUREXML = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code><return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";

    /**
     * 资源映射路径 前缀
     */
    public static final String RESOURCE_PREFIX = "/profile";

    /*********************** 接口参数获取常量 ************************/

    public static final String STATUS = "status";

    public static final String MESSAGE = "message";

    public static final String MSG = "msg";

    public static final String RESULT = "result";

    public static final String CODE = "code";

    public static final String DATA = "data";

    /**
     * operType
     */
    public static final String OPER_TYPE = "operType";

    public static final String APN_OPER_TYPE_START_0 = "0"; //开启apn
    public static final String APN_OPER_TYPE_CLOSE_1 = "1"; //关闭apn

    public static final String CARD_NUM = "cardNum";
    public static final String MSISDN = "msisdn";
    public static final String ICCID = "iccid";
    public static final String IMSI = "imsi";
    public static final String BATCH = "batch";
    public static final String SUPPORT_ID = "supportId";

    public static final String MSISDNS = "msisdns";
    public static final String ICCIDS = "iccids";
    public static final String IMSIS = "imsis";

    /**
     * 密钥
     */
    public static final String SECRET = "secret";

    /**
     * SIGN
     */
    public static final String SIGN = "sign";


}
