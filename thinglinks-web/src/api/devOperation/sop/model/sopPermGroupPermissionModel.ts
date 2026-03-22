export interface SopPermGroupPermissionPageQuery {
  groupId?: string; // 分组id
  apiId?: string; // 文档id
}

export interface SopPermGroupPermissionSaveVO {
  groupId?: string; // 分组id
  apiIdList?: string[]; // 文档id
}
export interface SopPermGroupPermissionUpdateVO {
  id: string;
  groupId?: string; // 分组id
  apiId?: string; // 文档id
}

export interface SopPermGroupPermissionResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  groupId?: string; // 分组id
  apiId?: string; // 文档id
}
