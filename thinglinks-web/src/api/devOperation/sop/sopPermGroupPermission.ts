import {
  SopPermGroupPermissionSaveVO,
  SopPermGroupPermissionUpdateVO,
  SopPermGroupPermissionResultVO,
  SopPermGroupPermissionPageQuery,
} from './model/sopPermGroupPermissionModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'sopPermGroupPermission';
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
  defHttp.request<SopPermGroupPermissionResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<SopPermGroupPermissionPageQuery>) =>
  defHttp.request<PageResult<SopPermGroupPermissionResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<SopPermGroupPermissionResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: SopPermGroupPermissionPageQuery) =>
  defHttp.request<SopPermGroupPermissionResultVO[]>({ ...Api.Query, params });

export const save = (params: SopPermGroupPermissionSaveVO) =>
  defHttp.request<SopPermGroupPermissionResultVO>({ ...Api.Save, params });

export const update = (params: SopPermGroupPermissionUpdateVO) =>
  defHttp.request<SopPermGroupPermissionResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });

export const deleteByGroupId = (params: SopPermGroupPermissionSaveVO) =>
  defHttp.request<boolean>({
    url: `${ServicePrefix}/${MODULAR}/delete`,
    method: RequestEnum.POST,
    params,
  });
