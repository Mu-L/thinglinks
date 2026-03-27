import { Ref } from 'vue';


export interface conditionActionSaveVOS {
  id?: string; 
  appId?: string; // 应用ID
  conditionActionSaveVOS?: any; // 规则条件动作参数
  conditionIdentification?: string; // 条件编码
  conditionScheme?: string; // 条件内容
  conditionType?: string; // 条件类型
  ruleId?: number; // 规则ID
  remark?: string; // 描述
  createdOrgId?: string; // 创建人组织
  antiShake: number; // 防抖状态
  antiShakeScheme: string; // 防抖策略
}
