package com.mqttsnet.thinglinks.nacos.facade.impl;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.nacos.api.NacosApi;
import com.mqttsnet.thinglinks.nacos.facade.NacosFacade;
import com.mqttsnet.thinglinks.nacos.vo.result.NacosInstanceResultVO;
import com.mqttsnet.thinglinks.nacos.vo.result.NacosListViewResultVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author tangyh
 * @since 2024/12/20 09:15
 */
@Service
public class NacosFacadeImpl implements NacosFacade {
    @Lazy
    @Autowired
    private NacosApi nacosApi;

    @Override
    public R<List<NacosInstanceResultVO>> getAllInstances(String serviceName, String groupName) {
        return nacosApi.getAllInstances(serviceName, groupName);
    }

    @Override
    public R<NacosListViewResultVO<String>> getServicesOfServer(int pageNo, int pageSize) {
        return nacosApi.getServicesOfServer(pageNo, pageSize);
    }
}
