import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { yesNoComponentProps } from '/@/utils/thinglinks/common';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';

const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('video.device.videoDeviceChannel.deviceIdentification'),
      dataIndex: 'deviceIdentification',
    },
    {
      title: t('video.device.videoDeviceChannel.channelIdentification'),
      dataIndex: 'channelIdentification',
    },
    {
      title: t('video.device.videoDeviceChannel.streamIdentification'),
      dataIndex: 'streamIdentification',
    },
    {
      title: t('video.device.videoDeviceChannel.channelType'),
      dataIndex: 'channelType',
    },
    {
      title: t('video.device.videoDeviceChannel.streamType'),
      dataIndex: 'streamType',
    },
    {
      title: t('video.device.videoDeviceChannel.channelName'),
      dataIndex: 'channelName',
    },
    {
      title: t('video.device.videoDeviceChannel.manufacturer'),
      dataIndex: 'manufacturer',
    },
    {
      title: t('video.device.videoDeviceChannel.model'),
      dataIndex: 'model',
    },
    {
      title: t('video.device.videoDeviceChannel.block'),
      dataIndex: 'block',
    },
    {
      title: t('video.device.videoDeviceChannel.provinceCode'),
      dataIndex: 'provinceCode',
    },
    {
      title: t('video.device.videoDeviceChannel.cityCode'),
      dataIndex: 'cityCode',
    },
    {
      title: t('video.device.videoDeviceChannel.regionCode'),
      dataIndex: 'regionCode',
    },
    {
      title: t('video.device.videoDeviceChannel.fullAddress'),
      dataIndex: 'fullAddress',
    },
    {
      title: t('video.device.videoDeviceChannel.latitude'),
      dataIndex: 'latitude',
    },
    {
      title: t('video.device.videoDeviceChannel.longitude'),
      dataIndex: 'longitude',
    },
    {
      title: t('video.device.videoDeviceChannel.safetyWay'),
      dataIndex: 'safetyWay',
    },
    {
      title: t('video.device.videoDeviceChannel.registerWay'),
      dataIndex: 'registerWay',
    },
    {
      title: t('video.device.videoDeviceChannel.certNum'),
      dataIndex: 'certNum',
    },
    {
      title: t('video.device.videoDeviceChannel.certStatus'),
      dataIndex: 'certStatus',
    },
    {
      title: t('video.device.videoDeviceChannel.certInvalidReasonCode'),
      dataIndex: 'certInvalidReasonCode',
    },
    {
      title: t('video.device.videoDeviceChannel.certExpiryTime'),
      dataIndex: 'certExpiryTime',
    },
    {
      title: t('video.device.videoDeviceChannel.secrecy'),
      dataIndex: 'secrecy',
    },
    {
      title: t('video.device.videoDeviceChannel.ipAddress'),
      dataIndex: 'ipAddress',
    },
    {
      title: t('video.device.videoDeviceChannel.port'),
      dataIndex: 'port',
    },
    {
      title: t('video.device.videoDeviceChannel.password'),
      dataIndex: 'password',
    },
    {
      title: t('video.device.videoDeviceChannel.onlineStatus'),
      dataIndex: 'onlineStatus',
    },
    {
      title: t('video.device.videoDeviceChannel.hasAudio'),
      dataIndex: 'hasAudio',
    },
    {
      title: t('video.device.videoDeviceChannel.ptzType'),
      dataIndex: 'ptzType',
    },
    {
      title: t('video.device.videoDeviceChannel.positionType'),
      dataIndex: 'positionType',
    },
    {
      title: t('video.device.videoDeviceChannel.roomType'),
      dataIndex: 'roomType',
    },
    {
      title: t('video.device.videoDeviceChannel.useType'),
      dataIndex: 'useType',
    },
    {
      title: t('video.device.videoDeviceChannel.supplyLightType'),
      dataIndex: 'supplyLightType',
    },
    {
      title: t('video.device.videoDeviceChannel.directionType'),
      dataIndex: 'directionType',
    },
    {
      title: t('video.device.videoDeviceChannel.resolution'),
      dataIndex: 'resolution',
    },
    {
      title: t('video.device.videoDeviceChannel.downloadSpeed'),
      dataIndex: 'downloadSpeed',
    },
    {
      title: t('video.device.videoDeviceChannel.svcSpaceSupportMod'),
      dataIndex: 'svcSpaceSupportMod',
    },
    {
      title: t('video.device.videoDeviceChannel.svcTimeSupportMode'),
      dataIndex: 'svcTimeSupportMode',
    },
    {
      title: t('video.device.videoDeviceChannel.extendParams'),
      dataIndex: 'extendParams',
    },
    {
      title: t('video.device.videoDeviceChannel.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('video.device.videoDeviceChannel.createdOrgId'),
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
      label: t('video.device.videoDeviceChannel.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.channelIdentification'),
      field: 'channelIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.streamIdentification'),
      field: 'streamIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.channelType'),
      field: 'channelType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.streamType'),
      field: 'streamType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.channelName'),
      field: 'channelName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.manufacturer'),
      field: 'manufacturer',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.model'),
      field: 'model',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.block'),
      field: 'block',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.provinceCode'),
      field: 'provinceCode',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.cityCode'),
      field: 'cityCode',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.regionCode'),
      field: 'regionCode',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.fullAddress'),
      field: 'fullAddress',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.latitude'),
      field: 'latitude',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.longitude'),
      field: 'longitude',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.safetyWay'),
      field: 'safetyWay',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.registerWay'),
      field: 'registerWay',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.certNum'),
      field: 'certNum',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.certStatus'),
      field: 'certStatus',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.certInvalidReasonCode'),
      field: 'certInvalidReasonCode',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.certExpiryTime'),
      field: 'certExpiryTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.secrecy'),
      field: 'secrecy',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.ipAddress'),
      field: 'ipAddress',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.port'),
      field: 'port',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.password'),
      field: 'password',
      component: 'InputPassword',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.onlineStatus'),
      field: 'onlineStatus',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoComponentProps(),
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.hasAudio'),
      field: 'hasAudio',
      component: 'RadioGroup',
      componentProps: {
        ...yesNoComponentProps(),
      },
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.ptzType'),
      field: 'ptzType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.positionType'),
      field: 'positionType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.roomType'),
      field: 'roomType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.useType'),
      field: 'useType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.supplyLightType'),
      field: 'supplyLightType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.directionType'),
      field: 'directionType',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.resolution'),
      field: 'resolution',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.downloadSpeed'),
      field: 'downloadSpeed',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.svcSpaceSupportMod'),
      field: 'svcSpaceSupportMod',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.svcTimeSupportMode'),
      field: 'svcTimeSupportMode',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.extendParams'),
      field: 'extendParams',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('video.device.videoDeviceChannel.remark'),
      field: 'remark',
      component: 'InputTextArea',
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
      label: t('video.device.videoDeviceChannel.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.channelIdentification'),
      field: 'channelIdentification',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.streamIdentification'),
      field: 'streamIdentification',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.channelType'),
      field: 'channelType',
      component: 'Input',
      defaultValue: '0',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.streamType'),
      field: 'streamType',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.channelName'),
      field: 'channelName',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.manufacturer'),
      field: 'manufacturer',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.model'),
      field: 'model',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.block'),
      field: 'block',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.provinceCode'),
      field: 'provinceCode',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.cityCode'),
      field: 'cityCode',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.regionCode'),
      field: 'regionCode',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.fullAddress'),
      field: 'fullAddress',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.latitude'),
      field: 'latitude',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.longitude'),
      field: 'longitude',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.safetyWay'),
      field: 'safetyWay',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.registerWay'),
      field: 'registerWay',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.certNum'),
      field: 'certNum',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.certStatus'),
      field: 'certStatus',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.certInvalidReasonCode'),
      field: 'certInvalidReasonCode',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.certExpiryTime'),
      field: 'certExpiryTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.secrecy'),
      field: 'secrecy',
      component: 'Input',
      defaultValue: '0',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.ipAddress'),
      field: 'ipAddress',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.port'),
      field: 'port',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.password'),
      field: 'password',
      component: 'InputPassword',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.onlineStatus'),
      field: 'onlineStatus',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.hasAudio'),
      field: 'hasAudio',
      component: 'RadioGroup',
      defaultValue: '0',
      componentProps: {
        ...yesNoComponentProps(),
      },
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.ptzType'),
      field: 'ptzType',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.positionType'),
      field: 'positionType',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.roomType'),
      field: 'roomType',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.useType'),
      field: 'useType',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.supplyLightType'),
      field: 'supplyLightType',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.directionType'),
      field: 'directionType',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.resolution'),
      field: 'resolution',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.downloadSpeed'),
      field: 'downloadSpeed',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.svcSpaceSupportMod'),
      field: 'svcSpaceSupportMod',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.svcTimeSupportMode'),
      field: 'svcTimeSupportMode',
      component: 'Input',
      required: true,
    },
    {
      label: t('video.device.videoDeviceChannel.extendParams'),
      field: 'extendParams',
      component: 'Input',
    },
    {
      label: t('video.device.videoDeviceChannel.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
