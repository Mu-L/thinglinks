import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';
import { useDict } from '/@/components/Dict';
const { getDictLabel } = useDict();

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.device.device.deviceName'),
      dataIndex: 'deviceName',
    },
    {
      title: t('iot.link.device.device.deviceStatus'),
      dataIndex: ['echoMap', 'deviceStatus'],
      customRender: ({ record }) => {
        ``;
        return getDictLabel('LINK_DEVICE_STATUS', record.deviceStatus, '');
      },
    },
    {
      title: t('iot.link.device.device.connectStatus'),
      dataIndex: ['connectStatus'],
      customRender: ({ record }) => {
        return getDictLabel('LINK_DEVICE_CONNECT_STATUS', record.connectStatus, '');
      },
    },
    {
      title: t('iot.link.device.device.description'),
      dataIndex: 'description',
    },
    {
      title: t('iot.link.device.device.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('iot.link.device.device.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.device.device.deviceIdentification'),
      field: 'deviceIdentification ',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.device.device.deviceName'),
      field: 'deviceName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.device.device.connectStatus'),
      field: 'connectStatus',
      colProps: { span: 6 },
      component: 'ApiSelect',
      show: true,
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_CONNECT_STATUS),
      },
    },
    {
      label: t('iot.link.device.device.deviceStatus'),
      field: 'deviceStatus',
      colProps: { span: 6 },
      component: 'ApiSelect',
      show: true,
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_STATUS),
      },
    },
    {
      field: 'createTimeRange',
      label: t('iot.link.device.device.createdTime'),
      component: 'RangePicker',
      colProps: { span: 8 },
    },
  ];
};

// 编辑页字段
export const editFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      label: 'ID',
      field: 'id',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.device.device.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: true,
        // placeholder: `请输入${t('iot.link.device.device.deviceIdentification')}`,
      },
    },
    {
      label: t('iot.link.device.device.actionType'),
      field: 'ApiSelect',
      component: 'Select',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: false,
        // placeholder: `请选择${t('iot.link.device.device.actionType')}`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_ACTION_TYPE),
      },
    },
    {
      label: t('iot.link.device.device.message'),
      field: 'message',
      component: 'InputTextArea',
      show: true,
      rules: [{ required: false }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.message')}`,
      },
    },
    {
      label: t('iot.link.device.device.status'),
      field: 'status',
      component: 'ApiSelect',
      defaultValue: '0',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: false,
        // placeholder: `请选择${t('iot.link.device.device.status')}`,
        ...dictComponentProps(DictEnum.LINK_PRODUCT_STATUS),
      },
    },
    {
      label: t('iot.link.device.device.remark'),
      field: 'remark',
      component: 'InputTextArea',
      show: true,
      rules: [{ required: false }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.remark')}`,
      },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
