package com.mqttsnet.thinglinks.userinfo.service;

import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.model.entity.system.SysUser;
import com.mqttsnet.thinglinks.model.vo.result.UserQuery;

/**
 * @author zuihou
 * @date 2020年02月24日10:41:49
 */
public interface UserResolverService {
    /**
     * 根据id查询用户
     *
     * @param userQuery 查询条件
     * @return 用户信息
     */
    R<SysUser> getById(UserQuery userQuery);
}
