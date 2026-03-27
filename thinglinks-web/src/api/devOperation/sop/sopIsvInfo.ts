import {
  SopIsvInfoSaveVO,
  SopIsvInfoUpdateVO,
  SopIsvInfoResultVO,
  SopIsvInfoPageQuery,
  SopIsvInfoApplyForVO,
  SopIsvInfoSubmitVO,
} from './model/sopIsvInfoModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';
import { AudioVO } from '../../thinglinks/common/model/optionsModel';
import {
  IsvKeysGenVO,
  KeyStore,
  SopIsvKeysResultVO,
  SopIsvKeysUpdateVO,
} from './model/sopIsvKeysModel';

const MODULAR = 'sopIsvInfo';
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
  UpdateKeys: {
    url: `${ServicePrefix}/${MODULAR}/updateKeys`,
    method: 'POST',
  } as AxiosRequestConfig,
  ApplyFor: {
    url: `${ServicePrefix}/${MODULAR}/open/applyFor`,
    method: 'POST',
  } as AxiosRequestConfig,
  Submit: {
    url: `${ServicePrefix}/${MODULAR}/open/submit`,
    method: 'POST',
  } as AxiosRequestConfig,
};

export const copy = (id: string) =>
  defHttp.request<SopIsvInfoResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<SopIsvInfoPageQuery>) =>
  defHttp.request<PageResult<SopIsvInfoResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<SopIsvInfoResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: SopIsvInfoPageQuery) =>
  defHttp.request<SopIsvInfoResultVO[]>({ ...Api.Query, params });

export const save = (params: SopIsvInfoSaveVO) =>
  defHttp.request<SopIsvInfoResultVO>({ ...Api.Save, params });

export const update = (params: SopIsvInfoUpdateVO) =>
  defHttp.request<SopIsvInfoResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });

export const examine = (params: AudioVO) =>
  defHttp.request<string>({
    url: `${ServicePrefix}/${MODULAR}/examine`,
    method: 'POST',
    params,
  });

export const getKeys = (isvId: string) =>
  defHttp.request<SopIsvKeysResultVO>({
    url: `${ServicePrefix}/${MODULAR}/getKeys?isvId=${isvId}`,
    method: 'GET',
  });

export const createKeys = (params: IsvKeysGenVO) =>
  defHttp.request<KeyStore>({
    url: `${ServicePrefix}/${MODULAR}/createKeys`,
    method: 'POST',
    params,
  });

export const updateKeys = (params: SopIsvKeysUpdateVO) =>
  defHttp.request<boolean>({
    ...Api.UpdateKeys,
    params,
  });

export const openPage = (params: PageParams<SopIsvInfoPageQuery>) =>
  defHttp.request<PageResult<SopIsvInfoResultVO>>({
    url: `${ServicePrefix}/${MODULAR}/open/page`,
    method: 'POST',
    params,
  });

export const applyFor = (params: SopIsvInfoApplyForVO) =>
  defHttp.request<string>({
    ...Api.ApplyFor,
    params,
  });

export const submit = (params: SopIsvInfoSubmitVO) =>
  defHttp.request<string>({
    ...Api.Submit,
    params,
  });
