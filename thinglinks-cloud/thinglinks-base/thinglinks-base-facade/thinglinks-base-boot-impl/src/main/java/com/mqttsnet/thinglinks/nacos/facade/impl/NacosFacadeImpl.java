package com.mqttsnet.thinglinks.nacos.facade.impl;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.nacos.NacosService;
import com.mqttsnet.thinglinks.nacos.facade.NacosFacade;
import com.mqttsnet.thinglinks.nacos.vo.result.NacosInstanceResultVO;
import com.mqttsnet.thinglinks.nacos.vo.result.NacosListViewResultVO;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * @author tangyh
 * @since 2024/12/20 09:15
 */
@Service
@RequiredArgsConstructor
public class NacosFacadeImpl implements NacosFacade {
    private final NacosService nacosService;

    @Override
    @SneakyThrows
    public R<List<NacosInstanceResultVO>> getAllInstances(String serviceName, String groupName) {
        return R.success(nacosService.getAllInstances(serviceName, groupName));
    }

    @Override
    @SneakyThrows
    public R<NacosListViewResultVO<String>> getServicesOfServer(int pageNo, int pageSize) {
        return R.success(nacosService.getServicesOfServer(pageNo, pageSize));
    }
}
