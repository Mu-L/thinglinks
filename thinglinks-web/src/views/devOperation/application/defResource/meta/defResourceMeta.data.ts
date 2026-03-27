import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();

// 元数据 表格
export const metaJsonColumns: BasicColumn[] = [
  {
    title: 'key',
    dataIndex: 'key',
  },
  {
    title: 'value',
    dataIndex: 'value',
    format: (text: string | number | boolean) => {
      if (text === true) {
        return 'true';
      } else if (text === false) {
        return 'false';
      } else {
        return text;
      }
    },
  },
];

// 元数据 编辑表单
export const editMetaFormSchema = (): FormSchema[] => {
  return [
    {
      label: 'key',
      field: 'key',
      component: 'AutoComplete',
      required: true,
      helpMessage: [t('devOperation.application.defResource.keyHelpMsg')],
      componentProps: {
        allowClear: true,
        getPopupContainer: () => document.body,
        filterOption: (input: string, option) => {
          return option.value.toUpperCase().indexOf(input.toUpperCase()) >= 0;
        },
        options: [
          { value: 'title' },
          { value: 'ignoreKeepAlive' },
          { value: 'affix' },
          { value: 'transitionName' },
          { value: 'hideBreadcrumb' },
          { value: 'carryParam' },
          { value: 'hideChildrenInMenu' },
          { value: 'currentActiveMenu' },
          { value: 'hideTab' },
          { value: 'hideMenu' },
          { value: 'ignoreRoute' },
          { value: 'content' },
          { value: 'dot' },
          { value: 'type' },
        ],
      },
    },
    {
      label: 'value',
      field: 'value',
      component: 'Input',
      required: true,
    },
  ];
};
