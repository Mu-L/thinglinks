import { Ref } from 'vue';
import { yesNoComponentProps } from '/@/utils/thinglinks/common';
import { FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';

const { t } = useI18n();

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
      label: t('devOperation.sop.sopHelpDoc.label'),
      field: 'label',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopHelpDoc.sortValue'),
      field: 'sortValue',
      component: 'InputNumber',
    },
    {
      label: t('devOperation.sop.sopHelpDoc.status'),
      field: 'status',
      component: 'RadioGroup',
      defaultValue: true,
      componentProps: {
        ...yesNoComponentProps(),
      },
    },
    {
      label: t('devOperation.sop.sopHelpDoc.content'),
      field: 'content',
      component: 'InputTextArea',
    },
    {
      label: t('devOperation.sop.sopHelpDoc.contentType'),
      field: 'contentType',
      component: 'InputNumber',
      defaultValue: 1,
      show: false,
    },
    {
      label: t('devOperation.sop.sopHelpDoc.parentId'),
      field: 'parentId',
      component: 'Input',
      defaultValue: 0,
      show: false,
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
