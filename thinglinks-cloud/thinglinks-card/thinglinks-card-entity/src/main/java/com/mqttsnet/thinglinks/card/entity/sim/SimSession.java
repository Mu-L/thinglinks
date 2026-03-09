package com.mqttsnet.thinglinks.card.entity.sim;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 单卡在线信息实时查询
 *
 * @Author: shisen
 * @Date: 2024/06/27 11:02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SimSession implements Serializable {

    private String apnId;

    /**
     * 妙月
     * GPRSSTATUS 移动v2
     */
    @JSONField(alternateNames = {"gprs", "status", "GPRSSTATUS", "status"})
    private String status;

    /**
     * ipAddress 联通
     * framedIpAddress 电信
     */
    @JSONField(alternateNames = {"ipAddress", "ip", "framedIpAddress", "ip"})
    private String ip;

    /**
     * dateSessionStarted 联通
     * eventTimestamp 电信
     */
    @JSONField(alternateNames = {"dateSessionStarted", "createDate", "eventTimestamp", "createDate"})
    private String createDate;

    /**
     * 接入方式
     * RAT 移动v2
     * rattype 电信
     */
    @JSONField(alternateNames = {"RAT", "rat", "rattype", "rat"})
    private String rat;

}
