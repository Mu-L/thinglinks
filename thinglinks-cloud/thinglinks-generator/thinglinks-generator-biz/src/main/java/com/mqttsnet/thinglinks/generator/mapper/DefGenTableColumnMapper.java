package com.mqttsnet.thinglinks.generator.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.generator.entity.DefGenTableColumn;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 代码生成字段
 * </p>
 *
 * @author mqttsnet
 * @date 2022-03-01
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface DefGenTableColumnMapper extends SuperMapper<DefGenTableColumn> {

}
