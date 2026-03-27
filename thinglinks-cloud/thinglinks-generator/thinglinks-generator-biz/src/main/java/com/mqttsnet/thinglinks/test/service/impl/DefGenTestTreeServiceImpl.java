package com.mqttsnet.thinglinks.test.service.impl;

import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.utils.TreeUtil;
import com.mqttsnet.thinglinks.test.entity.DefGenTestTree;
import com.mqttsnet.thinglinks.test.manager.DefGenTestTreeManager;
import com.mqttsnet.thinglinks.test.service.DefGenTestTreeService;
import com.mqttsnet.thinglinks.test.vo.query.DefGenTestTreePageQuery;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>
 * 业务实现类
 * 测试树结构
 * </p>
 *
 * @author mqttsnet
 * @date 2022-04-20 00:28:30
 * @create [2022-04-20 00:28:30] [mqttsnet] 
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DefGenTestTreeServiceImpl extends SuperServiceImpl<DefGenTestTreeManager, Long, DefGenTestTree> implements DefGenTestTreeService {

    @Override
    public List<DefGenTestTree> findTree(DefGenTestTreePageQuery query) {
        List<DefGenTestTree> list = superManager.list(Wraps.<DefGenTestTree>lbQ().orderByAsc(DefGenTestTree::getSortValue));
        return TreeUtil.buildTree(list);
    }

}


