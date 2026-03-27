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
      title: t('card.channel.cardChannelInfoConfig.channelId'),
      dataIndex: 'channelId',
    },
    {
      title: t('card.channel.cardChannelInfoConfig.requestTypeCode'),
      dataIndex: 'requestTypeCode',
      // slots: { customRender: 'requestTypeCode' },
    },
    {
      title: t('card.channel.cardChannelInfoConfig.url'),
      dataIndex: 'url',
    },
    {
      title: t('card.channel.cardChannelInfoConfig.extendParams'),
      dataIndex: 'extendParams',
    },
    {
      title: t('card.channel.cardChannelInfoConfig.remark'),
      dataIndex: 'remark',
    },
    // {
    //   title: t('card.channel.cardChannelInfoConfig.createdOrgId'),
    //   dataIndex: 'createdOrgId',
    // },
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
      label: t('card.channel.cardChannelInfoConfig.requestTypeCode'),
      field: 'requestTypeCode',
      component: 'ApiSelect',
      colProps: { span: 8 },
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.CARD_CHANNEL_CONFIG_REQUEST_TYPE_CODE),
      },
    },
    {
      label: t('card.channel.cardChannelInfoConfig.url'),
      field: 'url',
      component: 'Input',
      colProps: { span: 8 },
    },
    {
      field: 'createTimeRange',
      label: t('thinglinks.common.createdTime'),
      component: 'RangePicker',
      colProps: { span: 8 },
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
      label: t('card.channel.cardChannelInfoConfig.requestTypeCode'),
      field: 'requestTypeCode',
      component: 'ApiSelect',
      colProps: { span: 12 },
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.CARD_CHANNEL_CONFIG_REQUEST_TYPE_CODE),
      },
      required: true,
    },
    {
      label: t('card.channel.cardChannelInfoConfig.url'),
      field: 'url',
      component: 'Input',
      colProps: { span: 12 },
      required: true,
    },
    {
      label: t('card.channel.cardChannelInfoConfig.extendParams'),
      field: 'extendParams',
      component: 'Input',
      colProps: { span: 12 },
      required: true,
    },
    {
      label: t('card.channel.cardChannelInfoConfig.remark'),
      field: 'remark',
      component: 'InputTextArea',
      colProps: { span: 12 },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
