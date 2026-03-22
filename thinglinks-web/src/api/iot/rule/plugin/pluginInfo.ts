import {
  PluginInfoSaveVO,
  PluginInfoUpdateVO,
  PluginInfoResultVO,
  PluginInfoPageQuery,
} from './model/pluginInfoModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'pluginInfo';
// tips: 建议在ServicePrefixEnum中新增：RULE = '/rule'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.RULE;
// tips: /rule 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
// const ServicePrefix = ServicePrefixEnum.RULE;
const ServicePrefix = ServicePrefixEnum.RULE;

export const Api = {
  Page: {
    url: `${ServicePrefix}/${MODULAR}/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Detail: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/getPluginInfoDetails/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  Copy: {
    url: `${ServicePrefix}/${MODULAR}/copy`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Save: {
    url: `${ServicePrefix}/${MODULAR}/savePlugin`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updatePlugin`,
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
  Preload: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/preloadPlugin/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  Install: {
    url: `${ServicePrefix}/${MODULAR}/installOrUninstall`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  InstallAll: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/uninstallAllPluginsForInstances/${id}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
};

export const copy = (id: string) =>
  defHttp.request<PluginInfoResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<PluginInfoPageQuery>) =>
  defHttp.request<PageResult<PluginInfoResultVO>>({ ...Api.Page, params });
export const detail = (id: string) => defHttp.request<PluginInfoResultVO>({ ...Api.Detail(id) });
export const preload = (id: string) => defHttp.request<PluginInfoResultVO>({ ...Api.Preload(id) });
export const install = (params: PluginInfoSaveVO) =>
  defHttp.request<PluginInfoResultVO>({ ...Api.Install, params });
export const installAll = (id: string) =>
  defHttp.request<PluginInfoResultVO>({ ...Api.InstallAll(id) });
export const query = (params: PluginInfoPageQuery) =>
  defHttp.request<PluginInfoResultVO[]>({ ...Api.Query, params });

export const save = (params: PluginInfoSaveVO) =>
  defHttp.request<PluginInfoResultVO>({ ...Api.Save, params });

export const update = (params: PluginInfoUpdateVO) =>
  defHttp.request<PluginInfoResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
