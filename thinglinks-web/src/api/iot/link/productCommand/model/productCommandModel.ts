export interface ProductCommandPageQuery {
  serviceId?: string; // 服务ID
  commandCode?: string; // 指示命令的编码，如门磁的LOCK命令、摄像头的VIDEO_RECORD命令，命令名与参数共同构成一个完整的命令。支持英文大小写、数字及下划线，长度[2,50]。
  commandName?: string; // 指示命令名称
  description?: string; // 命令描述。
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductCommandSaveVO {
  serviceId?: string; // 服务ID
  commandCode?: string; // 指示命令的编码，如门磁的LOCK命令、摄像头的VIDEO_RECORD命令，命令名与参数共同构成一个完整的命令。支持英文大小写、数字及下划线，长度[2,50]。
  commandName?: string; // 指示命令名称
  description?: string; // 命令描述。
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductCommandUpdateVO {
  id: string;
  serviceId?: string; // 服务ID
  commandCode?: string; // 指示命令的编码，如门磁的LOCK命令、摄像头的VIDEO_RECORD命令，命令名与参数共同构成一个完整的命令。支持英文大小写、数字及下划线，长度[2,50]。
  commandName?: string; // 指示命令名称
  description?: string; // 命令描述。
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductCommandResultVO {
  echoMap?: any;
  id?: string; // 命令id
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  serviceId?: string; // 服务ID
  commandCode?: string; // 指示命令的编码，如门磁的LOCK命令、摄像头的VIDEO_RECORD命令，命令名与参数共同构成一个完整的命令。支持英文大小写、数字及下划线，长度[2,50]。
  commandName?: string; // 指示命令名称
  description?: string; // 命令描述。
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
