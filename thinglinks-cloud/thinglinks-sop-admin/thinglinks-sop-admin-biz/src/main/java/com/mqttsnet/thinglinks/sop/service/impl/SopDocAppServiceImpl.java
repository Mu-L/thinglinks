package com.mqttsnet.thinglinks.sop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.basic.database.mybatis.conditions.Wraps;
import com.mqttsnet.thinglinks.sop.entity.SopDocApp;
import com.mqttsnet.thinglinks.sop.manager.SopDocAppManager;
import com.mqttsnet.thinglinks.sop.manager.TornaClient;
import com.mqttsnet.thinglinks.sop.service.SopDocAppService;
import com.mqttsnet.thinglinks.sop.service.SopDocInfoService;
import com.mqttsnet.thinglinks.sop.vo.result.TornaModuleDTO;

/**
 * <p>
 * 业务实现类
 * 文档应用
 * </p>
 *
 * @author zuihou
 * @since 2025-05-07 10:52:47
 *
 */
@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SopDocAppServiceImpl extends SuperServiceImpl<SopDocAppManager, Long, SopDocApp> implements SopDocAppService {
    private final TornaClient tornaClient;
    private final SopDocInfoService sopDocInfoService;

    @Override
    public <SaveVO> SopDocApp save(SaveVO saveVO) {
        SopDocApp sopDocApp = super.saveBefore(saveVO);

        TornaModuleDTO tornaModuleDTO = tornaClient.execute("module.get", null, sopDocApp.getToken(), TornaModuleDTO.class);
        SopDocApp docApp = superManager.getOne(Wraps.<SopDocApp>lbQ().eq(SopDocApp::getToken, sopDocApp.getToken()));
        if (docApp == null) {
            docApp = new SopDocApp();
            docApp.setAppName(tornaModuleDTO.getName());
            docApp.setToken(sopDocApp.getToken());
            superManager.save(docApp);
        } else {
            docApp.setAppName(tornaModuleDTO.getName());
            superManager.updateById(docApp);
            sopDocApp.setId(docApp.getId());
        }
        // 同步文档
        sopDocInfoService.syncDocInfo(docApp, null);
        return sopDocApp;
    }


}


