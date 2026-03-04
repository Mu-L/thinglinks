import { ResultEnum, RequestHttpHeaderEnum } from '@/enums/httpEnum'
import { ErrorPageNameMap, PageEnum, PreviewEnum } from '@/enums/pageEnum'
import { docPath, giteeSourceCodePath } from '@/settings/pathConst'
import { StorageEnum } from '@/enums/storageEnum'
import { clearLocalStorage, clearCookie } from './storage'
import { useUserStore } from '@/store/modules/user/user';
import router from '@/router'
import { logoutApi } from '@/api/path'

/**
 * * 根据名字跳转路由
 * @param pageName 路由名称
 * @param isReplace 是否替换当前路由
 * @param windowOpen 是否在新窗口打开
 */
export const routerTurnByName = (
  pageName: string,
  isReplace?: boolean,
  windowOpen?: boolean
) => {
  if (windowOpen) {
    const path = fetchPathByName(pageName, 'href')
    if (path) {
      openNewWindow(path)
    }
    return
  }
  
  const routeConfig = { name: pageName }
  isReplace ? router.replace(routeConfig) : router.push(routeConfig)
}

/**
 * * 根据名称获取路由信息
 * @param pageName 路由名称
 * @param property 要获取的路由属性（如 'href'）
 * @returns 路由信息或指定属性值
 */
export const fetchPathByName = (pageName: string, property?: string) => {
  if (!router) {
    console.error('Router instance is not available')
    return property ? '' : null
  }
  
  try {
    const pathData = router.resolve({ name: pageName })
    return property ? (pathData as any)[property] : pathData
  } catch (error) {
    console.error('Failed to resolve route:', error, 'pageName:', pageName)
    return property ? '' : null
  }
}

/**
 * * 根据路径跳转路由
 * @param path 路由路径
 * @param query 路径参数数组
 * @param isReplace 是否替换当前路由
 * @param windowOpen 是否在新窗口打开
 */
export const routerTurnByPath = (
  path: string,
  query?: Array<string | number>,
  isReplace?: boolean,
  windowOpen?: boolean
) => {
  const fullPath = query?.length ? `${path}/${query.join('/')}` : path
  
  if (windowOpen) {
    return openNewWindow(fullPath)
  }
  
  const routeConfig = { path: fullPath }
  isReplace ? router.replace(routeConfig) : router.push(routeConfig)
}

/**
 * * 根据路径跳转路由（使用 query 参数）
 * @param path
 * @param queryParams
 * @param isReplace
 * @param windowOpen
 */
export const routerTurnByPathWithQuery = (
  path: string,
  queryParams?: Record<string, string | number>,
  isReplace?: boolean,
  windowOpen?: boolean
) => {
  if (windowOpen) {
    const queryString = queryParams 
      ? '?' + new URLSearchParams(Object.entries(queryParams).map(([k, v]) => [k, String(v)])).toString() 
      : ''
    return openNewWindow(path + queryString)
  }
  
  const routeConfig = {
    path: path,
    query: queryParams
  }
  
  if (isReplace) {
    router.replace(routeConfig)
  } else {
    router.push(routeConfig)
  }
}

/**
 * * 根据路由名称跳转路由（使用 query 参数）
 * @param pageName
 * @param queryParams
 * @param params
 * @param isReplace
 * @param windowOpen
 */
export const routerTurnByNameWithQuery = (
  pageName: string,
  queryParams?: Record<string, string | number>,
  params?: Record<string, string | number>,
  isReplace?: boolean,
  windowOpen?: boolean
) => {
  if (windowOpen) {
    try {
      const resolved = router.resolve({
        name: pageName,
        params: params,
        query: queryParams
      })
      const { origin, pathname } = document.location
      const fullUrl = `${origin}${pathname}${resolved.href}`
      return openNewWindow(fullUrl)
    } catch (error) {
      console.error('Failed to resolve route:', error, 'pageName:', pageName)
      return null
    }
  }
  
  const routeConfig = {
    name: pageName,
    params: params,
    query: queryParams
  }
  
  if (isReplace) {
    router.replace(routeConfig)
  } else {
    router.push(routeConfig)
  }
}

/**
 * * 错误页重定向
 * @param icon
 * @returns
 */
export const redirectErrorPage = (code: ResultEnum) => {
  if (!code) return false
  const pageName = ErrorPageNameMap.get(code)
  if (!pageName) return false
  routerTurnByName(pageName)
}

/**
 * * 重新加载当前路由页面
 */
export const reloadRoutePage = () => {
  routerTurnByName(PageEnum.RELOAD_NAME)
}

/**
 * * 退出登录
 */
export const logout = async () => {
  try {
    const res = await logoutApi()
    if(res && res.code === ResultEnum.DATA_SUCCESS) {
      window['$message'].success(window['$t']('global.logout_success'))
      clearCookie(RequestHttpHeaderEnum.COOKIE)
      clearLocalStorage(StorageEnum.GO_SYSTEM_STORE)
      routerTurnByName(PageEnum.BASE_LOGIN_NAME)
    }
  } catch (error) {
    window['$message'].success(window['$t']('global.logout_failure'))
  }
}

/**
 * * 新开页面
 * @param url
 */
export const openNewWindow = (url: string) => {
  return window.open(url, '_blank')
}

/**
 * * 打开项目文档
 * @param url
 */
export const openDoc = () => {
  openNewWindow(docPath)
}

/**
 * * 打开码云仓库地址
 * @param url
 */
export const openGiteeSourceCode = () => {
  openNewWindow(giteeSourceCodePath)
}

/**
 * * 判断是否是预览页
 * @returns boolean
 */
export const isPreview = () => {
  return document.location.hash.includes('preview')
}

/**
 * * 获取当前路由下的参数
 * @returns 路由参数对象
 */
export const fetchRouteParams = (): Record<string, any> => {
  if (!router?.currentRoute) {
    console.error('Router instance is not available')
    return {}
  }
  return router.currentRoute.value.params || {}
}

/**
 * * 获取当前路由的 identification 参数
 * @returns string
 */
export const fetchRouteParamsLocation = (): string => {
  if (!router?.currentRoute) {
    console.error('Router instance is not available')
    return ''
  }
  
  const route = router.currentRoute.value
  return (route.query?.identification as string) || ''
}

/**
 * * 回到主页面
 * @param confirm
 */
export const goHome = () => {
  routerTurnByName(PageEnum.BASE_HOME_NAME)
}

/**
 * * 判断是否登录
 * @return boolean
 */
export const loginCheck = async () => {
  if (useUserStore().getLastUpdateTime === 0) {
    try {
      await useUserStore().getUserInfoAction();
      return true
    } catch (error) {
      return false
    }
  }else {
    return true
  }
}

/**
 * * 预览地址
 * @param id 项目标识
 * @param queryParams 查询参数
 * @returns 预览页面的完整 URL
 */
export const previewPath = (
  id?: string | number, 
  queryParams?: Record<string, string | number>
): string => {
  if (!router?.currentRoute) {
    console.error('Router instance is not available')
    return ''
  }
  
  const { origin, pathname } = document.location
  const path = fetchPathByName(PreviewEnum.CHART_PREVIEW_NAME, 'href')
  
  if (!path) {
    console.error('Failed to resolve preview path')
    return ''
  }
  
  const route = router.currentRoute.value
  const identification = id || (route.query?.identification as string) || ''
  const isMyProject = queryParams?.isMyProject 
    ? Number(queryParams.isMyProject) 
    : (route.query?.isMyProject ? Number(route.query.isMyProject) : 0)
  
  // 合并 queryParams 和默认参数
  const finalQueryParams = {
    identification: String(identification),
    isMyProject,
    ...queryParams
  }
  
  const queryString = '?' + new URLSearchParams(
    Object.entries(finalQueryParams).map(([k, v]) => [k, String(v)])
  ).toString()
  
  return `${origin}${pathname}${path}${queryString}`
}
