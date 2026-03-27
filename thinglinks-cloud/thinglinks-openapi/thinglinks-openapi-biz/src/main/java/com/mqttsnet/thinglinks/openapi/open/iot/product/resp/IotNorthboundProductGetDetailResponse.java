package com.mqttsnet.thinglinks.openapi.open.iot.product.resp;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;

/**
 * Description:
 * 北向API-查询产品详情响应结果
 *
 * @author mqttsnet
 * @version 1.0.0
 * @since 2026/01/22
 */
@Data
@Builder
public class IotNorthboundProductGetDetailResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 产品ID
     */
    private Long id;

    /**
     * 产品标识
     */
    private String productIdentification;

    /**
     * 产品名称
     */
    private String productName;

    /**
     * 产品类型：1-普通产品COMMON，2-网关产品GATEWAY，0-其他未知产品
     */
    private Integer productType;

    /**
     * 厂商ID
     */
    private String manufacturerId;

    /**
     * 厂商名称
     */
    private String manufacturerName;

    /**
     * 产品型号
     */
    private String model;

    /**
     * 数据格式
     */
    private String dataFormat;

    /**
     * 设备类型
     */
    private String deviceType;

    /**
     * 协议类型
     */
    private String protocolType;

    /**
     * 产品描述
     */
    private String description;

    /**
     * 备注
     */
    private String remark;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;

}
