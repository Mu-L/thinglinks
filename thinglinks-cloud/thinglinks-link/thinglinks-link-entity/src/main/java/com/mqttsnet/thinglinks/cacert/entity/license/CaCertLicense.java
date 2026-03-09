package com.mqttsnet.thinglinks.cacert.entity.license;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;

import java.io.Serial;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;


/**
 * <p>
 * 实体类
 * CA许可证证书表
 * </p>
 *
 * @author mqttsnet
 * @since 2025-06-27 15:48:10
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName(value = "ca_cert_license", autoResultMap = true)
public class CaCertLicense extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 证书名称
     */
    @TableField(value = "cert_name", condition = LIKE)
    private String certName;

    /**
     * 颁发者通用名称
     */
    @TableField(value = "issuer_common_name", condition = LIKE)
    private String issuerCommonName;

    /**
     * 证书序列号
     * 十六进制大写
     */
    @TableField(value = "serial_number", condition = LIKE)
    private String serialNumber;
    /**
     * 通用名称
     */
    @TableField(value = "common_name", condition = LIKE)
    private String commonName;
    /**
     * 组织名称
     */
    @TableField(value = "organization", condition = LIKE)
    private String organization;
    /**
     * 组织单位名称
     */
    @TableField(value = "organizational_unit", condition = LIKE)
    private String organizationalUnit;
    /**
     * 国家
     */
    @TableField(value = "country_name", condition = LIKE)
    private String countryName;
    /**
     * 省份/州
     */
    @TableField(value = "province_name", condition = LIKE)
    private String provinceName;
    /**
     * 城市
     */
    @TableField(value = "locality_name", condition = LIKE)
    private String localityName;
    /**
     * 邮箱
     */
    @TableField(value = "email", condition = LIKE)
    private String email;
    /**
     * License文件内容(Base64编码)
     */
    @TableField(value = "license_base64", condition = LIKE)
    private String licenseBase64;
    /**
     * 营业执照文件ID
     */
    @TableField(value = "business_license_fileid", condition = EQUAL)
    private String businessLicenseFileid;
    /**
     * 授权证书文件ID
     */
    @TableField(value = "authorization_cert_fileid", condition = EQUAL)
    private String authorizationCertFileid;

    /**
     * CA证书(PEM格式)
     */
    @TableField(value = "ca_cert_pem", condition = EQUAL)
    private String caCertPem;

    /**
     * 证书文件ID
     */
    @TableField(value = "cert_fileid", condition = EQUAL)
    private String certFileid;
    /**
     * 签名算法
     */
    @TableField(value = "sign_algorithm", condition = EQUAL)
    private Integer signAlgorithm;
    /**
     * 算法(0-RSA、1-EC)
     */
    @TableField(value = "algorithm", condition = EQUAL)
    private Integer algorithm;
    /**
     * RSA公钥n或ECC Point x
     */
    @TableField(value = "param1", condition = LIKE)
    private String param1;
    /**
     * RSA公钥e或ECC Point y
     */
    @TableField(value = "param2", condition = LIKE)
    private String param2;
    /**
     * 扩展信息
     */
    @TableField(value = "extend_params", condition = LIKE)
    private String extendParams;
    /**
     * 证书颁发时间
     */
    @TableField(value = "not_before", condition = EQUAL)
    private LocalDateTime notBefore;
    /**
     * 证书过期时间
     */
    @TableField(value = "not_after", condition = EQUAL)
    private LocalDateTime notAfter;
    /**
     * 证书撤销时间
     */
    @TableField(value = "revoke_time", condition = EQUAL)
    private LocalDateTime revokeTime;
    /**
     * 撤销原因
     */
    @TableField(value = "revoke_reason", condition = EQUAL)
    private String revokeReason;
    /**
     * 证书状态(0-待完善、1-已颁发、2-已撤销)
     */
    @TableField(value = "state", condition = EQUAL)
    private Integer state;

    /**
     * 证书指纹(SHA-256)
     */
    @TableField(value = "thumbprint", condition = EQUAL)
    private String thumbprint;
    /**
     * 备注
     */
    @TableField(value = "remark", condition = LIKE)
    private String remark;
    /**
     * 创建人组织
     */
    @TableField(value = "created_org_id", condition = EQUAL)
    private Long createdOrgId;

    /**
     * 逻辑删除标识:0-未删除 1-已删除
     */
    @TableLogic
    @TableField(value = "deleted", condition = EQUAL)
    private Integer deleted;
}
