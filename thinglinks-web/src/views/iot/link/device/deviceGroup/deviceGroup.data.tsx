import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { Tag } from 'ant-design-vue';
// api
import { tree } from '/@/api/iot/link/group/deviceGroup';

const { t } = useI18n();

// 添加分组表单字段
export const addGroupFields = (): FormSchema[] => {
  return [
    {
      field: 'groupId',
      label: t('iot.link.group.deviceGroup.groupName'),
      component: 'ApiTreeSelect',
      componentProps: {
        api: tree,
        labelField: 'groupName',
        valueField: 'id',
        treeDefaultExpandAll: true,
        immediate: true,
      },
      rules: [{ trigger: 'change', required: true }],
    },
    {
      label: t('iot.link.device.device.remark'),
      field: 'remark',
      component: 'InputTextArea',
      defaultValue: '',
    },
  ];
};


// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.device.device.groupWhereDeviceIsLocat'),
      dataIndex: 'groupName',
      width: 'auto',
    },
    {
      title: t('iot.link.group.deviceGroup.deviceGroupDetail.groupId'),
      dataIndex: 'groupId',
      width: 'auto',
    },
    {
      title: t('iot.link.device.device.updatedTime'),
      dataIndex: 'updatedTime',
      width: 'auto',
    },
    {
      title: t('iot.link.device.device.remark'),
      dataIndex: 'remark',
      width: 'auto',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
