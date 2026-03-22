import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import {
  dictComponentProps2,
  stateNumberComponentProps,
  yesNoNumberComponentProps,
} from '/@/utils/thinglinks/common';

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
      title: t('devOperation.sop.sopApiInfo.isPermission'),
      dataIndex: 'isPermission',
      format(_, record) {
        return record.isPermission === 1 ?  t('thinglinks.common.yes') : t('thinglinks.common.no');
      },
    },
    {
      title: t('devOperation.sop.sopApiInfo.isNeedToken'),
      dataIndex: 'isNeedToken',
      format(_, record) {
        return record.isNeedToken === 1 ? t('thinglinks.common.yes') : t('thinglinks.common.no');
      },
    },
    {
      title: t('devOperation.sop.sopApiInfo.hasCommonResponse'),
      dataIndex: 'hasCommonResponse',
      format(_, record) {
        return record.hasCommonResponse === 1 ? t('thinglinks.common.yes') : t('thinglinks.common.no');
      },
    },
    {
      title: t('devOperation.sop.sopApiInfo.status'),
      dataIndex: 'status',
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
    {
      label: t('devOperation.sop.sopApiInfo.description'),
      field: 'description',
      component: 'Input',
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
      label: t('devOperation.sop.sopApiInfo.application'),
      field: 'application',
      component: 'Input',
      dynamicDisabled: true,
    },
    {
      label: t('devOperation.sop.sopApiInfo.apiName'),
      field: 'apiName',
      component: 'Input',
      dynamicDisabled: true,
    },
    {
      label: t('devOperation.sop.sopApiInfo.apiVersion'),
      field: 'apiVersion',
      component: 'Input',
      defaultValue: '1.0',
      dynamicDisabled: true,
    },
    {
      label: t('devOperation.sop.sopApiInfo.description'),
      field: 'description',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopApiInfo.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
    {
      label: t('devOperation.sop.sopApiInfo.interfaceClassName'),
      field: 'interfaceClassName',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopApiInfo.methodName'),
      field: 'methodName',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopApiInfo.paramInfo'),
      field: 'paramInfo',
      component: 'Input',
    },
    {
      label: t('devOperation.sop.sopApiInfo.isPermission'),
      field: 'isPermission',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoNumberComponentProps(),
      },
      defaultValue: 0,
    },
    {
      label: t('devOperation.sop.sopApiInfo.isNeedToken'),
      field: 'isNeedToken',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoNumberComponentProps(),
      },
      defaultValue: 0,
    },
    {
      label: t('devOperation.sop.sopApiInfo.hasCommonResponse'),
      field: 'hasCommonResponse',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoNumberComponentProps(),
      },
      defaultValue: 1,
    },
    {
      label: t('devOperation.sop.sopApiInfo.regSource'),
      field: 'regSource',
      component: 'ApiRadioGroup',
      componentProps: {
        ...dictComponentProps2({
          type: DictEnum.SOP_API_INFO_REG_SOURCE,
          extendFirst: true,
          stringToNumber: true,
        }),
      },
      // componentProps: {
      //   options: [
      //     { label: '系统注册', value: 1 },
      //     { label: '手动注册', value: 2 },
      //   ],
      // },
      defaultValue: 1,
    },
    {
      label: t('devOperation.sop.sopApiInfo.apiMode'),
      field: 'apiMode',
      component: 'RadioGroup',
      componentProps: {
        options: [
          { label: 'open', value: 1 },
          { label: 'Restful', value: 2 },
        ],
      },
      defaultValue: 1,
    },
    {
      label: t('devOperation.sop.sopApiInfo.status'),
      field: 'status',
      component: 'RadioGroup',
      componentProps: {
        ...stateNumberComponentProps(),
      },
      defaultValue: 1,
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
