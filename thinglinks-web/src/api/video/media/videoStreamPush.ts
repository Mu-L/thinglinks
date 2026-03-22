import {
  VideoStreamPushSaveVO,
  VideoStreamPushUpdateVO,
  VideoStreamPushResultVO,
  VideoStreamPushPageQuery,
} from './model/videoStreamPushModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'videoStreamPush';
// tips: 建议在ServicePrefixEnum中新增：VIDEO = '/video'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.VIDEO;
// tips: /video 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
// const ServicePrefix = ServicePrefixEnum.VIDEO;
const ServicePrefix = '/video';

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
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteStreamPush/${id}`,
      method: RequestEnum.DELETE,
    };
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
  defHttp.request<VideoStreamPushResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<VideoStreamPushPageQuery>) =>
  defHttp.request<PageResult<VideoStreamPushResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<VideoStreamPushResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: VideoStreamPushPageQuery) =>
  defHttp.request<VideoStreamPushResultVO[]>({ ...Api.Query, params });

export const save = (params: VideoStreamPushSaveVO) =>
  defHttp.request<VideoStreamPushResultVO>({ ...Api.Save, params });

export const update = (params: VideoStreamPushUpdateVO) =>
  defHttp.request<VideoStreamPushResultVO>({ ...Api.Update, params });
export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
