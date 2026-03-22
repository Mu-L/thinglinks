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
      title: t('devOperation.sop.sopDocInfo.docTitle'),
      dataIndex: 'docTitle',
    },
    {
      title: t('devOperation.sop.sopDocInfo.docName'),
      dataIndex: 'docName',
    },
    {
      title: t('devOperation.sop.sopDocInfo.docVersion'),
      dataIndex: 'docVersion',
      width: 100,
    },
    {
      title: t('devOperation.sop.sopDocInfo.description'),
      dataIndex: 'description',
    },
    {
      title: t('devOperation.sop.sopDocInfo.isPublish'),
      dataIndex: 'isPublish',
      width: 80,
      format: (_, record) => {
        return record.isPublish === 1 ? t('devOperation.sop.sopDocInfo.publish') : t('devOperation.sop.sopDocInfo.offline');
      },
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
      label: t('common.searchText'),
      field: 'docTitle',
      component: 'Input',
      colProps: { span: 6 },
    },
  ];
};

// 编辑页字段
export const editFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      label: t('devOperation.sop.sopDocApp.token'),
      field: 'token',
      component: 'Input',
      required: true,
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
