import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { query } from '../../../../../api/iot/rule/alarm/channel';
import { DictEnum } from '/@/enums/commonEnum';
import { useDict } from '/@/components/Dict';
const { getDictLabel } = useDict();

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.engine.alarmRecord.alarmName'),
      dataIndex: 'alarmName',
    },
    {
      title: t('iot.link.engine.alarmRecord.alarmIdentification'),
      dataIndex: 'alarmIdentification',
    },
    {
      title: t('iot.link.engine.alarmRecord.channelId'),
      dataIndex: 'channelId',
    },
    {
      title: t('iot.link.engine.alarmRecord.occurredTime'),
      dataIndex: 'occurredTime',
    },
    {
      title: t('iot.link.engine.alarmRecord.contentData'),
      dataIndex: 'contentData',
      width: 180,
    },
    {
      title: t('iot.link.engine.alarmRecord.handledTime'),
      dataIndex: 'handledTime',
    },
    {
      title: t('iot.link.engine.alarmRecord.handledStatus'),
      dataIndex: ['handledStatus'],
      customRender: ({ record }) => {
        return getDictLabel('RULE_ALARM_RECORD_HANDLED_STATUE', record.handledStatus, '');
      },
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.engine.alarmRecord.alarmIdentification'),
      field: 'alarmIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.engine.alarmRecord.handledStatus'),
      field: 'handledStatus',
      component: 'ApiSelect',
      colProps: { span: 5 },
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_ALARM_RECORD_HANDLED_STATUE),
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
// 查看页字段
export const viewFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.engine.alarmRecord.contentData'),
      field: 'contentData',
      component: 'Input',
      show: true,
      rules: [{ required: true }],
    },
    {
      label: t('iot.link.engine.alarmRecord.handledTime'),
      field: 'handledTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      colProps: { span: 12 },
    },
    {
      label: t('iot.link.engine.alarmRecord.resolvedTime'),
      field: 'resolvedTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      colProps: { span: 12 },
    },
    {
      label: t('iot.link.engine.alarmRecord.handlingNotes'),
      field: 'handlingNotes',
      component: 'InputTextArea',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        ...dictComponentProps(DictEnum.RULE_ALARM_LEVEL),
      },
    },
    {
      label: t('iot.link.engine.alarmRecord.resolutionNotes'),
      field: 'resolutionNotes',
      component: 'InputTextArea',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        ...dictComponentProps(DictEnum.RULE_ALARM_LEVEL),
      },
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
      field: 'handledStatus',
      label: 'handledStatus',
      component: 'Input',
      show: false,
      componentProps: {
        disabled: false,
        allowClear: true,
        ...dictComponentProps(DictEnum.RULE_ALARM_RECORD_HANDLED_STATUE),
      },
    },
    {
      label: t('iot.link.engine.alarmRecord.handlingNotes'),
      field: 'handleNotes',
      component: 'InputTextArea',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        ...dictComponentProps(DictEnum.RULE_ALARM_LEVEL),
      },
    },
  ];
};
export const editresolutionFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      field: 'id',
      label: 'ID',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.engine.alarmRecord.resolutionNotes'),
      field: 'handleNotes',
      component: 'InputTextArea',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        ...dictComponentProps(DictEnum.RULE_ALARM_LEVEL),
      },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
