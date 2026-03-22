import {
  CardSimInfoSaveVO,
  CardSimInfoUpdateVO,
  CardSimInfoResultVO,
  CardSimInfoPageQuery,
} from './model/cardSimInfoModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
// import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'cardSimInfo';
// tips: 建议在ServicePrefixEnum中新增：CARD = '/card'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.CARD;
// tips: /card 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
// const ServicePrefix = ServicePrefixEnum.CARD;
const ServicePrefix = '/card';

export const Api = {
  Page: {
    url: `${ServicePrefix}/${MODULAR}/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Detail: {
    url: `${ServicePrefix}/${MODULAR}/detail`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
  InfoDetail: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/getCardSimInfoDetails/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  Copy: {
    url: `${ServicePrefix}/${MODULAR}/copy`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveCardSimInfo`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateCardSimInfo`,
    method: RequestEnum.PUT,
  },
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteCardSimInfo/${id}`,
      method: RequestEnum.DELETE,
    } as AxiosRequestConfig;
  },
  Delete: {
    url: `${ServicePrefix}/${MODULAR}/deleteBatchCardSimInfo`,
    method: RequestEnum.DELETE,
  } as AxiosRequestConfig,
  Query: {
    url: `${ServicePrefix}/${MODULAR}/query`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Overview: {
    url: `${ServicePrefix}/${MODULAR}/cardSimOverview`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
};

export const copy = (id: number) =>
  defHttp.request<CardSimInfoResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<CardSimInfoPageQuery>) =>
  defHttp.request<PageResult<CardSimInfoResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<CardSimInfoResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: CardSimInfoPageQuery) =>
  defHttp.request<CardSimInfoResultVO[]>({ ...Api.Query, params });

export const save = (params: CardSimInfoSaveVO) =>
  defHttp.request<CardSimInfoResultVO>({ ...Api.Save, params });

export const overview = () => defHttp.request<CardSimInfoSaveVO>({ ...Api.Overview, params: {} });

export const update = (params: CardSimInfoUpdateVO) =>
  defHttp.request<CardSimInfoResultVO>({ ...Api.Update, params });

export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
export const getInfoDetail = (id: string) => defHttp.request<any>({ ...Api.InfoDetail(id) });
