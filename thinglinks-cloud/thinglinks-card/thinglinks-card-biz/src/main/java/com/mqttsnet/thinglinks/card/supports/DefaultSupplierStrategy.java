package com.mqttsnet.thinglinks.card.supports;

import com.mqttsnet.thinglinks.card.constants.OperatorRequestConstants;
import com.mqttsnet.thinglinks.card.enumeration.OperatorTypeEnum;
import com.mqttsnet.thinglinks.card.strategy.ChinaMobileCardStrategy;
import com.mqttsnet.thinglinks.card.strategy.ChinaTelecomCardStrategy;
import com.mqttsnet.thinglinks.card.strategy.ChinaUnicomCardStrategy;
import com.mqttsnet.thinglinks.card.utils.chinamobile.RequestType;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 供应商策略
 * 1 供应商实现类
 * 2 添加供应商接口api地址
 *
 * @Author: shisen
 * @Date: 2024/06/27 22:29
 */
@Service
@RequiredArgsConstructor
public class DefaultSupplierStrategy {

    private final DefaultSupportManager defaultSupportManager;
    private final ChinaMobileCardStrategy chinaMobileCardStrategy;
    private final ChinaUnicomCardStrategy chinaUnicomCardStrategy;
    private final ChinaTelecomCardStrategy chinaTelecomCardStrategy;

    /**
     * 初始化供应商
     */
    @PostConstruct
    public void init() {
        this.supplierInit();
        this.supplierApiMappingInit();
    }

    /**
     * 初始化供应商
     */
    private void supplierInit() {
        // 使用依赖注入的策略类实例
        defaultSupportManager.postProcessAfterInitialization(chinaMobileCardStrategy, chinaUnicomCardStrategy, chinaTelecomCardStrategy);
    }

    /**
     * 供应商API映射
     */
    private void supplierApiMappingInit() {
        /**
         * 移动onelink
         * 移动v5官方
         */
        Map<String, String> onelinkType = new HashMap<>();
        // 单卡基本信息
        onelinkType.put(RequestType.API23S00, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_BASIC_INFO);
        // 单卡单月流量  适合月套餐卡
        onelinkType.put(RequestType.API25U04, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_DATA_USAGE);
        // API23U07-单卡本月套餐内流量使用量实时查询 适合年包卡
        onelinkType.put(RequestType.API23U07, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_DATA_MARGIN);
        // 单卡状态查询
        onelinkType.put(RequestType.API25S04, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_STATUS);
        // 单卡在线信息实时查询
        onelinkType.put(RequestType.API25M01, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_SESSION);
        // 物联卡机卡分离状态查询
        onelinkType.put(RequestType.API23A04, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.CARD_BIND_STATUS);
        // API25U01-物联卡单日 GPRS 流量使用量批量
        onelinkType.put(RequestType.API25U01, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_DATA_USAGE_DAILY);
        // API25U03-物联卡单月 GPRS 流量使用量批量
        onelinkType.put(RequestType.API25U03, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_DATA_USAGE_MONTHLY);
        // API25M00-单卡开关机状态实时查询
        onelinkType.put(RequestType.API25M00, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.ON_OFF_STATUS);
        // API23M08-单卡通信功能开通查询
        onelinkType.put(RequestType.API23M08, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_COMMUNICATION_FUNCTION_STATUS);
        // API23S02-单卡状态变更历史查询
        onelinkType.put(RequestType.API23S02, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.QUERY_SIM_CHANGE_HISTORY);
        // API23S03-单卡状态变更
        onelinkType.put(RequestType.API23S03, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.CHANGE_SIM_STATUS);
        // API23B01-单卡余额信息实时查询
        onelinkType.put(RequestType.API23B01, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.BALANCE_INFO);
        // API23B00-集团客户账单实时查询
        onelinkType.put(RequestType.API23B00, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.EC_BILL);
        // API23S06-物联卡状态变更批量办理
        onelinkType.put(RequestType.API23S06, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_STATUS_BATCH);
        // API23M10-单卡状态变更
        onelinkType.put(RequestType.API23M10, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_CHANGE_HISTORY);
        // API23M09-物联卡通信功能开停批量办理
        onelinkType.put(RequestType.API23M09, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_COMMUNICATION_FUNCTION_BATCH);
        // API23E04-群组成员流量限额设置
        onelinkType.put(RequestType.API23E04, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.GROUP_MEMBER_DATA_USAGE);
        // API23M19-物联卡节电参数变更批量办理
        onelinkType.put(RequestType.API23M19, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_PARAMETER_NODE_BATCH);
        // API23U04-群组本月套餐内流量使用量实时查 (能查到总量 已使用 剩余量)
        onelinkType.put(RequestType.API23U04, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.GROUP_DATA_MARGIN);
        // API23M07-单卡 APN 功能开停
        onelinkType.put(RequestType.API23M07, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_APN_FUNCTION);
        // API23E02-成员归属群组查询
        onelinkType.put(RequestType.API23E02, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_GROUP_BY_MEMBER);
        // API25M03-物联卡 GPRS 上网功能重置
        onelinkType.put(RequestType.API25M03, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_GPRS_STATUS_RESET);
        // API25L00-物联卡实时位置经纬度查询
        onelinkType.put(RequestType.API25L00, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.POSITION_LOCATION_MESSAGE);
        // API25L01-物联卡实时位置区号查询 ec/query/district-position-location-message
        onelinkType.put(RequestType.API25L01, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.DISTRICT_POSITION_LOCATION_MESSAGE);
        // API25L02-物联卡最后上网位置经纬度查询 ec/query/last-position-location
        onelinkType.put(RequestType.API25L02, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.LAST_POSITION_LOCATION);
        // API25L04-物联卡最后上网位置区号查询  ec/query/last-position-location
        onelinkType.put(RequestType.API25L04, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.LAST_DISTRICT_POSITION);
        // API25L06-经纬度坐标系批量转换  ec/location/translate/batch
        onelinkType.put(RequestType.API25L06, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.TRANSLATE);
        // API25L42-经纬度逆地理解析 ec/location/regeo
        onelinkType.put(RequestType.API25L42, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.LOCATION_REGEO);
        // API23U06-单卡本月套餐内短信使用量实时查询
        onelinkType.put(RequestType.API23U06, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_SMS_MARGIN);
        // API25U02-物联卡单月短信使用量批量查询
        onelinkType.put(RequestType.API25U02, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.QUERY_SIM_SMS_USAGE_MONTHLY_BATCH);
        // API23S04-单卡绑定IMEI实时查询
        onelinkType.put(RequestType.API23S04, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_IMEI);
        // API23S05-物联卡归属平台批量查询接口
        onelinkType.put(RequestType.API23S05, OperatorRequestConstants.CHINA_MOBILE_URL_PREFIX + OperatorRequestConstants.SIM_PLATFORM_BATCH);
        defaultSupportManager.postProvidersUrlAfterInitialization(OperatorTypeEnum.CHINA_MOBILE, onelinkType);

        /**
         * 联通官方
         */
        Map<String, String> thirdPartyDongWanLt = new HashMap<>();
        // 卡状态变更信息查询
        thirdPartyDongWanLt.put(RequestType.API23S02, OperatorRequestConstants.CHINA_UNICOM_URL_PREFIX + "some-specific-url");
        defaultSupportManager.postProvidersUrlAfterInitialization(OperatorTypeEnum.CHINA_UNICOM, thirdPartyDongWanLt);

        /**
         * 电信官方
         */
        Map<String, String> telecommunicationsOfficial = new HashMap<>();
        // CTIOT_5GCMP_BQ018-批量 SIM 卡资料查询
        telecommunicationsOfficial.put(RequestType.API23S00, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.BATCH_QRY_SIM_INFO);
        // CTIOT_5GCMP_UQ013-批量流量查询（按天）
        telecommunicationsOfficial.put(RequestType.API25U04, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.BATCH_QRY_FLOW_BY_DATE);
        // CTIOT_5GCMP_BQ020-批量 SIM 卡状态查询
        telecommunicationsOfficial.put(RequestType.API25S04, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.BATCH_QRY_SIM_STATUS);
        // CTIOT_5GCMP_MAPN001-APN 使用信息查询
        telecommunicationsOfficial.put(RequestType.API25M01, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.QUERY_USING_APN);
        // 停复机
        telecommunicationsOfficial.put(RequestType.API23S03, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX);
        // API23M07-单卡 APN 功能开停
        telecommunicationsOfficial.put(RequestType.API23M07, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.APN_NET_STATUS_ACTION);
        // CTIOT_5GCMP_LWL006-批量坐标系转换
        telecommunicationsOfficial.put(RequestType.API25L06, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.BATCH_DS_TRANS);
        // CTIOT_5GCMP_BQ019-批量实名状态查询
        telecommunicationsOfficial.put(RequestType.API25Z00, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.QRY_REAL_NAME_STATUS);
        // CTIOT_5GCMP_MAPN004-APN 自主限速
        telecommunicationsOfficial.put(RequestType.API23M04, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.APN_MOUDLE_SPEED_LIMIT_ACTION);
        // CTIOT_5GCMP_BA005-达量断网新增、修改阈值及取消
        telecommunicationsOfficial.put(RequestType.API23E04, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.OFF_NET_ACTION);
        // CTIOT_5GCMP_RD001-风险诊断
        telecommunicationsOfficial.put(RequestType.API23A04, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.DIAGNOSIS);
        // CTIOT_5GCMP_LWL001-基站粗定位（实时在线位置 经纬度）
        telecommunicationsOfficial.put(RequestType.API25L00, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.QRY_LOCATION_SERVICE);
        // CTIOT_5GCMP_LWL004-历史轨迹查询（经纬度）
        telecommunicationsOfficial.put(RequestType.API25L02, OperatorRequestConstants.CHINA_TELECOM_URL_PREFIX + OperatorRequestConstants.QRY_POSITION_TRACK);
        defaultSupportManager.postProvidersUrlAfterInitialization(OperatorTypeEnum.CHINA_TELECOM, telecommunicationsOfficial);
    }
}