import { DeviceLocationResultVO, DeviceLocationSaveVO, DeviceLocationUpdateVO } from './model/deviceLocationModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum, ContentTypeEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'deviceLocation';
// tips: 建议在ServicePrefixEnum中新增：LINK = '/link'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.LINK;
// tips: /link 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
const ServicePrefix = ServicePrefixEnum.LINK;

export const Api = {
  Detail: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveDeviceLocation`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateDeviceLocation`,
    method: RequestEnum.PUT,
  },
  Page: {
    url: `${ServicePrefix}/${MODULAR}/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
};


export const deviceLocationDetail = (id: string) => defHttp.request<DeviceLocationResultVO>({ ...Api.Detail(id) });

export const saveDeviceLocation = (params: DeviceLocationSaveVO) =>
  defHttp.request<DeviceLocationSaveVO>({ ...Api.Save, params });

export const updateDeviceLocation = (params: DeviceLocationUpdateVO) =>
  defHttp.request<DeviceLocationUpdateVO>({ ...Api.Update, params });

export const getDeviceLocationPage = (params: DeviceLocationUpdateVO) =>
  defHttp.request<DeviceLocationUpdateVO>({ ...Api.Page, params });