package com.mqttsnet.thinglinks.system.mapper.system;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.system.entity.system.DefDict;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 字典
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-04
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface DefDictMapper extends SuperMapper<DefDict> {

}
