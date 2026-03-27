import {
  conditionActionSaveVOS,
} from './model/conditionActionModel';
import { PageParams, PageResult } from '/@/api/model/baseModel';
import { defHttp } from '/@/utils/http/axios';
import { RequestEnum } from '/@/enums/httpEnum';
import { ServicePrefixEnum } from '/@/enums/commonEnum';
import type { AxiosRequestConfig } from 'axios';

const MODULAR = 'ruleCondition';
const ServicePrefix = ServicePrefixEnum.RULE;

export const Api = {
  SaveRuleConditionAction: {
    url: `${ServicePrefix}/${MODULAR}/saveRuleConditionAction`,
    method: RequestEnum.POST,
  } as AxiosRequestConfig,

  UpdateRuleConditionAction: {
    url: `${ServicePrefix}/${MODULAR}/updateRuleConditionAction`,
    method: RequestEnum.PUT,
  },
};

export const saveRuleConditionAction = (params: conditionActionSaveVOS) =>
  defHttp.request<conditionActionSaveVOS>({ ...Api.SaveRuleConditionAction, params });

export const updateRuleConditionAction = (params: conditionActionSaveVOS) =>
  defHttp.request<conditionActionSaveVOS>({ ...Api.UpdateRuleConditionAction, params });


