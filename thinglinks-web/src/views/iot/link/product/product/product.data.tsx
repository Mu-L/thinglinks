import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum, FileBizTypeEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { useDict } from '/@/components/Dict';
const { getDictLabel } = useDict();
const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.product.product.appId'),
      dataIndex: 'appId',
    },
    {
      title: t('iot.link.product.product.templateId'),
      dataIndex: 'templateId',
    },
    {
      title: t('iot.link.product.product.productName'),
      dataIndex: 'productName',
    },
    {
      title: t('iot.link.product.product.productIdentification'),
      dataIndex: 'productIdentification',
    },
    {
      title: t('iot.link.product.product.productType'),
      dataIndex: 'productType',
      slots: { customRender: 'productTypeColumn' },
    },
    {
      title: t('iot.link.product.product.manufacturerId'),
      dataIndex: 'manufacturerId',
    },
    {
      title: t('iot.link.product.product.manufacturerName'),
      dataIndex: 'manufacturerName',
    },
    {
      title: t('iot.link.product.product.model'),
      dataIndex: 'model',
    },
    {
      title: t('iot.link.product.product.dataFormat'),
      dataIndex: 'dataFormat',
    },
    {
      title: t('iot.link.product.product.deviceType'),
      dataIndex: 'deviceType',
    },
    {
      title: t('iot.link.product.product.protocolType'),
      dataIndex: 'protocolType',
    },
    {
      title: t('iot.link.product.product.productStatus'),
      dataIndex: 'productStatus',
      slots: { customRender: 'productStatus' },
    },
    {
      title: t('iot.link.product.product.productVersion'),
      dataIndex: 'productVersion',
    },
    {
      title: t('iot.link.product.product.icon'),
      dataIndex: 'icon',
    },
    {
      title: t('iot.link.product.product.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('iot.link.product.product.createdOrgId'),
      dataIndex: 'createdOrgId',
    },
    {
      title: t('iot.link.product.product.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.device.device.appId'),
      field: 'appId',
      colProps: { span: 6 },
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('iot.link.product.product.protocolType'),
      field: 'protocolType',
      colProps: { span: 6 },
      component: 'ApiSelect',
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_PROTOCOL_TYPE),
      },
    },
    // {
    //   label: t('iot.link.product.product.templateId'),
    //   field: 'templateId',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    {
      label: t('iot.link.product.product.productName'),
      field: 'productName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.product.product.productIdentification'),
      field: 'productIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.product.product.productType'),
      field: 'productType',
      colProps: { span: 6 },
      component: 'ApiSelect',
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_TYPE),
      },
    },
    {
      label: t('iot.link.product.product.manufacturerId'),
      field: 'manufacturerId',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.product.product.manufacturerName'),
      field: 'manufacturerName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.product.product.model'),
      field: 'model',
      component: 'Input',
      colProps: { span: 6 },
    },
    // {
    //   label: t('iot.link.product.product.dataFormat'),
    //   field: 'dataFormat',
    //   colProps: { span: 6 },
    //   component: 'ApiSelect',
    //   defaultValue: 'JSON',
    //   componentProps: {
    //     disabled: true,
    //     placeholder: `请选择${t('iot.link.product.product.dataFormat')}`,
    //     ...dictComponentProps(DictEnum.LINK_PRODUCT_DATA_FORMAT),
    //   },
    // },
    {
      label: t('iot.link.product.product.deviceType'),
      field: 'deviceType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.product.product.protocolType'),
      field: 'protocolType',
      colProps: { span: 6 },
      component: 'ApiSelect',
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_PROTOCOL_TYPE),
      },
    },
    {
      label: t('iot.link.product.product.productStatus'),
      field: 'productStatus',
      colProps: { span: 6 },
      component: 'ApiSelect',
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_STATUS),
      },
    },
    {
      label: t('iot.link.product.product.productVersion'),
      field: 'productVersion',
      component: 'Input',
      colProps: { span: 6 },
    },
    // {
    //   label: t('iot.link.product.product.remark'),
    //   field: 'remark',
    //   component: 'InputTextArea',
    //   colProps: { span: 6 },
    // },
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
      label: t('iot.link.product.product.appId'),
      field: 'appId',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_APPLICATION_SCENARIO),
      },
      helpMessage: t('iot.link.product.product.appId'),
    },
    {
      label: t('iot.link.product.product.templateId'),
      field: 'id',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.product.product.productName'),
      field: 'productName',
      component: 'Input',
      rules: [{ required: true }],
      helpMessage: t('iot.link.product.product.helpMessage.productName'),
    },
    {
      label: t('iot.link.product.product.productIdentification'),
      field: 'productIdentification',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.product.product.productType'),
      field: 'productType',
      defaultValue: '1',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: _type.value === ActionEnum.EDIT,
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_PRODUCT_TYPE),
      },
      helpMessage: t('iot.link.product.product.helpMessage.productType'),
    },
    {
      label: t('iot.link.product.product.manufacturerId'),
      field: 'manufacturerId',
      component: 'Input',
      rules: [{ required: true }],
      helpMessage: t('iot.link.product.product.helpMessage.manufacturerId'),
    },
    {
      label: t('iot.link.product.product.manufacturerName'),
      field: 'manufacturerName',
      component: 'Input',
      rules: [{ required: true }],
      helpMessage: t('iot.link.product.product.helpMessage.manufacturerName'),
    },
    {
      label: t('iot.link.product.product.model'),
      field: 'model',
      component: 'Input',
      rules: [{ required: true }],
      helpMessage: t('iot.link.product.product.helpMessage.model'),
    },
    {
      label: t('iot.link.product.product.dataFormat'),
      field: 'dataFormat',
      component: 'ApiSelect',
      defaultValue: 'JSON',
      rules: [{ required: true }],
      componentProps: {
        disabled: true,
        allowClear: false,
        // placeholder: `请选择${t('iot.link.product.product.dataFormat')}`,
        ...dictComponentProps(DictEnum.LINK_PRODUCT_DATA_FORMAT),
      },
      helpMessage: t('iot.link.product.product.helpMessage.dataFormat'),
    },
    {
      label: t('iot.link.product.product.deviceType'),
      field: 'deviceType',
      component: 'Input',
      rules: [{ required: true }],
      helpMessage: t('iot.link.product.product.helpMessage.deviceType'),
    },
    {
      label: t('iot.link.product.product.protocolType'),
      field: 'protocolType',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_PRODUCT_PROTOCOL_TYPE),
      },
      helpMessage: t('iot.link.product.product.helpMessage.protocolType'),
    },
    {
      label: t('iot.link.product.product.productStatus'),
      field: 'productStatus',
      defaultValue: '0',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_PRODUCT_STATUS),
      },

      helpMessage: t('iot.link.product.product.productStatus'),
    },
    {
      label: t('iot.link.product.product.productVersion'),
      field: 'productVersion',
      component: 'Input',
      rules: [{ required: true }],
      helpMessage: t('iot.link.product.product.helpMessage.productVersion'),
    },
    {
      label: t('iot.link.product.product.icon'),
      field: 'icon',
      component: 'Upload',
      // colProps: { span: 22 },
      componentProps: ({ schema, tableAction, formActionType, formModel }) => {
        return {
          isDef: false,
          multiple: false,
          maxNumber: 1,
          accept: ['.png'],
          uploadParams: { bizType: FileBizTypeEnum.BASE_LINK_PRODUCT_ICON },
          resultField: 'id',
          onChange: async (file) => {
            await formActionType.setFieldsValue({ icon: [file[0]] });
            await formActionType.validateFields(['icon']);
          },
        };
      },
    },
    {
      label: t('iot.link.product.product.remark'),
      field: 'remark',
      component: 'InputTextArea',
      helpMessage: t('iot.link.product.product.remark'),
    },
    {
      label: t('iot.link.product.product.createdOrgId'),
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
