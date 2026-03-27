import { SopNotifyInfoResultVO, SopNotifyInfoPageQuery } from './model/sopNotifyInfoModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { ContentTypeEnum, RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';
import { IdVO } from '../../thinglinks/common/model/optionsModel';

const MODULAR = 'sopNotifyInfo';
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
  Push: {
    url: `${ServicePrefix}/${MODULAR}/push`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  End: {
    url: `${ServicePrefix}/${MODULAR}/end`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
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
  defHttp.request<SopNotifyInfoResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<SopNotifyInfoPageQuery>) =>
  defHttp.request<PageResult<SopNotifyInfoResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<SopNotifyInfoResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: SopNotifyInfoPageQuery) =>
  defHttp.request<SopNotifyInfoResultVO[]>({ ...Api.Query, params });

export const push = (id: string, url?: string) =>
  defHttp.request<boolean>({
    ...Api.Push,
    params: { id, url },
    headers: { 'Content-Type': ContentTypeEnum.FORM_URLENCODED },
  });

export const end = (params: IdVO) => defHttp.request<boolean>({ ...Api.End, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
