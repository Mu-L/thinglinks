export default {
  table: { title: '设备分组列表' },
  id: 'id',
  parentId: '父级ID',
  sortValue: '排序',
  appId: '应用ID',
  groupName: '分组名称',
  type: '分组类型',
  typeOptions: {
    normalGroup: '普通分组',
  },
  state: '状态',
  description: '分组描述',
  remark: '备注',
  createdTime: '创建时间',
  createdBy: '创建人',
  updatedTime: '最后修改时间',
  updatedBy: '最后修改人',
  createdOrgId: '创建人组织',
  delChildNodeTip: '选中节点及其子结点将被永久删除, 是否确定删除？',
  editDeviceGroup: {
    rootNode: '根节点',
    parentNode: '上级节点',
  },
  deviceGroupDetail: {
    groupDevice: '分组详情',
    queryFail: '获取分组信息失败，请重试',
    linkDevice: '关联设备',
    groupId: '分组ID',
    sortValueNum: '排序号',
  },
  addDeviceToGroup: {
    title: '添加此设备到分组',
    addSuccess: '添加成功',
  },
  search: {
    devicePlaceholder: '请输入设备标识',
  },
};
