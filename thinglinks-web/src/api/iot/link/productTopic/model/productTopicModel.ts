export interface ProductTopicPageQuery {
  productId?: string; // 设备id
  topicType?: number; // 类型(0:基础Topic,1:自定义Topic)
  topic?: string; // topic
  publisher?: string; // 发布者
  subscriber?: string; // 订阅者
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductTopicSaveVO {
  productId?: string; // 设备id
  topicType?: number; // 类型(0:基础Topic,1:自定义Topic)
  topic?: string; // topic
  publisher?: string; // 发布者
  subscriber?: string; // 订阅者
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductTopicUpdateVO {
  id: string;
  productId?: string; // 设备id
  topicType?: number; // 类型(0:基础Topic,1:自定义Topic)
  topic?: string; // topic
  publisher?: string; // 发布者
  subscriber?: string; // 订阅者
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}

export interface ProductTopicResultVO {
  echoMap?: any;
  id?: string; // id
  createdTime?: string; // 创建时间
  createdBy?: string; // 创建人
  updatedTime?: string; // 最后修改时间
  updatedBy?: string; // 最后修改人
  productId?: string; // 设备id
  topicType?: number; // 类型(0:基础Topic,1:自定义Topic)
  topic?: string; // topic
  publisher?: string; // 发布者
  subscriber?: string; // 订阅者
  remark?: string; // 备注
  createdOrgId?: string; // 创建人组织
}
