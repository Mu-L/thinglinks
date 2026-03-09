package com.mqttsnet.thinglinks.base.manager.user;

import com.mqttsnet.basic.base.manager.SuperCacheManager;
import com.mqttsnet.basic.interfaces.echo.LoadService;
import com.mqttsnet.thinglinks.base.entity.user.BaseOrg;

/**
 * <p>
 * 通用业务接口
 * 组织
 * </p>
 *
 * @author mqttsnet
 * @date 2021-10-18
 */
public interface BaseOrgManager extends SuperCacheManager<BaseOrg>, LoadService {
}
