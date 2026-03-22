import { DetailsQuery, DashboardDetailsResultVo } from './model/dashboardModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum, ContentTypeEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'dashboardStats';
// tips: 建议在ServicePrefixEnum中新增：LINK = '/link'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.LINK;
// tips: /link 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
const ServicePrefix = ServicePrefixEnum.LINK;

export const Api = {
  AssetSummary: {
    url: `${ServicePrefix}/${MODULAR}/assetSummary`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
  AssetStatsDetails: {
    url: `${ServicePrefix}/${MODULAR}/assetStatsDetails`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,
  TopologySummary: {
    url: `${ServicePrefix}/${MODULAR}/topologySummary`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
};

export const assetSummary = () =>
  defHttp.request<DashboardDetailsResultVo>({ ...Api.AssetSummary });

export const assetStatsDetails = (params: any) =>
  defHttp.request<DashboardDetailsResultVo>({ ...Api.AssetStatsDetails, params });

export const topologySummary = (params: any) =>
  defHttp.request<DashboardDetailsResultVo>({ ...Api.TopologySummary, params });
