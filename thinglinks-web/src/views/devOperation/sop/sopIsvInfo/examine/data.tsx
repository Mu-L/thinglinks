import { FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();

// 编辑页字段
export const editFormSchema = (): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'id',
      component: 'Input',
      show: false,
    },
    {
      label: t('devOperation.sop.sopIsvInfo.auditStatus'),
      field: 'auditStatus',
      rules: [{ required: true, type: 'number', message: '必填项' }],
      component: 'RadioButtonGroup',
      componentProps: {
        isBtn: true,
        options: [
          { value: 2, label: '通过' },
          { value: 99, label: '退回' },
        ],
      },
    },
    {
      label: t('devOperation.sop.sopIsvInfo.reviewComments'),
      field: 'reviewComments',
      component: 'InputTextArea',
    },
  ];
};
