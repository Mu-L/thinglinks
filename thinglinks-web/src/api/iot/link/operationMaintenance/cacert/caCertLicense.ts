import {
  CaCertLicenseSaveVO,
  CaCertLicenseUpdateVO,
  CaCertLicenseResultVO,
  CaCertLicensePageQuery,
} from './model/caCertLicenseModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'caCertLicense';
// tips: 建议在ServicePrefixEnum中新增：LINK = '/link'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.LINK;
// tips: /link 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
const ServicePrefix = ServicePrefixEnum.LINK;

export const Api = {
  Page: {
    url: `${ServicePrefix}/${MODULAR}/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Detail: {
    url: `${ServicePrefix}/${MODULAR}/detail`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
  Copy: {
    url: `${ServicePrefix}/${MODULAR}/copy`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Save: {
    url: `${ServicePrefix}/${MODULAR}`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}`,
    method: RequestEnum.PUT,
  },
  Delete: {
    url: `${ServicePrefix}/${MODULAR}`,
    method: RequestEnum.DELETE,
  } as AxiosRequestConfig,
  Query: {
    url: `${ServicePrefix}/${MODULAR}/query`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Import: {
    url: `${ServicePrefix}/${MODULAR}/importPemCertificate`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
};

export const copy = (id: string) =>
  defHttp.request<CaCertLicenseResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<CaCertLicensePageQuery>) =>
  defHttp.request<PageResult<CaCertLicenseResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<CaCertLicenseResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: CaCertLicensePageQuery) =>
  defHttp.request<CaCertLicenseResultVO[]>({ ...Api.Query, params });

export const save = (params: CaCertLicenseSaveVO) =>
  defHttp.request<CaCertLicenseResultVO>({ ...Api.Save, params });

export const update = (params: CaCertLicenseUpdateVO) =>
  defHttp.request<CaCertLicenseResultVO>({ ...Api.Update, params });

export const importPemCertificate = (params: CaCertLicenseSaveVO) =>
  defHttp.request<CaCertLicenseResultVO>({ ...Api.Import, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
