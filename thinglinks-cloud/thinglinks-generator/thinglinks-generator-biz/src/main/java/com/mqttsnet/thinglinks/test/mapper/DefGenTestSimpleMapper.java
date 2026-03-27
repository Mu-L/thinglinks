package com.mqttsnet.thinglinks.test.mapper;

import com.baomidou.mybatisplus.annotation.InterceptorIgnore;
import com.mqttsnet.basic.base.mapper.SuperMapper;
import com.mqttsnet.thinglinks.test.entity.DefGenTestSimple;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
 * 测试单表
 * </p>
 *
 * @author mqttsnet
 * @date 2022-04-15 15:36:45
 * @create [2022-04-15 15:36:45] [mqttsnet]
 */
@Repository
@InterceptorIgnore(tenantLine = "true", dynamicTableName = "true")
public interface DefGenTestSimpleMapper extends SuperMapper<DefGenTestSimple> {

}


