package com.mqttsnet.thinglinks.sop.manager;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.basic.utils.SpringUtils;
import com.mqttsnet.thinglinks.sop.entity.SopApiInfo;
import com.mqttsnet.thinglinks.sop.event.ChangeApiInfoEvent;
import com.mqttsnet.thinglinks.sop.vo.query.SopApiInfoPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopApiInfoResultVO;

import java.util.Collection;
import java.util.Collections;

/**
 * <p>
 * 通用业务接口
 * 接口信息表
 * </p>
 *
 * @author zuihou
 * @since [2025-05-07 10:52:47] [zuihou] [代码生成器生成]
 */
public interface SopApiInfoManager extends SuperManager<SopApiInfo> {

    default void sendChangeEvent(Long id) {
        SpringUtils.publishEvent(new ChangeApiInfoEvent(Collections.singletonList(id)));
    }

    default void sendChangeEvent(Collection<Long> idList) {
        SpringUtils.publishEvent(new ChangeApiInfoEvent(idList));
    }

    IPage<SopApiInfoResultVO> groupPage(IPage<SopApiInfo> page, SopApiInfoPageQuery query);
}


