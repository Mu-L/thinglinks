package com.mqttsnet.thinglinks.cache.enumeration;

import com.mqttsnet.basic.exception.BizException;

/**
 * 缓存业务类型
 */
public enum CacheEnum {
    DEVICE("device"),
    PRODUCT("product"),
    PRODUCT_MODEL("productModel"),
    ;
    private String code;

    CacheEnum(String code) {
        this.code = code;
    }

    public static CacheEnum fromCode(String code) {
        for (CacheEnum cacheEnum : CacheEnum.values()) {
            if (cacheEnum.getCode().equals(code)) {
                return cacheEnum;
            }
        }
        throw BizException.wrap("Invalid code: " + code);
    }

    public String getCode() {
        return code;
    }
}
