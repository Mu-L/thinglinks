export interface DeviceAclRulePageQuery {
  ruleName?: string; // 规则名称
  deviceIdentification?: string; // 设备标识
  actionType?: number; // 动作类型
  priority?: number; // 规则优先级
  topicPattern?: string; // MQTT主题模式
  ipWhitelist?: string; // IP白名单
  decision?: boolean; // 决策
  enabled?: boolean; // 是否启用
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  productIdentification?: string; // 产品标识
  ruleLevel?: number; // 规则级别
}

export interface DeviceAclRuleSaveVO {
  ruleName?: string; // 规则名称
  deviceIdentification?: string; // 设备标识
  actionType?: number; // 动作类型
  priority?: number; // 规则优先级
  topicPattern?: string; // MQTT主题模式
  ipWhitelist?: string; // IP白名单地址
  decision?: boolean; // 决策
  enabled?: boolean; // 是否启用
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  productIdentification?: string; // 产品标识
  ruleLevel?: number; // 规则级别
}

export interface DeviceAclRuleUpdateVO {
  id: string;
  ruleName?: string; // 规则名称
  deviceIdentification?: string; // 设备标识
  actionType?: number; // 动作类型
  priority?: number; // 规则优先级
  topicPattern?: string; // MQTT主题模式
  ipWhitelist?: string; // IP白名单地址
  decision?: boolean; // 决策
  enabled?: boolean; // 是否启用
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  productIdentification?: string; // 产品标识
  ruleLevel?: number; // 规则级别
}

export interface DeviceAclRuleResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  deviceIdentification?: string; // 设备标识
  actionType?: number; // 动作类型
  priority?: number; // 规则优先级
  topicPattern?: string; // MQTT主题模式
  ipWhitelist?: string; // IP白名单地址
  decision?: boolean; // 决策
  enabled?: boolean; // 是否启用
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
  productIdentification?: string; // 产品标识
  ruleLevel?: number; // 规则级别
}
