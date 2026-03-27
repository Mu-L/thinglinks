import {
  SopDocInfoSaveVO,
  SopDocInfoUpdateVO,
  SopDocInfoResultVO,
  SopDocInfoPageQuery,
} from './model/sopDocInfoModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { ContentTypeEnum, RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';
import { IdVO } from '../../thinglinks/common/model/optionsModel';

const MODULAR = 'sopDocInfo';
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
  defHttp.request<SopDocInfoResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<SopDocInfoPageQuery>) =>
  defHttp.request<PageResult<SopDocInfoResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<SopDocInfoResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: SopDocInfoPageQuery) =>
  defHttp.request<SopDocInfoResultVO[]>({ ...Api.Query, params });

export const tree = (params: SopDocInfoPageQuery) =>
  defHttp.request<SopDocInfoResultVO[]>({
    url: `${ServicePrefix}/${MODULAR}/tree`,
    method: RequestEnum.POST,
    params,
    headers: {
      'Content-Type': ContentTypeEnum.FORM_URLENCODED,
    },
  });

export const publish = (id: string, isPublish: number) =>
  defHttp.request<boolean>({
    url: `${ServicePrefix}/${MODULAR}/publish`,
    method: RequestEnum.POST,
    params: { id, isPublish },
    headers: {
      'Content-Type': ContentTypeEnum.FORM_URLENCODED,
    },
  });

export const syncAppDoc = (params: IdVO) =>
  defHttp.request<boolean>({
    url: `${ServicePrefix}/${MODULAR}/syncAppDoc`,
    method: RequestEnum.POST,
    params,
  });

export const syncDoc = (params: IdVO) =>
  defHttp.request<boolean>({
    url: `${ServicePrefix}/${MODULAR}/syncDoc`,
    method: RequestEnum.POST,
    params,
  });

export const save = (params: SopDocInfoSaveVO) =>
  defHttp.request<SopDocInfoResultVO>({ ...Api.Save, params });

export const update = (params: SopDocInfoUpdateVO) =>
  defHttp.request<SopDocInfoResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
