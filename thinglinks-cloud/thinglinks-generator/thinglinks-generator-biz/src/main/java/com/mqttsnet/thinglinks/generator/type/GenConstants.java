package com.mqttsnet.thinglinks.generator.type;

import java.util.List;

import com.mqttsnet.basic.base.entity.TreeEntity;

/**
 * @author mqttsnet
 * @date 2022/3/13 21:46
 */
public class GenConstants {
    /**
     * 页面不需要编辑字段
     */
    public static final List<String> NOT_EDIT = List.of(TreeEntity.CREATED_BY_FIELD, TreeEntity.CREATED_TIME_FIELD,
            TreeEntity.UPDATED_BY_FIELD, TreeEntity.UPDATED_TIME_FIELD, TreeEntity.CREATED_ORG_ID_FIELD);

    /**
     * 页面不需要显示的列表字段
     */
    public static final List<String> NOT_LIST = List.of(
            TreeEntity.CREATED_BY_FIELD, TreeEntity.CREATED_TIME_FIELD,
            TreeEntity.UPDATED_BY_FIELD, TreeEntity.UPDATED_TIME_FIELD, TreeEntity.CREATED_ORG_ID_FIELD
    );

    /**
     * 页面不需要查询字段
     */
    public static final List<String> NOT_QUERY = List.of(TreeEntity.ID_FIELD, TreeEntity.CREATED_BY_FIELD, TreeEntity.CREATED_TIME_FIELD,
            TreeEntity.UPDATED_BY_FIELD, TreeEntity.UPDATED_TIME_FIELD, TreeEntity.CREATED_ORG_ID_FIELD);

}
