package com.mqttsnet.thinglinks.sop.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.sop.entity.SopApiInfo;
import com.mqttsnet.thinglinks.sop.vo.query.SopApiInfoPageQuery;
import com.mqttsnet.thinglinks.sop.vo.result.SopApiInfoResultVO;

/**
 * <p>
 * Mapper 接口
 * 接口信息表
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
@Repository
public interface SopApiInfoMapper extends SuperMapper<SopApiInfo> {

    IPage<SopApiInfoResultVO> groupPage(IPage<SopApiInfo> page,
                                        @Param(Constants.WRAPPER) Wrapper<SopApiInfo> wrapper,
                                        @Param("query") SopApiInfoPageQuery query);
}


