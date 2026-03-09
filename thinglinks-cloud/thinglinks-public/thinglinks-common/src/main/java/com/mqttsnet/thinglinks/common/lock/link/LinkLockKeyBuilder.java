package com.mqttsnet.thinglinks.common.lock.link;

import java.time.Duration;

import com.mqttsnet.basic.model.cache.CacheKey;
import com.mqttsnet.basic.model.cache.CacheKeyBuilder;
import com.mqttsnet.basic.utils.StrPool;
import com.mqttsnet.thinglinks.common.cache.CacheKeyModular;
import com.mqttsnet.thinglinks.common.lock.LockKeyTable;

/**
 * Link 服务分布式锁 Key 构建器
 * <p>
 * 复用 CacheKeyBuilder 结构，锁 Key 格式: {PREFIX}:lock:{modular}:{table}:{field}:{value}
 * 示例: CacheKey前缀:lock:link:device:id:123
 *
 * @author mqttsnet
 * @date 2025/2/28
 */
public class LinkLockKeyBuilder implements CacheKeyBuilder {

    private String table;
    private String field;
    private Duration expire;

    private LinkLockKeyBuilder() {
        // 默认锁过期时间30秒
        this.expire = Duration.ofSeconds(30);
    }

    // ==================== 便捷静态方法 ====================

    /**
     * 根据用户ID构建设备保存操作锁 Key
     * 用于限制同一用户并发保存设备
     *
     * @param userId 用户ID
     * @return CacheKey
     */
    public static CacheKey forSaveDeviceByUserId(Long userId) {
        return device()
                .expire(Duration.ofSeconds(10))
                .field("saveUserId")
                .key(String.valueOf(userId));
    }

    /**
     * 根据用户ID构建设备更新操作锁 Key
     * 用于限制同一用户并发更新设备
     *
     * @param userId 用户ID
     * @return CacheKey
     */
    public static CacheKey forUpdateDeviceByUserId(Long userId) {
        return device()
                .expire(Duration.ofSeconds(10))
                .field("updateUserId")
                .key(String.valueOf(userId));
    }

    // ==================== 产品相关锁 ====================

    /**
     * 根据用户ID构建产品保存操作锁 Key
     * 用于限制同一用户并发保存产品
     *
     * @param userId 用户ID
     * @return CacheKey
     */
    public static CacheKey forSaveProductByUserId(Long userId) {
        return product()
                .expire(Duration.ofSeconds(10))
                .field("saveProduct")
                .key(String.valueOf(userId));
    }

    /**
     * 根据用户ID构建产品更新操作锁 Key
     * 用于限制同一用户并发更新产品
     *
     * @param userId 用户ID
     * @return CacheKey
     */
    public static CacheKey forUpdateProductByUserId(Long userId) {
        return product()
                .expire(Duration.ofSeconds(10))
                .field("updateProduct")
                .key(String.valueOf(userId));
    }

    // ==================== 产品服务相关锁 ====================

    /**
     * 根据用户ID构建产品服务保存操作锁 Key
     * 用于限制同一用户并发保存产品服务
     *
     * @param userId 用户ID
     * @return CacheKey
     */
    public static CacheKey forSaveProductServiceByUserId(Long userId) {
        return productService()
                .expire(Duration.ofSeconds(10))
                .field("saveProductService")
                .key(String.valueOf(userId));
    }

    /**
     * 根据用户ID构建产品服务更新操作锁 Key
     * 用于限制同一用户并发更新产品服务
     *
     * @param userId 用户ID
     * @return CacheKey
     */
    public static CacheKey forUpdateProductServiceByUserId(Long userId) {
        return productService()
                .expire(Duration.ofSeconds(10))
                .field("updateProductService")
                .key(String.valueOf(userId));
    }

    // ==================== 产品属性相关锁 ====================

    /**
     * 根据用户ID构建产品属性保存操作锁 Key
     * 用于限制同一用户并发保存产品属性
     *
     * @param userId 用户ID
     * @return CacheKey
     */
    public static CacheKey forSaveProductPropertyByUserId(Long userId) {
        return productProperty()
                .expire(Duration.ofSeconds(10))
                .field("saveProductProperty")
                .key(String.valueOf(userId));
    }

    /**
     * 根据用户ID构建产品属性更新操作锁 Key
     * 用于限制同一用户并发更新产品属性
     *
     * @param userId 用户ID
     * @return CacheKey
     */
    public static CacheKey forUpdateProductPropertyByUserId(Long userId) {
        return productProperty()
                .expire(Duration.ofSeconds(10))
                .field("updateProductProperty")
                .key(String.valueOf(userId));
    }


    // ==================== 链式构建方法 ====================

    /**
     * 创建设备锁构建器
     */
    public static LinkLockKeyBuilder device() {
        LinkLockKeyBuilder builder = new LinkLockKeyBuilder();
        builder.table = "device";
        builder.field = "id";
        return builder;
    }

    /**
     * 创建产品锁构建器
     */
    public static LinkLockKeyBuilder product() {
        LinkLockKeyBuilder builder = new LinkLockKeyBuilder();
        builder.table = "product";
        builder.field = "id";
        return builder;
    }

    /**
     * 创建OTA锁构建器
     */
    public static LinkLockKeyBuilder ota() {
        LinkLockKeyBuilder builder = new LinkLockKeyBuilder();
        builder.table = "ota";
        builder.field = "id";
        return builder;
    }

    /**
     * 创建产品服务锁构建器
     */
    public static LinkLockKeyBuilder productService() {
        LinkLockKeyBuilder builder = new LinkLockKeyBuilder();
        builder.table = "productService";
        builder.field = "id";
        return builder;
    }

    /**
     * 创建产品属性锁构建器
     */
    public static LinkLockKeyBuilder productProperty() {
        LinkLockKeyBuilder builder = new LinkLockKeyBuilder();
        builder.table = "productProperty";
        builder.field = "id";
        return builder;
    }

    /**
     * 设置字段为 id
     */
    public LinkLockKeyBuilder id() {
        this.field = "id";
        return this;
    }

    /**
     * 设置字段为 deviceIdentification
     */
    public LinkLockKeyBuilder deviceIdentification() {
        this.field = "deviceIdentification";
        return this;
    }

    /**
     * 设置字段为 productIdentification
     */
    public LinkLockKeyBuilder productIdentification() {
        this.field = "productIdentification";
        return this;
    }

    /**
     * 设置自定义字段
     */
    public LinkLockKeyBuilder field(String field) {
        this.field = field;
        return this;
    }

    /**
     * 设置锁过期时间
     */
    public LinkLockKeyBuilder expire(Duration expire) {
        this.expire = expire;
        return this;
    }


    // ==================== 实现 CacheKeyBuilder 接口 ====================

    @Override
    public String getTenant() {
        // 分布式锁不需要租户隔离，返回null
        return null;
    }

    @Override
    public String getTable() {
        return LockKeyTable.LOCK_PREFIX + StrPool.COLON + this.table;
    }

    @Override
    public String getModular() {
        return CacheKeyModular.LINK;
    }

    @Override
    public String getField() {
        return this.field;
    }

    @Override
    public ValueType getValueType() {
        return null;
    }

    @Override
    public Duration getExpire() {
        return this.expire;
    }
}