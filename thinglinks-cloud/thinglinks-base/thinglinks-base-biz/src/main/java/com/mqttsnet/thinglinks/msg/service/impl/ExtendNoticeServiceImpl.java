package com.mqttsnet.thinglinks.msg.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.msg.entity.ExtendNotice;
import com.mqttsnet.thinglinks.msg.manager.ExtendNoticeManager;
import com.mqttsnet.thinglinks.msg.service.ExtendNoticeService;
import com.mqttsnet.thinglinks.msg.vo.query.ExtendNoticePageQuery;
import com.mqttsnet.thinglinks.msg.vo.result.ExtendNoticeResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * 业务实现类
 * 通知表
 * </p>
 *
 * @author mqttsnet
 * @date 2022-07-04 15:51:37
 * @create [2022-07-04 15:51:37] [mqttsnet] 
 */
@DS(DsConstant.BASE_TENANT)
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class ExtendNoticeServiceImpl extends SuperServiceImpl<ExtendNoticeManager, Long, ExtendNotice> implements ExtendNoticeService {
    @Override
    public IPage<ExtendNoticeResultVO> page(IPage<ExtendNoticeResultVO> page, PageParams<ExtendNoticePageQuery> params) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean mark(List<Long> noticeIds, Long employeeId) {
        if (CollectionUtil.isEmpty(noticeIds) || employeeId == null) {
            return true;
        }

        return superManager.update(
                Wraps.<ExtendNotice>lbU().eq(ExtendNotice::getRecipientId, employeeId)
                        .in(ExtendNotice::getId, noticeIds)
                        .set(ExtendNotice::getIsRead, true)
                        .set(ExtendNotice::getReadTime, LocalDateTime.now())
        );
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteMyNotice(List<Long> noticeIds) {
        ArgumentAssert.notEmpty(noticeIds, "请选择需要删除的消息");
        return superManager.removeByIds(noticeIds);
    }
}


