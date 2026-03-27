import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'ruleCondition/operator';
const ServicePrefix = ServicePrefixEnum.RULE;

export const Api = {
  OperateList: {
    url: `${ServicePrefix}/${MODULAR}/operateList`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
  ConnectList: {
    url: `${ServicePrefix}/${MODULAR}/connectList`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig
};

export const operate = (params: {}) =>  defHttp.request({ ...Api.OperateList, params });
export const connect = (params: {}) =>  defHttp.request({ ...Api.ConnectList, params });
