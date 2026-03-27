import { Ref } from 'vue';
export interface RuleInstancePageQuery {
  appId?: string; // 应用ID
  createdOrgId?: number; // 创建人组织
  flowData?: string; // 流程数据
  flowId?: string; // 流程ID， 规则实例类型为“规则编排”时，该项为对应的NedRed流程
  id?: number; // 主键
  remark?: string; // 备注
  ruleName?: string; // 规则实例名称
  status?: number; // 状态
  type?: number; // 规则实例类型(0规则编排、1设备告警、2数据转发）
}

export interface RuleInstanceSaveVO {
  appId?: string; // 应用ID
  createdOrgId?: number; // 创建人组织
  flowData?: string; // 流程数据
  flowId?: string; // 流程ID， 规则实例类型为“规则编排”时，该项为对应的NedRed流程
  remark?: string; // 备注
  ruleName?: string; // 规则实例名称
  status?: number; // 状态
  type?: number; // 规则实例类型(0规则编排、1设备告警、2数据转发）
}

export interface RuleInstanceUpdateVO {
  appId?: string; // 应用ID
  createdOrgId?: number; // 创建人组织
  flowData?: string; // 流程数据
  flowId?: string; // 流程ID， 规则实例类型为“规则编排”时，该项为对应的NedRed流程
  id?: number; // 主键
  remark?: string; // 备注
  ruleName?: string; // 规则实例名称
  status?: number; // 状态
  type?: number; // 规则实例类型(0规则编排、1设备告警、2数据转发）
}

export interface RuleInstanceResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  appId?: string; // 应用ID
  alarmName?: string; // 告警名称
  alarmIdentification?: string; // 告警编码
  alarmLevel?: string; // 告警级别
  alarmScene?: string; // 告警场景
  alarmChannel?: string; // 告警渠道
  remark?: string; // 规则描述
  status?: number; // 状态
}