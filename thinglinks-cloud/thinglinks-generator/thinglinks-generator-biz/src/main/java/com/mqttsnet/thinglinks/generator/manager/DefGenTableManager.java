package com.mqttsnet.thinglinks.generator.manager;

import com.baomidou.mybatisplus.annotation.DbType;
import com.mqttsnet.basic.base.manager.SuperManager;
import com.mqttsnet.thinglinks.generator.entity.DefGenTable;

import javax.sql.DataSource;

/**
 * <p>
 * 通用业务接口
 * 代码生成
 * </p>
 *
 * @author mqttsnet
 * @date 2022-03-01
 */
public interface DefGenTableManager extends SuperManager<DefGenTable> {
    /**
     * 获取数据源
     *
     * @param dsId dsId
     * @return javax.sql.DataSource
     * @date 2022/3/26 11:25 AM
     * @create [2022/3/26 11:25 AM ] [mqttsnet] [初始创建]
     * @update [2022/3/26 11:25 AM ] [mqttsnet] [变更描述]
     */
    DataSource getDs(Long dsId);

    /**
     * 获取数据库类型
     *
     * @return com.baomidou.mybatisplus.annotation.DbType
     * @create [2022/8/17 9:02 PM ] [mqttsnet] [初始创建]
     */
    DbType getDbType();
}
