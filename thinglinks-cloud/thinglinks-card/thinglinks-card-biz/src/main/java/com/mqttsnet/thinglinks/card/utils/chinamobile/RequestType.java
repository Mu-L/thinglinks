package com.mqttsnet.thinglinks.card.utils.chinamobile;

/**
 * 请求类型
 *
 * @Author: shisen
 * @Date: 2024/06/27 20:54
 */
public class RequestType {

    /**
     * CMIOT_API23U12-单卡流量池内使用量实时查询
     * 集团客户可通过该接口查询单卡在流量池或流量池共享下实时使用量。
     */
    public static final String API23U12 = "API23U12";

    /**
     * CMIOT_API25U04-单卡本月流量累计使用量查询
     * 查询集团所属物联卡当月的GPRS使用量，PB号段为截至前一天24点流量，CT号段为实时流量。（单位：KB）。
     */
    public static final String API25U04 = "API25U04";

    /**
     * CMIOT_API23E04-群组成员流量限额设置
     * 集团客户可通过物联卡卡号（msisdn，单卡）调用该接口实现添加/删除/变更群组成员卡流量池内流量限额的设置。新增/变更流量限额设置时若当前已用额度超过设置额度，就算设置成功，该账期限额也不会生效，限额下一账期生效。
     */
    public static final String API23E04 = "API23E04";

    /**
     * CMIOT_API23S00-单卡基本信息查询
     * 查询物联卡码号信息、开卡时间、首次激活时间。
     */
    public static final String API23S00 = "API23S00";

    /**
     * CMIOT_API25S04-单卡状态查询
     * 通过卡号查询物联卡的状态信息。
     */
    public static final String API25S04 = "API25S04";

    /**
     * CMIOT_API25M01-单卡在线信息实时查询
     * 查询物联卡的在线信息，区分APN，返回APN信息、IP地址、会话开始时间。
     */
    public static final String API25M01 = "API25M01";

    /**
     * CMIOT_API23M07-单卡APN功能开停
     * 集团客户可以通过卡号（msisdniccidimsi三选一，单卡）办理集团归属物联卡的APN功能开/停（同一卡号3分钟内不能通过此类接口重复办理业务）。
     */
    public static final String API23M07 = "API23M07";

    /**
     * CMIOT_API23U07-单卡本月套餐流量用量实时查询
     * 实时查询物联卡本月套餐内流量使用量。
     */
    public static final String API23U07 = "API23U07";

    /**
     * CMIOT_API23U04-群组本月套餐内流量使用量实时查询
     * 实时查询群组本月套餐内GPRS流量使用量信息。
     */
    public static final String GROUP_DATA_MARGIN = "API23U04";

    /**
     * API23U04-群组本月套餐内流量使用量实时查 (能查到总量 已使用 剩余量)
     */
    public static final String API23U04 = "API23U04";

    /**
     * CMIOT_API23S03-单卡状态变更
     * 集团客户可以通过卡号（msisdniccidimsi三选一，单卡）变更集团归属物联卡的状态（同一卡号3分钟内不能通过此类接口重复办理业务）。
     */
    public static final String API23S03 = "API23S03";

    /**
     * CMIOT_API23S06-物联卡状态变更批量办理
     * 集团客户可以通过卡号调用该接口批量办理物联卡的状态变更，每次不超过100张卡，同一卡号3分钟内不得重复调用该接口。如需查询办理结果则根据该接口返回的任务流水号调“CMIOT_API23M10-物联卡业务批量办理结果查询”接口查询办理结果。（OneLink-CT的主EC客户查询副EC卡数据时，一次查询的卡号必须归属于同一个EC）
     */
    public static final String API23S06 = "API23S06";

    /**
     * CMIOT_API23S02-单卡状态变更历史查询
     * 通过卡号查询物联卡的状态变更历史。
     */
    public static final String API23S02 = "API23S02";

    /**
     * CMIOT_API25U01-物联卡单日GPRS流量使用量批量查询
     * 通过此接口可以批量（暂定100张）查询物联卡某一天GPRS流量使用量（仅支持查询近7天中某一天的数据，截止前一天）
     */
    public static final String API25U01 = "API25U01";

    /**
     * CMIOT_API23A04-物联卡机卡分离状态查询
     * 集团客户可通过物联卡卡号（msisdn，单卡）调用该接口查询已订购话单侧或网络侧机卡绑定的单卡的机卡绑定状态。
     */
    public static final String API23A04 = "API23A04";

    /**
     * CMIOT_API23M08-单卡通信功能开通查询
     * 客户通过卡号查询物联卡通信功能开通情况。
     */
    public static final String API23M08 = "API23M08";

    /**
     * CMIOT_API25U03-物联卡单月GPRS流量使用量批量查询
     * 通过此接口可以批量（暂定100张）查询集团客户下所属SIM卡的月数据使用情况。批量查询多个用户、指定日期的数据使用量，仅支持查询近6个月中某月的使用量，其中本月数据截止为前一天
     */
    public static final String API25U03 = "API25U03";

    /**
     * CMIOT_API23M09-物联卡通信功能开停批量办理
     * 集团客户可以通过卡号调用该接口批量办理物联卡的通信功能（语音、短信、国际漫游、数据通信服务）开停，每次不超过100张卡，同一卡号3分钟内不得重复调用该接口。如需查询办理结果则根据该接口返回的任务流水号调“CMIOT_API23M10-物联卡业务批量办理结果查询”接口查询办理结果。（OneLink-CT的主EC客户查询副EC卡数据时，一次查询的卡号必须归属于同一个EC）
     */
    public static final String API23M09 = "API23M09";

    /**
     * CMIOT_API23M10-物联卡业务批量办理结果查询
     * 集团客户可以通过物联卡批量处理的任务流水号接口查询物联卡业务批量办理的结果。
     */
    public static final String API23M10 = "API23M10";

    /**
     * API23U03-单卡短信发送
     */
    public static final String API23U03 = "API23U03";

    /**
     * API23U05-单卡短信发送结果查询 用于第三方上游
     */
    public static final String API23U05 = "API23U05";




    /*      以下仅用于移动v5       */
    /**
     * API23U06-单卡本月套餐内短信使用量实时查询
     */
    public static final String API23U06 = "API23U06";

    /**
     * API25U00-物联卡单日短信使用量批量查询
     */
    public static final String API25U00 = "API25U00";

    /**
     * API25U02-物联卡单月短信使用量批量查询
     */
    public static final String API25U02 = "API25U02";

    /**
     * CMIOT_API23S04-单卡绑定IMEI实时查询
     */
    public static final String API23S04 = "API23S04";

    /**
     * CMIOT_API23S05-物联卡归属平台批量查询接口
     */
    public static final String API23S05 = "API23S05";

    /**
     * API25C03-物联卡终端控制上行短信记录批量查
     */
    public static final String API25C03 = "API25C03";

    /*      以下仅用于移动v5 结束      */

    /**
     * API25C02-物联卡终端控制下行模板短信记录批
     */
    public static final String API25C02 = "API25C02";

    /**
     * API25M00-单卡开关机状态实时查询
     */
    public static final String API25M00 = "API25M00";

    /**
     * API23M19-物联卡节电参数变更批量办理
     */
    public static final String API23M19 = "API23M19";

    /**
     * API23E02-成员归属群组查询
     */
    public static final String API23E02 = "API23E02";

    /**
     * API25M03-物联卡 GPRS 上网功能重置
     */
    public static final String API25M03 = "API25M03";

    /**
     * API25Z00-实名制信息查询接口
     */
    public static final String API25Z00 = "API25Z00";

    /**
     * API23M04-单卡自主限速
     */
    public static final String API23M04 = "API23M04";

    /**
     * API23R02-可订购资费实时查询 && 单个号码可订购套餐查询
     */
    public static final String API23R02 = "API23R02";

    /**
     * API23B02-物联卡账户充值交费 && 单个号码套餐办理
     */
    public static final String API23B02 = "API23B02";

    /**
     * API25L00-物联卡实时位置经纬度查询
     */
    public static final String API25L00 = "API25L00";

    /**
     * API25L01-物联卡实时位置区号查询
     */
    public static final String API25L01 = "API25L01";

    /**
     * API25L02-物联卡最后上网位置经纬度查询
     */
    public static final String API25L02 = "API25L02";

    /**
     * CMIOT_API25L04-物联卡最后上网位置区号查询
     */
    public static final String API25L04 = "API25L04";

    /**
     * CMIOT_API25L06-经纬度坐标系批量转换
     */
    public static final String API25L06 = "API25L06";


    /**
     * API25L42-经纬度逆地理解析
     */
    public static final String API25L42 = "API25L42";

    /**
     * CMIOT_API23B01-单卡余额信息实时查询
     */
    public static final String API23B01 = "API23B01";

    /**
     * CMIOT_API23B00-集团客户账单实时查询
     */
    public static final String API23B00 = "API23B00";

    /**
     * 效验token有效性
     */
    public static final String SMS_SEND = "100004";


    /******************* 移动v2 ***************/
    /**
     * API2307-物联卡单月 GPRS 使用量批量查询
     */
    public static final String API2307 = "API2307";

}
