export interface ProductCommandRequestPageQuery {
  serviceId?: string; // 服务ID
  commandId?: string; // 命令ID
  parameterCode?: string; // 参数编码
  parameterName?: string; // 命令中参数的名字。
  parameterDescription?: string; // 命令中参数的描述，不影响实际功能，可配置为空字符串""。
  datatype?: string; // 指示数据类型。取值范围：string、int、decimal
  enumlist?: string; // 指示枚举值。如开关状态status可有如下取值"enumList" : ["OPEN","CLOSE"]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
  max?: string; // 指示最大值。仅当dataType为int、decimal时生效，逻辑小于等于。
  maxlength?: string; // 指示字符串长度。仅当dataType为string时生效。
  min?: string; // 指示最小值。仅当dataType为int、decimal时生效，逻辑大于等于。
  required?: string; // 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。
  step?: string; // 指示步长。
  unit?: string; // 指示单位。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductCommandRequestSaveVO {
  serviceId?: string; // 服务ID
  commandId?: string; // 命令ID
  parameterCode?: string; // 参数编码
  parameterName?: string; // 命令中参数的名字。
  parameterDescription?: string; // 命令中参数的描述，不影响实际功能，可配置为空字符串""。
  datatype?: string; // 指示数据类型。取值范围：string、int、decimal
  enumlist?: string; // 指示枚举值。如开关状态status可有如下取值"enumList" : ["OPEN","CLOSE"]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
  max?: string; // 指示最大值。仅当dataType为int、decimal时生效，逻辑小于等于。
  maxlength?: string; // 指示字符串长度。仅当dataType为string时生效。
  min?: string; // 指示最小值。仅当dataType为int、decimal时生效，逻辑大于等于。
  required?: string; // 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。
  step?: string; // 指示步长。
  unit?: string; // 指示单位。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductCommandRequestUpdateVO {
  id: string;
  serviceId?: string; // 服务ID
  commandId?: string; // 命令ID
  parameterCode?: string; // 参数编码
  parameterName?: string; // 命令中参数的名字。
  parameterDescription?: string; // 命令中参数的描述，不影响实际功能，可配置为空字符串""。
  datatype?: string; // 指示数据类型。取值范围：string、int、decimal
  enumlist?: string; // 指示枚举值。如开关状态status可有如下取值"enumList" : ["OPEN","CLOSE"]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
  max?: string; // 指示最大值。仅当dataType为int、decimal时生效，逻辑小于等于。
  maxlength?: string; // 指示字符串长度。仅当dataType为string时生效。
  min?: string; // 指示最小值。仅当dataType为int、decimal时生效，逻辑大于等于。
  required?: string; // 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。
  step?: string; // 指示步长。
  unit?: string; // 指示单位。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductCommandRequestResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  serviceId?: string; // 服务ID
  commandId?: string; // 命令ID
  parameterCode?: string; // 参数编码
  parameterName?: string; // 命令中参数的名字。
  parameterDescription?: string; // 命令中参数的描述，不影响实际功能，可配置为空字符串""。
  datatype?: string; // 指示数据类型。取值范围：string、int、decimal
  enumlist?: string; // 指示枚举值。如开关状态status可有如下取值"enumList" : ["OPEN","CLOSE"]目前本字段是非功能性字段，仅起到描述作用。建议准确定义。
  max?: string; // 指示最大值。仅当dataType为int、decimal时生效，逻辑小于等于。
  maxlength?: string; // 指示字符串长度。仅当dataType为string时生效。
  min?: string; // 指示最小值。仅当dataType为int、decimal时生效，逻辑大于等于。
  required?: string; // 指示本条属性是否必填，取值为0或1，默认取值1（必填）。目前本字段是非功能性字段，仅起到描述作用。
  step?: string; // 指示步长。
  unit?: string; // 指示单位。取值根据参数确定，如：•温度单位：“C”或“K”•百分比单位：“%”•压强单位：“Pa”或“kPa”
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
