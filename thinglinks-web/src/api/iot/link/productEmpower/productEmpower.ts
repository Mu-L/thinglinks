import {
  ProductPropertySaveVO,
  ProductPropertyUpdateVO,
  ProductPropertyResultVO,
  ProductPropertyPageQuery,
} from './model/productEmpowerModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'empowermentRecord';
// tips: 建议在ServicePrefixEnum中新增：LINK = '/link'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.LINK;
// tips: /link 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
const ServicePrefix = ServicePrefixEnum.LINK;

export const Api = {
  Page: {
    url: `${ServicePrefix}/${MODULAR}/page`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Detail: {
    url: `${ServicePrefix}/${MODULAR}/detail`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveProductEmpowerment`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateEmpowermentRecord`,
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
  defHttp.request<ProductPropertyResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<ProductPropertyPageQuery>) =>
  defHttp.request<PageResult<ProductPropertyResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<ProductPropertyResultVO>({ ...Api.Detail, params: { id } });

export const query = (params: ProductPropertyPageQuery) =>
  defHttp.request<ProductPropertyResultVO[]>({ ...Api.Query, params });

export const save = (params: ProductPropertySaveVO) =>
  defHttp.request<ProductPropertyResultVO>({ ...Api.Save, params });

export const update = (params: ProductPropertyUpdateVO) =>
  defHttp.request<ProductPropertyResultVO>({ ...Api.Update, params });

export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });
