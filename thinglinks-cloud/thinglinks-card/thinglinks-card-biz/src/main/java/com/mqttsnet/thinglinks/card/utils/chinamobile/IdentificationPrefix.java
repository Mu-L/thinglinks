package com.mqttsnet.thinglinks.card.utils.chinamobile;

/**
 * rediskey前缀
 *
 * @Author: shisen
 * @Date: 2024/06/27 6:54
 */
public class IdentificationPrefix {

    /**
     * 保存卡源密匙key
     */
    public static final String CHANNEL = "IOT:CHANNEL_KEY:";

    /**
     * 保存卡源密匙key  新版
     */
    public static final String NEW_CHANNEL = "IOT:NEW_CHANNEL:";

    /**
     * onelink平台的token   key
     */
    public static final String ONE_TOKEN = "IOT:ONE_TOKEN:";

    /**
     * 缓存所有卡号到 set
     */
    public static final String MSISDN_KEY = "iot:all_msisdn:";
    public static final String ICCID_KEY = "iot:all_iccid:";

    /**
     * iccid关联卡
     */
    public static final String ICCID_RELEVANCE_MSISDN_KEY = "iot:iccid_relevance_msisdn_key:";

    /**
     * 群组重试限额key 用于记录前后间隔时间
     */
    public static final String RETRY_GROUP_MEMBER_DATA_USAGE = "iot:retry_group_member_data_usage:";

    /**
     * 卡变更记录
     */
    public static final String CHANGE_SIM_STATUS = "iot:changeSimStatus:";

    /**
     * 公众号key 保存密匙
     */
    public static final String GZH = "gzh:";

    /**
     * 业务办理结果
     */
    public static final String JOB_ID = "jobId:";

    /**
     * 业务刷新间隔时间
     */
    public static final String JOB_REFRESH = "job:REFRESH:";

    /**
     * 昨日top
     */
    public static final String TOP_YESTERDAY_TOP_USER = "top:yesterdayTop:user:";

    /**
     * 昨日top
     */
    public static final String TOP_SKYTOP_USER_TOTAL = "top:skyTop:user:total:";

    /**
     * 按天 6天 top
     */
    public static final String TOP_SKYTOP_USER = "top:skyTop:user:";

    /**
     * 按月 6个月 top
     */
    public static final String TOP_MONTHTOP_USER = "top:monthTop:user:";


    /**
     * onelink 保存用户对接密匙 secret
     */
    public static final String APP_KEY = "IOT:APP_KEY:";

    /**
     * 访问api 结果信息保存到缓存
     */
    public static final String API_RESULT = "IOT:API_RESULT:";


    /**
     * onelink平台的token   key
     */
    public static final String ONE_TOKEN_INTERVAL_TIME = "IOT:ONE_TOKEN_INTERVAL_TIME:";

    /**
     * 省份
     */
    public static final String API_PROVINCE = "IOT:PROVINCE:";

    /**
     * 分布式锁key
     */
    public static final String LOCK = "IOT:LOCK:";
}
