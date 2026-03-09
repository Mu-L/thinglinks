package com.mqttsnet.thinglinks.common.cache.common;


import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.thinglinks.common.cache.CacheKeyTable;

import java.time.Duration;
import java.time.LocalDate;

/**
 * 参数 KEY
 * {tenant}:TODAY_LOGIN_PV -> long
 * <p>
 * #c_login_log
 *
 * @author mqttsnet
 * @date 2020/9/20 6:45 下午
 */
public class TodayLoginPvCacheKeyBuilder implements CacheKeyBuilder {
    public static CacheKey build(LocalDate now) {
        return new TodayLoginPvCacheKeyBuilder().key(now.toString());
    }

    @Override
    public String getTable() {
        return CacheKeyTable.TODAY_LOGIN_PV;
    }

    @Override
    public Duration getExpire() {
        return Duration.ofDays(2L);
    }
}
