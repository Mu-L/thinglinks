/*
 * Copyright 2002-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mqttsnet.thinglinks.oauth.granter;


import com.mqttsnet.basic.base.R;
import com.mqttsnet.thinglinks.oauth.vo.param.LoginParamVO;
import com.mqttsnet.thinglinks.oauth.vo.result.LoginResultVO;

/**
 * 授予token接口
 *
 * @author mqttsnet
 * @date 2020年03月31日10:21:21
 */
public interface TokenGranter {

    /**
     * 获取用户信息
     *
     * @param loginParam 授权参数
     * @return LoginDTO
     */
    R<LoginResultVO> login(LoginParamVO loginParam);

    /**
     * 退出
     *
     * @return
     */
    R<Boolean> logout();

    /**
     * 切换企业和机构
     *
     * @param tenantId  tenantId
     * @param orgId 单位或部门id
     * @return com.mqttsnet.thinglinks.oauth.vo.result.LoginResultVO
     * @author mqttsnet
     * @date 2022/9/16 1:14 PM
     * @create [2022/9/16 1:14 PM ] [mqttsnet] [初始创建]
     */
    LoginResultVO switchTenantAndOrg(Long tenantId, Long orgId);

}
