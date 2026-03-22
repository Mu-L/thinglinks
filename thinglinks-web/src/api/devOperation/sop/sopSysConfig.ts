import {
  SopSysConfigSaveVO,
  SopSysConfigUpdateVO,
  SopSysConfigResultVO,
  SopSysConfigPageQuery,
} from './model/sopSysConfigModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import type { AxiosRequestConfig } from 'axios';
import { ServicePrefixEnum } from '/@/enums/commonEnum';

const MODULAR = 'sopSysConfig';
const ServicePrefix = ServicePrefixEnum.SOPADMIN;

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
};

export const copy = (id: string) =>
  defHttp.request<SopSysConfigResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<SopSysConfigPageQuery>) =>
  defHttp.request<PageResult<SopSysConfigResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<SopSysConfigResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: SopSysConfigPageQuery) =>
  defHttp.request<SopSysConfigResultVO[]>({ ...Api.Query, params });

export const save = (params: SopSysConfigSaveVO) =>
  defHttp.request<SopSysConfigResultVO>({ ...Api.Save, params });

export const update = (params: SopSysConfigUpdateVO) =>
  defHttp.request<SopSysConfigResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
