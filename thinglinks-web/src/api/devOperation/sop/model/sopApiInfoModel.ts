export interface SopApiInfoPageQuery {
  application?: string; // 应用名称
  apiName?: string; // 接口名称
  apiVersion?: string; // 版本号
  description?: string; // 接口描述
  remark?: string; // 备注
  interfaceClassName?: string; // 接口class
  methodName?: string; // 方法名称
  paramInfo?: string; // 参数信息
  isPermission?: number; // 接口是否需要授权访问
  isNeedToken?: number; // 是否需要appAuthToken
  hasCommonResponse?: number; // 是否有公共响应参数
  regSource?: number; // 注册来源
  apiMode?: number; // 接口模式
  status?: number; // 状态
}

export interface SopApiInfoSaveVO {
  application?: string; // 应用名称
  apiName?: string; // 接口名称
  apiVersion?: string; // 版本号
  description?: string; // 接口描述
  remark?: string; // 备注
  interfaceClassName?: string; // 接口class
  methodName?: string; // 方法名称
  paramInfo?: string; // 参数信息
  isPermission?: number; // 接口是否需要授权访问
  isNeedToken?: number; // 是否需要appAuthToken
  hasCommonResponse?: number; // 是否有公共响应参数
  regSource?: number; // 注册来源
  apiMode?: number; // 接口模式
  status?: number; // 状态
}

export interface SopApiInfoUpdateVO {
  id: string;
  application?: string; // 应用名称
  apiName?: string; // 接口名称
  apiVersion?: string; // 版本号
  description?: string; // 接口描述
  remark?: string; // 备注
  interfaceClassName?: string; // 接口class
  methodName?: string; // 方法名称
  paramInfo?: string; // 参数信息
  isPermission?: number; // 接口是否需要授权访问
  isNeedToken?: number; // 是否需要appAuthToken
  hasCommonResponse?: number; // 是否有公共响应参数
  regSource?: number; // 注册来源
  apiMode?: number; // 接口模式
  status?: number; // 状态
}

export interface SopApiInfoResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 添加时间
  updatedTime?: string; // 修改时间
  createdBy?: string; // 创建人id
  updatedBy?: string; // 修改人id
  application?: string; // 应用名称
  apiName?: string; // 接口名称
  apiVersion?: string; // 版本号
  description?: string; // 接口描述
  remark?: string; // 备注
  interfaceClassName?: string; // 接口class
  methodName?: string; // 方法名称
  paramInfo?: string; // 参数信息
  isPermission?: number; // 接口是否需要授权访问
  isNeedToken?: number; // 是否需要appAuthToken
  hasCommonResponse?: number; // 是否有公共响应参数
  regSource?: number; // 注册来源
  apiMode?: number; // 接口模式
  status?: number; // 状态
}
