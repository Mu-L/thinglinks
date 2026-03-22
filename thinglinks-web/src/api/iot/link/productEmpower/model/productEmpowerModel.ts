export interface ProductPropertyPageQuery {
  serviceId?: string; // 服务ID
  propertyCode?: string; // 指示属性编码
  propertyName?: string; // 指示属性名称
  datatype?: string; // 指示数据类型：取值范围：string、int、decimal（float和double都可以使用此类型）、DateTime、jsonObject上报数据时，复杂类型数据格式如下：•DateTime:yyyyMMdd’T’HHmmss’Z’如:20151212T121212Z•jsonObject：自定义json结构体，平台不理解只透传
  description?: string; // 属性描述，不影响实际功能，可配置为空字符串""。
  enumlist?: string; // 指示枚举值:如开关状态status可有如下取值"enumList" : ["OPEN","CLOSE"]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
  max?: string; // 指示最大值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑小于等于。
  maxlength?: string; // 指示字符串长度。仅当dataType为string、DateTime时生效。
  method?: string; // 指示访问模式。R:可读；W:可写；E属性值更改时上报数据取值范围：R、RW、RE、RWE
  min?: string; // 指示最小值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑大于等于。
  required?: string; // 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。(字典值link_product_isRequired：0非必填 1必填)
  step?: string; // 指示步长。
  unit?: string; // 指示单位。支持长度不超过50。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
  icon?: string; // 图标png图
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductPropertySaveVO {
  serviceId?: string; // 服务ID
  propertyCode?: string; // 指示属性编码
  propertyName?: string; // 指示属性名称
  datatype?: string; // 指示数据类型：取值范围：string、int、decimal（float和double都可以使用此类型）、DateTime、jsonObject上报数据时，复杂类型数据格式如下：•DateTime:yyyyMMdd’T’HHmmss’Z’如:20151212T121212Z•jsonObject：自定义json结构体，平台不理解只透传
  description?: string; // 属性描述，不影响实际功能，可配置为空字符串""。
  enumlist?: string; // 指示枚举值:如开关状态status可有如下取值"enumList" : ["OPEN","CLOSE"]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
  max?: string; // 指示最大值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑小于等于。
  maxlength?: string; // 指示字符串长度。仅当dataType为string、DateTime时生效。
  method?: string; // 指示访问模式。R:可读；W:可写；E属性值更改时上报数据取值范围：R、RW、RE、RWE
  min?: string; // 指示最小值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑大于等于。
  required?: string; // 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。(字典值link_product_isRequired：0非必填 1必填)
  step?: string; // 指示步长。
  unit?: string; // 指示单位。支持长度不超过50。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
  icon?: string; // 图标png图
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductPropertyUpdateVO {
  id: string;
  serviceId?: string; // 服务ID
  propertyCode?: string; // 指示属性编码
  propertyName?: string; // 指示属性名称
  datatype?: string; // 指示数据类型：取值范围：string、int、decimal（float和double都可以使用此类型）、DateTime、jsonObject上报数据时，复杂类型数据格式如下：•DateTime:yyyyMMdd’T’HHmmss’Z’如:20151212T121212Z•jsonObject：自定义json结构体，平台不理解只透传
  description?: string; // 属性描述，不影响实际功能，可配置为空字符串""。
  enumlist?: string; // 指示枚举值:如开关状态status可有如下取值"enumList" : ["OPEN","CLOSE"]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
  max?: string; // 指示最大值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑小于等于。
  maxlength?: string; // 指示字符串长度。仅当dataType为string、DateTime时生效。
  method?: string; // 指示访问模式。R:可读；W:可写；E属性值更改时上报数据取值范围：R、RW、RE、RWE
  min?: string; // 指示最小值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑大于等于。
  required?: string; // 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。(字典值link_product_isRequired：0非必填 1必填)
  step?: string; // 指示步长。
  unit?: string; // 指示单位。支持长度不超过50。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
  icon?: string; // 图标png图
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductPropertyResultVO {
  echoMap?: any;
  id?: string; // 属性id
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  serviceId?: string; // 服务ID
  propertyCode?: string; // 指示属性编码
  propertyName?: string; // 指示属性名称
  datatype?: string; // 指示数据类型：取值范围：string、int、decimal（float和double都可以使用此类型）、DateTime、jsonObject上报数据时，复杂类型数据格式如下：•DateTime:yyyyMMdd’T’HHmmss’Z’如:20151212T121212Z•jsonObject：自定义json结构体，平台不理解只透传
  description?: string; // 属性描述，不影响实际功能，可配置为空字符串""。
  enumlist?: string; // 指示枚举值:如开关状态status可有如下取值"enumList" : ["OPEN","CLOSE"]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
  max?: string; // 指示最大值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑小于等于。
  maxlength?: string; // 指示字符串长度。仅当dataType为string、DateTime时生效。
  method?: string; // 指示访问模式。R:可读；W:可写；E属性值更改时上报数据取值范围：R、RW、RE、RWE
  min?: string; // 指示最小值。支持长度不超过50的数字。仅当dataType为int、decimal时生效，逻辑大于等于。
  required?: string; // 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。(字典值link_product_isRequired：0非必填 1必填)
  step?: string; // 指示步长。
  unit?: string; // 指示单位。支持长度不超过50。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
  icon?: string; // 图标png图
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
