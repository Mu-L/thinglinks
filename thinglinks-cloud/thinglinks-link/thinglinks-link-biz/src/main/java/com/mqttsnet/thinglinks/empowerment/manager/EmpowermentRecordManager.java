package com.mqttsnet.thinglinks.empowerment.manager;

import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.empowerment.entity.EmpowermentRecord;
import com.mqttsnet.thinglinks.empowerment.vo.query.EmpowermentRecordPageQuery;

import java.util.List;

/**
 * <p>
 * 通用业务接口
 * 赋能记录表
 * </p>
 *
 * @author mqttsnet
 * @date 2023-09-15 17:20:27
 * @create [2023-09-15 17:20:27] [mqttsnet]
 */
public interface EmpowermentRecordManager extends SuperManager<EmpowermentRecord> {

    /**
     * 获取赋能记录列表
     *
     * @param pageQuery 参数
     * @return {@link List<EmpowermentRecord>} 赋能记录集合
     */
    List<EmpowermentRecord> getEmpowermentRecordList(EmpowermentRecordPageQuery pageQuery);
}


