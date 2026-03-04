export type ListType = {
  id: string
  createdTime: string
  createdBy: string
  updatedTime: string
  updatedBy: string
  echoMap: Record<string, any> // 如果 echoMap 的结构不确定，使用 Record 来表示动态属性
  code: string
  name: string
  abbreviation: string
  creditCode: string
  contactPerson: string
  contactPhone: string
  classify: string | null
  contactEmail: string
  provinceId: string
  provinceName: string | null
  cityId: string
  cityName: string | null
  districtId: string
  districtName: string | null
  address: string
  registerType: string
  connectType: string
  state: boolean
  status: string
  readonly: boolean
  createdName: string
  expirationTime: string | null
  describe: string
  reviewComments: string | null
  employeeState: boolean
  employeeId: string | null
  isDefault: boolean
  orgList: any[] // orgList 可能是一个动态数组，根据实际需求修改
}
