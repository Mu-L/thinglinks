export interface CardChannelInfoConfigPageQuery {
  channelId?: string; // 渠道id
  requestTypeCode?: string; // 请求类型编码
  url?: string; // 供应商接口地址
  extendParams?: string; // 扩展参数
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardChannelInfoConfigSaveVO {
  channelId?: string; // 渠道id
  requestTypeCode?: string; // 请求类型编码
  url?: string; // 供应商接口地址
  extendParams?: string; // 扩展参数
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardChannelInfoConfigUpdateVO {
  id: string;
  channelId?: string; // 渠道id
  requestTypeCode?: string; // 请求类型编码
  url?: string; // 供应商接口地址
  extendParams?: string; // 扩展参数
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardChannelInfoConfigResultVO {
  echoMap?: any;
  id?: string; //
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  channelId?: string; // 渠道id
  requestTypeCode?: string; // 请求类型编码
  url?: string; // 供应商接口地址
  extendParams?: string; // 扩展参数
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
