// import { any } from "vue-types";
export interface DeviceLocationResultVO {
  id: number; // id
  cityCode: string; // 	市编码
  createdOrgId?: string; // 创建人组织
  fullName: string; // 		位置名称
  latitude: string; // 纬度
  longitude: string; // 	经度
  provinceCode: string; // 省,直辖市编码
  regionCode: string; // 区县
  remark?: string; // 备注
}

export interface DeviceLocationSaveVO {
  cityCode: string; // 	市编码
  deviceIdentification: string; // 设备标识
  createdOrgId?: string; // 创建人组织
  fullName: string; // 		位置名称
  latitude: string; // 纬度
  longitude: string; // 	经度
  provinceCode: string; // 省,直辖市编码
  regionCode: string; // 区县
  remark?: string; // 备注
}

export interface DeviceLocationUpdateVO {
  id: number; // id
  cityCode: string; // 	市编码
  createdOrgId?: string; // 创建人组织
  fullName: string; // 		位置名称
  latitude: string; // 纬度
  longitude: string; // 	经度
  provinceCode: string; // 省,直辖市编码
  regionCode: string; // 区县
  remark?: string; // 备注
}