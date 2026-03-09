package com.mqttsnet.thinglinks.card.entity.sim;

import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * -单卡状态查询
 *
 * @Author: shisen
 * @Date: 2024/06/27 11:23
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class SimStatus implements Serializable {

    /**
     * 通道一 code
     * 数字类型
     */
    @JSONField(alternateNames = {"code", "cardStatus", "card_status", "cardStatus", "card_state", "cardStatus", "simStatus", "cardStatus"})
    private Integer cardStatus;

    /**
     * 最后一次变更时间
     */
    private String lastChangeDate;

    /**
     * 文本型
     * 官方电信 productMainStatusCd
     */
    @JSONField(alternateNames = {"productMainStatusCd", "fullStatusReason"})
    private String fullStatusReason;

    /**
     * 移动v2
     */
    private String STATUS;
}
