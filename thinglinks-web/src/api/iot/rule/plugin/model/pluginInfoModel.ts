export interface PluginInfoPageQuery {
  appId?: string; // 应用ID，所属应用场景
  applicationName?: string; // 应用名称，SpringBoot应用名称
  pluginIdentification?: string; // 实例唯一标识，自动生成：plugin_code + version
  pluginCode?: string; // 插件代码标识，取自 pluginMeta.properties
  pluginName?: string; // 插件名称，文件名
  version?: string; // 插件版本，取自 pluginMeta.properties
  description?: string; // 插件描述，取自 pluginMeta.properties
  fileId?: string; // 文件在服务器上的唯一标识，用于查询文件临时路径
  fileSize?: string; // 文件大小（MB）
  status?: boolean; // 状态：0-上传成功，1-运行中，2-启动失败，3-停用中，4-停用失败，5-未知异常
  level?: boolean; // 插件级别：0-系统级，1-用户级
  type?: boolean; // 插件类型：0-设备协议插件，1-业务插件
  runMode?: boolean; // 运行模式：0-单节点，1-集群
  licenseType?: string; // 许可证类型（如GPL, MIT, 商业等）
  licenseKey?: string; // 许可证密钥或证书
  validUntil?: string; // 许可证有效期
  fileHash?: string; // 文件的哈希值，用于验证文件的完整性（如 SHA-256）
  scanStatus?: string; // 扫描状态：PENDING, SUCCESS, FAILED
  scanReportFileId?: string; // 扫描报告的文件ID
  scanDate?: string; // 最后一次扫描的日期
  scanSummary?: string; // 扫描摘要（如发现的漏洞数目等）
  extendParams?: string; // 扩展参数（预留）
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface PluginInfoSaveVO {
  appId?: string; // 应用ID，所属应用场景
  applicationName?: string; // 应用名称，SpringBoot应用名称
  pluginIdentification?: string; // 实例唯一标识，自动生成：plugin_code + version
  pluginCode?: string; // 插件代码标识，取自 pluginMeta.properties
  pluginName?: string; // 插件名称，文件名
  version?: string; // 插件版本，取自 pluginMeta.properties
  description?: string; // 插件描述，取自 pluginMeta.properties
  fileId?: string; // 文件在服务器上的唯一标识，用于查询文件临时路径
  fileSize?: string; // 文件大小（MB）
  status?: boolean; // 状态：0-上传成功，1-运行中，2-启动失败，3-停用中，4-停用失败，5-未知异常
  level?: boolean; // 插件级别：0-系统级，1-用户级
  type?: boolean; // 插件类型：0-设备协议插件，1-业务插件
  runMode?: boolean; // 运行模式：0-单节点，1-集群
  licenseType?: string; // 许可证类型（如GPL, MIT, 商业等）
  licenseKey?: string; // 许可证密钥或证书
  validUntil?: string; // 许可证有效期
  fileHash?: string; // 文件的哈希值，用于验证文件的完整性（如 SHA-256）
  scanStatus?: string; // 扫描状态：PENDING, SUCCESS, FAILED
  scanReportFileId?: string; // 扫描报告的文件ID
  scanDate?: string; // 最后一次扫描的日期
  scanSummary?: string; // 扫描摘要（如发现的漏洞数目等）
  extendParams?: string; // 扩展参数（预留）
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface PluginInfoUpdateVO {
  id: string;
  appId?: string; // 应用ID，所属应用场景
  applicationName?: string; // 应用名称，SpringBoot应用名称
  pluginIdentification?: string; // 实例唯一标识，自动生成：plugin_code + version
  pluginCode?: string; // 插件代码标识，取自 pluginMeta.properties
  pluginName?: string; // 插件名称，文件名
  version?: string; // 插件版本，取自 pluginMeta.properties
  description?: string; // 插件描述，取自 pluginMeta.properties
  fileId?: string; // 文件在服务器上的唯一标识，用于查询文件临时路径
  fileSize?: string; // 文件大小（MB）
  status?: boolean; // 状态：0-上传成功，1-运行中，2-启动失败，3-停用中，4-停用失败，5-未知异常
  level?: boolean; // 插件级别：0-系统级，1-用户级
  type?: boolean; // 插件类型：0-设备协议插件，1-业务插件
  runMode?: boolean; // 运行模式：0-单节点，1-集群
  licenseType?: string; // 许可证类型（如GPL, MIT, 商业等）
  licenseKey?: string; // 许可证密钥或证书
  validUntil?: string; // 许可证有效期
  fileHash?: string; // 文件的哈希值，用于验证文件的完整性（如 SHA-256）
  scanStatus?: string; // 扫描状态：PENDING, SUCCESS, FAILED
  scanReportFileId?: string; // 扫描报告的文件ID
  scanDate?: string; // 最后一次扫描的日期
  scanSummary?: string; // 扫描摘要（如发现的漏洞数目等）
  extendParams?: string; // 扩展参数（预留）
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface PluginInfoResultVO {
  echoMap?: any;
  id?: string; // 主键
  createdBy?: string; // 创建人
  createdTime?: string; // 创建时间
  updatedBy?: string; // 更新人
  updatedTime?: string; // 更新时间
  appId?: string; // 应用ID，所属应用场景
  applicationName?: string; // 应用名称，SpringBoot应用名称
  pluginIdentification?: string; // 实例唯一标识，自动生成：plugin_code + version
  pluginCode?: string; // 插件代码标识，取自 pluginMeta.properties
  pluginName?: string; // 插件名称，文件名
  version?: string; // 插件版本，取自 pluginMeta.properties
  description?: string; // 插件描述，取自 pluginMeta.properties
  fileId?: string; // 文件在服务器上的唯一标识，用于查询文件临时路径
  fileSize?: string; // 文件大小（MB）
  status?: boolean; // 状态：0-上传成功，1-运行中，2-启动失败，3-停用中，4-停用失败，5-未知异常
  level?: boolean; // 插件级别：0-系统级，1-用户级
  type?: boolean; // 插件类型：0-设备协议插件，1-业务插件
  runMode?: boolean; // 运行模式：0-单节点，1-集群
  licenseType?: string; // 许可证类型（如GPL, MIT, 商业等）
  licenseKey?: string; // 许可证密钥或证书
  validUntil?: string; // 许可证有效期
  fileHash?: string; // 文件的哈希值，用于验证文件的完整性（如 SHA-256）
  scanStatus?: string; // 扫描状态：PENDING, SUCCESS, FAILED
  scanReportFileId?: string; // 扫描报告的文件ID
  scanDate?: string; // 最后一次扫描的日期
  scanSummary?: string; // 扫描摘要（如发现的漏洞数目等）
  extendParams?: string; // 扩展参数（预留）
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
