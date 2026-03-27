package com.mqttsnet.thinglinks.card.service.anytenant.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.StringUtils;
import com.mqttsnet.thinglinks.card.abstrac.AbstractCard;
import com.mqttsnet.thinglinks.card.entity.auto.IotCardAuthToken;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfo;
import com.mqttsnet.thinglinks.card.enumeration.OperatorTypeEnum;
import com.mqttsnet.thinglinks.card.service.anytenant.CardSimOpenAnyTenantService;
import com.mqttsnet.thinglinks.card.service.channel.CardChannelInfoService;
import com.mqttsnet.thinglinks.card.service.sim.CardSimInfoService;
import com.mqttsnet.thinglinks.card.supports.DefaultSupportManager;
import com.mqttsnet.thinglinks.card.utils.chinamobile.RequestType;
import com.mqttsnet.thinglinks.card.vo.param.CardSimOpenApiParam;
import com.mqttsnet.thinglinks.card.vo.query.sim.CardSimInfoPageQuery;
import com.mqttsnet.thinglinks.card.vo.result.sim.CardSimInfoResultVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * 对外提供API调用,逻辑控制
 *
 * @Author: shisen
 * @Date: 2024/06/28 20:37
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class CardSimOpenAnyTenantServiceImpl implements CardSimOpenAnyTenantService {

    private final CardSimInfoService cardSimInfoService;
    private final CardChannelInfoService cardChannelInfoService;
    private final DefaultSupportManager defaultSupportManager;


    protected CardSimOpenApiParam cardIsExist(CardSimOpenApiParam cardSimOpenApiParam) {
        if (StringUtils.isEmpty(cardSimOpenApiParam.getCardNumber()) && StringUtils.isEmpty(cardSimOpenApiParam.getIccid())) {
            throw new BizException("卡号信息错误，请检查MSISDN、ICCID、IMSI是否正确");
        }
        if (StringUtils.isNotEmpty(cardSimOpenApiParam.getCardNumber())) {
            processCardNumber(cardSimOpenApiParam);
        } else if (StringUtils.isNotEmpty(cardSimOpenApiParam.getIccid())) {
            processIccid(cardSimOpenApiParam);
        }
        return cardSimOpenApiParam;
    }


    private IotCardAuthToken getPlatformToken(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        CardSimInfoPageQuery cardSimInfoPageQuery = new CardSimInfoPageQuery();
        cardSimInfoPageQuery.setIccid(cardSimOpenApiParam.getIccid());
        cardSimInfoPageQuery.setCardNumber(cardSimOpenApiParam.getCardNumber());
        List<CardSimInfoResultVO> cardSimInfoResultVOList = cardSimInfoService.getCardSimInfoResultVOList(cardSimInfoPageQuery);

        if (CollUtil.isEmpty(cardSimInfoResultVOList)) {
            log.error("未匹配到正确的SIM卡信息");
        }

        CardSimInfoResultVO cardSimInfoResultVO = cardSimInfoResultVOList.get(0);

        CardChannelInfo channelInfo = cardChannelInfoService.getSuperManager().getById(cardSimInfoResultVO.getChannelId());
        if (Objects.isNull(channelInfo)) {
            log.warn("未查询到此卡绑定的运营商渠道 channelId:{}", cardSimInfoResultVO.getChannelId());
            throw new BizException("未查询到此卡绑定的运营商渠道");
        }
        IotCardAuthToken iotToken = authenticateAndRetrieveToken(cardSimInfoResultVO, channelInfo);
        if (iotToken == null) {
            log.error("卡源加载失败: {}", channelInfo.getChannelName());
            throw new BizException("查询结果为空");
        }
        return iotToken;
    }


    private IotCardAuthToken authenticateAndRetrieveToken(CardSimInfoResultVO cardSimInfoResultVO, CardChannelInfo channelInfo) throws IOException {
        IotCardAuthToken token = buildIotCardToken(cardSimInfoResultVO, channelInfo);
        IotCardAuthToken iotToken = defaultSupportManager.getSpecificProviders(OperatorTypeEnum.get(channelInfo.getOperatorType())).authenticate(channelInfo, token);
        if (!Objects.isNull(iotToken)) {
            iotToken.setPrivateParameters(Collections.singletonMap(channelInfo.getChannelName(), channelInfo));
        }
        return iotToken;
    }

    private IotCardAuthToken buildIotCardToken(CardSimInfoResultVO cardSimInfoResultVO, CardChannelInfo channelInfo) {
        return IotCardAuthToken.builder().cardNum(cardSimInfoResultVO.getCardNumber()).iccid(cardSimInfoResultVO.getIccid()).imsi(cardSimInfoResultVO.getImsi()).cardId(cardSimInfoResultVO.getId()).apnName(cardSimInfoResultVO.getApnName()).channelName(channelInfo.getChannelName()).supportId(OperatorTypeEnum.get(channelInfo.getOperatorType())).build();
    }

    private void processCardNumber(CardSimOpenApiParam cardSimOpenApiParam) {
        String cardNum = cardSimOpenApiParam.getCardNumber();
        if (cardNum.length() >= 22) {
            cardSimOpenApiParam.setCardNumber(cardNum.split("_")[0]);
        }
        log.info("处理后的卡号: {}", cardSimOpenApiParam.getCardNumber());
    }

    private void processIccid(CardSimOpenApiParam cardSimOpenApiParam) {
        String iccid = cardSimOpenApiParam.getIccid();
        if (iccid.length() >= 30) {
            cardSimOpenApiParam.setIccid(iccid.split("_")[0]);
            cardSimOpenApiParam.setCardNumber(null);
        }
        log.info("处理后的ICCID: {}", cardSimOpenApiParam.getIccid());
    }

    private IotCardAuthToken executeStrategy(IotCardAuthToken iotToken) {
        AbstractCard abstractCard = defaultSupportManager.getSpecificProviders(iotToken.getSupportId());
        return abstractCard.prepareCollar(iotToken);
    }

    @Override
    public IotCardAuthToken simBasicInfo(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simBasicInfo方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23S00);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simBasicInfo方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simChangeHistory(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simChangeHistory方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23S02);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simChangeHistory方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simImei(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simImei方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23S04);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simImei方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simPlatformBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simPlatformBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23S05);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simPlatformBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simMoSmsBatch(CardSimOpenApiParam cardSimOpenApiParam) throws Exception {
        log.info("进入simMoSmsBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25C03);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simMoSmsBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simDataUsageInPool(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simDataUsageInPool方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23U12);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simDataUsageInPool方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simDataUsage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simDataUsage方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25U04);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simDataUsage方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simStatus方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25S04);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simStatus方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simSession(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simSession方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25M01);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simSession方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simCommunicationFunctionStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simCommunicationFunctionStatus方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23M08);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simCommunicationFunctionStatus方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken groupDataUsage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入groupDataUsage方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23U04);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出groupDataUsage方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken groupDataMargin(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入groupDataMargin方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.GROUP_DATA_MARGIN);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出groupDataMargin方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simApnFunction(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simApnFunction方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23M07);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simApnFunction方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simGroupByMember(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simGroupByMember方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23E02);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simGroupByMember方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simGprsStatusReset(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simGprsStatusReset方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25M03);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simGprsStatusReset方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken groupMemberDataUsage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入groupMemberDataUsage方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23E04);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出groupMemberDataUsage方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken positionLocationMessage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入positionLocationMessage方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25L00);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出positionLocationMessage方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken districtPositionLocationMessage(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入districtPositionLocationMessage方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25L01);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出districtPositionLocationMessage方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken lastPositionLocation(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入lastPositionLocation方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25L02);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出lastPositionLocation方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken locationRegeo(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入locationRegeo方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25L42);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出locationRegeo方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken changeSimStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入changeSimStatus方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23S03);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出changeSimStatus方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simStatusBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simStatusBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23S06);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simStatusBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simParameterNodeBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simParameterNodeBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23M19);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simParameterNodeBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simDataMargin(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simDataMargin方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23U07);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simDataMargin方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simBatchResult(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simBatchResult方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23M10);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simBatchResult方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simDataUsageDailyBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simDataUsageDailyBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25U01);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simDataUsageDailyBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simDataUsageMonthlyBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simDataUsageMonthlyBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25U03);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simDataUsageMonthlyBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken cardBindStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入cardBindStatus方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23A04);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出cardBindStatus方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simCommunicationFunctionBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simCommunicationFunctionBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23M09);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simCommunicationFunctionBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simSmsMargin(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simSmsMargin方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23U06);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simSmsMargin方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simSmsUsageMonthlyBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simSmsUsageMonthlyBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25U02);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simSmsUsageMonthlyBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simSmsUsageDailyBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simSmsUsageDailyBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25U00);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simSmsUsageDailyBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken simMtSmsBatch(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入simMtSmsBatch方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25C02);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出simMtSmsBatch方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken onOffStatus(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入onOffStatus方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25M00);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出onOffStatus方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken balanceInfo(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入balanceInfo方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23B01);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出balanceInfo方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken ecBill(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入ecBill方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API23B00);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出ecBill方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken lastDistrictPosition(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入lastDistrictPosition方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25L04);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出lastDistrictPosition方法，返回结果: {}", result);
        return result;
    }

    @Override
    public IotCardAuthToken translate(CardSimOpenApiParam cardSimOpenApiParam) throws IOException {
        log.info("进入translate方法，参数: {}", cardSimOpenApiParam);
        CardSimOpenApiParam tCardInfo = cardIsExist(cardSimOpenApiParam);
        IotCardAuthToken iotToken = getPlatformToken(tCardInfo);
        iotToken.setRequestType(RequestType.API25L06);
        IotCardAuthToken result = executeStrategy(iotToken);
        log.info("退出translate方法，返回结果: {}", result);
        return result;
    }


}
