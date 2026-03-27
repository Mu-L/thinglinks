export interface DeviceGroupRelPageQuery {
  groupId?: string; // 分组ID
  deviceIdentification?: string; // 设备标识
  remark?: string; // 备注
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  createdOrgId?: string; // 创建人组织
}

export interface DeviceGroupRelSaveVO {
  groupId?: string; // 分组ID
  deviceIdentification?: string; // 设备标识
  remark?: string; // 备注
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  createdOrgId?: string; // 创建人组织
}

export interface DeviceGroupRelUpdateVO {
  id: string;
  groupId?: string; // 分组ID
  deviceIdentification?: string; // 设备标识
  remark?: string; // 备注
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  createdOrgId?: string; // 创建人组织
}

export interface DeviceGroupRelResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  groupId?: string; // 分组ID
  deviceIdentification?: string; // 设备标识
  remark?: string; // 备注
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  createdOrgId?: string; // 创建人组织
}
