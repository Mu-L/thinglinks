package com.mqttsnet.thinglinks.common.cache.common;


import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

/**
 * 参数 KEY
 * {tenant}:LOGIN_LOG_TEN_DAY -> long
 * <p>
 * #c_login_log
 *
 * @author mqttsnet
 * @date 2020/9/20 6:45 下午
 */
public class LoginLogTenDayCacheKeyBuilder implements CacheKeyBuilder {
    @Override
    public String getTable() {
        return CacheKeyTable.LOGIN_LOG_TEN_DAY;
    }

}
