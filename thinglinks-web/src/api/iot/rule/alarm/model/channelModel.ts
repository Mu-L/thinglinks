export interface ChannelPageQuery {
  channelConfig?: string; // 告警配置
  channelName?: string; // 渠道名称
  channelType?: string; // 渠道类型
  createdOrgId?: number; // 创建人组织
  id?: number; // 主键
  ids?: any[]; // 主键集合
  remark?: string; // 描述
  status?: string; // 启用状态
}

export interface ChannelSaveVO {
  channelConfig?: string; // 告警配置
  channelName?: string; // 渠道名称
  channelType?: string; // 渠道类型
  createdOrgId?: number; // 创建人组织
  remark?: string; // 描述
  status?: string; // 启用状态
}

export interface ChannelUpdateVO {
  channelConfig: string; // 告警配置
  channelName?: string; // 渠道名称
  channelType?: string; // 渠道类型
  createdOrgId?: number; // 创建人组织
  id?: string; // 主键
  remark?: string; // 描述
  status?: string; // 启用状态
}

export interface ChannelResultVO {
  echoMap?: any;
  id?: number; // 主键
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  channelConfig?: string; // 产品标识
  channelName?: string; // 渠道名称
  channelType?: string; // 渠道类型
  status?: string; // 启用状态
  remark?: string; // 描述
  token?: string; // tokenId
  secret?: string; // secret
  createdOrgId?: number; // 创建人组织
  appId?: string; // appId
  appSecret?: string; // appSecret
}
