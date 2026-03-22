import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { yesNoComponentProps } from '/@/utils/thinglinks/common';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import type { VideoMediaServerPageQuery } from '/@/api/video/media/model/videoMediaServerModel';

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('video.media.videoStreamPush.appId'),
      dataIndex: 'appId',
    },
    {
      title: t('video.media.videoStreamPush.streamIdentification'),
      dataIndex: 'streamIdentification',
    },
    {
      title: t('video.media.videoStreamPush.totalReaderCount'),
      dataIndex: 'totalReaderCount',
    },
    {
      title: t('video.media.videoStreamPush.originType'),
      dataIndex: 'originType',
    },
    {
      title: t('video.media.videoStreamPush.originUrl'),
      dataIndex: 'originUrl',
    },
    {
      title: t('video.media.videoStreamPush.vhost'),
      dataIndex: 'vhost',
    },
    {
      title: t('video.media.videoStreamPush.bytesSpeed'),
      dataIndex: 'bytesSpeed',
    },
    {
      title: t('video.media.videoStreamPush.aliveSecond'),
      dataIndex: 'aliveSecond',
    },
    {
      title: t('video.media.videoStreamPush.mediaIdentification'),
      dataIndex: 'mediaIdentification',
    },
    {
      title: t('video.media.videoStreamPush.serverId'),
      dataIndex: 'serverId',
    },
    {
      title: t('video.media.videoStreamPush.pushTime'),
      dataIndex: 'pushTime',
    },
    {
      title: t('video.media.videoStreamPush.status'),
      dataIndex: 'status',
    },
    {
      title: t('video.media.videoStreamPush.pushIng'),
      dataIndex: 'pushIng',
    },
    {
      title: t('video.media.videoStreamPush.self'),
      dataIndex: 'self',
    },
    {
      title: t('video.media.videoStreamPush.extendParams'),
      dataIndex: 'extendParams',
    },
    {
      title: t('video.media.videoStreamPush.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('video.media.videoStreamPush.createdOrgId'),
      dataIndex: 'createdOrgId',
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
      label: t('video.media.videoStreamPush.appId'),
      field: 'appId',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('video.media.videoStreamPush.streamIdentification'),
      field: 'streamIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamPush.originType'),
      field: 'originType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamPush.originUrl'),
      field: 'originUrl',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamPush.vhost'),
      field: 'vhost',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamPush.mediaIdentification'),
      field: 'mediaIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamPush.pushTime'),
      field: 'pushTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamPush.status'),
      field: 'status',
      component: 'ApiSelect',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        // placeholder: `请选择状态`,
        // ...dictComponentProps(DictEnum.LINK_DEVICE_COMMAND_STATUS),
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamPush.pushIng'),
      field: 'pushIng',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoComponentProps(),
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamPush.self'),
      field: 'self',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoComponentProps(),
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
      label: t('video.media.videoStreamPush.appId'),
      field: 'appId',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_APPLICATION_SCENARIO),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.streamIdentification'),
      field: 'streamIdentification',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.totalReaderCount'),
      field: 'totalReaderCount',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.originType'),
      field: 'originType',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.originUrl'),
      field: 'originUrl',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.vhost'),
      field: 'vhost',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.bytesSpeed'),
      field: 'bytesSpeed',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.aliveSecond'),
      field: 'aliveSecond',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.mediaIdentification'),
      field: 'mediaIdentification',
      component: 'ApiVideoSelectNodeCard',
      componentProps: ({ formModel, formActionType }) => {
        return {
          type: 'videoNode',
          onSelect: (value: VideoMediaServerPageQuery) => {
            const { validateFields } = formActionType;
            formModel.mediaIdentification = value?.mediaIdentification;
            validateFields(['mediaIdentification']);
          },
          value: formModel.mediaIdentification,
        };
      },
      required: true,
      colProps: {
        span: 22,
      },
    },
    {
      label: t('video.media.videoStreamPush.serverId'),
      field: 'serverId',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.pushTime'),
      field: 'pushTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.status'),
      field: 'status',
      component: 'RadioGroup',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.pushIng'),
      field: 'pushIng',
      component: 'RadioGroup',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.self'),
      field: 'self',
      component: 'RadioGroup',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamPush.extendParams'),
      field: 'extendParams',
      component: 'Input',
    },
    {
      label: t('video.media.videoStreamPush.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
