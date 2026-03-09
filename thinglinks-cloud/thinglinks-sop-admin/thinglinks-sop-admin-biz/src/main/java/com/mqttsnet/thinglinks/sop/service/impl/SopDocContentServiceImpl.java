package com.mqttsnet.thinglinks.sop.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.mqttsnet.basic.base.service.impl.SuperServiceImpl;
import com.mqttsnet.thinglinks.sop.entity.SopDocContent;
import com.mqttsnet.thinglinks.sop.manager.SopDocContentManager;
import com.mqttsnet.thinglinks.sop.service.SopDocContentService;

/**
 * <p>
 * 业务实现类
 * 文档内容
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
public class SopDocContentServiceImpl extends SuperServiceImpl<SopDocContentManager, Long, SopDocContent> implements SopDocContentService {


}


