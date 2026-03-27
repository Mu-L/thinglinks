import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { yesNoComponentProps, dictComponentProps } from '/@/utils/thinglinks/common';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum, DictEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { useDict } from '/@/components/Dict';

const { t } = useI18n();
const { getDictLabel } = useDict();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('video.device.videoDeviceInfo.deviceIdentification'),
      dataIndex: 'deviceIdentification',
    },
    {
      title: t('video.device.videoDeviceInfo.deviceName'),
      dataIndex: 'deviceName',
    },
    {
      title: t('video.device.videoDeviceInfo.customName'),
      dataIndex: 'customName',
    },
    {
      title: t('video.device.videoDeviceInfo.mediaIdentification'),
      dataIndex: 'mediaIdentification',
    },
    {
      title: t('video.device.videoDeviceInfo.manufacturer'),
      dataIndex: 'manufacturer',
    },
    {
      title: t('video.device.videoDeviceInfo.model'),
      dataIndex: 'model',
    },
    {
      title: t('video.device.videoDeviceInfo.firmware'),
      dataIndex: 'firmware',
    },
    {
      title: t('video.device.videoDeviceInfo.transport'),
      dataIndex: 'transport',
      slots: { customRender: 'transport' },
    },
    {
      title: t('video.device.videoDeviceInfo.streamMode'),
      dataIndex: 'streamMode',
      slots: { customRender: 'streamMode' },
    },
    {
      title: t('video.device.videoDeviceInfo.onlineStatus'),
      dataIndex: 'onlineStatus',
    },
    {
      title: t('video.device.videoDeviceInfo.registerTime'),
      dataIndex: 'registerTime',
    },
    {
      title: t('video.device.videoDeviceInfo.keepaliveTime'),
      dataIndex: 'keepaliveTime',
    },
    {
      title: t('video.device.videoDeviceInfo.ip'),
      dataIndex: 'ip',
    },
    {
      title: t('video.device.videoDeviceInfo.port'),
      dataIndex: 'port',
    },
    {
      title: t('video.device.videoDeviceInfo.wanIp'),
      dataIndex: 'wanIp',
    },
    {
      title: t('video.device.videoDeviceInfo.lanIp'),
      dataIndex: 'lanIp',
    },
    {
      title: t('video.device.videoDeviceInfo.hostAddress'),
      dataIndex: 'hostAddress',
    },
    {
      title: t('video.device.videoDeviceInfo.expires'),
      dataIndex: 'expires',
    },
    {
      title: t('video.device.videoDeviceInfo.subscribeCycleForCatalog'),
      dataIndex: 'subscribeCycleForCatalog',
    },
    {
      title: t('video.device.videoDeviceInfo.subscribeCycleForMobilePosition'),
      dataIndex: 'subscribeCycleForMobilePosition',
    },
    {
      title: t('video.device.videoDeviceInfo.subscribeCycleForAlarm'),
      dataIndex: 'subscribeCycleForAlarm',
    },
    {
      title: t('video.device.videoDeviceInfo.mobilePositionSubmissionInterval'),
      dataIndex: 'mobilePositionSubmissionInterval',
    },
    {
      title: t('video.device.videoDeviceInfo.charset'),
      dataIndex: 'charset',
      slots: { customRender: 'charset' },
    },
    {
      title: t('video.device.videoDeviceInfo.ssrcCheck'),
      dataIndex: 'ssrcCheck',
    },
    {
      title: t('video.device.videoDeviceInfo.geoCoordSys'),
      dataIndex: 'geoCoordSys',
    },
    {
      title: t('video.device.videoDeviceInfo.sdpIp'),
      dataIndex: 'sdpIp',
    },
    {
      title: t('video.device.videoDeviceInfo.localIp'),
      dataIndex: 'localIp',
    },
    {
      title: t('video.device.videoDeviceInfo.password'),
      dataIndex: 'password',
    },
    {
      title: t('video.device.videoDeviceInfo.asMessageChannel'),
      dataIndex: 'asMessageChannel',
    },
    {
      title: t('video.device.videoDeviceInfo.keepaliveIntervalTime'),
      dataIndex: 'keepaliveIntervalTime',
    },
    {
      title: t('video.device.videoDeviceInfo.keepaliveTimeoutCount'),
      dataIndex: 'keepaliveTimeoutCount',
    },
    {
      title: t('video.device.videoDeviceInfo.positionCapability'),
      dataIndex: 'positionCapability',
    },
    {
      title: t('video.device.videoDeviceInfo.broadcastPushAfterAck'),
      dataIndex: 'broadcastPushAfterAck',
    },
    {
      title: t('video.device.videoDeviceInfo.ability'),
      dataIndex: 'ability',
    },
    {
      title: t('video.device.videoDeviceInfo.extendParams'),
      dataIndex: 'extendParams',
    },
    {
      title: t('video.device.videoDeviceInfo.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('video.device.videoDeviceInfo.createdOrgId'),
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
      label: t('video.device.videoDeviceInfo.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceInfo.deviceName'),
      field: 'deviceName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceInfo.manufacturer'),
      field: 'manufacturer',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceInfo.transport'),
      field: 'transport',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceInfo.streamMode'),
      field: 'streamMode',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceInfo.port'),
      field: 'port',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceInfo.charset'),
      field: 'charset',
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
      label: t('video.device.videoDeviceInfo.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.deviceName'),
      field: 'deviceName',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.customName'),
      field: 'customName',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.mediaIdentification'),
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
    },
    {
      label: t('video.device.videoDeviceInfo.manufacturer'),
      field: 'manufacturer',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.model'),
      field: 'model',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.firmware'),
      field: 'firmware',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.transport'),
      field: 'transport',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_DEVICE_TRANSPORT),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.streamMode'),
      field: 'streamMode',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_DEVICE_STREAM_MODE),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.onlineStatus'),
      field: 'onlineStatus',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.registerTime'),
      field: 'registerTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      required: true,
    },

    {
      label: t('video.device.videoDeviceInfo.keepaliveTime'),
      field: 'keepaliveTime',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.ip'),
      field: 'ip',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.port'),
      field: 'port',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.wanIp'),
      field: 'wanIp',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.lanIp'),
      field: 'lanIp',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.hostAddress'),
      field: 'hostAddress',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.expires'),
      field: 'expires',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.subscribeCycleForCatalog'),
      field: 'subscribeCycleForCatalog',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.subscribeCycleForMobilePosition'),
      field: 'subscribeCycleForMobilePosition',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.subscribeCycleForAlarm'),
      field: 'subscribeCycleForAlarm',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.mobilePositionSubmissionInterval'),
      field: 'mobilePositionSubmissionInterval',
      component: 'Input',
      defaultValue: '5',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.charset'),
      field: 'charset',
      component: 'ApiSelect',
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.VIDEO_DEVICE_CHARSET),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.ssrcCheck'),
      field: 'ssrcCheck',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.geoCoordSys'),
      field: 'geoCoordSys',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.sdpIp'),
      field: 'sdpIp',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.localIp'),
      field: 'localIp',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.password'),
      field: 'password',
      component: 'InputPassword',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.asMessageChannel'),
      field: 'asMessageChannel',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.keepaliveIntervalTime'),
      field: 'keepaliveIntervalTime',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.keepaliveTimeoutCount'),
      field: 'keepaliveTimeoutCount',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.positionCapability'),
      field: 'positionCapability',
      component: 'Input',
      defaultValue: '0',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.broadcastPushAfterAck'),
      field: 'broadcastPushAfterAck',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.ability'),
      field: 'ability',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceInfo.extendParams'),
      field: 'extendParams',
      component: 'Input',
    },
    {
      label: t('video.device.videoDeviceInfo.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
