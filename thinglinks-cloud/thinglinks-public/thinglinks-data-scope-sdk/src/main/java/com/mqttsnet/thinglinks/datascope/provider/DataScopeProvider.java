package com.mqttsnet.thinglinks.datascope.provider;

import com.mqttsnet.thinglinks.datascope.model.DataFieldProperty;

import java.util.List;

/**
 * @author mqttsnet
 * @date 2022/1/9 23:28
 */
public interface DataScopeProvider {
    /**
     * 查询数据字段
     *
     * @param fsp fsp
     * @return java.util.List<com.mqttsnet.thinglinks.datascope.model.DataFieldProperty>
     * @author mqttsnet
     * @date 2022/10/28 4:41 PM
     * @create [2022/10/28 4:41 PM ] [mqttsnet] [初始创建]
     */
    List<DataFieldProperty> findDataFieldProperty(List<DataFieldProperty> fsp);
}
