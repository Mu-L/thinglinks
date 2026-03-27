package com.mqttsnet.thinglinks.product.vo.result;

import cn.hutool.core.map.MapUtil;
import com.mqttsnet.basic.annotation.echo.Echo;
import com.mqttsnet.basic.base.entity.Entity;
import com.mqttsnet.basic.interfaces.echo.EchoVO;
import com.mqttsnet.thinglinks.model.constant.EchoApi;
import com.mqttsnet.thinglinks.model.constant.EchoDictType;
import com.mqttsnet.thinglinks.productservice.vo.result.ProductServiceResultVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 表单查询方法返回值VO
 * 产品模型
 * </p>
 *
 * @author mqttsnet
 * @date 2023-03-14 19:39:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@Schema(title = "ProductResultVO", description = "产品模型")
public class ProductResultVO extends Entity<Long> implements Serializable, EchoVO {

    @Serial
    private static final long serialVersionUID = 1L;
    @Builder.Default
    private Map<String, Object> echoMap = MapUtil.newHashMap();

    @Schema(description = "id")
    private Long id;

    /**
     * 应用ID
     */
    @Schema(description = "应用ID")
    private String appId;
    /**
     * 产品id
     */
    @Schema(description = "产品id")
    private Long templateId;
    /**
     * 产品名称:自定义，支持中文、英文大小写、数字、下划线和中划线
     */
    @Schema(description = "产品名称:自定义，支持中文、英文大小写、数字、下划线和中划线")
    private String productName;
    /**
     * 产品标识
     */
    @Schema(description = "产品标识")
    private String productIdentification;
    /**
     * 支持以下两种产品类型1•COMMON：普通产品，需直连设备。2•GATEWAY：网关产品，可挂载子设备。 0其他未知产品
     */
    @Schema(description = "支持以下两种产品类型1•COMMON：普通产品，需直连设备。2•GATEWAY：网关产品，可挂载子设备。 0其他未知产品")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_PRODUCT_TYPE)
    private Integer productType;
    /**
     * 厂商ID:支持英文大小写，数字，下划线和中划线
     */
    @Schema(description = "厂商ID:支持英文大小写，数字，下划线和中划线")
    private String manufacturerId;
    /**
     * 厂商名称 :支持中文、英文大小写、数字、下划线和中划线
     */
    @Schema(description = "厂商名称 :支持中文、英文大小写、数字、下划线和中划线")
    private String manufacturerName;
    /**
     * 产品型号，建议包含字母或数字来保证可扩展性。支持英文大小写、数字、下划线和中划线
     */
    @Schema(description = "产品型号，建议包含字母或数字来保证可扩展性。支持英文大小写、数字、下划线和中划线")
    private String model;
    /**
     * 数据格式，默认为JSON无需修改。
     */
    @Schema(description = "数据格式，默认为JSON无需修改。")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_PRODUCT_DATA_FORMAT)
    private String dataFormat;
    /**
     * 设备类型:支持英文大小写、数字、下划线和中划线
     */
    @Schema(description = "设备类型:支持英文大小写、数字、下划线和中划线")
    private String deviceType;
    /**
     * 设备接入平台的协议类型，默认为MQTT无需修改。
     */
    @Schema(description = "设备接入平台的协议类型，默认为MQTT无需修改。")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_PRODUCT_PROTOCOL_TYPE)
    private String protocolType;
    /**
     * 状态(字典值：0启用  1停用)
     */
    @Schema(description = "状态(字典值：0启用  1停用)")
    @Echo(api = EchoApi.DICTIONARY_ITEM_FEIGN_CLASS, dictType = EchoDictType.Link.LINK_PRODUCT_STATUS)
    private Integer productStatus;
    /**
     * 产品版本
     */
    @Schema(description = "产品版本")
    private String productVersion;
    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;
    /**
     * 产品描述
     */
    @Schema(description = "产品描述")
    private String remark;
    /**
     * 创建人组织
     */
    @Schema(description = "创建人组织")
    @Echo(api = EchoApi.ORG_ID_CLASS)
    private Long createdOrgId;


    @Schema(description = "产品模型服务")
    private List<ProductServiceResultVO> services;

}
