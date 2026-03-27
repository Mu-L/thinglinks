package com.mqttsnet.thinglinks.sop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.model.vo.save.StatusUpdateVO;
import com.mqttsnet.thinglinks.sop.entity.SopApiInfo;
import com.mqttsnet.thinglinks.sop.vo.query.SopApiInfoPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopApiInfoResultVO;


/**
 * <p>
 * 业务接口
 * 接口信息表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
public interface SopApiInfoService extends SuperService<Long, SopApiInfo> {
    Boolean updateStatus(StatusUpdateVO statusUpdate);

    IPage<SopApiInfoResultVO> groupPage(PageParams<SopApiInfoPageQuery> params);
}


