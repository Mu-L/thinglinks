package com.mqttsnet.thinglinks.card.entity.auto;

import lombok.Data;

/**
 * 密钥实体类
 *
 * @Author: shisen
 * @Date: 2024/06/27 14:29
 */
@Data
public class IotKeyParameter {
    /**
     * Database Column Remarks:
     * 公共应用键
     *
     * @mbg.generated
     */
    private String appkey;

    /**
     * Database Column Remarks:
     * 公共密钥
     *
     * @mbg.generated
     */
    private String secret;

    /**
     * Database Column Remarks:
     * 公共code
     *
     * @mbg.generated
     */
    private String code;

    /**
     * Database Column Remarks:
     * 渠道上分配给客户的appid
     *
     * @mbg.generated
     */
    private String appid;

    /**
     * Database Column Remarks:
     * 密匙
     *
     * @mbg.generated
     */
    private String password;
}
