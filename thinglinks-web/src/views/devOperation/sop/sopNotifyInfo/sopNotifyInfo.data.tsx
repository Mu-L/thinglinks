import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('devOperation.sop.sopNotifyInfo.appId'),
      dataIndex: 'appId',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.apiName'),
      dataIndex: 'apiName',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.apiVersion'),
      dataIndex: 'apiVersion',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.lastSendTime'),
      dataIndex: 'lastSendTime',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.nextSendTime'),
      dataIndex: 'nextSendTime',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.sendCnt'),
      dataIndex: 'sendCnt',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.content'),
      dataIndex: 'content',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.notifyStatus'),
      dataIndex: ['echoMap', 'notifyStatus'],
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.errorMsg'),
      dataIndex: 'errorMsg',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.resultContent'),
      dataIndex: 'resultContent',
    },
    {
      title: t('devOperation.sop.sopNotifyInfo.remark'),
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
      label: t('devOperation.sop.sopNotifyInfo.appId'),
      field: 'appId',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.apiName'),
      field: 'apiName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.notifyStatus'),
      field: 'notifyStatus',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.NotifyStatusEnum),
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
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.appId'),
      field: 'appId',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.apiName'),
      field: 'apiName',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.apiVersion'),
      field: 'apiVersion',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.notifyUrl'),
      field: 'notifyUrl',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.lastSendTime'),
      field: 'lastSendTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.nextSendTime'),
      field: 'nextSendTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.sendMax'),
      field: 'sendMax',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.sendCnt'),
      field: 'sendCnt',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.content'),
      field: 'content',
      component: 'InputTextArea',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.notifyStatus'),
      field: 'notifyStatus',
      component: 'InputTextArea',
      defaultValue: '1',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.errorMsg'),
      field: 'errorMsg',
      component: 'InputTextArea',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.resultContent'),
      field: 'resultContent',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
    {
      label: t('devOperation.sop.sopNotifyInfo.tenantId'),
      field: 'tenantId',
      component: 'Input',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
