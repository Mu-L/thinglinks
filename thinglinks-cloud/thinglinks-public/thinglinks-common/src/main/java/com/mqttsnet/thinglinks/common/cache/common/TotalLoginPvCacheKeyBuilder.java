package com.mqttsnet.thinglinks.common.cache.common;


import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

/**
 * 参数 KEY
 * {tenant}:TOTAL_LOGIN_PV -> long
 * <p>
 * #c_login_log
 *
 * @author mqttsnet
 * @date 2020/9/20 6:45 下午
 */
public class TotalLoginPvCacheKeyBuilder implements CacheKeyBuilder {
    public static CacheKey build() {
        return new TotalLoginPvCacheKeyBuilder().key();
    }

    @Override
    public String getTable() {
        return CacheKeyTable.TOTAL_LOGIN_PV;
    }
}
