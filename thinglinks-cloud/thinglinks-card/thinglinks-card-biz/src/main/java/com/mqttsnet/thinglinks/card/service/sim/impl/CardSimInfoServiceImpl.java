package com.mqttsnet.thinglinks.card.service.sim.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.converter.Builder;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.card.entity.sim.CardSimInfo;
import com.mqttsnet.thinglinks.card.enumeration.CardTypeEnum;
import com.mqttsnet.thinglinks.card.enumeration.CarrierTypeEnum;
import com.mqttsnet.thinglinks.card.enumeration.SimCardOnlineFlagEnum;
import com.mqttsnet.thinglinks.card.enumeration.SimCardStatusEnum;
import com.mqttsnet.thinglinks.card.enumeration.SimCardUseTypeEnum;
import com.mqttsnet.thinglinks.card.manager.sim.CardSimInfoManager;
import com.mqttsnet.thinglinks.card.service.channel.CardChannelInfoService;
import com.mqttsnet.thinglinks.card.service.sim.CardSimInfoService;
import com.mqttsnet.thinglinks.card.vo.query.sim.CardSimInfoPageQuery;
import com.mqttsnet.thinglinks.card.vo.result.channel.CardChannelInfoResultVO;
import com.mqttsnet.thinglinks.card.vo.result.sim.CardSimInfoResultVO;
import com.mqttsnet.thinglinks.card.vo.result.sim.CardSimOverviewResultVO;
import com.mqttsnet.thinglinks.card.vo.save.sim.CardSimInfoSaveVO;
import com.mqttsnet.thinglinks.card.vo.update.sim.CardSimInfoUpdateVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

/**
 * <p>
 * 业务实现类
 * 物联网卡信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 23:45:39
 * @create [2024-06-26 23:45:39] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class CardSimInfoServiceImpl extends SuperServiceImpl<CardSimInfoManager, Long, CardSimInfo> implements CardSimInfoService {

    private final CardChannelInfoService cardChannelInfoService;

    @Override
    public CardSimInfoSaveVO saveCardSimInfo(CardSimInfoSaveVO saveVO) {
        log.info("Save the iot card information saveVO:{}", saveVO);
        validateSaveVO(saveVO);
        CardSimInfo cardSimInfo = builderCardSimInfoSaveVO(saveVO);
        superManager.save(cardSimInfo);
        return BeanPlusUtil.toBeanIgnoreError(cardSimInfo, CardSimInfoSaveVO.class);
    }

    @Override
    public CardSimInfoUpdateVO updateCardSimInfo(CardSimInfoUpdateVO updateVO) {
        log.info("Update the iot card information updateVO:{}", updateVO);
        validateUpdateVO(updateVO);
        Builder<CardSimInfo> deviceBuilder = builderCardSimInfoUpdateVO(updateVO);
        //更新
        superManager.updateById(deviceBuilder.with(CardSimInfo::setId, updateVO.getId()).build());
        return updateVO;
    }

    @Override
    public CardSimInfoResultVO getCardSimInfoDetails(Long id) {
        if (id == null) {
            throw BizException.wrap("The ID cannot be null");
        }
        CardSimInfo cardSimInfo = super.getById(id);
        if (cardSimInfo == null) {
            throw BizException.wrap("Iot card information does not exist");
        }
        CardSimInfoResultVO cardSimInfoResultVO = BeanPlusUtil.toBeanIgnoreError(cardSimInfo, CardSimInfoResultVO.class);

        CardChannelInfoResultVO channelInfoDetails = cardChannelInfoService.getChannelInfoDetails(cardSimInfo.getChannelId());
        cardSimInfoResultVO.setCardChannelInfoResultVO(channelInfoDetails);

        return cardSimInfoResultVO;
    }

    @Override
    public Boolean deleteCardSimInfo(Long id) {
        if (id == null) {
            throw BizException.wrap("The ID cannot be null");
        }
        return superManager.removeById(id);
    }

    @Override
    public CardSimOverviewResultVO getCardSimOverview() {
        List<CardSimInfo> list = superManager.list();
        CardSimOverviewResultVO resultVO = new CardSimOverviewResultVO();
        resultVO.setTotalCardsCount(list.size());

        Map<CardTypeEnum, AtomicLong> cardTypeCountMap = Arrays.stream(CardTypeEnum.values())
                .collect(Collectors.toMap(type -> type, type -> new AtomicLong(0)));
        Map<CarrierTypeEnum, AtomicLong> carrierTypeCountMap = Arrays.stream(CarrierTypeEnum.values())
                .collect(Collectors.toMap(type -> type, type -> new AtomicLong(0)));
        Map<SimCardStatusEnum, AtomicLong> simCardStatusCountMap = Arrays.stream(SimCardStatusEnum.values())
                .collect(Collectors.toMap(type -> type, type -> new AtomicLong(0)));
        Map<SimCardUseTypeEnum, AtomicLong> useTypeCountMap = Arrays.stream(SimCardUseTypeEnum.values())
                .collect(Collectors.toMap(type -> type, type -> new AtomicLong(0)));
        Map<SimCardOnlineFlagEnum, AtomicLong> onlineFlagCountMap = Arrays.stream(SimCardOnlineFlagEnum.values())
                .collect(Collectors.toMap(type -> type, type -> new AtomicLong(0)));

        list.forEach(card -> {
            Optional.ofNullable(CardTypeEnum.fromValue(card.getCardType()))
                    .ifPresent(type -> {
                        AtomicLong count = cardTypeCountMap.get(type.orElse(null));
                        if (count != null) {
                            count.incrementAndGet();
                        }
                    });

            Optional.ofNullable(CarrierTypeEnum.fromValue(card.getCarrierType()))
                    .ifPresent(type -> {
                        AtomicLong count = carrierTypeCountMap.get(type.orElse(null));
                        if (count != null) {
                            count.incrementAndGet();
                        }
                    });

            Optional.ofNullable(SimCardStatusEnum.fromValue(card.getStatus()))
                    .ifPresent(type -> {
                        AtomicLong count = simCardStatusCountMap.get(type.orElse(null));
                        if (count != null) {
                            count.incrementAndGet();
                        }
                    });

            Optional.ofNullable(SimCardUseTypeEnum.fromValue(card.getUseType()))
                    .ifPresent(type -> {
                        AtomicLong count = useTypeCountMap.get(type.orElse(null));
                        if (count != null) {
                            count.incrementAndGet();
                        }
                    });

            Optional.ofNullable(SimCardOnlineFlagEnum.fromValue(card.getOnlineFlag()))
                    .ifPresent(type -> {
                        AtomicLong count = onlineFlagCountMap.get(type.orElse(null));
                        if (count != null) {
                            count.incrementAndGet();
                        }
                    });
        });

        resultVO.setCardTypePlugInCount(cardTypeCountMap.getOrDefault(CardTypeEnum.PLUG_IN, new AtomicLong(0)).intValue());
        resultVO.setCardTypePatchCount(cardTypeCountMap.getOrDefault(CardTypeEnum.PATCH, new AtomicLong(0)).intValue());

        resultVO.setCarrierTypeMobileCount(carrierTypeCountMap.getOrDefault(CarrierTypeEnum.MOBILE, new AtomicLong(0)).intValue());
        resultVO.setCarrierTypeTelecomCount(carrierTypeCountMap.getOrDefault(CarrierTypeEnum.TELECOM, new AtomicLong(0)).intValue());
        resultVO.setCarrierTypeUnicomCount(carrierTypeCountMap.getOrDefault(CarrierTypeEnum.UNICOM, new AtomicLong(0)).intValue());

        resultVO.setStatusPendingActivationCount(simCardStatusCountMap.getOrDefault(SimCardStatusEnum.PENDING_ACTIVATION, new AtomicLong(0)).intValue());
        resultVO.setStatusActivatedCount(simCardStatusCountMap.getOrDefault(SimCardStatusEnum.ACTIVATED, new AtomicLong(0)).intValue());
        resultVO.setStatusDeactivatedCount(simCardStatusCountMap.getOrDefault(SimCardStatusEnum.DEACTIVATED, new AtomicLong(0)).intValue());

        resultVO.setUseTypeNormalCount(useTypeCountMap.getOrDefault(SimCardUseTypeEnum.NORMAL, new AtomicLong(0)).intValue());
        resultVO.setUseTypeSharedPoolCount(useTypeCountMap.getOrDefault(SimCardUseTypeEnum.SHARED_POOL, new AtomicLong(0)).intValue());
        resultVO.setUseTypeTrafficPoolCount(useTypeCountMap.getOrDefault(SimCardUseTypeEnum.TRAFFIC_POOL, new AtomicLong(0)).intValue());

        resultVO.setOnlineFlagOnlineCount(onlineFlagCountMap.getOrDefault(SimCardOnlineFlagEnum.ONLINE, new AtomicLong(0)).intValue());
        resultVO.setOnlineFlagOfflineCount(onlineFlagCountMap.getOrDefault(SimCardOnlineFlagEnum.OFFLINE, new AtomicLong(0)).intValue());

        return resultVO;
    }


    @Override
    public List<CardSimInfoResultVO> getCardSimInfoResultVOList(CardSimInfoPageQuery query) {
        List<CardSimInfo> cardSimInfoList = superManager.getCardSimInfoList(query);
        return BeanPlusUtil.toBeanList(cardSimInfoList, CardSimInfoResultVO.class);
    }


    private void validateSaveVO(CardSimInfoSaveVO saveVO) {
        // Perform validations
        ArgumentAssert.notBlank(saveVO.getIccid(), "The SIM card unique identification code cannot be empty");
        ArgumentAssert.notBlank(saveVO.getCardNumber(), "The card number cannot be empty");
        // Additional validations...
    }

    private void validateUpdateVO(CardSimInfoUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "The ID cannot be null");
        // Additional validations...
    }

    private CardSimInfo builderCardSimInfoSaveVO(CardSimInfoSaveVO saveVO) {
        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return BeanPlusUtil.copyProperties(saveVO, CardSimInfo.class);
    }

    private Builder<CardSimInfo> builderCardSimInfoUpdateVO(CardSimInfoUpdateVO updateVO) {
        return Builder.of(CardSimInfo::new)
                .with(CardSimInfo::setImsi, updateVO.getImsi())
                .with(CardSimInfo::setIccid, updateVO.getIccid())
                .with(CardSimInfo::setCardNumber, updateVO.getCardNumber())
                .with(CardSimInfo::setCardType, updateVO.getCardType())
                .with(CardSimInfo::setChannelId, updateVO.getChannelId())
                .with(CardSimInfo::setFlowsUsed, updateVO.getFlowsUsed())
                .with(CardSimInfo::setFlowsTotal, updateVO.getFlowsTotal())
                .with(CardSimInfo::setFlowsRest, updateVO.getFlowsRest())
                .with(CardSimInfo::setVirtualFlowsUsed, updateVO.getVirtualFlowsUsed())
                .with(CardSimInfo::setVirtualFlowsRest, updateVO.getVirtualFlowsRest())
                .with(CardSimInfo::setVirtualFlowsTotal, updateVO.getVirtualFlowsTotal())
                .with(CardSimInfo::setSmsFlag, updateVO.getSmsFlag())
                .with(CardSimInfo::setGprsFlag, updateVO.getGprsFlag())
                .with(CardSimInfo::setOpenTime, updateVO.getOpenTime())
                .with(CardSimInfo::setLastOpenTime, updateVO.getLastOpenTime())
                .with(CardSimInfo::setStartTime, updateVO.getStartTime())
                .with(CardSimInfo::setEndTime, updateVO.getEndTime())
                .with(CardSimInfo::setFlowsEndTime, updateVO.getFlowsEndTime())
                .with(CardSimInfo::setCarrierType, updateVO.getCarrierType())
                .with(CardSimInfo::setSmsCount, updateVO.getSmsCount())
                .with(CardSimInfo::setStatus, updateVO.getStatus())
                .with(CardSimInfo::setUseType, updateVO.getUseType())
                .with(CardSimInfo::setApnName, updateVO.getApnName())
                .with(CardSimInfo::setIpAddress, updateVO.getIpAddress())
                .with(CardSimInfo::setGainTime, updateVO.getGainTime())
                .with(CardSimInfo::setOnlineFlag, updateVO.getOnlineFlag())
                .with(CardSimInfo::setStopCardType, updateVO.getStopCardType())
                .with(CardSimInfo::setMonthlyWarning, updateVO.getMonthlyWarning())
                .with(CardSimInfo::setImei, updateVO.getImei())
                .with(CardSimInfo::setLimitFlow, updateVO.getLimitFlow())
                .with(CardSimInfo::setLimitFlag, updateVO.getLimitFlag())
                .with(CardSimInfo::setLimitStatus, updateVO.getLimitStatus())
                .with(CardSimInfo::setOfferId, updateVO.getOfferId())
                .with(CardSimInfo::setSearchableStatus, updateVO.getSearchableStatus())
                .with(CardSimInfo::setRemark, updateVO.getRemark())
                .with(CardSimInfo::setCreatedOrgId, updateVO.getCreatedOrgId());
    }


}


