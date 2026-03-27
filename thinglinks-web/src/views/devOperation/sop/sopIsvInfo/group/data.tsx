import { FormSchema } from '/@/components/Table';
import { query } from '/@/api/devOperation/sop/sopPermGroup';
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();
// 编辑页字段
export const editFormSchema = (): FormSchema[] => {
  return [
    {
      field: 'isvId',
      label: 'isvId',
      component: 'Input',
      show: false,
    },
    {
      label: t('devOperation.sop.sopIsvInfo.group'),
      field: 'groupIdList',
      component: 'ApiSelect',
      componentProps: {
        api: query,
        labelField: 'groupName',
        valueField: 'id',
        showSearch: true,
        mode: 'multiple',
      },
    },
  ];
};
