import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { yesNoComponentProps, renderYesNoComponent } from '/@/utils/thinglinks/common';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { useDict } from '/@/components/Dict';

const { t } = useI18n();
const { getDictLabel } = useDict();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('video.media.videoMediaServer.appId'),
      dataIndex: 'appId',
      customRender: ({ text }) => {
        return getDictLabel(DictEnum.VIDEO_APPLICATION_SCENARIO, text);
      },
    },
    {
      title: t('video.media.videoMediaServer.ip'),
      dataIndex: 'ip',
    },
    {
      title: t('video.media.videoMediaServer.hookIp'),
      dataIndex: 'hookIp',
    },
    {
      title: t('video.media.videoMediaServer.sdpIp'),
      dataIndex: 'sdpIp',
    },
    {
      title: t('video.media.videoMediaServer.streamIp'),
      dataIndex: 'streamIp',
    },
    {
      title: t('video.media.videoMediaServer.httpPort'),
      dataIndex: 'httpPort',
    },
    {
      title: t('video.media.videoMediaServer.httpSslPort'),
      dataIndex: 'httpSslPort',
    },
    {
      title: t('video.media.videoMediaServer.rtmpPort'),
      dataIndex: 'rtmpPort',
    },
    {
      title: t('video.media.videoMediaServer.rtmpSslPort'),
      dataIndex: 'rtmpSslPort',
    },
    {
      title: t('video.media.videoMediaServer.rtpProxyPort'),
      dataIndex: 'rtpProxyPort',
    },
    {
      title: t('video.media.videoMediaServer.rtspPort'),
      dataIndex: 'rtspPort',
    },
    {
      title: t('video.media.videoMediaServer.rtspSslPort'),
      dataIndex: 'rtspSslPort',
    },
    {
      title: t('video.media.videoMediaServer.flvPort'),
      dataIndex: 'flvPort',
    },
    {
      title: t('video.media.videoMediaServer.flvSslPort'),
      dataIndex: 'flvSslPort',
    },
    {
      title: t('video.media.videoMediaServer.wsFlvPort'),
      dataIndex: 'wsFlvPort',
    },
    {
      title: t('video.media.videoMediaServer.wsFlvSslPort'),
      dataIndex: 'wsFlvSslPort',
    },
    {
      title: t('video.media.videoMediaServer.autoConfig'),
      dataIndex: 'autoConfig',
      customRender: ({ text }) => renderYesNoComponent(text, true),
    },
    {
      title: t('video.media.videoMediaServer.secret'),
      dataIndex: 'secret',
    },
    {
      title: t('video.media.videoMediaServer.type'),
      dataIndex: 'type',
      customRender: ({ text }) => {
        return getDictLabel(DictEnum.VIDEO_MEDIA_SERVER_TYPE, text);
      },
    },
    {
      title: t('video.media.videoMediaServer.rtpEnable'),
      dataIndex: 'rtpEnable',
      customRender: ({ text }) => renderYesNoComponent(text, true),
    },
    {
      title: t('video.media.videoMediaServer.rtpPortRange'),
      dataIndex: 'rtpPortRange',
    },
    {
      title: t('video.media.videoMediaServer.sendRtpPortRange'),
      dataIndex: 'sendRtpPortRange',
    },
    {
      title: t('video.media.videoMediaServer.recordAssistPort'),
      dataIndex: 'recordAssistPort',
    },
    {
      title: t('video.media.videoMediaServer.defaultServer'),
      dataIndex: 'defaultServer',
      customRender: ({ text }) => renderYesNoComponent(text, true),
    },
    {
      title: t('video.media.videoMediaServer.hookAliveInterval'),
      dataIndex: 'hookAliveInterval',
    },
    {
      title: t('video.media.videoMediaServer.recordPath'),
      dataIndex: 'recordPath',
    },
    {
      title: t('video.media.videoMediaServer.recordDay'),
      dataIndex: 'recordDay',
    },
    {
      title: t('video.media.videoMediaServer.transcodeSuffix'),
      dataIndex: 'transcodeSuffix',
    },
    {
      title: t('video.media.videoMediaServer.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('video.media.videoMediaServer.createdOrgId'),
      dataIndex: 'createdOrgId',
    },
    {
      title: t('video.media.videoMediaServer.extendParams'),
      dataIndex: 'extendParams',
    },
    {
      title: t('video.media.videoMediaServer.name'),
      dataIndex: 'name',
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
      label: t('video.media.videoMediaServer.name'),
      field: 'name',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoMediaServer.appId'),
      field: 'appId',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('video.media.videoMediaServer.ip'),
      field: 'ip',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoMediaServer.hookIp'),
      field: 'hookIp',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoMediaServer.httpPort'),
      field: 'httpPort',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoMediaServer.secret'),
      field: 'secret',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.media.videoMediaServer.type'),
      field: 'type',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.VIDEO_MEDIA_SERVER_TYPE),
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
      label: t('video.media.videoMediaServer.name'),
      field: 'name',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoMediaServer.appId'),
      field: 'appId',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_APPLICATION_SCENARIO),
      },
      required: true,
    },
    {
      label: t('video.media.videoMediaServer.ip'),
      field: 'ip',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoMediaServer.httpPort'),
      field: 'httpPort',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoMediaServer.secret'),
      field: 'secret',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.media.videoMediaServer.type'),
      field: 'type',
      component: 'ApiSelect',
      defaultValue: 'zlm',
      componentProps: {
        ...dictComponentProps(DictEnum.VIDEO_MEDIA_SERVER_TYPE),
      },
      required: true,
    },
    {
      label: t('video.media.videoMediaServer.remark'),
      field: 'remark',
      component: 'InputTextArea',
      colProps: { span: 22 },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
