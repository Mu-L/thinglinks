package com.mqttsnet.thinglinks.empowerment.manager.impl;

import cn.hutool.core.text.CharSequenceUtil;
import com.mqttsnet.basic.base.manager.impl.SuperManagerImpl;
import com.mqttsnet.basic.database.mybatis.conditions.query.QueryWrap;
import com.mqttsnet.thinglinks.empowerment.entity.EmpowermentRecord;
import com.mqttsnet.thinglinks.empowerment.manager.EmpowermentRecordManager;
import com.mqttsnet.thinglinks.empowerment.mapper.EmpowermentRecordMapper;
import com.mqttsnet.thinglinks.empowerment.vo.query.EmpowermentRecordPageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 通用业务实现类
 * 赋能记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-15 17:20:27
 * @create [2023-09-15 17:20:27] [mqttsnet]
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class EmpowermentRecordManagerImpl extends SuperManagerImpl<EmpowermentRecordMapper, EmpowermentRecord> implements EmpowermentRecordManager {

    private final EmpowermentRecordMapper empowermentRecordMapper;

    /**
     * 获取赋能记录列表
     *
     * @param pageQuery 参数
     * @return {@link List <EmpowermentRecord>} 赋能记录集合
     */
    @Override
    public List<EmpowermentRecord> getEmpowermentRecordList(EmpowermentRecordPageQuery pageQuery) {
        QueryWrap<EmpowermentRecord> queryWrap = new QueryWrap<>();

        // 根据赋能标识查询
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(pageQuery.getEmpowermentIdentification()), EmpowermentRecord::getEmpowermentIdentification,
                pageQuery.getEmpowermentIdentification());

        // 根据赋能类型查询
        queryWrap.lambda().eq(null != pageQuery.getEmpowermentType(), EmpowermentRecord::getEmpowermentType, pageQuery.getEmpowermentType());

        // 根据版本查询
        queryWrap.lambda().eq(CharSequenceUtil.isNotBlank(pageQuery.getVersion()), EmpowermentRecord::getVersion, pageQuery.getVersion());

        // 根据开始时间和结束时间范围查询
        if (null != pageQuery.getStartTime() && null != pageQuery.getEndTime()) {
            queryWrap.lambda().between(EmpowermentRecord::getStartTime, pageQuery.getStartTime(), pageQuery.getEndTime());
        } else {
            if (null != pageQuery.getStartTime()) {
                queryWrap.lambda().le(EmpowermentRecord::getStartTime, pageQuery.getStartTime());
            }
            if (null != pageQuery.getEndTime()) {
                queryWrap.lambda().ge(EmpowermentRecord::getEndTime, pageQuery.getEndTime());
            }
        }

        // 根据创建时间降序，若创建时间相同则根据ID降序
        queryWrap.lambda().orderByDesc(EmpowermentRecord::getCreatedTime, EmpowermentRecord::getId);

        return empowermentRecordMapper.selectList(queryWrap);
    }

}


