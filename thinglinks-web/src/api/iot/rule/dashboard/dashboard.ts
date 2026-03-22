import { DetailsQuery, DashboardDetailsResultVo } from './model/dashboardModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum, ContentTypeEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'ruleDashboardStats';
const ServicePrefix = ServicePrefixEnum.RULE;

export const Api = {
  AssetSummary: {
    url: `${ServicePrefix}/${MODULAR}/assetSummary`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
};

export const ruleAssetSummary = () => defHttp.request({ ...Api.AssetSummary });
