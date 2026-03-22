import {
  RuleGroovyScriptSaveVO,
  RuleGroovyScriptUpdateVO,
  RuleGroovyScriptResultVO,
  RuleGroovyScriptPageQuery,
} from './model/ruleGroovyScriptModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'groovyScript';
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
    url: `${ServicePrefix}/${MODULAR}/saveGroovyScript`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateGroovyScript`,
    method: RequestEnum.PUT,
  },
  Delete: {
    url: `${ServicePrefix}/${MODULAR}`,
    method: RequestEnum.DELETE,
  } as AxiosRequestConfig,
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteGroovyScript/${id}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
  Query: {
    url: `${ServicePrefix}/${MODULAR}/query`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  RunCompile: {
    url: `${ServicePrefix}/${MODULAR}/runDirectCompile`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
};

export const copy = (id: string) =>
  defHttp.request<RuleGroovyScriptResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<RuleGroovyScriptPageQuery>) =>
  defHttp.request<PageResult<RuleGroovyScriptResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<RuleGroovyScriptResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: RuleGroovyScriptPageQuery) =>
  defHttp.request<RuleGroovyScriptResultVO[]>({ ...Api.Query, params });

export const save = (params: RuleGroovyScriptSaveVO) =>
  defHttp.request<RuleGroovyScriptResultVO>({ ...Api.Save, params });

export const update = (params: RuleGroovyScriptUpdateVO) =>
  defHttp.request<RuleGroovyScriptResultVO>({ ...Api.Update, params });
export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
export const runCompile = (params: RuleGroovyScriptPageQuery) =>
  defHttp.request<RuleGroovyScriptResultVO[]>({ ...Api.RunCompile, params });
