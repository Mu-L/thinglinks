export interface IsvInfoApplyForParam {
  name: string;
  remark?: string;
}

export interface IsvInfoSubmitParam {
  id: string;
  name: string;
  remark?: string;
}

export interface IsvInfoVO {
  id?: string;
  appId?: string;
  status?: number;
  hasKeys?: number;
  remark?: string;
  groupNames?: string;
  addTime?: string;
  updateTime?: string;
  addBy?: Recordable;
  updateBy?: Recordable;
  startExpirationTime?: string;
  endExpirationTime?: string;
  auditStatus?: number;
  auditTime?: string;
  submissionTime?: string;
  creationMethod?: number;
  reviewComments?: string;
  tenantId?: string;
  isvId?: string;
}
