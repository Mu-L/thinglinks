import { Ref } from 'vue';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.productTopic.productTopic.functionType'),
      dataIndex: ['echoMap', 'functionType'],
    },
    {
      title: t('iot.link.productTopic.productTopic.topic'),
      dataIndex: 'topic',
      ellipsis: true,
      showSorterTooltip: true,
      enableCopy: true,
      enableTooltip: true,
      tooltipPlacement: 'topLeft',
    },
    {
      title: t('iot.link.productTopic.productTopic.publisher'),
      dataIndex: ['echoMap', 'publisher'],
    },
    {
      title: t('iot.link.productTopic.productTopic.subscriber'),
      dataIndex: ['echoMap', 'subscriber'],
    },
    {
      title: t('iot.link.productTopic.productTopic.remark'),
      dataIndex: 'remark',
      ellipsis: true,
      showSorterTooltip: true,
      enableTooltip: true,
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.productTopic.productTopic.topic'),
      field: 'topic',
      component: 'Input',
      colProps: { span: 6 },
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
      label: t('iot.link.productTopic.productTopic.topic'),
      field: 'topic',
      component: 'Input',
      rules: [{ required: true }],
    },
    {
      label: t('iot.link.productTopic.productTopic.functionType'),
      field: 'functionType',
      component: 'ApiSelect',
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_PRODUCT_TOPIC_FUNCTION_TYPE),
      },
    },
    {
      label: t('iot.link.productTopic.productTopic.publisher'),
      field: 'publisher',
      component: 'ApiSelect',
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_PRODUCT_TOPIC_PUBLISHER),
      },
    },
    {
      label: t('iot.link.productTopic.productTopic.subscriber'),
      field: 'subscriber',
      component: 'ApiSelect',
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_PRODUCT_TOPIC_SUBSCRIBER),
      },
    },
    {
      label: t('iot.link.productTopic.productTopic.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
