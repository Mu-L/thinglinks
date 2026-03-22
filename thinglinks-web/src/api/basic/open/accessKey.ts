import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';
import { IsvInfoApplyForParam, IsvInfoSubmitParam, IsvInfoVO } from './model/accessKeyModel';

const MODULAR = 'sopIsvInfo';

export const Api = {
  Page: {
    url: `${ServicePrefixEnum.SOPADMIN}/${MODULAR}/open/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefixEnum.SOPADMIN}/${MODULAR}/open/update`,
    method: RequestEnum.PUT,
  } as AxiosRequestConfig,
  ApplyFor: {
    url: `${ServicePrefixEnum.SOPADMIN}/${MODULAR}/open/applyFor`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Submit: {
    url: `${ServicePrefixEnum.SOPADMIN}/${MODULAR}/open/submit`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
};

export const page = (params: PageParams<IsvInfoVO>) =>
  defHttp.request<PageResult<IsvInfoVO>>({ ...Api.Page, params });

export const getKeys = (isvId: string) =>
  defHttp.request<IsvInfoVO>({
    url: `${ServicePrefixEnum.SOPADMIN}/${MODULAR}/getKeys?isvId=${isvId}`,
    method: RequestEnum.GET,
  });

export const applyFor = (params: IsvInfoApplyForParam) =>
  defHttp.request<string>({ ...Api.ApplyFor, params });

export const update = (params: IsvInfoApplyForParam) =>
  defHttp.request<string>({ ...Api.Update, params });

export const submit = (params: IsvInfoSubmitParam) =>
  defHttp.request<string>({ ...Api.Submit, params });
