import {
  SopPermGroupSaveVO,
  SopPermGroupUpdateVO,
  SopPermGroupResultVO,
  SopPermGroupPageQuery,
} from './model/sopPermGroupModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'sopPermGroup';
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
  defHttp.request<SopPermGroupResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<SopPermGroupPageQuery>) =>
  defHttp.request<PageResult<SopPermGroupResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<SopPermGroupResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: SopPermGroupPageQuery) =>
  defHttp.request<SopPermGroupResultVO[]>({ ...Api.Query, params });

export const save = (params: SopPermGroupSaveVO) =>
  defHttp.request<SopPermGroupResultVO>({ ...Api.Save, params });

export const update = (params: SopPermGroupUpdateVO) =>
  defHttp.request<SopPermGroupResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });

export const listByGroupId = (isvId: string) =>
  defHttp.request<string[]>({
    url: `${ServicePrefix}/${MODULAR}/listByGroupId?isvId=${isvId}`,
    method: RequestEnum.GET,
  });

export const updateIsvGroup = (params: SopPermGroupSaveVO) =>
  defHttp.request<boolean>({
    url: `${ServicePrefix}/${MODULAR}/updateIsvGroup`,
    method: RequestEnum.POST,
    params,
  });
