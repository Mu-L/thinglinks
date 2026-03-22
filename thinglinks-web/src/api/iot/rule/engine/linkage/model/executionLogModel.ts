export interface ExecutionLogPageQuery {
  id?: string;
  appId?: string; // 应用ID
  ruleIdentification?: string; // 唯一标识
  ruleName?: string; // 规则名称
  status?: string; // 规则执行状态：0-未执行，1-执行中，2-已完成
  startTime?: number; // 	规则执行开始时间
  endTime?: string; // 	规则执行结束时间
  remark?: string; // 产品
  extendParams?: string; // 扩展参数（文本格式）
}
