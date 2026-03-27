package com.mqttsnet.thinglinks.cache.helper;

import com.mqttsnet.basic.cache.redis2.CacheResult;
import com.mqttsnet.basic.cache.repository.CachePlusOps;
import com.mqttsnet.basic.model.cache.CacheKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * ============================================================================
 * Description:
 * Rule 规则类 数据缓存操作助手
 * ============================================================================
 *
 * @author Sun Shihuan
 * @version 1.0.0
 * -----------------------------------------------------------------------------
 * Revision History:
 * Date         Author          Version     Description
 * --------      --------     -------   --------------------
 * 2025/3/25      Sun Shihuan        1.0        Initial creation
 * -----------------------------------------------------------------------------
 * @email
 * @date 2025/3/25 11:08
 */
@Component
@Slf4j
public class RuleCacheDataHelper {

    private final CachePlusOps cachePlusOps;


    public RuleCacheDataHelper(CachePlusOps cachePlusOps) {
        this.cachePlusOps = cachePlusOps;
    }


    /**
     * 获取脚本内容
     *
     * @param cacheKey 缓存键
     * @return {@link Object} 脚本内容
     */
    public Object getScriptContent(CacheKey cacheKey) {
        CacheResult<Object> objectCacheResult = cachePlusOps.get(cacheKey);
        return objectCacheResult.getRawValue();
    }

    /**
     * 设置脚本内容
     *
     * @param cacheKey 缓存键
     * @param content  脚本内容
     */
    public void setScriptContent(CacheKey cacheKey, String content) {
        cachePlusOps.del(cacheKey);
        cachePlusOps.set(cacheKey, content);
    }

    /**
     * 删除脚本内容
     *
     * @param cacheKey 缓存键
     * @return 删除的脚本内容数量
     */
    public Long delScriptContent(CacheKey cacheKey) {
        return cachePlusOps.del(cacheKey);
    }


}
