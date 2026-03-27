package com.mqttsnet.thinglinks.config.groovy.loader;

import cn.hutool.core.util.StrUtil;
import com.mqttsnet.basic.exception.BizException;
import com.mqttsnet.basic.groovy.compiler.DynamicCodeCompiler;
import com.mqttsnet.basic.groovy.entity.ScriptEntry;
import com.mqttsnet.basic.groovy.entity.ScriptQuery;
import com.mqttsnet.basic.groovy.loader.ScriptLoader;
import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.utils.SnowflakeIdUtil;
import com.mqttsnet.thinglinks.cache.helper.RuleCacheDataHelper;
import com.mqttsnet.thinglinks.record.script.ScriptIdentifier;
import com.mqttsnet.thinglinks.vo.query.script.RuleGroovyScriptQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 从Redis里加载脚本loader
 *
 * @author mqttsnet 2022/09/25 13:09
 */
@Slf4j
public class RedisScriptLoader implements ScriptLoader {


    private final RuleCacheDataHelper ruleCacheDataHelper;

    private final DynamicCodeCompiler dynamicCodeCompiler;

    public RedisScriptLoader(RuleCacheDataHelper ruleCacheDataHelper,
                             DynamicCodeCompiler dynamicCodeCompiler) {
        this.ruleCacheDataHelper = ruleCacheDataHelper;
        this.dynamicCodeCompiler = dynamicCodeCompiler;
    }


    /**
     * 从Redis中加载脚本
     *
     * @param query 查询对象
     * @return {@link ScriptEntry} 脚本对象
     * @throws Exception
     */
    @Override
    public ScriptEntry load(ScriptQuery query) throws Exception {
        if (StrUtil.isBlank(query.getUniqueKey())) {
            throw BizException.wrap("脚本唯一键不能为空");
        }
        RuleGroovyScriptQuery ruleGroovyScriptQuery = new RuleGroovyScriptQuery(query.getUniqueKey());
        CacheKey cacheKey = ScriptIdentifier.buildCacheKey(ruleGroovyScriptQuery);
        // 从Redis中根据key查找脚本
        Object scriptContent = ruleCacheDataHelper.getScriptContent(cacheKey);
        if (Objects.isNull(scriptContent)) {
            throw BizException.wrap("脚本不存在");
        }
        String script = scriptContent.toString();
        // 获取脚本指纹
        String fingerprint = DigestUtils.md5DigestAsHex(script.getBytes());
        // 创建脚本对象
        ScriptEntry scriptEntry = new ScriptEntry(cacheKey.getKey(), script, fingerprint, System.currentTimeMillis());
        // 动态加载脚本为Class
        Class<?> aClass = dynamicCodeCompiler.compile(scriptEntry);
        scriptEntry.setClazz(aClass);
        return scriptEntry;
    }

    /**
     * 从Redis中加载脚本
     *
     * @param cacheKey 缓存Key
     * @return {@link ScriptEntry} 脚本对象
     * @throws Exception
     */
    @Override
    public ScriptEntry load(@NonNull CacheKey cacheKey) throws Exception {
        if (StrUtil.isBlank(cacheKey.getKey())) {
            throw BizException.wrap("脚本唯一键不能为空");
        }
        // 从Redis中根据key查找脚本
        Object scriptContent = ruleCacheDataHelper.getScriptContent(cacheKey);
        if (Objects.isNull(scriptContent)) {
            throw BizException.wrap("脚本不存在");
        }
        String script = scriptContent.toString();
        // 获取脚本指纹
        String fingerprint = DigestUtils.md5DigestAsHex(script.getBytes());
        // 创建脚本对象
        ScriptEntry scriptEntry = new ScriptEntry(cacheKey.getKey(), script, fingerprint, System.currentTimeMillis());
        // 动态加载脚本为Class
        Class<?> aClass = dynamicCodeCompiler.compile(scriptEntry);
        scriptEntry.setClazz(aClass);
        return scriptEntry;
    }


    /**
     * 从Redis中加载所有脚本
     * // TODO 待实现
     *
     * @return List<ScriptEntry> 脚本列表
     */
    @Override
    public List<ScriptEntry> load() {
        List<ScriptEntry> resultList = new ArrayList<>();
        /*String key = groovyRedisLoaderProperties.getNamespace();

        // 获取到所有脚本的key
        Set<Object> hashKeys = redisTemplate.opsForHash().keys(key);
        ruleCacheDataHelper.getScriptContentSet()
        // 没有脚本
        if (CollectionUtils.isEmpty(hashKeys)) {
            logger.error("can not found hashKeys by key [{}].", key);
            return resultList;
        }

        // 获取所有脚本
        for (Object hashKey : hashKeys) {
            // groovy脚本内容
            String script = (String) redisTemplate.opsForHash().get(key, hashKey);
            if (!StringUtils.hasText(script)) {
                logger.error("note can not found script content by key [{}] and hashKey [{}]", key, hashKey);
                continue;
            }
            // 获取脚本指纹
            String fingerprint = DigestUtils.md5DigestAsHex(script.getBytes());
            // 创建脚本对象
            ScriptEntry scriptEntry = new ScriptEntry(hashKey.toString(), script, fingerprint, System.currentTimeMillis());
            resultList.add(scriptEntry);
        }*/

        return resultList;
    }


    /**
     * 编译脚本内容
     *
     * @param scriptContent 不可为空的脚本内容
     * @return 包含编译后类的脚本实体
     * @throws BizException 当内容为空或编译失败时抛出
     */
    public ScriptEntry compileScript(@NonNull String scriptContent) throws Exception {
        if (StrUtil.isBlank(scriptContent)) {
            throw BizException.wrap("脚本内容不能为空");
        }

        // 生成内容指纹
        String fingerprint = DigestUtils.md5DigestAsHex(scriptContent.getBytes());
        // 创建脚本实体
        ScriptEntry entry = new ScriptEntry(SnowflakeIdUtil.nextId(), scriptContent, fingerprint, System.currentTimeMillis());

        // 动态编译
        Class<?> clazz = dynamicCodeCompiler.compile(entry);
        entry.setClazz(clazz);
        return entry;
    }
}
