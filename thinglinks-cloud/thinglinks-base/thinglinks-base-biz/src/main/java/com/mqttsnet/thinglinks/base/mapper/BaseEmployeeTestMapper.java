package com.mqttsnet.thinglinks.base.mapper;

import com.mqttsnet.basic.annotation.database.TenantLine;
import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.base.entity.user.BaseEmployee;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * 仅仅测试使用
 *
 * @author mqttsnet
 * @date 2021-10-18
 */
@Repository
@TenantLine
public interface BaseEmployeeTestMapper extends SuperMapper<BaseEmployee> {
    /**
     * get
     *
     * @param id id
     * @return com.mqttsnet.thinglinks.base.entity.user.BaseEmployee
     * @author mqttsnet
     * @date 2022/10/28 4:38 PM
     */
    @TenantLine(false)
    @Select("select * from base_employee where id = #{id}")
    BaseEmployee get(Long id);

}
