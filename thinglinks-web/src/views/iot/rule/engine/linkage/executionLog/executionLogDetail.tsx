import { BasicColumn, FormSchema } from '/@/components/Table';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';
import { useI18n } from '/@/hooks/web/useI18n';
const { t } = useI18n();

export function columns(): BasicColumn[] {
  return [
    {
      dataIndex: 'id',
      title: t('iot.link.engine.executionLog.id'),
    },
    {
      dataIndex: 'ruleName',
      title: t('iot.link.engine.executionLog.ruleName'),
    },
    {
      dataIndex: 'status',
      title: t('iot.link.engine.executionLog.status'),
      slots: { customRender: 'status' },
    },
    {
      dataIndex: 'startTime',
      title: t('iot.link.engine.executionLog.startTime'),
    },
    {
      dataIndex: 'endTime',
      title: t('iot.link.engine.executionLog.endTime'),
    },
    {
      dataIndex: 'remark',
      title: t('iot.link.engine.executionLog.remark'),
      // slots: { customRender: 'remark' },
      width: 200,
    },
    {
      dataIndex: 'extendParams',
      title: t('iot.link.engine.executionLog.extendParams'),
      // slots: { customRender: 'extendParams' },
    },
  ];
}
export function searchFormSchema(): FormSchema[] {
  return [
    {
      label: t('iot.link.engine.executionLog.id'),
      field: 'id',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.engine.executionLog.status'),
      field: 'status',
      component: 'ApiSelect',
      componentProps: {
        ...dictComponentProps(DictEnum.RULE_EXECUTION_LOG_STATUS),
      },
      colProps: { span: 6 },
    },
    {
      field: 'createTimeRange',
      label: t('thinglinks.common.createdTime'),
      component: 'RangePicker',
      colProps: { span: 6 },
    },
  ];
}
export function conditionColumns(): BasicColumn[] {
  return [
    {
      dataIndex: 'conditionUuid',
      title: t('iot.link.engine.executionLog.condition.conditionUuid'),
    },
    {
      dataIndex: 'conditionType',
      title: t('iot.link.engine.executionLog.condition.conditionType'),
      slots: { customRender: 'conditionType' },
    },
    {
      dataIndex: 'evaluationResult',
      title: t('iot.link.engine.executionLog.condition.evaluationResult'),
      slots: { customRender: 'evaluationResult' },
    },
    {
      dataIndex: 'startTime',
      title: t('iot.link.engine.executionLog.condition.startTime'),
    },
    {
      dataIndex: 'endTime',
      title: t('iot.link.engine.executionLog.condition.endTime'),
    },
    {
      dataIndex: 'remark',
      title: t('iot.link.engine.executionLog.remark'),
      slots: { customRender: 'remark' },
    },
    {
      dataIndex: 'extendParams',
      title: t('iot.link.engine.executionLog.extendParams'),
      slots: { customRender: 'extendParams' },
    },
  ];
}
export function actionColumns(): BasicColumn[] {
  return [
    {
      dataIndex: 'ruleExecutionId',
      title: t('iot.link.engine.executionLog.action.ruleExecutionId'),
    },
    {
      dataIndex: 'actionType',
      title: t('iot.link.engine.executionLog.action.actionType'),
      slots: { customRender: 'actionType' },
    },
    {
      dataIndex: 'actionContent',
      title: t('iot.link.engine.executionLog.action.actionContent'),
      slots: { customRender: 'actionContent' },
    },
    {
      dataIndex: 'result',
      title: t('iot.link.engine.executionLog.action.result'),
      slots: { customRender: 'result' },
    },
    {
      dataIndex: 'startTime',
      title: t('iot.link.engine.executionLog.action.startTime'),
    },
    {
      dataIndex: 'endTime',
      title: t('iot.link.engine.executionLog.action.endTime'),
    },
    {
      dataIndex: 'remark',
      title: t('iot.link.engine.executionLog.remark'),
      slots: { customRender: 'remark' },
    },
    {
      dataIndex: 'extendParams',
      title: t('iot.link.engine.executionLog.extendParams'),
      slots: { customRender: 'extendParams' },
    },
  ];
}
