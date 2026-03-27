export interface CardSimInfoPageQuery {
  imsi?: string; // 国际移动用户识别码
  iccid?: string; // SIM卡唯一识别码
  cardNumber?: string; // 卡号
  cardType?: number; // 卡类型 0 插拔卡 1 贴片IC卡
  channelId?: string; // 渠道ID
  flowsUsed?: string; // 已用流量
  flowsTotal?: string; // 总流量
  flowsRest?: string; // 本月剩余流量
  virtualFlowsUsed?: string; // 虚拟已用流量
  virtualFlowsRest?: string; // 虚拟剩余流量
  virtualFlowsTotal?: string; // 虚拟总流量
  smsFlag?: number; // 是否有短信 0 无 1 有
  gprsFlag?: number; // GPRS开关 0 关 1 开
  openTime?: string; // 开卡时间
  lastOpenTime?: string; // 最晚激活时间
  startTime?: string; // 激活时间
  endTime?: string; // 失效时间
  flowsEndTime?: string; // 流量到期时间
  carrierType?: number; // 运营商类型 1 移动 2 电信 3 联通
  smsCount?: number; // 已发送短信数量
  status?: number; // SIM卡状态 1 待激活 2 已激活 3 停机
  useType?: number; // 使用类型 1 普卡 2 共享池 3 流量池
  apnName?: string; // APN名称
  ipAddress?: string; // IP地址
  gainTime?: string; // 获取时间
  onlineFlag?: number; // 在线状态 0 不在线 1 在线
  stopCardType?: number; // 停卡类型 1 系统停卡 2 人工停卡 0 正常
  monthlyWarning?: string; // 当月流量预警记录
  imei?: string; // 关联设备IMEI
  limitFlow?: number; // 流量限制阀值
  limitFlag?: number; // 流量阀值状态 0 未开启 1 开启
  limitStatus?: number; // 流量限制状态 0 未限制 1 已限制
  offerId?: string; // 事务ID
  searchableStatus?: number; // API是否可查 0 不可查 1 可查
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardSimInfoSaveVO {
  imsi?: string; // 国际移动用户识别码
  iccid?: string; // SIM卡唯一识别码
  cardNumber?: string; // 卡号
  cardType?: number; // 卡类型 0 插拔卡 1 贴片IC卡
  channelId?: string; // 渠道ID
  flowsUsed?: string; // 已用流量
  flowsTotal?: string; // 总流量
  flowsRest?: string; // 本月剩余流量
  virtualFlowsUsed?: string; // 虚拟已用流量
  virtualFlowsRest?: string; // 虚拟剩余流量
  virtualFlowsTotal?: string; // 虚拟总流量
  smsFlag?: number; // 是否有短信 0 无 1 有
  gprsFlag?: number; // GPRS开关 0 关 1 开
  openTime?: string; // 开卡时间
  lastOpenTime?: string; // 最晚激活时间
  startTime?: string; // 激活时间
  endTime?: string; // 失效时间
  flowsEndTime?: string; // 流量到期时间
  carrierType?: number; // 运营商类型 1 移动 2 电信 3 联通
  smsCount?: number; // 已发送短信数量
  status?: number; // SIM卡状态 1 待激活 2 已激活 3 停机
  useType?: number; // 使用类型 1 普卡 2 共享池 3 流量池
  apnName?: string; // APN名称
  ipAddress?: string; // IP地址
  gainTime?: string; // 获取时间
  onlineFlag?: number; // 在线状态 0 不在线 1 在线
  stopCardType?: number; // 停卡类型 1 系统停卡 2 人工停卡 0 正常
  monthlyWarning?: string; // 当月流量预警记录
  imei?: string; // 关联设备IMEI
  limitFlow?: number; // 流量限制阀值
  limitFlag?: number; // 流量阀值状态 0 未开启 1 开启
  limitStatus?: number; // 流量限制状态 0 未限制 1 已限制
  offerId?: string; // 事务ID
  searchableStatus?: number; // API是否可查 0 不可查 1 可查
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardSimInfoUpdateVO {
  id: string;
  imsi?: string; // 国际移动用户识别码
  iccid?: string; // SIM卡唯一识别码
  cardNumber?: string; // 卡号
  cardType?: number; // 卡类型 0 插拔卡 1 贴片IC卡
  channelId?: string; // 渠道ID
  flowsUsed?: string; // 已用流量
  flowsTotal?: string; // 总流量
  flowsRest?: string; // 本月剩余流量
  virtualFlowsUsed?: string; // 虚拟已用流量
  virtualFlowsRest?: string; // 虚拟剩余流量
  virtualFlowsTotal?: string; // 虚拟总流量
  smsFlag?: number; // 是否有短信 0 无 1 有
  gprsFlag?: number; // GPRS开关 0 关 1 开
  openTime?: string; // 开卡时间
  lastOpenTime?: string; // 最晚激活时间
  startTime?: string; // 激活时间
  endTime?: string; // 失效时间
  flowsEndTime?: string; // 流量到期时间
  carrierType?: number; // 运营商类型 1 移动 2 电信 3 联通
  smsCount?: number; // 已发送短信数量
  status?: number; // SIM卡状态 1 待激活 2 已激活 3 停机
  useType?: number; // 使用类型 1 普卡 2 共享池 3 流量池
  apnName?: string; // APN名称
  ipAddress?: string; // IP地址
  gainTime?: string; // 获取时间
  onlineFlag?: number; // 在线状态 0 不在线 1 在线
  stopCardType?: number; // 停卡类型 1 系统停卡 2 人工停卡 0 正常
  monthlyWarning?: string; // 当月流量预警记录
  imei?: string; // 关联设备IMEI
  limitFlow?: number; // 流量限制阀值
  limitFlag?: number; // 流量阀值状态 0 未开启 1 开启
  limitStatus?: number; // 流量限制状态 0 未限制 1 已限制
  offerId?: string; // 事务ID
  searchableStatus?: number; // API是否可查 0 不可查 1 可查
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface CardSimInfoResultVO {
  echoMap?: any;
  id?: string; // 主键
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  imsi?: string; // 国际移动用户识别码
  iccid?: string; // SIM卡唯一识别码
  cardNumber?: string; // 卡号
  cardType?: number; // 卡类型 0 插拔卡 1 贴片IC卡
  channelId?: string; // 渠道ID
  flowsUsed?: string; // 已用流量
  flowsTotal?: string; // 总流量
  flowsRest?: string; // 本月剩余流量
  virtualFlowsUsed?: string; // 虚拟已用流量
  virtualFlowsRest?: string; // 虚拟剩余流量
  virtualFlowsTotal?: string; // 虚拟总流量
  smsFlag?: number; // 是否有短信 0 无 1 有
  gprsFlag?: number; // GPRS开关 0 关 1 开
  openTime?: string; // 开卡时间
  lastOpenTime?: string; // 最晚激活时间
  startTime?: string; // 激活时间
  endTime?: string; // 失效时间
  flowsEndTime?: string; // 流量到期时间
  carrierType?: number; // 运营商类型 1 移动 2 电信 3 联通
  smsCount?: number; // 已发送短信数量
  status?: number; // SIM卡状态 1 待激活 2 已激活 3 停机
  useType?: number; // 使用类型 1 普卡 2 共享池 3 流量池
  apnName?: string; // APN名称
  ipAddress?: string; // IP地址
  gainTime?: string; // 获取时间
  onlineFlag?: number; // 在线状态 0 不在线 1 在线
  stopCardType?: number; // 停卡类型 1 系统停卡 2 人工停卡 0 正常
  monthlyWarning?: string; // 当月流量预警记录
  imei?: string; // 关联设备IMEI
  limitFlow?: number; // 流量限制阀值
  limitFlag?: number; // 流量阀值状态 0 未开启 1 开启
  limitStatus?: number; // 流量限制状态 0 未限制 1 已限制
  offerId?: string; // 事务ID
  searchableStatus?: number; // API是否可查 0 不可查 1 可查
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
