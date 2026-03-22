import {
  LinkageSaveVO,
  LinkageUpdateVO,
  LinkageResultVO,
  LinkagePageQuery,
} from './model/linkageModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'rule';
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
  GetRuleDetails: (id: string) => {
    return {
      url: `${ServicePrefix}/${MODULAR}/getRuleDetails/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveRule`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateRule`,
    method: RequestEnum.PUT,
  },
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteRule/${id}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
  Delete: {
    url: `${ServicePrefix}/${MODULAR}/deleteRules`,
    method: RequestEnum.DELETE,
  } as AxiosRequestConfig,
};

export const page = (params: PageParams<LinkagePageQuery>) =>
  defHttp.request<PageResult<LinkageResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<LinkageResultVO>({
    ...{
      url: `${ServicePrefix}/${MODULAR}/${id}`,
      method: RequestEnum.GET,
    },
    params: {},
  });
export const getRuleDetails = (id: string) =>
  defHttp.request<LinkageResultVO>({ ...Api.GetRuleDetails(id) });

export const save = (params: LinkageSaveVO) =>
  defHttp.request<LinkageResultVO>({ ...Api.Save, params });

export const update = (params: LinkageUpdateVO) =>
  defHttp.request<LinkageResultVO>({ ...Api.Update, params });

export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
export const changeStatus = (id: string, status: number) =>
  defHttp.request<any>({
    ...{
      url: `${ServicePrefix}/${MODULAR}/updateRuleStatus/${id}?status=${status}`,
      method: RequestEnum.PUT,
    },
    params: {},
  });
