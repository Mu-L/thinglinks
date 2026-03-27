import { defHttp } from '/@/utils/http/axios';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import { DocInfoViewVO, SopDocInfoResultVO } from '../../devOperation/sop/model/sopDocInfoModel';
import { SopDocAppResultVO } from '../../devOperation/sop/model/sopDocAppModel';

const ServicePrefix = ServicePrefixEnum.SOPADMIN;

export const getDocDetail = (id: string) =>
  defHttp.get<DocInfoViewVO>({
    url: `${ServicePrefix}/anyTenant/website/doc/detail`,
    params: { id },
  });

export const findDocTree = (docAppId: string) =>
  defHttp.get<SopDocInfoResultVO[]>({
    url: `${ServicePrefix}/anyTenant/website/doc/tree`,
    params: { docAppId },
  });

export const findHelpTree = () =>
  defHttp.post<SopDocInfoResultVO[]>({
    url: `${ServicePrefix}/anyTenant/website/help/tree`,
  });

export const findAppList = () =>
  defHttp.get<SopDocAppResultVO[]>({
    url: `${ServicePrefix}/anyTenant/website/app/list`,
  });
