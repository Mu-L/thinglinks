export interface SopDocAppPageQuery {
  appName?: string; // 应用名称
  token?: string; // Torna应用token
  isPublish?: number; // 是否发布
}

export interface SopDocAppSaveVO {
  appName?: string; // 应用名称
  token?: string; // Torna应用token
  isPublish?: number; // 是否发布
}

export interface SopDocAppUpdateVO {
  id: string;
  appName?: string; // 应用名称
  token?: string; // Torna应用token
  isPublish?: number; // 是否发布
}

export interface SopDocAppResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  appName?: string; // 应用名称
  token?: string; // Torna应用token
  isPublish?: number; // 是否发布
}
