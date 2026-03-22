export interface SopPermGroupPageQuery {
  groupName?: string; // 分组名称
  deletedAt?: string; // 是否删除
}

export interface SopPermGroupSaveVO {
  groupName?: string; // 分组名称
  deletedAt?: string; // 是否删除
}

export interface SopPermGroupUpdateVO {
  id: string;
  groupName?: string; // 分组名称
  deletedAt?: string; // 是否删除
}

export interface SopPermGroupResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  groupName?: string; // 分组名称
  deletedAt?: string; // 是否删除
}
