import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.serviceId'),
      dataIndex: 'serviceId',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.commandId'),
      dataIndex: 'commandId',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.parameterCode'),
      dataIndex: 'parameterCode',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.parameterName'),
      dataIndex: 'parameterName',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.parameterDescription'),
      dataIndex: 'parameterDescription',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.datatype'),
      dataIndex: 'datatype',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.enumlist'),
      dataIndex: 'enumlist',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.max'),
      dataIndex: 'max',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.maxlength'),
      dataIndex: 'maxlength',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.min'),
      dataIndex: 'min',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.required'),
      dataIndex: 'required',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.step'),
      dataIndex: 'step',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.unit'),
      dataIndex: 'unit',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('iot.link.productCommandRequest.productCommandRequest.createdOrgId'),
      dataIndex: 'createdOrgId',
    },
    {
      title: t('common.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    // {
    //   label: t('iot.link.productCommandRequest.productCommandRequest.serviceId'),
    //   field: 'serviceId',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.productCommandRequest.productCommandRequest.commandId'),
    //   field: 'commandId',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.parameterCode'),
      field: 'parameterCode',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.parameterName'),
      field: 'parameterName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.parameterDescription'),
      field: 'parameterDescription',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.datatype'),
      field: 'datatype',
      component: 'Input',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_SERVICE_COMMAND_DATA_TYPE),
      },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.enumlist'),
      field: 'enumlist',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.max'),
      field: 'max',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.maxlength'),
      field: 'maxlength',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.min'),
      field: 'min',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.required'),
      field: 'required',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.step'),
      field: 'step',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.unit'),
      field: 'unit',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.remark'),
      field: 'remark',
      component: 'InputTextArea',
      colProps: { span: 6 },
    },
    {
      field: 'createTimeRange',
      label: t('common.createdTime'),
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
      label: t('iot.link.productCommandRequest.productCommandRequest.serviceId'),
      field: 'serviceId',
      component: 'Input',
      defaultValue: '0',
      show: false,
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.commandId'),
      field: 'commandId',
      component: 'Input',
      defaultValue: '0',
      show: false,
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.parameterCode'),
      field: 'parameterCode',
      component: 'Input',
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.parameterName'),
      field: 'parameterName',
      component: 'Input',
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.parameterDescription'),
      field: 'parameterDescription',
      component: 'Input',
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.datatype'),
      field: 'datatype',
      component: 'ApiSelect',
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_SERVICE_COMMAND_DATA_TYPE),
      },
      rules: [{ required: true }],
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.enumlist'),
      field: 'enumlist',
      component: 'Input',
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.max'),
      field: 'max',
      component: 'Input',
      show: ({ values }) => {
        return ['int', 'decimal'].indexOf(values.datatype) !== -1;
      },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.maxlength'),
      field: 'maxlength',
      component: 'Input',
      rules: [{ required: true }],
      // show: ({ values }) => {
      //   return ['string'].indexOf(values.datatype) !== -1;
      // },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.min'),
      field: 'min',
      component: 'Input',
      show: ({ values }) => {
        return ['int', 'decimal'].indexOf(values.datatype) !== -1;
      },
    },
    {
      label: t('iot.link.productCommandResponse.productCommandResponse.required'),
      field: 'required',
      defaultValue: '1',
      component: 'ApiRadioGroup',
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_SERVICE_REQUIRED),
        isBtn: true,
        isSolid: true,
      },
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.step'),
      field: 'step',
      component: 'Input',
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.unit'),
      field: 'unit',
      component: 'Input',
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
    {
      label: t('iot.link.productCommandRequest.productCommandRequest.createdOrgId'),
      field: 'createdOrgId',
      component: 'Input',
      show: false,
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
