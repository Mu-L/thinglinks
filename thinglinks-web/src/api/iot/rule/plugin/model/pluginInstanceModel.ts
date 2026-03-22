export interface PluginInstancePageQuery {
  instanceIdentification?: string; // 实例唯一标识
  instanceName?: string; // 实例名称，用于标识实例的友好名称
  applicationName?: string; // 应用名称，SpringBoot应用名称
  machineIp?: string; // 实例运行所在的机器 IP 地址
  portRangeStart?: number; // 实例可用端口范围起始值
  portRangeEnd?: number; // 实例可用端口范围结束值
  extendParams?: string; // 扩展参数（预留）
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  weight?: number; // 实例的权重
  healthy?: boolean; // 实例的健康状态
  enabled?: boolean; // 实例是否启用
  ephemeral?: boolean; // 实例是否为临时实例
  clusterName?: string; // 实例所在集群名称
  heartBeatInterval?: string; // 实例心跳间隔时间(毫秒)
  heartBeatTimeOut?: string; // 实例心跳超时时间(毫秒)
  ipDeleteTimeOut?: string; // 实例IP删除超时时间(毫秒)
  machinePort?: string; // 实例机器端口
}

export interface PluginInstanceSaveVO {
  instanceIdentification?: string; // 实例唯一标识
  instanceName?: string; // 实例名称，用于标识实例的友好名称
  applicationName?: string; // 应用名称，SpringBoot应用名称
  machineIp?: string; // 实例运行所在的机器 IP 地址
  portRangeStart?: number; // 实例可用端口范围起始值
  portRangeEnd?: number; // 实例可用端口范围结束值
  extendParams?: string; // 扩展参数（预留）
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  weight?: number; // 实例的权重
  healthy?: boolean; // 实例的健康状态
  enabled?: boolean; // 实例是否启用
  ephemeral?: boolean; // 实例是否为临时实例
  clusterName?: string; // 实例所在集群名称
  heartBeatInterval?: string; // 实例心跳间隔时间(毫秒)
  heartBeatTimeOut?: string; // 实例心跳超时时间(毫秒)
  ipDeleteTimeOut?: string; // 实例IP删除超时时间(毫秒)
  machinePort?: string; // 实例机器端口
}

export interface PluginInstanceUpdateVO {
  id: string;
  instanceIdentification?: string; // 实例唯一标识
  instanceName?: string; // 实例名称，用于标识实例的友好名称
  applicationName?: string; // 应用名称，SpringBoot应用名称
  machineIp?: string; // 实例运行所在的机器 IP 地址
  portRangeStart?: number; // 实例可用端口范围起始值
  portRangeEnd?: number; // 实例可用端口范围结束值
  extendParams?: string; // 扩展参数（预留）
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  weight?: number; // 实例的权重
  healthy?: boolean; // 实例的健康状态
  enabled?: boolean; // 实例是否启用
  ephemeral?: boolean; // 实例是否为临时实例
  clusterName?: string; // 实例所在集群名称
  heartBeatInterval?: string; // 实例心跳间隔时间(毫秒)
  heartBeatTimeOut?: string; // 实例心跳超时时间(毫秒)
  ipDeleteTimeOut?: string; // 实例IP删除超时时间(毫秒)
  machinePort?: string; // 实例机器端口
}

export interface PluginInstanceResultVO {
  echoMap?: any;
  id?: string; // 主键
  createdBy?: string; // 创建人
  createdTime?: string; // 创建时间
  updatedBy?: string; // 更新人
  updatedTime?: string; // 更新时间
  instanceIdentification?: string; // 实例唯一标识
  instanceName?: string; // 实例名称，用于标识实例的友好名称
  applicationName?: string; // 应用名称，SpringBoot应用名称
  machineIp?: string; // 实例运行所在的机器 IP 地址
  portRangeStart?: number; // 实例可用端口范围起始值
  portRangeEnd?: number; // 实例可用端口范围结束值
  extendParams?: string; // 扩展参数（预留）
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  weight?: number; // 实例的权重
  healthy?: boolean; // 实例的健康状态
  enabled?: boolean; // 实例是否启用
  ephemeral?: boolean; // 实例是否为临时实例
  clusterName?: string; // 实例所在集群名称
  heartBeatInterval?: string; // 实例心跳间隔时间(毫秒)
  heartBeatTimeOut?: string; // 实例心跳超时时间(毫秒)
  ipDeleteTimeOut?: string; // 实例IP删除超时时间(毫秒)
  machinePort?: string; // 实例机器端口
}
