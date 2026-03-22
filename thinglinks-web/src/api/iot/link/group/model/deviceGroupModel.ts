export interface DeviceGroupPageQuery {
  appId?: string; // 应用ID
  groupName?: string; // 分组名称
  type?: number; // 分组类型
  state?: boolean; // 状态
  description?: string; // 分组描述
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  parentId?: string;
  sortValue?: number;
}

export interface DeviceGroupSaveVO {
  appId?: string; // 应用ID
  groupName?: string; // 分组名称
  type?: number; // 分组类型
  state?: boolean; // 状态
  description?: string; // 分组描述
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  parentId?: string;
  sortValue?: number;
}

export interface DeviceGroupUpdateVO {
  id: string;
  appId?: string; // 应用ID
  groupName?: string; // 分组名称
  type?: number; // 分组类型
  state?: boolean; // 状态
  description?: string; // 分组描述
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  parentId?: string;
  sortValue?: number;
}

export interface DeviceGroupResultVO {
  echoMap?: any;
  id?: string; // id
  parentId?: string; // 父级ID
  sortValue?: number; // 排序
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  appId?: string; // 应用ID
  groupName?: string; // 分组名称
  type?: number; // 分组类型
  state?: boolean; // 状态
  description?: string; // 分组描述
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
