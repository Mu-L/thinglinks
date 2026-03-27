import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';

const { t } = useI18n();

// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('devOperation.sop.sopApiInfo.application'),
      dataIndex: 'application',
    },
    {
      title: t('devOperation.sop.sopApiInfo.apiName'),
      dataIndex: 'apiName',
    },
    {
      title: t('devOperation.sop.sopApiInfo.apiVersion'),
      dataIndex: 'apiVersion',
    },
    {
      title: t('devOperation.sop.sopApiInfo.description'),
      dataIndex: 'description',
    },
    {
      title: t('devOperation.sop.sopApiInfo.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('devOperation.sop.sopApiInfo.status'),
      dataIndex: 'status',
      width: 80,
      format(_, record) {
        return record.status === 1 ? t('thinglinks.common.enable') : t('thinglinks.common.disable');
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
      label: t('devOperation.sop.sopApiInfo.application'),
      field: 'application',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('devOperation.sop.sopApiInfo.apiName'),
      field: 'apiName',
      component: 'Input',
      colProps: { span: 6 },
    },
  ];
};
