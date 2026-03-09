package com.mqttsnet.thinglinks.card.service.anytenant;


import com.mqttsnet.thinglinks.card.entity.auto.IotCardAuthToken;
import com.mqttsnet.thinglinks.card.vo.param.CardSimOpenApiParam;

import java.io.IOException;

/**
 * @Author: shisen
 * @Date: 2020/7/8 20:37
 */
public interface CardSimOpenAnyTenantService {

    /**
     * 查询单卡基本信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡基本信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken simBasicInfo(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询单卡状态变更历史
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡状态变更历史
     * @throws IOException 异常信息
     */
    IotCardAuthToken simChangeHistory(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询单卡绑定IMEI实时信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡绑定IMEI信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken simImei(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量查询物联卡归属平台信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡归属平台信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken simPlatformBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量查询物联卡终端控制上行短信记录
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡终端控制上行短信记录信息
     * * @throws Exception 异常信息
     */
    IotCardAuthToken simMoSmsBatch(CardSimOpenApiParam cardSimOpenApiParam) throws Exception;

    /**
     * 查询单卡流量池内使用量实时信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡流量池内使用量实时信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken simDataUsageInPool(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询单卡本月流量累计使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡本月流量累计使用量
     * @throws IOException 异常信息
     */
    IotCardAuthToken simDataUsage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询单卡状态信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡状态信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken simStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询单卡在线信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡在线信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken simSession(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询单卡通信功能开通情况
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡通信功能开通情况
     * @throws IOException 异常信息
     */
    IotCardAuthToken simCommunicationFunctionStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 实时查询群组本月GPRS流量累计使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 群组本月GPRS流量累计使用量
     * @throws IOException 异常信息
     */
    IotCardAuthToken groupDataUsage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 实时查询群组本月套餐内GPRS流量使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 群组本月套餐内GPRS流量使用量
     * @throws IOException 异常信息
     */
    IotCardAuthToken groupDataMargin(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 单卡APN功能开停
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡APN功能开停
     * @throws IOException 异常信息
     */
    IotCardAuthToken simApnFunction(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询物联卡归属的群组信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡归属的群组信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken simGroupByMember(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 重置物联卡GPRS上网功能
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡GPRS上网功能重置
     * @throws IOException 异常信息
     */
    IotCardAuthToken simGprsStatusReset(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 设置群组成员卡流量池内流量限额
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 群组成员卡流量池内流量限额设置
     * @throws IOException 异常信息
     */
    IotCardAuthToken groupMemberDataUsage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询物联卡当前附网基站所在的经纬度信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡当前附网基站所在的经纬度信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken positionLocationMessage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询物联卡当前实时位置区号信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡当前实时位置区号信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken districtPositionLocationMessage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询物联卡的最后上网基站所在的经纬度信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡最后上网基站所在的经纬度信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken lastPositionLocation(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询经纬度对应的语义化地理位置信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 经纬度对应的语义化地理位置信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken locationRegeo(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 变更物联卡的状态
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡状态变更结果
     * @throws IOException 异常信息
     */
    IotCardAuthToken changeSimStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量变更物联卡的状态
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡状态批量变更结果
     * @throws IOException 异常信息
     */
    IotCardAuthToken simStatusBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量变更物联卡的节电参数
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡节电参数批量变更结果
     * @throws IOException 异常信息
     */
    IotCardAuthToken simParameterNodeBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 实时查询物联卡本月套餐内流量使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡本月套餐内流量使用量
     * @throws IOException 异常信息
     */
    IotCardAuthToken simDataMargin(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询物联卡业务批量办理结果
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡业务批量办理结果
     * @throws IOException 异常信息
     */
    IotCardAuthToken simBatchResult(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量查询物联卡某一天GPRS流量使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡某一天GPRS流量使用量
     * @throws IOException 异常信息
     */
    IotCardAuthToken simDataUsageDailyBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量查询集团客户下所属SIM卡的月数据使用情况
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 集团客户下所属SIM卡的月数据使用情况
     * @throws IOException 异常信息
     */
    IotCardAuthToken simDataUsageMonthlyBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询物联卡机卡绑定状态
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡机卡绑定状态
     * @throws IOException 异常信息
     */
    IotCardAuthToken cardBindStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量办理物联卡通信功能开停
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡通信功能开停批量办理结果
     * @throws IOException 异常信息
     */
    IotCardAuthToken simCommunicationFunctionBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;


    /**
     * 实时查询物联卡本月套餐内短信使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡本月套餐内短信使用量
     * @throws IOException 异常信息
     */
    IotCardAuthToken simSmsMargin(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量查询物联卡月短信使用情况
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡月短信使用情况
     * @throws IOException 异常信息
     */
    IotCardAuthToken simSmsUsageMonthlyBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 批量查询物联卡某一天短信使用量
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡某一天短信使用量
     * @throws IOException 异常信息
     */
    IotCardAuthToken simSmsUsageDailyBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询物联卡终端控制下行短信记录
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡终端控制下行短信记录
     * @throws IOException 异常信息
     */
    IotCardAuthToken simMtSmsBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询终端开关机信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 终端开关机信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken onOffStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询单卡余额信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 单卡余额信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken balanceInfo(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询集团客户账单
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 集团客户账单
     * @throws IOException 异常信息
     */
    IotCardAuthToken ecBill(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 查询物联卡最后上网位置区号信息
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 物联卡最后上网位置区号信息
     * @throws IOException 异常信息
     */
    IotCardAuthToken lastDistrictPosition(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;

    /**
     * 实现各位置坐标系的经纬度转换
     *
     * @param cardSimOpenApiParam 请求体
     * @return {@link IotCardAuthToken} 各位置坐标系的经纬度转换结果
     * @throws IOException 异常信息
     */
    IotCardAuthToken translate(CardSimOpenApiParam cardSimOpenApiParam) throws IOException;


}
