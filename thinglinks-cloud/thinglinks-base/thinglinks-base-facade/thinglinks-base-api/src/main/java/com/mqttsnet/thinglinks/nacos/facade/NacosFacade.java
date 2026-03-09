package com.mqttsnet.thinglinks.nacos.facade;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.nacos.vo.result.NacosInstanceResultVO;
import com.mqttsnet.thinglinks.nacos.vo.result.NacosListViewResultVO;

import java.util.List;

/**
 *
 * @author tangyh
 * @since 2024/12/20 09:15
 */
public interface NacosFacade {
    R<List<NacosInstanceResultVO>> getAllInstances(String serviceName, String groupName);

    R<NacosListViewResultVO<String>> getServicesOfServer(int pageNo, int pageSize);
}
