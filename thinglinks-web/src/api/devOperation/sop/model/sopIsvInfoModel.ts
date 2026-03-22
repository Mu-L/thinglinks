export interface SopIsvInfoPageQuery {
  name?: string; // 名称
  appId?: string; // appKey
  status?: number; // 状态
  remark?: string; // 备注
  startExpirationTime?: string; // 开始有效期
  endExpirationTime?: string; // 结束有效期
  auditStatus?: number; // 审核状态
  auditTime?: string; // 审核时间
  submissionTime?: string; // 提交时间
  creationMethod?: number; // 创建方式
  reviewComments?: string; // 审核意见
  tenantId?: string; // 租户id
}

export interface SopIsvInfoSaveVO {
  name?: string; // 名称
  appId?: string; // appKey
  status?: number; // 状态
  remark?: string; // 备注
  startExpirationTime?: string; // 开始有效期
  endExpirationTime?: string; // 结束有效期
  auditStatus?: number; // 审核状态
  auditTime?: string; // 审核时间
  submissionTime?: string; // 提交时间
  creationMethod?: number; // 创建方式
  reviewComments?: string; // 审核意见
  tenantId?: string; // 租户id
}

export interface SopIsvInfoUpdateVO {
  id: string;
  name?: string; // 名称
  appId?: string; // appKey
  status?: number; // 状态
  remark?: string; // 备注
  startExpirationTime?: string; // 开始有效期
  endExpirationTime?: string; // 结束有效期
  auditStatus?: number; // 审核状态
  auditTime?: string; // 审核时间
  submissionTime?: string; // 提交时间
  creationMethod?: number; // 创建方式
  reviewComments?: string; // 审核意见
  tenantId?: string; // 租户id
}

export interface SopIsvInfoResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  name?: string; // 名称
  appId?: string; // appKey
  status?: number; // 状态
  remark?: string; // 备注
  startExpirationTime?: string; // 开始有效期
  endExpirationTime?: string; // 结束有效期
  auditStatus?: number; // 审核状态
  auditTime?: string; // 审核时间
  submissionTime?: string; // 提交时间
  creationMethod?: number; // 创建方式
  reviewComments?: string; // 审核意见
  tenantId?: string; // 租户id
}

export interface SopIsvInfoApplyForVO {
  name: string;
  remark?: string;
}

export interface SopIsvInfoSubmitVO {
  id: string;
  name: string;
  remark?: string;
}
