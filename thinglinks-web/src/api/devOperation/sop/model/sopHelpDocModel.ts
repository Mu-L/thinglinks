export interface SopHelpDocPageQuery {
  label?: string; // 文档名称
  status?: boolean; // 状态
  content?: string; // 内容
  contentType?: number; // 内容类型
  parentId?: string;
  sortValue?: number;
}

export interface SopHelpDocSaveVO {
  label?: string; // 文档名称
  status?: boolean; // 状态
  content?: string; // 内容
  contentType?: number; // 内容类型
  parentId?: string;
  sortValue?: number;
}

export interface SopHelpDocUpdateVO {
  id: string;
  label?: string; // 文档名称
  status?: boolean; // 状态
  content?: string; // 内容
  contentType?: number; // 内容类型
  parentId?: string;
  sortValue?: number;
}

export interface SopHelpDocResultVO {
  echoMap?: any;
  id?: string; // id
  sortValue?: number; // 排序
  parentId?: string; // 父级id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  label?: string; // 文档名称
  status?: boolean; // 状态
  content?: string; // 内容
  contentType?: number; // 内容类型
}
