export interface SopNotifyInfoPageQuery {
  appId?: string; // app_id
  apiName?: string; // api_name
  apiVersion?: string; // api_version
  notifyUrl?: string; // 回调url
  lastSendTime?: string; // 最近一次发送时间
  nextSendTime?: string; // 下一次发送时间
  sendMax?: number; // 最大发送次数
  sendCnt?: number; // 已发送次数
  content?: string; // 发送内容
  notifyStatus?: string; // 状态
  errorMsg?: string; // 失败原因
  resultContent?: string; // 返回结果
  remark?: string; // 备注
  tenantId?: string; // 租户ID
}

export interface SopNotifyInfoSaveVO {
  appId?: string; // app_id
  apiName?: string; // api_name
  apiVersion?: string; // api_version
  notifyUrl?: string; // 回调url
  lastSendTime?: string; // 最近一次发送时间
  nextSendTime?: string; // 下一次发送时间
  sendMax?: number; // 最大发送次数
  sendCnt?: number; // 已发送次数
  content?: string; // 发送内容
  notifyStatus?: string; // 状态
  errorMsg?: string; // 失败原因
  resultContent?: string; // 返回结果
  remark?: string; // 备注
  tenantId?: string; // 租户ID
}

export interface SopNotifyInfoUpdateVO {
  id: string;
  appId?: string; // app_id
  apiName?: string; // api_name
  apiVersion?: string; // api_version
  notifyUrl?: string; // 回调url
  lastSendTime?: string; // 最近一次发送时间
  nextSendTime?: string; // 下一次发送时间
  sendMax?: number; // 最大发送次数
  sendCnt?: number; // 已发送次数
  content?: string; // 发送内容
  notifyStatus?: string; // 状态
  errorMsg?: string; // 失败原因
  resultContent?: string; // 返回结果
  remark?: string; // 备注
  tenantId?: string; // 租户ID
}

export interface SopNotifyInfoResultVO {
  echoMap?: any;
  id?: string; //
  createdTime?: string; //
  updatedTime?: string; //
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  appId?: string; // app_id
  apiName?: string; // api_name
  apiVersion?: string; // api_version
  notifyUrl?: string; // 回调url
  lastSendTime?: string; // 最近一次发送时间
  nextSendTime?: string; // 下一次发送时间
  sendMax?: number; // 最大发送次数
  sendCnt?: number; // 已发送次数
  content?: string; // 发送内容
  notifyStatus?: string; // 状态
  errorMsg?: string; // 失败原因
  resultContent?: string; // 返回结果
  remark?: string; // 备注
  tenantId?: string; // 租户ID
}
