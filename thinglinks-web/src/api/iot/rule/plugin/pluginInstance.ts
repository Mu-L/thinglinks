import {
  PluginInstanceSaveVO,
  PluginInstanceUpdateVO,
  PluginInstanceResultVO,
  PluginInstancePageQuery,
} from './model/pluginInstanceModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'pluginInstance';
// tips: 建议在ServicePrefixEnum中新增：RULE = '/rule'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.RULE;
// tips: /rule 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
// const ServicePrefix = ServicePrefixEnum.RULE;
const ServicePrefix = ServicePrefixEnum.RULE;

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
    url: `${ServicePrefix}/${MODULAR}/savePluginInstance`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updatePluginInstance`,
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
  Available: {
    url: `${ServicePrefix}/${MODULAR}/getAvailableInstances`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
  ServerMonitor: {
    url: `${ServicePrefix}/ruleDashboardStats/getRuleServerMonitor`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
};

export const copy = (id: string) =>
  defHttp.request<PluginInstanceResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<PluginInstancePageQuery>) =>
  defHttp.request<PageResult<PluginInstanceResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<PluginInstanceResultVO>({ ...Api.Detail, params: { id } });

export const available = () => defHttp.request<PluginInstanceResultVO>({ ...Api.Available });
export const ruleServerMonitor = () => defHttp.request({ ...Api.ServerMonitor });

export const query = (params: PluginInstancePageQuery) =>
  defHttp.request<PluginInstanceResultVO[]>({ ...Api.Query, params });

export const save = (params: PluginInstanceSaveVO) =>
  defHttp.request<PluginInstanceResultVO>({ ...Api.Save, params });

export const update = (params: PluginInstanceUpdateVO) =>
  defHttp.request<PluginInstanceResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
