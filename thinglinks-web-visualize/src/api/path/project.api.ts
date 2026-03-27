import { http } from '@/api/http'
import { httpErrorHandle } from '@/utils'
import { ContentTypeEnum, RequestHttpEnum, ModuleTypeEnum } from '@/enums/httpEnum'
import { ProjectItem, ProjectDetail } from './project'

// * 上传文件
export const uploadFile = async (data: object) => {
  try {
    const res = await http(RequestHttpEnum.POST)<{
      /**
       * 文件地址
       */
      fileName: string,
      fileurl: string,
      id: string,
    }>(`${ModuleTypeEnum.BASEfILE}/file/upload`, data, ContentTypeEnum.FORM_DATA)
    return res
  } catch {
    httpErrorHandle()
  }
}

/*
 * /findUrlFormTenantByIdUsingPOST
 * 通过id查询文件地址
*/
export const getIndexImage = async (data: object) => {
  try {
    const res = await http(RequestHttpEnum.POST)(`${ModuleTypeEnum.BASEfILE}/file/findUrlById`, data)
    return res
  } catch {
    httpErrorHandle()
  }
}