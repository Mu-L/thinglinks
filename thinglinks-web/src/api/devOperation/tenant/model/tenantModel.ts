import { FileResultVO } from '/@/api/thinglinks/file/model/uploadModel';
import { BaseOrgResultVO } from '/@/api/basic/user/model/baseOrgModel';

export interface TenantPageQuery {
  name: string;
  code: string;
  abbreviation: string;
  creditCode: string;
  contactPerson: string;
  contactPhone: string;
  contactEmail: string;
  provinceId: string;
  provinceName: string;
  cityId: string;
  cityName: string;
  districtId: string;
  districtName: string;
  address: string;
  registerType: string;
  connectType: string;
  status: string;
  state: boolean;
  reviewComments: string;
  readonly: boolean;
  createdName: string;
  expirationTime: string;
  describe: string;
  createdTime: string;
  createdBy: string;
  updatedTime: string;
  updatedBy: string;
}
export interface TenantSaveDTO {
  name: string;
  code: string;
  abbreviation?: string;
  creditCode?: string;
  contactPerson?: string;
  contactPhone?: string;
  contactEmail?: string;
  provinceId?: string;
  provinceName?: string;
  cityId?: string;
  cityName?: string;
  districtId?: string;
  districtName?: string;
  address?: string;
  state?: boolean;
  createdName?: string;
  expirationTime?: string;
  describe?: string;
  logo?: string;
  logos?: FileResultVO[];
}

export interface TenantUpdateDTO {
  id: string;
  name: string;
  code: string;
  abbreviation?: string;
  creditCode?: string;
  contactPerson?: string;
  contactPhone?: string;
  contactEmail?: string;
  provinceId?: string;
  provinceName?: string;
  cityId?: string;
  cityName?: string;
  districtId?: string;
  districtName?: string;
  address?: string;
  state?: boolean;
  createdName?: string;
  expirationTime?: string;
  describe?: string;
  logo?: string;
  logos?: FileResultVO[];
}

export interface Tenant {
  id: string;
  name: string;
  code: string;
  abbreviation: string;
  creditCode: string;
  contactPerson: string;
  contactPhone: string;
  contactEmail: string;
  provinceId: string;
  provinceName: string;
  cityId: string;
  cityName: string;
  districtId: string;
  districtName: string;
  address: string;
  registerType: string;
  connectType: string;
  status: string;
  state: boolean;
  readonly: boolean;
  createdName: string;
  reviewComments: string;
  expirationTime: string;
  describe: string;
  createdTime: string;
  createdBy: string;
  updatedTime: string;
  updatedBy: string;

  employeeState: boolean;
  isDefault: boolean;
  logo?: string;
  logos?: FileResultVO[];
  area?: string[];
  orgList?: BaseOrgResultVO[];
}

export interface DefTenantInitVO {
  id: string;
  connectType?: string;
  baseDatasourceId?: string;
  extendDatasourceId?: string;
}

export interface ReviewComment {
  id: string;
  status: string;
  reviewComments?: string;
}
