import { DeviceResultVO } from './model/deviceModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum, ContentTypeEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'deviceShadow';
// tips: 建议在ServicePrefixEnum中新增：LINK = '/link'，并将下方代码改为： const ServicePrefix = ServicePrefixEnum.LINK;
// tips: /link 需要与 thinglinks-gateway-server.yml中配置的Path一致，否则无法正常调用接口！！！
const ServicePrefix = ServicePrefixEnum.LINK;

export const Api = {
  QueryDeviceShadow: {
    url: `${ServicePrefix}/${MODULAR}/queryDeviceShadow`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
};

export const queryDeviceShadow = (params: String) =>
  defHttp.request<DeviceResultVO[]>({ ...Api.QueryDeviceShadow, params });
