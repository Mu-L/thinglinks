// import { any } from "vue-types";

export interface DeviceCommandPageQuery {
  id: number; // id
  commandType?: number; // 命令类型
  commandIdentification?: string; // 	命令标识
  deviceIdentification?: string; // 	设备标识
  content?: string; // 	内容信息
  status?: string; // 状态
  remark?: string; // 备注
}
export interface DeviceCommandWrapper {
  parallel: any; // 串行
  serial: any; //  并行
}
