package com.mqttsnet.thinglinks.generator.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.db.meta.Column;
import cn.hutool.db.meta.MetaUtil;
import cn.hutool.db.meta.Table;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.mqttsnet.basic.base.request.PageParams;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.basic.utils.ArgumentAssert;
import com.mqttsnet.basic.utils.BeanPlusUtil;
import com.mqttsnet.thinglinks.common.constant.DsConstant;
import com.mqttsnet.thinglinks.generator.config.GeneratorConfig;
import com.mqttsnet.thinglinks.generator.entity.DefGenTable;
import com.mqttsnet.thinglinks.generator.entity.DefGenTableColumn;
import com.mqttsnet.thinglinks.generator.manager.DefGenTableColumnManager;
import com.mqttsnet.thinglinks.generator.manager.DefGenTableManager;
import com.mqttsnet.thinglinks.generator.service.DefGenTableColumnService;
import com.mqttsnet.thinglinks.generator.utils.GenUtils;
import com.mqttsnet.thinglinks.generator.vo.query.DefGenTableColumnPageQuery;
import com.mqttsnet.thinglinks.generator.vo.result.DefGenTableColumnResultVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

/**
 * <p>
 * 业务实现类
 * 代码生成字段
 * </p>
 *
 * @author mqttsnet
 * @date 2022-03-01
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@DS(DsConstant.DEFAULTS)
public class DefGenTableColumnServiceImpl extends SuperServiceImpl<DefGenTableColumnManager, Long, DefGenTableColumn> implements DefGenTableColumnService {
    private final DefGenTableManager defGenTableManager;
    private final GeneratorConfig generatorConfig;


    @Override
    public IPage<DefGenTableColumnResultVO> pageColumn(PageParams<DefGenTableColumnPageQuery> params) {
        IPage<DefGenTableColumn> page = params.buildPage();
        DefGenTableColumnPageQuery model = params.getModel();
        DefGenTableColumn column = BeanUtil.toBean(model, DefGenTableColumn.class);
        superManager.page(page, Wraps.lbQ(column));
        return BeanPlusUtil.toBeanPage(page, DefGenTableColumnResultVO.class);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean syncField(Long tableId, Long id) {
        DefGenTable genTable = defGenTableManager.getById(tableId);
        DefGenTableColumn genTableColumn = getById(id);
        ArgumentAssert.notNull(genTable, "请先选择需要同步的表");
        ArgumentAssert.notNull(genTableColumn, "请先选择需要同步的字段");
        DataSource ds = defGenTableManager.getDs(genTable.getDsId());

        Table tableMeta = MetaUtil.getTableMeta(ds, genTable.getName());
        for (Column column : tableMeta.getColumns()) {
            if (genTableColumn.getName().equals(column.getName())) {
                DefGenTableColumn tableColumn = GenUtils.initColumnField(generatorConfig, defGenTableManager.getDbType(), genTable, column);
                if (tableColumn != null) {
                    tableColumn.setId(id);
                    tableColumn.setTableId(tableId);
                    superManager.updateById(tableColumn);
                    break;
                }
            }
        }
        return true;
    }
}
