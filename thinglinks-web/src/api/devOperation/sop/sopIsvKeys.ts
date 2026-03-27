import {
  SopIsvKeysSaveVO,
  SopIsvKeysUpdateVO,
  SopIsvKeysResultVO,
  SopIsvKeysPageQuery,
} from './model/sopIsvKeysModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'sopIsvKeys';
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
  defHttp.request<SopIsvKeysResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<SopIsvKeysPageQuery>) =>
  defHttp.request<PageResult<SopIsvKeysResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<SopIsvKeysResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: SopIsvKeysPageQuery) =>
  defHttp.request<SopIsvKeysResultVO[]>({ ...Api.Query, params });

export const save = (params: SopIsvKeysSaveVO) =>
  defHttp.request<SopIsvKeysResultVO>({ ...Api.Save, params });

export const update = (params: SopIsvKeysUpdateVO) =>
  defHttp.request<SopIsvKeysResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
