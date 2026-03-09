package com.mqttsnet.thinglinks.entity.plugin;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.mqttsnet.basic.base.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.baomidou.mybatisplus.annotation.SqlCondition.EQUAL;
import static com.mqttsnet.thinglinks.model.constant.Condition.LIKE;


/**
 * <p>
 * 实体类
 * 插件信息表
 * </p>
 *
 * @author mqttsnet
 * @date 2024-08-25 19:05:11
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Builder
@TableName("plugin_info")
public class PluginInfo extends Entity<Long> {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 应用ID，所属应用场景
     */
    @TableField(value = "app_id", condition = LIKE)
    private String appId;
    /**
     * 插件唯一标识，自动生成：plugin_code + version
     */
    @TableField(value = "plugin_identification", condition = LIKE)
    private String pluginIdentification;
    /**
     * 插件代码标识，取自 pluginMeta.properties
     */
    @TableField(value = "plugin_code", condition = LIKE)
    private String pluginCode;
    /**
     * 插件名称
     */
    @TableField(value = "plugin_name", condition = LIKE)
    private String pluginName;
    /**
     * 插件版本，取自 pluginMeta.properties
     */
    @TableField(value = "version", condition = LIKE)
    private String version;
    /**
     * 插件描述，取自 pluginMeta.properties
     */
    @TableField(value = "description", condition = LIKE)
    private String description;
    /**
     * 文件在服务器上的唯一标识，用于查询文件临时路径
     */
    @TableField(value = "file_id", condition = LIKE)
    private String fileId;
    /**
     * 文件大小（MB）
     */
    @TableField(value = "file_size", condition = EQUAL)
    private BigDecimal fileSize;
    /**
     * 状态
     */
    @TableField(value = "status", condition = EQUAL)
    private Integer status;
    /**
     * 插件级别：0-系统级，1-用户级
     */
    @TableField(value = "level", condition = EQUAL)
    private Integer level;
    /**
     * 插件类型：0-设备协议插件，1-业务插件
     */
    @TableField(value = "type", condition = EQUAL)
    private Integer type;
    /**
     * 运行模式：0-单节点，1-集群
     */
    @TableField(value = "run_mode", condition = EQUAL)
    private Integer runMode;
    /**
     * 许可证类型（如GPL, MIT, 商业等）
     */
    @TableField(value = "license_type", condition = LIKE)
    private String licenseType;
    /**
     * 许可证密钥或证书
     */
    @TableField(value = "license_key", condition = LIKE)
    private String licenseKey;
    /**
     * 许可证有效期
     */
    @TableField(value = "valid_until", condition = EQUAL)
    private LocalDate validUntil;
    /**
     * 文件的哈希值，用于验证文件的完整性（如 SHA-256）
     */
    @TableField(value = "file_hash", condition = LIKE)
    private String fileHash;
    /**
     * 扫描状态：PENDING, SUCCESS, FAILED
     */
    @TableField(value = "scan_status", condition = LIKE)
    private String scanStatus;
    /**
     * 扫描报告的文件ID
     */
    @TableField(value = "scan_report_file_id", condition = LIKE)
    private String scanReportFileId;
    /**
     * 最后一次扫描的日期
     */
    @TableField(value = "scan_date", condition = EQUAL)
    private LocalDateTime scanDate;
    /**
     * 扫描摘要（如发现的漏洞数目等）
     */
    @TableField(value = "scan_summary", condition = LIKE)
    private String scanSummary;
    /**
     * 扩展参数（预留）
     */
    @TableField(value = "extend_params", condition = LIKE)
    private String extendParams;
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


}
