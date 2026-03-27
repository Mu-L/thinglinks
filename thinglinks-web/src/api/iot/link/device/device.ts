import {
  DeviceSaveVO,
  DeviceUpdateVO,
  DeviceResultVO,
  DevicePageQuery,
  DeviceStatusVO,
  UploadFormData,
} from './model/deviceModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum, ContentTypeEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'device';
// tips: 建议在ServicePrefixEnum中新增：LINK = '/link'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.LINK;
// tips: /link 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
const ServicePrefix = ServicePrefixEnum.LINK;

export const Api = {
  Page: {
    url: `${ServicePrefix}/${MODULAR}/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Detail: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/getDeviceDetails/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  DetailBydeviceIdentification: function (deviceIdentification: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/getDeviceDetailsByIdentification/${deviceIdentification}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  DetailBydeviceIdentifications: {
    url: `${ServicePrefix}/${MODULAR}/getDeviceDetailsByIdentifications`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
  Copy: {
    url: `${ServicePrefix}/${MODULAR}/copy`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveDevice`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateDevice`,
    method: RequestEnum.PUT,
  },
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteDevice/${id}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
  Delete: {
    url: `${ServicePrefix}/${MODULAR}/deleteDevices`,
    method: RequestEnum.DELETE,
  } as AxiosRequestConfig,
  Disconnect: function (deviceIdentification: string) {
    return {
      url: `${ServicePrefix}/deviceAction/disconnect/${deviceIdentification}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
  Query: {
    url: `${ServicePrefix}/${MODULAR}/query`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Overview: {
    url: `${ServicePrefix}/${MODULAR}/deviceOverview`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
  Status: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/updateDeviceStatus/${id}`,
      method: RequestEnum.PUT,
      headers: {
        'Content-Type': ContentTypeEnum.FORM_URLENCODED,
      },
    } as AxiosRequestConfig;
  },
  GetDeviceDetailsPage: {
    url: `${ServicePrefix}/${MODULAR}/getDeviceDetailsPage`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  BulkExport: {
    url: `${ServicePrefix}/${MODULAR}/exportDevices`,
    responseType: 'blob',
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  ImportDevice: {
    url: `${ServicePrefix}/${MODULAR}/importDeviceExcel`,
    responseType: 'blob',
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  /**
   * 获取待升级的原版本号
   * @param productIdentification 产品标识
   * @returns 原版本号
   */
  GetSourceVersions: function (productIdentification: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/getDeviceVersionByProduct/${productIdentification}`,
      method: RequestEnum.GET,
    };
  } as AxiosRequestConfig,
};

export const copy = (id: string) =>
  defHttp.request<DeviceResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<DevicePageQuery>) =>
  defHttp.request<PageResult<DeviceResultVO>>({ ...Api.Page, params });

export const detail = (id: string) => defHttp.request<DeviceResultVO>({ ...Api.Detail(id) });
export const detailBydeviceIdentification = (deviceIdentification: string) =>
  defHttp.request<DeviceResultVO>({ ...Api.DetailBydeviceIdentification(deviceIdentification) });

export const detailBydeviceIdentifications = (params: String) =>
  defHttp.request<DeviceResultVO[]>({ ...Api.DetailBydeviceIdentifications, params });

export const deviceQuery = (params: DevicePageQuery) =>
  defHttp.request<DeviceResultVO[]>({ ...Api.Query, params });

export const save = (params: DeviceSaveVO) =>
  defHttp.request<DeviceResultVO>({ ...Api.Save, params });

export const update = (params: DeviceUpdateVO) =>
  defHttp.request<DeviceResultVO>({ ...Api.Update, params });

export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
export const disconnect = (deviceIdentification: string) =>
  defHttp.request<boolean>({ ...Api.Disconnect(deviceIdentification) });

export const overview = () => defHttp.request<DeviceResultVO>({ ...Api.Overview, params: {} });

export const statusChange = (id: string, params: DeviceSaveVO) =>
  defHttp.request<DeviceStatusVO>({ ...Api.Status(id), params });

export const getDeviceDetailsPage = (params: DeviceSaveVO) =>
  defHttp.request<DeviceResultVO>({ ...Api.GetDeviceDetailsPage, params });
export const bulkExport = (params: string[]) =>
  defHttp.request<any>({ ...Api.BulkExport, params }, { isTransformResponse: false });
export const importDeviceFile = (params: UploadFormData) =>
  defHttp.request<any>({ ...Api.ImportDevice, params }, { isReturnNativeResponse: true });

export const getDeviceVersionByProduct = (productIdentification: string) =>
  defHttp.request<string[]>({ ...Api.GetSourceVersions(productIdentification) });
