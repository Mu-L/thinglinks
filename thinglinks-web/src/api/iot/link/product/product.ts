import {
  ProductSaveVO,
  ProductUpdateVO,
  ProductResultVO,
  ProductPageQuery,
  ProductQuickSaveVO,
} from './model/productModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'product';
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
  Copy: {
    url: `${ServicePrefix}/${MODULAR}/copy`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Save: {
    url: `${ServicePrefix}/${MODULAR}/saveProduct`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Update: {
    url: `${ServicePrefix}/${MODULAR}/updateProduct`,
    method: RequestEnum.PUT,
  },
  DeleteSingle: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/deleteProduct/${id}`,
      method: RequestEnum.DELETE,
    };
  },
  Delete: {
    url: `${ServicePrefix}/${MODULAR}/deleteProducts`,
    method: RequestEnum.DELETE,
  } as AxiosRequestConfig,
  Query: {
    url: `${ServicePrefix}/${MODULAR}/query`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  Quick: {
    url: `${ServicePrefix}/${MODULAR}/generateProductJson`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  ExportJson: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/exportJson/${id}`,
      method: RequestEnum.GET,
      responseType: 'blob',
    } as AxiosRequestConfig;
  },
  ImportProductJsonFile: {
    url: `${ServicePrefix}/${MODULAR}/importProductJsonFile`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  GetFullProductInfo: function (id: string) {
    return {
      url: `${ServicePrefix}/${MODULAR}/getFullInfo/${id}`,
      method: RequestEnum.GET,
    } as AxiosRequestConfig;
  },
  GetFullProductInfos: {
    url: `${ServicePrefix}/${MODULAR}/getFullInfos`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
};

export const copy = (id: string) =>
  defHttp.request<ProductResultVO>({ ...Api.Copy, params: { id } });

export const page = (params: PageParams<ProductPageQuery>) =>
  defHttp.request<PageResult<ProductResultVO>>({ ...Api.Page, params });

export const detail = (id: string) =>
  defHttp.request<ProductResultVO>({
    ...{
      url: `${ServicePrefix}/${MODULAR}/${id}`,
      method: RequestEnum.GET,
    },
    params: {},
  });

export const query = (params: ProductPageQuery) =>
  defHttp.request<ProductResultVO[]>({ ...Api.Query, params });

export const save = (params: ProductSaveVO) =>
  defHttp.request<ProductResultVO>({ ...Api.Save, params });

export const update = (params: ProductUpdateVO) =>
  defHttp.request<ProductResultVO>({ ...Api.Update, params });

export const deleteSingle = (id: string) => defHttp.request<boolean>({ ...Api.DeleteSingle(id) });
export const remove = (params: string[]) => defHttp.request<boolean>({ ...Api.Delete, params });

export const quick = (params: ProductQuickSaveVO) =>
  defHttp.request<ProductResultVO>(
    { ...Api.Quick, params },
    {
      errorMessageMode: 'notification',
      successMessageMode: 'notification',
    },
  );

export const exportJson = (id: string) =>
  defHttp.request<void>({ ...Api.ExportJson(id) }, { isReturnNativeResponse: true });

export const importProductJsonFile = (params: FormData) => {
  return defHttp.request<any>(
    { ...Api.ImportProductJsonFile, params },
    { isReturnNativeResponse: true, errorMessageMode: 'notification', successMessageMode: 'notification' },
  );
};

export const getFullProductInfo = (id: string) =>
  defHttp.request<ProductResultVO>({ ...Api.GetFullProductInfo(id) });

export const getFullProductInfos = (params: string) =>
  defHttp.request<ProductResultVO>({ ...Api.GetFullProductInfos, params });
