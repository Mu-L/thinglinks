import {
  ChannelSaveVO,
  ChannelUpdateVO,
  ChannelResultVO,
  ChannelPageQuery,
} from './model/channelModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'ruleAlarmChannel';
const ServicePrefix = ServicePrefixEnum.RULE;

export const Api = {
  Page: {
    url: `${ServicePrefix}/${MODULAR}/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Detail: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/getAlarmChannelDetails/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  Copy: {
    url: `${ServicePrefix}/${MODULAR}/copy`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveAlarmChannel`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateAlarmChannel`,
    method: RequestEnum.PUT,
  },
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteAlarmChannel/${id}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
  Delete: {
    url: `${ServicePrefix}/${MODULAR}`,
    method: RequestEnum.DELETE,
  } as AxiosRequestConfig,
  Query: {
    url: `${ServicePrefix}/${MODULAR}/query`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Get: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
};

export const copy = (id: string) =>
  defHttp.request<ChannelResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<ChannelPageQuery>) =>
  defHttp.request<PageResult<ChannelResultVO>>({ ...Api.Page, params });

export const detail = (id: string) => defHttp.request<ChannelResultVO>({ ...Api.Detail(id) });

export const query = (params: ChannelPageQuery) =>
  defHttp.request<ChannelResultVO[]>({ ...Api.Query, params });

export const save = (params: ChannelSaveVO) =>
  defHttp.request<ChannelResultVO>({ ...Api.Save, params });

export const update = (params: ChannelUpdateVO) =>
  defHttp.request<ChannelResultVO>({ ...Api.Update, params });

export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });

export const get = (id: string) => defHttp.request<ChannelResultVO>({ ...Api.Get(id) });
