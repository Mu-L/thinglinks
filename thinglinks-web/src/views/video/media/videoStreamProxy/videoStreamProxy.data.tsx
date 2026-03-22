import { Ref } from 'vue';
import { renderYesNoComponent, yesNoComponentProps } from '/@/utils/thinglinks/common';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { useDict } from '/@/components/Dict';
import type { VideoMediaServerPageQuery } from '/@/api/video/media/model/videoMediaServerModel';

const { t } = useI18n();
const { getDictLabel } = useDict();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('video.media.videoStreamProxy.appId'),
      dataIndex: 'appId',
      customRender: ({ text }) => {
        return getDictLabel(DictEnum.VIDEO_APPLICATION_SCENARIO, text);
      },
    },
    {
      title: t('video.media.videoStreamProxy.proxyType'),
      dataIndex: 'proxyType',
      customRender: ({ text }) => {
        return getDictLabel(DictEnum.VIDEO_MEDIA_STREAM_PROXY_TYPE, text);
      },
    },
    {
      title: t('video.media.videoStreamProxy.proxyName'),
      dataIndex: 'proxyName',
    },
    {
      title: t('video.media.videoStreamProxy.streamIdentification'),
      dataIndex: 'streamIdentification',
    },
    {
      title: t('video.media.videoStreamProxy.url'),
      dataIndex: 'url',
    },
    {
      title: t('video.media.videoStreamProxy.srcUrl'),
      dataIndex: 'srcUrl',
    },
    {
      title: t('video.media.videoStreamProxy.dstUrl'),
      dataIndex: 'dstUrl',
    },
    {
      title: t('video.media.videoStreamProxy.timeoutMs'),
      dataIndex: 'timeoutMs',
    },
    {
      title: t('video.media.videoStreamProxy.ffmpegCmdKey'),
      dataIndex: 'ffmpegCmdKey',
    },
    {
      title: t('video.media.videoStreamProxy.rtpType'),
      dataIndex: 'rtpType',
      customRender: ({ text }) => {
        return getDictLabel(DictEnum.VIDEO_MEDIA_STREAM_PROXY_RTP_TYPE, text);
      },
    },
    {
      title: t('video.media.videoStreamProxy.gbIdentification'),
      dataIndex: 'gbIdentification',
    },
    {
      title: t('video.media.videoStreamProxy.mediaIdentification'),
      dataIndex: 'mediaIdentification',
    },
    {
      title: t('video.media.videoStreamProxy.enableAudio'),
      dataIndex: 'enableAudio',
      customRender: ({ text }) => {
        return renderYesNoComponent(text, true);
      },
    },
    {
      title: t('video.media.videoStreamProxy.enableMp4'),
      dataIndex: 'enableMp4',
      customRender: ({ text }) => {
        return renderYesNoComponent(text, true);
      },
    },
    {
      title: t('video.media.videoStreamProxy.status'),
      dataIndex: 'status',
    },
    {
      title: t('video.media.videoStreamProxy.enableRemoveNoneReader'),
      dataIndex: 'enableRemoveNoneReader',
      customRender: ({ text }) => {
        return renderYesNoComponent(text, true);
      },
    },
    {
      title: t('video.media.videoStreamProxy.streamKey'),
      dataIndex: 'streamKey',
    },
    {
      title: t('video.media.videoStreamProxy.enableDisableNoneReader'),
      dataIndex: 'enableDisableNoneReader',
      customRender: ({ text }) => {
        return renderYesNoComponent(text, true);
      },
    },
    {
      title: t('video.media.videoStreamProxy.extendParams'),
      dataIndex: 'extendParams',
    },
    {
      title: t('video.media.videoStreamProxy.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('video.media.videoStreamProxy.createdOrgId'),
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
      label: t('video.media.videoStreamProxy.appId'),
      field: 'appId',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('video.media.videoStreamProxy.proxyType'),
      field: 'proxyType',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_MEDIA_STREAM_PROXY_TYPE),
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamProxy.proxyName'),
      field: 'proxyName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamProxy.streamIdentification'),
      field: 'streamIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamProxy.timeoutMs'),
      field: 'timeoutMs',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamProxy.rtpType'),
      field: 'rtpType',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_MEDIA_STREAM_PROXY_RTP_TYPE),
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamProxy.mediaIdentification'),
      field: 'mediaIdentification',
      component: 'ApiVideoSelectNodeCard',
      colProps: { span: 6 },
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
    },
    {
      label: t('video.media.videoStreamProxy.enableAudio'),
      field: 'enableAudio',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoComponentProps(),
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoStreamProxy.status'),
      field: 'status',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_MEDIA_STREAM_PROXY_STATUS),
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
      label: t('video.media.videoStreamProxy.proxyName'),
      field: 'proxyName',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.appId'),
      field: 'appId',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_APPLICATION_SCENARIO),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.proxyType'),
      field: 'proxyType',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_MEDIA_STREAM_PROXY_TYPE),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.streamIdentification'),
      field: 'streamIdentification',
      component: 'Input',
    },
    {
      label: t('video.media.videoStreamProxy.url'),
      field: 'url',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.srcUrl'),
      field: 'srcUrl',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.dstUrl'),
      field: 'dstUrl',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.timeoutMs'),
      field: 'timeoutMs',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.rtpType'),
      field: 'rtpType',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_MEDIA_STREAM_PROXY_RTP_TYPE),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.enableAudio'),
      field: 'enableAudio',
      component: 'RadioGroup',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.enableMp4'),
      field: 'enableMp4',
      component: 'RadioGroup',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.enableRemoveNoneReader'),
      field: 'enableRemoveNoneReader',
      component: 'RadioGroup',
      defaultValue: false,
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.media.videoStreamProxy.status'),
      field: 'status',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_MEDIA_STREAM_PROXY_STATUS),
      },
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
      label: t('video.media.videoStreamProxy.remark'),
      field: 'remark',
      component: 'InputTextArea',
      colProps: {
        span: 22,
      },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
