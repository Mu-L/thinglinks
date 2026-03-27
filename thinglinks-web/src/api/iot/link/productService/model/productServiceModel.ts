export interface ProductServicePageQuery {
  productId?: string; // 产品标识
  serviceCode?: string; // 服务编码:支持英文大小写、数字、下划线和中划线
  serviceName?: string; // 服务名称
  serviceType?: string; // 服务类型
  serviceStatus?: number; // 状态(字典值：0启用  1停用)
  description?: string; // 服务的描述信息:文本描述，不影响实际功能，可配置为空字符串""。
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductServiceSaveVO {
  productId?: string; // 产品标识
  serviceCode?: string; // 服务编码:支持英文大小写、数字、下划线和中划线
  serviceName?: string; // 服务名称
  serviceType?: string; // 服务类型
  serviceStatus?: number; // 状态(字典值：0启用  1停用)
  description?: string; // 服务的描述信息:文本描述，不影响实际功能，可配置为空字符串""。
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductServiceUpdateVO {
  id: string;
  productId?: string; // 产品标识
  serviceCode?: string; // 服务编码:支持英文大小写、数字、下划线和中划线
  serviceName?: string; // 服务名称
  serviceType?: string; // 服务类型
  serviceStatus?: number; // 状态(字典值：0启用  1停用)
  description?: string; // 服务的描述信息:文本描述，不影响实际功能，可配置为空字符串""。
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductServiceResultVO {
  echoMap?: any;
  id?: string; // 服务id
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  productId?: string; // 产品标识
  serviceCode?: string; // 服务编码:支持英文大小写、数字、下划线和中划线
  serviceName?: string; // 服务名称
  serviceType?: string; // 服务类型
  serviceStatus?: number; // 状态(字典值：0启用  1停用)
  description?: string; // 服务的描述信息:文本描述，不影响实际功能，可配置为空字符串""。
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
