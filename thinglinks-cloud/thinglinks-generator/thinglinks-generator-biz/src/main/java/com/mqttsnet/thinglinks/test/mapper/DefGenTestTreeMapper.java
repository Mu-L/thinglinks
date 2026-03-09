package com.mqttsnet.thinglinks.test.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.test.entity.DefGenTestTree;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 测试树结构
 * </p>
 *
 * @author mqttsnet
 * @date 2022-04-20 00:28:30
 * @create [2022-04-20 00:28:30] [mqttsnet] 
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface DefGenTestTreeMapper extends SuperMapper<DefGenTestTree> {

}


