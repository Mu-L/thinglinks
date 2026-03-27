import {
  SopHelpDocSaveVO,
  SopHelpDocUpdateVO,
  SopHelpDocResultVO,
  SopHelpDocPageQuery,
} from './model/sopHelpDocModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'sopHelpDoc';
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
  Tree: {
    url: `${ServicePrefix}/${MODULAR}/tree`,
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
};

export const tree = (params?: SopHelpDocPageQuery) =>
  defHttp.request<SopHelpDocResultVO>({ ...Api.Tree, params });

export const copy = (id: string) =>
  defHttp.request<SopHelpDocResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<SopHelpDocPageQuery>) =>
  defHttp.request<PageResult<SopHelpDocResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<SopHelpDocResultVO>({ ...Api.Detail, params: { id } });

export const save = (params: SopHelpDocSaveVO) =>
  defHttp.request<SopHelpDocResultVO>({ ...Api.Save, params });

export const update = (params: SopHelpDocUpdateVO) =>
  defHttp.request<SopHelpDocResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
