import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum, FileBizTypeEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { Tag, Tooltip, message } from 'ant-design-vue';
import CopyableText from '/@/components/CopyableText';

const { t } = useI18n();

// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.productProperty.productProperty.serviceId'),
      dataIndex: 'serviceId',
    },
    {
      title: t('iot.link.productProperty.productProperty.propertyCode'),
      dataIndex: 'propertyCode',
      width: 150,
      ellipsis: true,
      customRender: ({ record }) => {
        return <CopyableText text={record.propertyCode || ''} />;
      },
    },
    {
      title: t('iot.link.productProperty.productProperty.propertyName'),
      dataIndex: 'propertyName',
      width: 120,
      ellipsis: true,
      customRender: ({ record }) => {
        return (
          <Tooltip placement="topLeft" title={record.propertyName}>
            <span style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
              {record.propertyName}
            </span>
          </Tooltip>
        );
      },
    },
    {
      title: t('iot.link.productProperty.productProperty.datatype'),
      dataIndex: 'datatype',
      width: 100,
    },
    {
      title: t('iot.link.productProperty.productProperty.description'),
      dataIndex: 'description',
      width: 150,
      ellipsis: true,
      customRender: ({ record }) => {
        return (
          <Tooltip placement="topLeft" title={record.description}>
            <span style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
              {record.description}
            </span>
          </Tooltip>
        );
      },
    },
    {
      title: t('iot.link.productProperty.productProperty.enumlist'),
      dataIndex: 'enumlist',
      width: 100,
    },
    {
      title: t('iot.link.productProperty.productProperty.max'),
      dataIndex: 'max',
      width: 80,
    },
    {
      title: t('iot.link.productProperty.productProperty.maxlength'),
      dataIndex: 'maxlength',
      width: 100,
    },
    {
      title: t('iot.link.productProperty.productProperty.method'),
      dataIndex: 'method',
      width: 100,
    },
    {
      title: t('iot.link.productProperty.productProperty.min'),
      dataIndex: 'min',
      width: 80,
    },
    {
      title: t('iot.link.productProperty.productProperty.required'),
      dataIndex: 'required',
      width: 90,
    },
    {
      title: t('iot.link.productProperty.productProperty.step'),
      dataIndex: 'step',
      width: 80,
    },
    {
      title: t('iot.link.productProperty.productProperty.unit'),
      dataIndex: 'unit',
      width: 70,
    },
    {
      title: t('iot.link.productProperty.productProperty.icon'),
      dataIndex: 'icon',
      width: 80,
    },
    {
      title: t('iot.link.productProperty.productProperty.remark'),
      dataIndex: 'remark',
      width: 120,
      ellipsis: true,
      customRender: ({ record }) => {
        return (
          <Tooltip placement="topLeft" title={record.remark}>
            <span style={{ overflow: 'hidden', textOverflow: 'ellipsis', whiteSpace: 'nowrap' }}>
              {record.remark}
            </span>
          </Tooltip>
        );
      },
    },
    {
      title: t('iot.link.productProperty.productProperty.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
    {
      title: t('thinglinks.common.updatedTime'),
      dataIndex: 'updatedTime',
      sorter: true,
      width: 180,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.productProperty.productProperty.propertyCode'),
      field: 'propertyCode',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productProperty.productProperty.propertyName'),
      field: 'propertyName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.productProperty.productProperty.description'),
      field: 'description',
      component: 'Input',
      colProps: { span: 6 },
    }
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
      label: t('iot.link.productProperty.productProperty.serviceId'),
      field: 'serviceId',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.productProperty.productProperty.propertyCode'),
      field: 'propertyCode',
      component: 'Input',
      rules: [{ required: true }],
      componentProps: {
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.propertyName'),
      field: 'propertyName',
      component: 'Input',
      rules: [{ required: true }],
      componentProps: {
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.datatype'),
      field: 'datatype',
      component: 'ApiSelect',
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_SERVICE_PROPERTY_DATA_TYPE),
      },
      rules: [{ required: true }],
    },
    {
      label: t('iot.link.productProperty.productProperty.icon'),
      field: 'icon',
      component: 'Upload',
      // colProps: { span: 22 },
      componentProps: ({ schema, tableAction, formActionType, formModel }) => {
        return {
          isDef: false,
          maxSize: 2048,
          multiple: false,
          maxNumber: 1,
          accept: ['.png','.jpg'],
          uploadParams: { bizType: FileBizTypeEnum.BASE_LINK_PRODUCT_ICON },
          resultField: 'id',
          onChange: async (file) => {
            console.log(111, file);

            await formActionType.setFieldsValue({ icon: [file[0]] });
            await formActionType.validateFields(['icon']);
          },
        };
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.description'),
      field: 'description',
      component: 'InputTextArea',
      colProps: {
        span: 22,
      },
      componentProps: {
        rows: 3,
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.enumlist'),
      field: 'enumlist',
      component: 'Input',
      show: ({ values }) => {
        return ['string'].indexOf(values.datatype) !== -1;
      },
      componentProps: {
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.min'),
      field: 'min',
      component: 'Input',
      show: ({ values }) => {
        return ['int', 'decimal'].indexOf(values.datatype) !== -1;
      },
      componentProps: {
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.max'),
      field: 'max',
      component: 'Input',
      show: ({ values }) => {
        return ['int', 'decimal'].indexOf(values.datatype) !== -1;
      },
      componentProps: {
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.maxlength'),
      field: 'maxlength',
      component: 'Input',
      rules: [{ required: true }],
      // show: ({ values }) => {
      //   return ['string','DateTime'].indexOf(values.datatype) !== -1;
      // },
      componentProps: {
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.method'),
      field: 'method',
      component: 'ApiRadioGroup',
      rules: [{ required: true }],
      defaultValue: 'RWE',
      componentProps: {
        ...dictComponentProps(DictEnum.ACCESS_MODE),
        isBtn: true,
        isSolid: true,
      },
    },

    {
      label: t('iot.link.productProperty.productProperty.required'),
      field: 'required',
      defaultValue: '1',
      component: 'ApiRadioGroup',
      rules: [{ required: true }],
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_SERVICE_REQUIRED),
        isBtn: true,
        isSolid: true,
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.step'),
      field: 'step',
      component: 'Input',
      componentProps: {
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.unit'),
      field: 'unit',
      component: 'Input',
      componentProps: {
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.remark'),
      field: 'remark',
      component: 'InputTextArea',
      colProps: {
        span: 22,
      },
      componentProps: {
        rows: 3,
        placeholder: t('common.inputText'),
      },
    },
    {
      label: t('iot.link.productProperty.productProperty.createdOrgId'),
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
