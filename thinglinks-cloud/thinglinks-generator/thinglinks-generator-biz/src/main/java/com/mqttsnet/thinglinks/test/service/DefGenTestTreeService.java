package com.mqttsnet.thinglinks.test.service;

import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.test.entity.DefGenTestTree;
import com.mqttsnet.thinglinks.test.vo.query.DefGenTestTreePageQuery;

import java.util.List;


/**
 * <p>
 * 业务接口
 * 测试树结构
 * </p>
 *
 * @author mqttsnet
 * @date 2022-04-20 00:28:30
 * @create [2022-04-20 00:28:30] [mqttsnet] 
 */
public interface DefGenTestTreeService extends SuperService<Long, DefGenTestTree> {

    /**
     * 查询树结构
     *
     * @param query 参数
     * @return 树
     */
    List<DefGenTestTree> findTree(DefGenTestTreePageQuery query);
}


