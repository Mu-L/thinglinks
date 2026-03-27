export default {
  table: { title: 'DeviceGroup list' },
  id: 'Id',
  parentId: 'ParentId',
  sortValue: 'SortValue',
  appId: 'AppId',
  groupName: 'GroupName',
  type: 'Type',
  typeOptions: {
    normalGroup: 'Ordinary grouping',
  },
  state: 'State',
  description: 'Description',
  remark: 'Remark',
  createdTime: 'CreatedTime',
  createdBy: 'CreatedBy',
  updatedTime: 'UpdatedTime',
  updatedBy: 'UpdatedBy',
  createdOrgId: 'CreatedOrgId',
  delChildNodeTip:
    'The selected node and its child nodes will be permanently deleted. Are you sure they will be deleted?',
  editDeviceGroup: {
    rootNode: 'Root node',
    parentNode: 'Superior node',
  },
  deviceGroupDetail: {
    groupDevice: 'Group details',
    queryFail: 'Failed to obtain the grouping information. Please try again',
    linkDevice: 'Associated device',
    groupId: 'Group ID',
    sortValueNum: 'Sorting number',
  },
  addDeviceToGroup: {
    title: 'Add this device to the group',
    addSuccess: 'Added successfully',
  },
  search: {
    devicePlaceholder: 'Please enter device identifier',
  },
};
