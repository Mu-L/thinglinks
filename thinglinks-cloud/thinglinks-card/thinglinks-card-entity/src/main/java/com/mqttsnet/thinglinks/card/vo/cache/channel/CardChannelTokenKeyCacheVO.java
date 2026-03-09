package com.mqttsnet.thinglinks.card.vo.cache.channel;

import com.mqttsnet.thinglinks.card.enumeration.OperatorTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 所有平台token
 *
 * @Author: shisen
 * @Date: 2024/06/27 15:20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode
@Builder
public class CardChannelTokenKeyCacheVO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 认证
     */
    private String token;

    /**
     * 事务id
     */
    private String transid;

    /**
     * 卡源
     */
    private String channelName;

    /**
     * 时间戳
     */
    private long timeStamp;

    /**
     * 时间戳
     */
    private String time;

    /**
     * 请求接口类型
     */
    private String requestType;

    /**
     * 卡id
     */
    private Long cardId;

    /**
     * 卡号
     */
    private String cardNum;

    /**
     * iccid
     */
    private String iccid;

    /**
     * imsi
     */
    private String imsi;

    /**
     * imsi
     */
    private String c;

    /**
     * 应用
     */
    private String apnName;

    /**
     * 扩展字段
     */
    private String extension;

    /*********密匙key***********/

    private String apikey;

    private String appId;

    private String account;

    private String sign;

    private String dev_id;

    private String url;

    private String requestMethod;

    /**
     * 接口私有参数
     */
    private Map<String, Object> privateParameters;

    /**
     * 接口请求参数集合
     */
    private Map<String, Object> parameters = new HashMap<>();

    /**
     * 接口请求头参数集合
     */
    private Map<String, String> headerParameters;

    /**
     * 密钥参数
     */
    private String keyParameterJson;

    /**
     * 供应商id
     */
    private OperatorTypeEnum supportId;

    /**
     * 接口返回数据集合,组装成对象
     */
    private Object resultObjct;

    /**
     * 供应商响应
     */
    private ResultMap resultMap;

    /**
     * 接口返回数据集合,组装成对象
     */
    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ResultMap {
        private String status;
        private String message;
        private Object result;
    }
}
