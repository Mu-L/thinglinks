import { ref, reactive } from 'vue'
import { goDialog, httpErrorHandle } from '@/utils'
import { DialogEnum } from '@/enums/pluginEnum'
import { Chartype, ChartList } from '../../../index.d'
import { ResultEnum } from '@/enums/httpEnum'
// api
import { page, delProject, updateProjectStatus } from '@/api/thinglinks/view/myProject'
import { pageTemplate, delProjectTemplate, updateProjectStatusTemplate } from '@/api/thinglinks/view/projectTemplate'
import { getIndexImage } from '@/api/path'

// 项目类型枚举
export type ProjectType = 'myProject' | 'projectTemplate'

// API 接口映射类型
interface ProjectApiMap {
  page: (data: any) => Promise<any>
  delete: (data: { id: number | string }) => Promise<any>
  updateStatus: (data: { id: number | string; status: number }) => Promise<any>
}

// 根据项目类型获取对应的 API 接口
const getProjectApi = (type: ProjectType): ProjectApiMap => {
  // 这里可以根据 type 返回不同的 API 接口
  // 目前接口相同，但预留扩展空间
  if (type === 'myProject') {
    // 我的项目接口
    return {
      page: (data: any) => page(data),
      delete: (data: { id: number | string }) => delProject(data),
      updateStatus: (data: { id: number | string; status: number }) => updateProjectStatus(data)
    }
  } else {
    // 项目模版接口
    return {
      page: (data: any) => pageTemplate(data),
      delete: (data: { id: number | string }) => delProjectTemplate(data),
      updateStatus: (data: { id: number | string; status: number }) => updateProjectStatusTemplate(data)
    }
  }
}

// 数据初始化
export const useDataListInit = (projectType: ProjectType = 'myProject') => {
  // 根据项目类型获取对应的 API
  const api = getProjectApi(projectType)
  const loading = ref(true)

  const paginat = reactive({
    // 当前页数
    page: 1,
    // 每页值
    limit: 12,
    // 总数
    count: 10
  })

  // TODO: 接口联调  待封装
  const list = ref<ChartList>([])

  // 筛选参数
  const filterParams = ref<{
    projectName?: string
    projectIdentification?: string
    status?: number
  }>({})

  // 设置筛选参数
  const setFilterParams = (params: { projectName?: string; projectIdentification?: string; status?: number }) => {
    filterParams.value = params
  }

  // 数据请求
  const getList = async () => {
    loading.value = true
    const res = await api.page({
      current: paginat.page,
      size: paginat.limit,
      model: {
        ...filterParams.value
      }
    })
    const { data } = res as any // 这里的count与data平级，不在Response结构中
    try {
    if (data?.records) {
      paginat.count = data.total
      let ids = [];
      data.records.map((item: any) => {
        if (item.indexImageId) {
          ids.push(item.indexImageId)
        }
      });
      const indexImageList = await getIndexImage(ids);
      list.value = data.records.map(item => {
        if (indexImageList.code === ResultEnum.DATA_SUCCESS && indexImageList.data[item.indexImageId]) {
          item.indexImage = indexImageList.data[item.indexImageId];
        }
        return item;
      })
      setTimeout(() => {
        loading.value = false
      }, 500)
      return
    } else {
      loading.value = false;
      }
    } catch (error) {
      httpErrorHandle()
    }
  }

  // 修改页数
  const changePage = (_page: number) => {
    paginat.page = _page
    getList()
  }

  // 修改大小
  const changeSize = (_size: number) => {
    paginat.limit = _size
    getList()
  }
  
  // 删除处理
  const deleteHandle = (cardData: Chartype) => {
    goDialog({
      type: DialogEnum.DELETE,
      promise: true,
      onPositiveCallback: () =>
        new Promise(res => {
          res(
            api.delete({
              id: cardData.id
            })
          )
        }),
      promiseResCallback: (res: any) => {
        if (res.code === ResultEnum.DATA_SUCCESS) {
          window['$message'].success(window['$t']('global.r_delete_success'))
          getList()
          return
        }
        httpErrorHandle()
      }
    })
  }

  // 发布处理
  const releaseHandle = async (cardData: Chartype, index: number) => {
    const { id, status } = cardData
    const res = await api.updateStatus({
      id: id,
      // [-1未发布, 1发布]
      status: status > 0  ? -1 : 1
    })
    if (res && res.code === ResultEnum.DATA_SUCCESS) {
      list.value = []
      getList()
      // 发布 -> 未发布
      if (status > 0) {
        window['$message'].success(window['$t']('global.r_unpublish_success'))
        return
      }
      // 未发布 -> 发布
      window['$message'].success(window['$t']('global.r_publish_success'))
      return
    }
    httpErrorHandle()
  }


  return {
    loading,
    paginat,
    list,
    getList,
    releaseHandle,
    changeSize,
    changePage,
    deleteHandle,
    setFilterParams
  }
}
