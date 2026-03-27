import { Ref } from 'vue';
import { dateUtil } from '/@/utils/dateUtil';
import { dictComponentProps, yesNoComponentProps } from '/@/utils/thinglinks/common';
import { FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { DictEnum } from '/@/enums/commonEnum';

const { t } = useI18n();

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
      field: 'parentId',
      label: 'parentId',
      component: 'Input',
      show: false,
    },
    {
      field: 'parentName',
      label: t('iot.link.group.deviceGroup.editDeviceGroup.parentNode'),
      defaultValue: t('iot.link.group.deviceGroup.editDeviceGroup.rootNode'),
      component: 'Input',
      dynamicDisabled: true,
    },
    // {
    //   label: t('iot.link.group.deviceGroup.appId'),
    //   field: 'appId',
    //   component: 'Input',
    // },
    {
      label: t('iot.link.device.device.appId'),
      field: 'appId',
      component: 'ApiSelect',
      rules: [{ required: true }],
      componentProps: {
        allowClear: false,
        ...dictComponentProps(DictEnum.LINK_APPLICATION_SCENARIO),
      },
    },
    {
      label: t('iot.link.group.deviceGroup.groupName'),
      field: 'groupName',
      component: 'Input',
    },
    {
      label: t('iot.link.group.deviceGroup.type'),
      field: 'type',
      component: 'ApiSelect',
      defaultValue: 0,
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_GROUP_TYPE),
        stringToNumber: true,
      },
    },
    {
      label: t('iot.link.group.deviceGroup.state'),
      field: 'state',
      component: 'RadioGroup',
      defaultValue: true,
      componentProps: {
        ...yesNoComponentProps(),
      },
    },
    {
      label: t('iot.link.group.deviceGroup.description'),
      field: 'description',
      component: 'Input',
    },
    {
      label: t('iot.link.group.deviceGroup.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
    // {
    //   label: t('iot.link.group.deviceGroup.createdOrgId'),
    //   field: 'createdOrgId',
    //   component: 'InputNumber',
    // },
    {
      label: t('iot.link.group.deviceGroup.sortValue'),
      field: 'sortValue',
      component: 'InputNumber',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
