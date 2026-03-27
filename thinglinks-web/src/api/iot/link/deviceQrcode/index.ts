import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'deviceQrcode';
const ServicePrefix = ServicePrefixEnum.LINK;

export const Api = {
  getQrcode: {
    url: `${ServicePrefix}/${MODULAR}/generateDeviceQrcode`,
    method: RequestEnum.GET,
  } as AxiosRequestConfig,
};

export const getQrcode = (params: any) =>
  defHttp.request<any>(
    { ...Api.getQrcode, responseType: 'arraybuffer', params },
    { isTransformResponse: false },
  );
