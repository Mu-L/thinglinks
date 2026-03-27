package com.mqttsnet.thinglinks.card.constants;

import com.mqttsnet.thinglinks.card.enumeration.OperatorTypeEnum;
import com.mqttsnet.thinglinks.card.utils.chinamobile.RequestType;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 运营商请求地址常量类
 *
 * @Author: shisen
 * @Date: 2024/06/27 4:11
 */
public class OperatorRequestConstants {

    // public static final String CHINA_MOBILE_URL_PREFIX = "https://api.iot.10086.cn/";

    // 中国移动平台API前缀(移动沙箱环境)
    public static final String CHINA_MOBILE_URL_PREFIX = "http://111.10.45.200:7000/";

    // 中国联通平台API前缀(联通正式环境)
    public static final String CHINA_UNICOM_URL_PREFIX = "";

    // 中国电信平台API前缀(电信正式环境)
    public static final String CHINA_TELECOM_URL_PREFIX = "https://cmp-api.ctwing.cn:20164/";


    // 请求协议类型
    public static final String POST = "POST";
    public static final String GET = "GET";

    /*******************************************中国移动平台开始 **************************************************/
    /**
     * CMIOT_API23000-认证服务接口
     */
    public static final String TOKEN = "v5/ec/get/token";

    /**
     * CMIOT_API23S00-单卡基本信息查询
     * 用于获取开卡时间和激活时间
     */
    public static final String SIM_BASIC_INFO = "v5/ec/query/sim-basic-info";

    /**
     * CMIOT_API23U12-单卡流量池内使用量实时查询
     * 可通过该接口查询单卡在流量池或流量池共享下实时使用量
     */
    public static final String SIM_DATA_USAGE_INPOOL = "v5/ec/query/sim-data-usage-inpool";

    /**
     * CMIOT_API25U04-单卡本月流量累计使用量查询
     * 查询集团所属物联卡当月的GPRS使用量，PB号段为截至前一天24点流量，CT号段为实时流量。（单位：KB）
     */
    public static final String SIM_DATA_USAGE = "v5/ec/query/sim-data-usage";

    /**
     * CMIOT_API25S04-单卡状态查询
     * 通过卡号查询物联卡的状态信息
     */
    public static final String SIM_STATUS = "v5/ec/query/sim-status";

    /**
     * CMIOT_API23S02-单卡状态变更历史查询
     * 通过卡号查询物联卡的状态变更历史
     */
    public static final String QUERY_SIM_CHANGE_HISTORY = "v5/ec/query/sim-change-history";

    /**
     * CMIOT_API25M01-单卡在线信息实时查询
     * 查询物联卡的在线信息，区分APN，返回APN信息、IP地址、会话开始时间
     */
    public static final String SIM_SESSION = "v5/ec/query/sim-session";

    /**
     * CMIOT_API23M08-单卡通信功能开通查询
     * 客户通过卡号查询物联卡通信功能开通情况
     */
    public static final String SIM_COMMUNICATION_FUNCTION_STATUS = "v5/ec/query/sim-communication-function-status";

    /**
     * CMIOT_API23U00-群组本月流量累计使用量实时查询
     * 实时查询群组本月GPRS流量累计使用量（若群组有多个流量池商品，使用量为多个商品累加）
     */
    public static final String GROUP_DATA_USAGE = "v5/ec/query/group_data_usage";

    /**
     * CMIOT_API23U04-群组本月套餐内流量使用量实时查询
     * 实时查询群组本月套餐内GPRS流量使用量信息
     */
    public static final String GROUP_DATA_MARGIN = "v5/ec/query/group-data-margin";

    /**
     * CMIOT_API23M07-单卡APN功能开停
     * 集团客户可以通过卡号（msisdniccidimsi三选一，单卡）办理集团归属物联卡的APN功能开/停（同一卡号3分钟内不能通过此类接口重复办理业务）
     */
    public static final String SIM_APN_FUNCTION = "v5/ec/operate/sim-apn-function";

    /**
     * CMIOT_API23E02-成员归属群组查询
     * 查询物联卡归属的群组信息
     */
    public static final String SIM_GROUP_BY_MEMBER = "v5/ec/query/group-by-member";

    /**
     * CMIOT_API25M03-物联卡GPRS上网功能重置
     * 在集团客户遇到物联卡上网功能异常的时候可以通过调用该接口来重置恢复GPRS上网功能（同一卡号3分钟内不能通过此类接口重复办理业务）
     */
    public static final String SIM_GPRS_STATUS_RESET = "v5/ec/operate/sim-gprs-status-reset";

    /**
     * CMIOT_API23E04-群组成员流量限额设置
     * 集团客户可通过物联卡卡号（msisdn，单卡）调用该接口实现添加/删除/变更群组成员卡流量池内流量限额的设置。新增/变更流量限额设置时若当前已用额度超过设置额度，就算设置成功，该账期限额也不会生效，限额下一账期生效
     */
    public static final String GROUP_MEMBER_DATA_USAGE = "v5/ec/limit/group-member-data-usage";

    /**
     * CMIOT_API25L00-物联卡实时位置经纬度查询
     * 查询物联卡当前附网基站所在的经纬度信息，最大定位误差约2000米
     */
    public static final String POSITION_LOCATION_MESSAGE = "v5/ec/query/position-location-message";

    /**
     * CMIOT_API25L01-物联卡实时位置区号查询
     * 集团客户可以通过卡号（MSISDN/ICCID/IMSI，单卡）查询该卡当前实时位置区号信息（区号）
     */
    public static final String DISTRICT_POSITION_LOCATION_MESSAGE = "v5/ec/query/district-position-location-message";

    /**
     * CMIOT_API25L03-物联卡最后上网位置经纬度批量查询
     * 集团客户可以通过卡号（msisdniccidimsi三选一，每次不超过20张卡）查询集团归属物联卡的最后上网基站所在的经纬度信息，最大定位误差约2000米。
     */
    public static final String LAST_POSITION_LOCATION = "v5/ec/query/last-position-location";

    /**
     * CMIOT_API25L42-经纬度逆地理解析
     * 集团客户可以通过该接口查询经纬度对应的语义化地理位置信息（省份城市区县街道号）
     */
    public static final String LOCATION_REGEO = "v5/ec/location/regeo";

    /**
     * CMIOT_API23S03-单卡状态变更
     * 集团客户可以通过卡号（msisdniccidimsi三选一，单卡）变更集团归属物联卡的状态（同一卡号3分钟内不能通过此类接口重复办理业务）
     */
    public static final String CHANGE_SIM_STATUS = "v5/ec/change/sim-status";

    /**
     * CMIOT_API23S06-物联卡状态变更批量办理
     * 集团客户可以通过卡号调用该接口批量办理物联卡的状态变更，每次不超过100张卡，同一卡号3分钟内不得重复调用该接口。如需查询办理结果则根据该接口返回的任务流水号调“CMIOT_API23M10-物联卡业务批量办理结果查询”接口查询办理结果。（OneLink-CT的主EC客户查询副EC卡数据时，一次查询的卡号必须归属于同一个EC）
     */
    public static final String SIM_STATUS_BATCH = "v5/ec/change/sim-status/batch";

    /**
     * CMIOT_API23M19-物联卡节电参数变更批量办理
     * 集团客户可以通过卡号批量变更NB-IOT用户的 APN名称及对应的节电参数。每次不超过100张卡，同一卡号3分钟内不得重复调用该接口。如需查询办理结果则根据该接口返回的任务流水号调“CMIOT_API23M10-物联卡业务批量办理结果查询”接口查询办理结果。（OneLink-CT的主EC客户查询副EC卡数据时，一次查询的卡号必须归属于同一个EC）
     */
    public static final String SIM_PARAMETER_NODE_BATCH = "v5/ec/change/sim-parameter-node/batch";

    /**
     * CMIOT_API23U07-单卡本月套餐流量用量实时查询
     * 实时查询物联卡本月套餐内流量使用量
     */
    public static final String SIM_DATA_MARGIN = "v5/ec/query/sim-data-margin";

    /**
     * CMIOT_API23M10-物联卡业务批量办理结果查询
     * 集团客户可以通过物联卡批量处理的任务流水号接口查询物联卡业务批量办理的结果
     */
    public static final String SIM_CHANGE_HISTORY = "v5/ec/query/sim-batch-result";

    /**
     * CMIOT_API25U01-物联卡单日GPRS流量使用量批量查询
     * 通过此接口可以批量（暂定100张）查询物联卡某一天GPRS流量使用量（仅支持查询近7天中某一天的数据，截止前一天）
     */
    public static final String SIM_DATA_USAGE_DAILY = "v5/ec/query/sim-data-usage-daily/batch";

    /**
     * CMIOT_API25U03-物联卡单月GPRS流量使用量批量查询
     * 通过此接口可以批量（暂定100张）查询集团客户下所属SIM卡的月数据使用情况。批量查询多个用户、指定日期的数据使用量，仅支持查询近6个月中某月的使用量，其中本月数据截止为前一天
     */
    public static final String SIM_DATA_USAGE_MONTHLY = "v5/ec/query/sim-data-usage-monthly/batch";

    /**
     * CMIOT_API23A04-物联卡机卡分离状态查询
     * 集团客户可通过物联卡卡号（msisdn，单卡）调用该接口查询已订购话单侧或网络侧机卡绑定的单卡的机卡绑定状态
     */
    public static final String CARD_BIND_STATUS = "v5/ec/query/card-bind-status";

    /**
     * CMIOT_API23M09-物联卡通信功能开停批量办理
     * 集团客户可以通过卡号调用该接口批量办理物联卡的通信功能（语音、短信、国际漫游、数据通信服务）开停，每次不超过100张卡，同一卡号3分钟内不得重复调用该接口。如需查询办理结果则根据该接口返回的任务流水号调“CMIOT_API23M10-物联卡业务批量办理结果查询”接口查询办理结果。（OneLink-CT的主EC客户查询副EC卡数据时，一次查询的卡号必须归属于同一个EC）
     */
    public static final String SIM_COMMUNICATION_FUNCTION_BATCH = "v5/ec/operate/sim-communication-function/batch";

    /**
     * API23M10-物联卡业务批量办理结果查询
     */
    public static final String SIM_BATCH_RESULT = "v5/ec/query/sim-batch-result";

    /**
     * CMIOT_API23U06-单卡本月套餐短信用量实时查询
     * 实时查询物联卡本月套餐内短信使用量
     */
    public static final String SIM_SMS_MARGIN = "v5/ec/query/sim-sms-margin";

    /**
     * CMIOT_API25U02-物联卡单月短信使用量批量查询
     * 通过此接口可以查询某集团客户下所属SIM卡的月短信使用情况。批量查询多个用户、指定日期的短信使用量，仅支持查询近6个月中某月的使用量，其中本月数据截止为前一天
     */
    public static final String QUERY_SIM_SMS_USAGE_MONTHLY_BATCH = "v5/ec/query/sim-sms-usage-monthly/batch";

    /**
     * CMIOT_API23S04-单卡绑定IMEI实时查询
     */
    public static final String SIM_IMEI = "v5/ec/query/sim-imei";

    /**
     * CMIOT_API23S05-物联卡归属平台批量查询接口
     */
    public static final String SIM_PLATFORM_BATCH = "v5/ec/query/sim-platform/batch";

    /**
     * CMIOT_API25U00-物联卡单日短信使用量批量查询
     * 批量（暂定100张）查询物联卡某一天短信使用量（仅支持查询近7天中某一天的数据，截止前一天）
     */
    public static final String SIM_SMS_USAGE_DAILY_BATCH = "v5/ec/query/sim-sms-usage-daily/batch";

    /**
     * API25U02-物联卡单月短信使用量批量查询
     */
    public static final String SIM_SMS_USAGE_MONTHLY_BATCH = "v5/ec/query/sim-sms-usage-monthly/batch";

    /**
     * CMIOT_API25C03-物联卡终端控制上行短信记录批量查询
     * 查询物联卡终端控制上行短信的发送记录，每次查询最大不超过20张卡
     */
    public static final String SIM_MO_SMS_BATCH = "v5/ec/query/sim-mo-sms/batch";

    /**
     * CMIOT_API25C02-物联卡终端控制下行模板短信记录批量查询
     * 查询物联卡终端控制下行短信的发送记录，每次查询最大不超过20张卡
     */
    public static final String SIM_MT_SMS_BATCH = "v5/ec/query/sim-mt-sms/batch";


    /**
     * CMIOT_API25M00-单卡开关机状态实时查询
     * 查询终端的开关机信息
     */
    public static final String ON_OFF_STATUS = "v5/ec/query/on-off-status";

    /**
     * CMIOT_API23B01-单卡余额信息实时查询
     * 单卡余额信息实时查询
     */
    public static final String BALANCE_INFO = "v5/ec/query/balance-info";

    /**
     * CMIOT_API23B00-集团客户账单实时查询
     * 集团客户账单实时查询
     */
    public static final String EC_BILL = "v5/ec/query/ec-bill";

    /**
     * CMIOT_API25L04-物联卡最后上网位置区号查询
     * 物联卡最后上网位置区号查询
     * 集团客户可以通过卡号（msisdniccidims三选一，单卡）查询集团归属物联卡的最后上网位置区号信息（城市编码/区号）
     */
    public static final String LAST_DISTRICT_POSITION = "v5/ec/query/last-district-position";

    /**
     * API25L06-经纬度坐标系批量转换
     * 集团客户可以通过该接口实现各位置坐标系的经纬度转换（批量上限为20组经纬度坐标）
     */
    public static final String TRANSLATE = "v5/ec/location/translate/batch";


    /*******************************************中国移动平台结束 **************************************************/


    /*************************************************中国联通平台开始*************************************************/
    /*************************************************中国联通平台结束*************************************************/

    /*************************************************中国电信平台开始*************************************************/
    /**
     * CTIOT_5GCMP_BQ018-批量 SIM 卡资料查询
     * 批量 SIM 卡资料查询
     */
    public static final String BATCH_QRY_SIM_INFO = "api/v1/prod/batchQrySimInfo";

    /**
     * CTIOT_5GCMP_UQ013-批量流量查询（按天）
     * 批量流量查询（按天）
     */
    public static final String BATCH_QRY_FLOW_BY_DATE = "api/v1/batchQry/batchQryFlowByDate";

    /**
     * CTIOT_5GCMP_BQ020-批量 SIM 卡状态查询
     * 批量 SIM 卡状态查询
     */
    public static final String BATCH_QRY_SIM_STATUS = "/api/v1/prod/batchQrySimStatus";

    /**
     * CTIOT_5GCMP_BQ019-批量实名状态查询
     * 批量实名状态查询
     */
    public static final String QRY_REAL_NAME_STATUS = "api/v1/realName/qryRealNameStatus";

    /**
     * CTIOT_5GCMP_MAPN002-APN 上网功能启用、停止操作
     * APN 上网功能启用、停止操作
     */
    public static final String APN_NET_STATUS_ACTION = "openapi/v1/subscribers/apnNetStatusAction";


    /**
     * CTIOT_5GCMP_LWL006-批量坐标系转换
     * 批量坐标系转换
     */
    public static final String BATCH_DS_TRANS = "openapi/v1/map/batchDsTrans";

    /**
     * CTIOT_5GCMP_MAPN004-APN 自主限速
     * 自主限速
     */
    public static final String APN_MOUDLE_SPEED_LIMIT_ACTION = "openapi/v1/subscribers/apnMoudleSpeedLimitAction";

    /**
     * CTIOT_5GCMP_BA005-达量断网新增、修改阈值及取消
     * 达量断网新增、修改阈值及取消
     */
    public static final String OFF_NET_ACTION = "5gcmp/openapi/v1/common/offNetAction";

    /**
     * CTIOT_5GCMP_RD001-风险诊断
     * 风险诊断
     */
    public static final String DIAGNOSIS = "api/v1/openRisk/{access_number}/diagnosis";

    /**
     * CTIOT_5GCMP_LWL001-基站粗定位（实时在线位置经纬度）
     * 实时在线位置经纬度
     */
    public static final String QRY_LOCATION_SERVICE = "openapi/v1/map/qryLocationService";

    /**
     * CTIOT_5GCMP_LWL004-历史轨迹查询（经纬度）
     * 历史轨迹查询（经纬度）
     */
    public static final String QRY_POSITION_TRACK = "openapi/v1/map/qryPositionTrack";

    /**
     * CTIOT_5GCMP_MAPN001-APN 使用信息查询
     * APN 使用信息查询
     */
    public static final String QUERY_USING_APN = "openapi/v1/subscribers/queryUsingAPN";


    /*************************************************中国电信平台结束*************************************************/

    private static final Map<OperatorTypeEnum, Map<String, Map<String, List<String>>>> providerUrls = new HashMap<>();

    static {
        // 初始化中国移动的URL
        Map<String, Map<String, List<String>>> chinaMobileUrls = new HashMap<>();
        chinaMobileUrls.put(RequestType.API23S00, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_BASIC_INFO, POST, GET));
        chinaMobileUrls.put(RequestType.API23U12, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_DATA_USAGE_INPOOL, POST, GET));
        chinaMobileUrls.put(RequestType.API25U04, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_DATA_USAGE, POST, GET));
        chinaMobileUrls.put(RequestType.API23E04, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + GROUP_MEMBER_DATA_USAGE, POST, GET));
        chinaMobileUrls.put(RequestType.API25S04, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_STATUS, POST, GET));
        chinaMobileUrls.put(RequestType.API25M01, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_SESSION, POST, GET));
        chinaMobileUrls.put(RequestType.API23M07, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_APN_FUNCTION, POST, GET));
        chinaMobileUrls.put(RequestType.API23U07, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_DATA_MARGIN, POST, GET));
        chinaMobileUrls.put(RequestType.API23U04, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + GROUP_DATA_MARGIN, POST, GET));
        chinaMobileUrls.put(RequestType.API23S03, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + CHANGE_SIM_STATUS, POST, GET));
        chinaMobileUrls.put(RequestType.API23S06, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_STATUS_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API23S02, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + QUERY_SIM_CHANGE_HISTORY, POST, GET));
        chinaMobileUrls.put(RequestType.API25U01, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_DATA_USAGE_DAILY, POST, GET));
        chinaMobileUrls.put(RequestType.API23A04, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + CARD_BIND_STATUS, POST, GET));
        chinaMobileUrls.put(RequestType.API23M08, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_COMMUNICATION_FUNCTION_STATUS, POST, GET));
        chinaMobileUrls.put(RequestType.API25U03, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_DATA_USAGE_MONTHLY, POST, GET));
        chinaMobileUrls.put(RequestType.API23M09, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_COMMUNICATION_FUNCTION_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API23M10, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_BATCH_RESULT, POST, GET));
        chinaMobileUrls.put(RequestType.API23U06, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_SMS_MARGIN, POST, GET));
        chinaMobileUrls.put(RequestType.API25U00, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_SMS_USAGE_DAILY_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API25U02, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + QUERY_SIM_SMS_USAGE_MONTHLY_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API23S04, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_IMEI, POST, GET));
        chinaMobileUrls.put(RequestType.API23S05, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_PLATFORM_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API25C03, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_MO_SMS_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API25C02, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_MT_SMS_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API25M00, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + ON_OFF_STATUS, POST, GET));
        chinaMobileUrls.put(RequestType.API23M19, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_PARAMETER_NODE_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API23E02, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_GROUP_BY_MEMBER, POST, GET));
        chinaMobileUrls.put(RequestType.API25M03, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_GPRS_STATUS_RESET, POST, GET));
        chinaMobileUrls.put(RequestType.API25Z00, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_COMMUNICATION_FUNCTION_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API23M04, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_COMMUNICATION_FUNCTION_BATCH, POST, GET));
        chinaMobileUrls.put(RequestType.API25L00, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + POSITION_LOCATION_MESSAGE, POST, GET));
        chinaMobileUrls.put(RequestType.API25L01, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + DISTRICT_POSITION_LOCATION_MESSAGE, POST, GET));
        chinaMobileUrls.put(RequestType.API25L02, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + LAST_POSITION_LOCATION, POST, GET));
        chinaMobileUrls.put(RequestType.API25L04, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + LAST_DISTRICT_POSITION, POST, GET));
        chinaMobileUrls.put(RequestType.API25L06, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + TRANSLATE, POST, GET));
        chinaMobileUrls.put(RequestType.API25L42, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + LOCATION_REGEO, POST, GET));
        chinaMobileUrls.put(RequestType.API23B01, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + BALANCE_INFO, POST, GET));
        chinaMobileUrls.put(RequestType.API23B00, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + EC_BILL, POST, GET));
        chinaMobileUrls.put(RequestType.API2307, createUrlMethodsMap(CHINA_MOBILE_URL_PREFIX + SIM_DATA_USAGE_MONTHLY, POST, GET));
        // 其他中国移动的URL映射
        providerUrls.put(OperatorTypeEnum.CHINA_MOBILE, chinaMobileUrls);

        // 初始化中国联通的URL及其支持的请求方法
        Map<String, Map<String, List<String>>> chinaUnicomUrls = new HashMap<>();
        chinaUnicomUrls.put(RequestType.API23S00, createUrlMethodsMap(CHINA_UNICOM_URL_PREFIX + "sim-basic-info", POST, GET));
        // 其他中国联通的URL映射
        providerUrls.put(OperatorTypeEnum.CHINA_UNICOM, chinaUnicomUrls);

        // 初始化中国电信的URL及其支持的请求方法
        Map<String, Map<String, List<String>>> chinaTelecomUrls = new HashMap<>();
        chinaTelecomUrls.put(RequestType.API23S00, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + BATCH_QRY_SIM_INFO, POST, GET));
        chinaTelecomUrls.put(RequestType.API25U04, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + BATCH_QRY_FLOW_BY_DATE, POST, GET));
        chinaTelecomUrls.put(RequestType.API25S04, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + BATCH_QRY_SIM_STATUS, POST, GET));
        chinaTelecomUrls.put(RequestType.API25M01, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + QUERY_USING_APN, POST, GET));
        chinaTelecomUrls.put(RequestType.API23M07, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + APN_NET_STATUS_ACTION, POST, GET));
        chinaTelecomUrls.put(RequestType.API23A04, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + DIAGNOSIS, POST, GET));
        chinaTelecomUrls.put(RequestType.API25U03, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + BATCH_QRY_FLOW_BY_DATE, POST, GET));
        chinaTelecomUrls.put(RequestType.API23M19, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + BATCH_DS_TRANS, POST, GET));
        chinaTelecomUrls.put(RequestType.API25Z00, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + QRY_REAL_NAME_STATUS, POST, GET));
        chinaTelecomUrls.put(RequestType.API23M04, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + APN_MOUDLE_SPEED_LIMIT_ACTION, POST, GET));
        chinaTelecomUrls.put(RequestType.API25L00, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + QRY_LOCATION_SERVICE, POST, GET));
        chinaTelecomUrls.put(RequestType.API25L02, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + QRY_POSITION_TRACK, POST, GET));
        chinaTelecomUrls.put(RequestType.API25L06, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + BATCH_DS_TRANS, POST, GET));
        chinaTelecomUrls.put(RequestType.API2307, createUrlMethodsMap(CHINA_TELECOM_URL_PREFIX + BATCH_QRY_FLOW_BY_DATE, POST, GET));
        // 其他中国电信的URL映射
        providerUrls.put(OperatorTypeEnum.CHINA_TELECOM, chinaTelecomUrls);
    }

    /**
     * 创建URL和请求方法的映射
     *
     * @param url     API URL
     * @param methods 支持的请求方法列表
     * @return URL和请求方法的映射
     */
    private static Map<String, List<String>> createUrlMethodsMap(String url, String... methods) {
        Map<String, List<String>> urlMethodsMap = new HashMap<>();
        if (methods == null || methods.length == 0) {
            urlMethodsMap.put(url, Arrays.asList(POST, GET)); // 默认支持 POST 和 GET
        } else {
            urlMethodsMap.put(url, Arrays.asList(methods));
        }
        return urlMethodsMap;
    }

    /**
     * 获取对应运营商和请求类型的URL和请求方法列表
     *
     * @param operatorType 运营商类型
     * @param requestType  请求类型
     * @return 对应的URL和请求方法列表
     */
    public static Map<String, List<String>> getProviderUrlMethods(OperatorTypeEnum operatorType, String requestType) {
        Map<String, Map<String, List<String>>> urls = providerUrls.get(operatorType);
        if (urls != null) {
            return urls.get(requestType);
        }
        return null;
    }
}