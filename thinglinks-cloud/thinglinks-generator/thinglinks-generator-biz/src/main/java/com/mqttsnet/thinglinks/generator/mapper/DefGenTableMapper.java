package com.mqttsnet.thinglinks.generator.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.generator.entity.DefGenTable;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 代码生成
 * </p>
 *
 * @author mqttsnet
 * @date 2022-03-01
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface DefGenTableMapper extends SuperMapper<DefGenTable> {

}
