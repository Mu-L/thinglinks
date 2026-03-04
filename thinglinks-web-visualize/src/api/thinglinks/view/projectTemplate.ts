import { MockMethod } from 'vite-plugin-mock'
import { ContentTypeEnum, RequestHttpEnum } from '@/enums/httpEnum'
import { ServicePrefixEnum } from '@/enums/commonEnum';

import { post,put,del,get } from '@/api/http';

const Api = {
  // 列表
  Page: {
    url: `${ServicePrefixEnum.VIEW}/projectTemplate/page`,
    method: RequestHttpEnum.POST,
  },

  // 状态变更
  UpdateProjectStatus:{
    url: `${ServicePrefixEnum.VIEW}/projectTemplate/updateTemplateStatus`,
    method: RequestHttpEnum.PUT,
  },

  // 删除
  Delete:{
    url: `${ServicePrefixEnum.VIEW}/projectTemplate/deleteTemplate`,
    method: RequestHttpEnum.DELETE,
  },
  
  // 保存项目
  SaveProject: {
    url: `${ServicePrefixEnum.VIEW}/projectTemplate/saveTemplate`,
    method: RequestHttpEnum.POST,
  },

  // 修改项目
  UpdateProject: {
    url: `${ServicePrefixEnum.VIEW}/projectTemplate/updateTemplate`,
    method: RequestHttpEnum.PUT,
  },

  // 项目详情
  DetailsProject: {
    url: `${ServicePrefixEnum.VIEW}/projectTemplate/getProjectTemplateDetails`,
    method: RequestHttpEnum.GET,
  }
}

/**
 * @description: 列表
 */
export function pageTemplate(data: any) {
  return post(Api.Page.url, data);
}

/**
 * @description: 删除项目
 */
export function delProjectTemplate(data: { id: number | string }) {
  return del(`${Api.Delete.url}/${data.id}`);
}

/**
 * @description: 更新项目状态
 */
export function updateProjectStatusTemplate(data: { id: number | string; status: number }) {
  return put(`${Api.UpdateProjectStatus.url}/${data.id}`, { status: Number(data.status) }, ContentTypeEnum.FORM_DATA);
}

/**
 * @description: 保存项目
 */
export function saveProjectTemplate(data: any) {
  return post(Api.SaveProject.url, data);
}

/**
 * @description: 修改项目
 */
export function updateProjectTemplate(data: any) {
  return put(Api.UpdateProject.url, data);
}

/**
 * @description: 项目详情
 */
export function detailsProjectTemplate(data: any) {
  return get(`${Api.DetailsProject.url}/${data.identification}`);
}