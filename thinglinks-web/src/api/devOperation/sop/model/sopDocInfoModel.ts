export interface SopDocInfoPageQuery {
  docAppId?: string; // 应用id
  docTitle?: string; // 文档标题
  docId?: string; // 文档id
  docCode?: string; // 文档编码
  docType?: number; // 文档类型
  sourceType?: number; // 来源类型
  docVersion?: string; // 文档版本号
  docName?: string; // 文档名称
  description?: string; // 描述
  isFolder?: number; // 是否分类
  isPublish?: number; // 状态
  parentId?: string; // 父文档节点id
}

export interface SopDocInfoSaveVO {
  docAppId?: string; // 应用id
  docTitle?: string; // 文档标题
  docId?: string; // 文档id
  docCode?: string; // 文档编码
  docType?: number; // 文档类型
  sourceType?: number; // 来源类型
  docVersion?: string; // 文档版本号
  docName?: string; // 文档名称
  description?: string; // 描述
  isFolder?: number; // 是否分类
  isPublish?: number; // 状态
  parentId?: string; // 父文档节点id
}

export interface SopDocInfoUpdateVO {
  id: string;
  docAppId?: string; // 应用id
  docTitle?: string; // 文档标题
  docId?: string; // 文档id
  docCode?: string; // 文档编码
  docType?: number; // 文档类型
  sourceType?: number; // 来源类型
  docVersion?: string; // 文档版本号
  docName?: string; // 文档名称
  description?: string; // 描述
  isFolder?: number; // 是否分类
  isPublish?: number; // 状态
  parentId?: string; // 父文档节点id
}

export interface SopDocInfoResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  docAppId?: string; // 应用id
  docTitle?: string; // 文档标题
  docId?: string; // 文档id
  docCode?: string; // 文档编码
  docType?: number; // 文档类型
  sourceType?: number; // 来源类型
  docVersion?: string; // 文档版本号
  docName?: string; // 文档名称
  description?: string; // 描述
  isFolder?: number; // 是否分类
  isPublish?: number; // 状态
  parentId?: string; // 父文档节点id
  children?: SopDocInfoResultVO[];
}

export interface DocInfoViewVO {
  docInfoView: any;
  docInfoConfig: any;
}
