package com.mqttsnet.thinglinks.card.service.channel.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.context.ContextUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.card.entity.channel.CardChannelInfo;
import com.mqttsnet.thinglinks.card.manager.channel.CardChannelInfoManager;
import com.mqttsnet.thinglinks.card.service.channel.CardChannelInfoService;
import com.mqttsnet.thinglinks.card.vo.result.channel.CardChannelInfoResultVO;
import com.mqttsnet.thinglinks.card.vo.save.channel.CardChannelInfoSaveVO;
import com.mqttsnet.thinglinks.card.vo.update.channel.CardChannelInfoUpdateVO;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 物联卡渠道表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-06-26 21:55:13
 * @create [2024-06-26 21:55:13] [mqttsnet]
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
public class CardChannelInfoServiceImpl extends SuperServiceImpl<CardChannelInfoManager, Long, CardChannelInfo> implements CardChannelInfoService {


    @Override
    public CardChannelInfoSaveVO saveChannelInfo(CardChannelInfoSaveVO saveVO) {
        log.info("saveChannelInfo saveVO:{}", saveVO);
        // 校验参数
        checkedChannelSaveVO(saveVO);

        // 构建参数
        CardChannelInfo channelInfo = buildChannelSaveVO(saveVO);

        // 保存渠道信息
        superManager.save(channelInfo);

        return BeanPlusUtil.toBeanIgnoreError(channelInfo, CardChannelInfoSaveVO.class);
    }

    @Override
    public CardChannelInfoUpdateVO updateChannelInfo(CardChannelInfoUpdateVO updateVO) {
        log.info("updateChannelInfo updateVO:{}", updateVO);

        // 校验参数
        checkedChannelUpdateVO(updateVO);

        // 构建参数
        CardChannelInfo channelInfo = buildChannelUpdateVO(updateVO);

        // 更新渠道信息
        superManager.updateById(channelInfo);
        return updateVO;
    }

    @Override
    public Boolean deleteChannelInfo(Long id) {
        ArgumentAssert.notNull(id, "The ID cannot be null");
        CardChannelInfo channelInfo = superManager.getById(id);
        if (null == channelInfo) {
            throw BizException.wrap("The channel info does not exist");
        }
        // TODO 校验渠道信息是否被使用
        return superManager.removeById(id);
    }

    @Override
    public Boolean deleteChannelInfos(List<Long> ids) {
        ArgumentAssert.notEmpty(ids, "ids Cannot be empty");
        boolean allDeleted = ids.stream().distinct().allMatch(id -> {
            CardChannelInfo channelInfo = superManager.getById(id);
            if (null == channelInfo) {
                return false;
            }
            // TODO 校验渠道信息是否被使用
            return superManager.removeById(id);
        });
        return allDeleted;
    }

    @Override
    public CardChannelInfoResultVO getChannelInfoDetails(Long id) {

        if (id == null) {
            throw BizException.wrap("The ID cannot be null");
        }
        CardChannelInfo channelInfo = super.getById(id);
        if (channelInfo == null) {
            throw BizException.wrap("The channel info does not exist");
        }
        return BeanPlusUtil.toBeanIgnoreError(channelInfo, CardChannelInfoResultVO.class);
    }


    private void checkedChannelSaveVO(CardChannelInfoSaveVO saveVO) {
        ArgumentAssert.notBlank(saveVO.getChannelName(), "channelName Cannot be null");
        ArgumentAssert.notNull(saveVO.getStatus(), "status Cannot be null");
        ArgumentAssert.notBlank(saveVO.getAppKey(), "appKey Cannot be null");
        ArgumentAssert.notBlank(saveVO.getSecret(), "secret Cannot be null");
    }

    private CardChannelInfo buildChannelSaveVO(CardChannelInfoSaveVO saveVO) {
        saveVO.setCreatedOrgId(ContextUtil.getCurrentDeptId());
        return BeanPlusUtil.copyProperties(saveVO, CardChannelInfo.class);
    }

    private void checkedChannelUpdateVO(CardChannelInfoUpdateVO updateVO) {
        ArgumentAssert.notNull(updateVO.getId(), "id Cannot be null");

        CardChannelInfo channelInfo = superManager.getById(updateVO.getId());
        if (null == channelInfo) {
            throw BizException.wrap("channelInfo is not exist");
        }

        if (updateVO.getAppKey() != null) {
            ArgumentAssert.notBlank(updateVO.getAppKey(), "appKey Cannot be null");
        }
        if (updateVO.getSecret() != null) {
            ArgumentAssert.notBlank(updateVO.getSecret(), "secret Cannot be null");
        }
    }


    private CardChannelInfo buildChannelUpdateVO(CardChannelInfoUpdateVO updateVO) {
        CardChannelInfo channelInfo = BeanPlusUtil.copyProperties(updateVO, CardChannelInfo.class);
        channelInfo.setId(updateVO.getId());
        return channelInfo;
    }

}


