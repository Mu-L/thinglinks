package com.mqttsnet.thinglinks.card.entity.sim;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 单卡在线信息 list
 *
 * @Author: shisen
 * @Date: 2024/06/27 17:30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SimSessionList implements Serializable {

    /**
     * 平台默认在线状态
     * 00: 离线
     * 01：在线
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
     * apn 电信
     */
    @JSONField(alternateNames = {"apn", "apnId"})
    private String apnId;

    /**
     * 移动
     */
    private List<SimSession> simSessionList;

    /**
     * 接入方式
     * RAT 移动v2
     * rattype 电信
     */
    @JSONField(alternateNames = {"RAT", "rat", "rattype", "rat"})
    private String rat;
}
