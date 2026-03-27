export interface SopSysConfigPageQuery {
  configKey?: string; // 配置key
  configValue?: string; // 配置值
  remark?: string; // 备注
  deletedAt?: string; // 是否删除
}

export interface SopSysConfigSaveVO {
  configKey?: string; // 配置key
  configValue?: string; // 配置值
  remark?: string; // 备注
  deletedAt?: string; // 是否删除
}

export interface SopSysConfigUpdateVO {
  id: string;
  configKey?: string; // 配置key
  configValue?: string; // 配置值
  remark?: string; // 备注
  deletedAt?: string; // 是否删除
}

export interface SopSysConfigResultVO {
  echoMap?: any;
  id?: string; // 主键id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 最后更新人id
  configKey?: string; // 配置key
  configValue?: string; // 配置值
  remark?: string; // 备注
  deletedAt?: string; // 是否删除
}
