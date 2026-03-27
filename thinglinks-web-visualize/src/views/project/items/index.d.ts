export type Chartype = {
  // id: number | string
  // title: string // 标题
  // label?: string // 标签
  // time: string, // 时间
  // image: string, // 预览图地址
  // createId: string, // 创建者
  // release: boolean // false 未发布 | true 已发布
  id: number | string
  projectName: string // 标题
  remark: string // 标签
  status: number // 0未发布 | 1已发布
}

export type ChartList = Chartype[]

/**
 * @description: 
 */
export type ProjectResultVO = {
  createdBy?: number;
  createdOrgId?: number;
  createdTime?: string;
  id?: number;
  status?: number;
  indexImage: string;
  indexImageId: string;
  projectIdentification?: string;
  projectName?: string;
  remark?: string;
}
export type ProjectList = ProjectResultVO[]
