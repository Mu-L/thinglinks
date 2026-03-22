import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('devOperation.sop.sopSysConfig.configKey'),
      dataIndex: 'configKey',
    },
    {
      title: t('devOperation.sop.sopSysConfig.configValue'),
      dataIndex: 'configValue',
    },
    {
      title: t('devOperation.sop.sopSysConfig.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('thinglinks.common.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('devOperation.sop.sopSysConfig.configKey'),
      field: 'configKey',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('devOperation.sop.sopSysConfig.configValue'),
      field: 'configValue',
      component: 'Input',
      colProps: { span: 6 },
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
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      label: t('devOperation.sop.sopSysConfig.configKey'),
      field: 'configKey',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopSysConfig.configValue'),
      field: 'configValue',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopSysConfig.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
