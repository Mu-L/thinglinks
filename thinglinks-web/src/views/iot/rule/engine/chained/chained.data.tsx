import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';
import { buildUUID } from '/@/utils/uuid';
const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.engine.chained.id'),
      dataIndex: 'id',
    },
    {
      title: t('iot.link.engine.chained.ruleName'),
      dataIndex: 'ruleName',
    },
    {
      title: t('iot.link.engine.chained.type'),
      dataIndex: 'type',
      slots: { customRender: 'type' },
    },
    {
      title: t('iot.link.engine.chained.remark'),
      dataIndex: 'remark',
      ellipsis: true,
      showSorterTooltip: true,
      customRender: ({ record }) => {
        return record.remark;
      },
    },
    {
      title: t('iot.link.engine.chained.status'),
      dataIndex: 'status',
      slots: { customRender: 'status' },
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      field: 'ruleName',
      label: t('iot.link.engine.chained.ruleName'),
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      field: 'type',
      label: t('iot.link.engine.chained.type'),
      component: 'ApiSelect',
      colProps: { span: 6 },
      show: true,
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_INSTANCE_TYPE),
      },
    },
    {
      label: t('iot.link.engine.chained.instanceAddress'),
      field: 'instanceAddress',
      component: 'ApiSelect',
      show: true,
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_INSTANCE_ADDRESS),
      },
    },
    {
      label: t('iot.link.engine.chained.status'),
      field: 'status',
      component: 'ApiSelect',
      show: true,
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_INSTANCE_STATUS),
      },
    },
    {
      field: 'createTimeRange',
      label: t('thinglinks.common.createdTime'),
      component: 'RangePicker',
      colProps: { span: 6 },
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
      label: t('iot.link.engine.chained.appId'),
      field: 'appId',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('iot.link.engine.chained.ruleName'),
      field: 'ruleName',
      component: 'Input',
      rules: [{ required: true }],
    },
    {
      label: t('iot.link.engine.chained.instanceAddress'),
      field: 'instanceAddress',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.RULE_INSTANCE_ADDRESS),
      },
    },
    {
      label: t('iot.link.engine.chained.status'),
      field: 'status',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        numberToString: true,
        ...dictComponentProps(DictEnum.RULE_INSTANCE_STATUS),
      },
    },
    {
      label: t('iot.link.engine.chained.type'),
      field: 'type',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        // placeholder: `请选择${t('iot.link.device.device.actionType')}`,
        ...dictComponentProps(DictEnum.RULE_INSTANCE_TYPE),
      },
    },
    {
      label: t('iot.link.engine.chained.flowId'),
      field: 'flowId',
      component: 'Input',
      show: true,
      rules: [{ required: false }],
      defaultValue: buildUUID(),
      componentProps: {
        disabled: true,
      },
    },
    {
      label: t('iot.link.engine.chained.flowData'),
      field: 'flowData',
      component: 'InputTextArea',
      show: true,
      rules: [{ required: false }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.engine.chained.flowData')}`,
      },
    },
    {
      label: t('iot.link.engine.chained.remark'),
      field: 'remark',
      component: 'InputTextArea',
      show: true,
      rules: [{ required: false }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.engine.chained.remark')}`,
      },
    },
    {
      label: t('iot.link.product.product.createdOrgId'),
      field: 'createdOrgId',
      component: 'Input',
      show: false,
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
