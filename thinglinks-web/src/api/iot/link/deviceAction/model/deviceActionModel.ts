// import { any } from "vue-types";

export interface DeviceActionPageQuery {
  id: number; // id
  actionType?: string; // 动作类型
  deviceIdentification?: string; // 	设备标识
  message?: string; // 	内容信息
  status?: string; // 状态
  remark?: string; // 备注
}

export interface DeviceActionSaveVO {
  actionType?: string; // 动作类型
  deviceIdentification?: string; // 	设备标识
  message?: string; // 	内容信息
  status?: string; // 状态
  remark?: string; // 备注
}

export interface DeviceActionUpdateVO {
  id: number; // id
  actionType?: string; // 动作类型
  deviceIdentification?: string; // 	设备标识
  message?: string; // 	内容信息
  status?: string; // 状态
  remark?: string; // 备注
}

export interface DeviceActionResultVO {
  echoMap?: any;
  id: number; // id
  actionType?: string; // 动作类型
  deviceIdentification?: string; // 	设备标识
  message?: string; // 	内容信息
  status?: string; // 状态
  remark?: string; // 备注
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  
}