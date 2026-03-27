import {
  AlarmRecordSaveVO,
  AlarmRecordUpdateVO,
  AlarmRecordResultVO,
  AlarmRecordPageQuery,
} from './model/recordModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'ruleAlarmRecord';
const ServicePrefix = ServicePrefixEnum.RULE;

export const Api = {
  Page: {
    url: `${ServicePrefix}/${MODULAR}/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Detail: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/getAlarmRecordDetails/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveAlarmRecord`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateAlarmRecord`,
    method: RequestEnum.PUT,
  },
  Solve: {
    url: `${ServicePrefix}/${MODULAR}/handleOrSolveAlarmRecord`,
    method: RequestEnum.PUT,
  },
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteAlarmRecord/${id}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
  Delete: {
    url: `${ServicePrefix}/${MODULAR}`,
    method: RequestEnum.DELETE,
  } as AxiosRequestConfig,
};

export const page = (params: PageParams<AlarmRecordPageQuery>) =>
  defHttp.request<PageResult<AlarmRecordResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<AlarmRecordResultVO>({
    ...{
      url: `${ServicePrefix}/${MODULAR}/getAlarmRecordDetails/${id}`,
      method: RequestEnum.GET,
    },
    params: {},
  });

export const save = (params: AlarmRecordSaveVO) =>
  defHttp.request<AlarmRecordResultVO>({ ...Api.Save, params });

export const update = (params: AlarmRecordUpdateVO) =>
  defHttp.request<AlarmRecordResultVO>({ ...Api.Update, params });

export const solve = (params: AlarmRecordUpdateVO) =>
  defHttp.request<AlarmRecordResultVO>({ ...Api.Solve, params });

export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
