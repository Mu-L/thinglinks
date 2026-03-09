package com.mqttsnet.thinglinks.generator.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.base.service.SuperService;
import com.mqttsnet.thinglinks.generator.entity.DefGenTableColumn;
import com.mqttsnet.thinglinks.generator.vo.query.DefGenTableColumnPageQuery;
import com.mqttsnet.thinglinks.generator.vo.result.DefGenTableColumnResultVO;

/**
 * <p>
 * 业务接口
 * 代码生成字段
 * </p>
 *
 * @author mqttsnet
 * @date 2022-03-01
 */
public interface DefGenTableColumnService extends SuperService<Long, DefGenTableColumn> {

    /**
     * 分页查询指定表的字段
     *
     * @param params params
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.mqttsnet.thinglinks.generator.vo.result.DefGenTableColumnResultVO>
     * @author mqttsnet
     * @date 2022/10/28 4:54 PM
     * @create [2022/10/28 4:54 PM ] [mqttsnet] [初始创建]
     */
    IPage<DefGenTableColumnResultVO> pageColumn(PageParams<DefGenTableColumnPageQuery> params);

    /**
     * 同步字段结构和注释
     *
     * @param id      id
     * @param tableId
     * @return java.lang.Boolean
     * @author mqttsnet
     * @date 2022/3/26 11:19 AM
     * @create [2022/3/26 11:19 AM ] [mqttsnet] [初始创建]
     */
    Boolean syncField(Long tableId, Long id);
}
