export interface CardChannelInfoPageQuery {
  channelName?: string; // 渠道商名称（如:中国移动）
  keyParameter?: string; // 密钥集合
  officialFlag?: number; // 是否直属官方平台(如直接对接是移动onelink  0不是  1是)
  refreshFlag?: number; // token是否需要重复刷新 true是 false否 默认是: false
  operatorType?: number; // 所属运营商(1移动、2电信 、3联通）
  provinceName?: string; // 省份名称
  provinceCode?: string; // 省份编码
  appKey?: string; // 公共应用键
  secret?: string; // 公共密钥
  code?: string; // 公共code
  appId?: string; // 客户appid
  password?: string; // 密匙
  status?: number; // 渠道状态(0启用、1停用)
  channelType?: number; // 渠道类别(如:山东移动)
  extendParams?: string; // 扩展参数
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardChannelInfoSaveVO {
  channelName?: string; // 渠道商名称（如:中国移动）
  keyParameter?: string; // 密钥集合
  officialFlag?: number; // 是否直属官方平台(如直接对接是移动onelink  0不是  1是)
  refreshFlag?: number; // token是否需要重复刷新 true是 false否 默认是: false
  operatorType?: number; // 所属运营商(1移动、2电信 、3联通）
  provinceName?: string; // 省份名称
  provinceCode?: string; // 省份编码
  appKey?: string; // 公共应用键
  secret?: string; // 公共密钥
  code?: string; // 公共code
  appId?: string; // 客户appid
  password?: string; // 密匙
  status?: number; // 渠道状态(0启用、1停用)
  channelType?: number; // 渠道类别(如:山东移动)
  extendParams?: string; // 扩展参数
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardChannelInfoUpdateVO {
  id: string;
  channelName?: string; // 渠道商名称（如:中国移动）
  keyParameter?: string; // 密钥集合
  officialFlag?: number; // 是否直属官方平台(如直接对接是移动onelink  0不是  1是)
  refreshFlag?: number; // token是否需要重复刷新 true是 false否 默认是: false
  operatorType?: number; // 所属运营商(1移动、2电信 、3联通）
  provinceName?: string; // 省份名称
  provinceCode?: string; // 省份编码
  appKey?: string; // 公共应用键
  secret?: string; // 公共密钥
  code?: string; // 公共code
  appId?: string; // 客户appid
  password?: string; // 密匙
  status?: number; // 渠道状态(0启用、1停用)
  channelType?: number; // 渠道类别(如:山东移动)
  extendParams?: string; // 扩展参数
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardChannelInfoResultVO {
  echoMap?: any;
  id?: string; // 渠道id
  createdBy?: string; // 创建人
  createdTime?: string; // 创建时间
  updatedBy?: string; // 更新人
  updatedTime?: string; // 更新时间
  channelName?: string; // 渠道商名称（如:中国移动）
  keyParameter?: string; // 密钥集合
  officialFlag?: number; // 是否直属官方平台(如直接对接是移动onelink  0不是  1是)
  refreshFlag?: number; // token是否需要重复刷新 true是 false否 默认是: false
  operatorType?: number; // 所属运营商(1移动、2电信 、3联通）
  provinceName?: string; // 省份名称
  provinceCode?: string; // 省份编码
  appKey?: string; // 公共应用键
  secret?: string; // 公共密钥
  code?: string; // 公共code
  appId?: string; // 客户appid
  password?: string; // 密匙
  status?: number; // 渠道状态(0启用、1停用)
  channelType?: number; // 渠道类别(如:山东移动)
  extendParams?: string; // 扩展参数
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
