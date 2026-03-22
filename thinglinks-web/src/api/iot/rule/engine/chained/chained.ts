import {
  RuleInstanceSaveVO,
  RuleInstanceUpdateVO,
  RuleInstanceResultVO,
  RuleInstancePageQuery,
} from './model/chainedModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'ruleInstance';
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
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveRuleInstance`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateRuleInstance`,
    method: RequestEnum.PUT,
  },
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteRuleInstance/${id}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
};

export const page = (params: PageParams<RuleInstancePageQuery>) =>
  defHttp.request<PageResult<RuleInstanceResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<RuleInstanceResultVO>({
    ...{
      url: `${ServicePrefix}/${MODULAR}/${id}`,
      method: RequestEnum.GET,
    },
    params: {},
  });

export const save = (params: RuleInstanceSaveVO) =>
  defHttp.request<RuleInstanceResultVO>({ ...Api.Save, params });

export const update = (params: RuleInstanceUpdateVO) =>
  defHttp.request<RuleInstanceResultVO>({ ...Api.Update, params });

export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const changeStatus = (id: string, status: number) =>
  defHttp.request<any>({
    ...{
      url: `${ServicePrefix}/${MODULAR}/updateRuleInstanceStatus/${id}?status=${status}`,
      method: RequestEnum.PUT,
    },
    params: {},
  });
