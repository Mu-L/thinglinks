import { reactive, Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt, RuleType } from '/@/api/thinglinks/common/formValidateService';
import { dictComponentProps } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';
import citiesGd from '/@/utils/thinglinks/citiesGd.json';
import { query } from '../../../../../api/iot/link/product/product';
import { deviceQuery } from '../../../../../api/iot/link/device/device';
import { useDict } from '/@/components/Dict';
import { array } from 'vue-types';
const { getDictList, getDictLabel } = useDict();
const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.device.device.connectStatus'),
      dataIndex: ['connectStatus'],
      customRender: ({ record }) => {
        return getDictLabel('LINK_DEVICE_CONNECT_STATUS', record.connectStatus, '');
      },
    },
    {
      title: t('iot.link.device.device.appId'),
      dataIndex: 'appId',
    },
    {
      title: t('iot.link.device.device.deviceIdentification'),
      dataIndex: 'deviceIdentification',
    },
    {
      title: t('iot.link.device.device.clientId'),
      dataIndex: 'clientId',
    },
    {
      title: t('iot.link.device.device.deviceStatus'),
      dataIndex: ['echoMap', 'deviceStatus'],
      customRender: ({ record }) => {
        return getDictLabel('LINK_DEVICE_STATUS', record.deviceStatus, '');
      },
    },
    {
      title: t('iot.link.device.device.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
    {
      title: t('iot.link.device.device.remark'),
      dataIndex: 'remark',
    },
    // {
    //   title: t('iot.link.device.device.userName'),
    //   dataIndex: 'userName',
    // },
    // {
    //   title: t('iot.link.device.device.password'),
    //   dataIndex: 'password',
    // },

    // {
    //   title: t('iot.link.device.device.authMode'),
    //   dataIndex: 'authMode',
    // },
    // {
    //   title: t('iot.link.device.device.encryptKey'),
    //   dataIndex: 'encryptKey',
    // },
    // {
    //   title: t('iot.link.device.device.encryptVector'),
    //   dataIndex: 'encryptVector',
    // },
    // {
    //   title: t('iot.link.device.device.signKey'),
    //   dataIndex: 'signKey',
    // },
    // {
    //   title: t('iot.link.device.device.encryptMethod'),
    //   dataIndex: 'encryptMethod',
    // },

    // {
    //   title: t('iot.link.device.device.deviceName'),
    //   dataIndex: 'deviceName',
    // },
    // {
    //   title: t('iot.link.device.device.connector'),
    //   dataIndex: 'connector',
    // },
    // {
    //   title: t('iot.link.device.device.description'),
    //   dataIndex: 'description',
    // },
    // {
    //   title: t('iot.link.device.device.deviceStatus'),
    //   dataIndex: 'deviceStatus',
    // },
    // {
    //   title: t('iot.link.device.device.connectStatus'),
    //   dataIndex: 'connectStatus',
    // },
    // {
    //   title: t('iot.link.device.device.deviceTags'),
    //   dataIndex: 'deviceTags',
    // },
    // {
    //   title: t('iot.link.device.device.productId'),
    //   dataIndex: 'productId',
    // },
    // {
    //   title: t('iot.link.device.device.swVersion'),
    //   dataIndex: 'swVersion',
    // },
    // {
    //   title: t('iot.link.device.device.fwVersion'),
    //   dataIndex: 'fwVersion',
    // },
    // {
    //   title: t('iot.link.device.device.deviceSdkVersion'),
    //   dataIndex: 'deviceSdkVersion',
    // },
    // {
    //   title: t('iot.link.device.device.gatewayId'),
    //   dataIndex: 'gatewayId',
    // },
    // {
    //   title: t('iot.link.device.device.nodeType'),
    //   dataIndex: 'nodeType',
    // },

    // {
    //   title: t('iot.link.device.device.createdOrgId'),
    //   dataIndex: 'createdOrgId',
    // },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.device.device.connectStatus'),
      field: 'connectStatus',
      colProps: { span: 6 },
      component: 'ApiSelect',
      show: true,
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_CONNECT_STATUS),
      },
    },
    {
      label: t('iot.link.device.device.deviceStatus'),
      field: 'deviceStatus',
      colProps: { span: 6 },
      component: 'ApiSelect',
      show: true,
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_STATUS),
      },
    },
    {
      label: t('iot.link.device.device.clientId'),
      field: 'clientId',
      component: 'Input',
      colProps: { span: 6 },
    },
    // {
    //   label: t('iot.link.device.device.userName'),
    //   field: 'userName',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.password'),
    //   field: 'password',
    //   component: 'InputPassword',
    //   colProps: { span: 6 },
    // },
    {
      label: t('iot.link.device.device.appId'),
      field: 'appId',
      component: 'Input',
      colProps: { span: 6 },
    },
    // {
    //   label: t('iot.link.device.device.authMode'),
    //   field: 'authMode',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.encryptKey'),
    //   field: 'encryptKey',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.encryptVector'),
    //   field: 'encryptVector',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.signKey'),
    //   field: 'signKey',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.encryptMethod'),
    //   field: 'encryptMethod',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    {
      label: t('iot.link.device.device.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    // {
    //   label: t('iot.link.device.device.deviceName'),
    //   field: 'deviceName',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.connector'),
    //   field: 'connector',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.description'),
    //   field: 'description',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },

    // {
    //   label: t('iot.link.device.device.deviceTags'),
    //   field: 'deviceTags',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.productId'),
    //   field: 'productId',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.swVersion'),
    //   field: 'swVersion',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.fwVersion'),
    //   field: 'fwVersion',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.deviceSdkVersion'),
    //   field: 'deviceSdkVersion',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    // {
    //   label: t('iot.link.device.device.gatewayId'),
    //   field: 'gatewayId',
    //   component: 'Input',
    //   colProps: { span: 6 },
    // },
    {
      label: t('iot.link.device.device.nodeType'),
      field: 'nodeType',
      colProps: { span: 6 },
      component: 'ApiSelect',
      show: true,
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_NODE_TYPE),
      },
    },
    // {
    //   label: t('iot.link.device.device.remark'),
    //   field: 'remark',
    //   component: 'InputTextArea',
    //   colProps: { span: 6 },
    // },
    {
      field: 'createTimeRange',
      label: t('iot.link.device.device.createdTime'),
      component: 'RangePicker',
      colProps: { span: 6 },
    },
  ];
};

// 编辑页字段
export const editFormSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      label: 'ID',
      field: 'id',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.device.device.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      show: false,
    },
    {
      field: 'area',
      label: t('iot.link.device.device.area'),
      colProps: {
        span: 22,
      },
      component: 'Cascader',
      rules: [{ required: true, type: 'array' }],
      componentProps: ({ formModel, formActionType }) => {
        return {
          options: citiesGd,
          onChange: (e) => {
            if (e) {
              const province = citiesGd.find((item) => {
                return item.value === e[0];
              });
              const city = province?.children.find((item) => {
                return item.value === e[1];
              });
              const district = city?.children.find((item) => {
                return item.value === e[2];
              });
              formModel.address =
                (province?.label || '') + (city?.label || '') + (district?.label || '');
              const { updateSchema } = formActionType;
              updateSchema({
                field: 'map',
                componentProps: {
                  address: formModel.address,
                  isInput: true, // 触发地图组件的输入事件，让地图组件重新渲染zu、
                },
              });
            } else {
              formModel.address = '';
            }
          },
        };
      },
    },
    {
      label: t('iot.link.device.device.address'),
      field: 'address',
      component: 'InputTextArea',
      show: true,
      rules: [{ required: true }],
      colProps: {
        span: 22,
      },
      componentProps: ({ formModel, formActionType }) => {
        return {
          disabled: false,
          // placeholder: `请输入${t('iot.link.device.device.address')}`,
          onChange: (e) => {
            console.log(e.target.value);
            const { updateSchema } = formActionType;
            updateSchema({
              field: 'map',
              componentProps: {
                address: e.target.value,
                isInput: true, // 触发地图组件的输入事件，让地图组件重新渲染zu、
              },
            });
          },
        };
      },
    },
    {
      label: t('iot.link.device.device.map'),
      colProps: {
        span: 22,
      },
      rules: [{ required: true, type: 'array' }],
      field: 'map',
      component: 'AMap',
      componentProps: ({ formModel, formActionType }) => {
        return {
          address: formModel.address,
          onUpdateMap: (e) => {
            console.log('onUpdateMap', e);
            formModel.map = e;
            const { validate } = formActionType;
            validate(['area']);
            validate(['map']);
          },
          onAddressClick: (e, addressComponent, formattedAddress) => {
            const { validate } = formActionType;
            console.log(e);
            const province = citiesGd.find((item) => {
              return item.label.replace(/\s/g, '') === addressComponent.province;
            });
            const city =
              province?.children.find((item) => {
                return item.label.replace(/\s/g, '') === addressComponent.city;
              }) || province?.children[0];
            const district =
              city?.children.find((item) => {
                return item.label.replace(/\s/g, '') === addressComponent.district;
              }) || city?.children[0];
            formModel.area = [province?.value, city?.value, district?.value];
            formModel.address = formattedAddress;
            formModel.map = e;
            validate(['map']);
            validate(['area']);
            validate(['address']);
          },
        };
      },
    },
    {
      label: t('iot.link.device.device.remark'),
      field: 'remark',
      component: 'InputTextArea',
      show: true,
      rules: [{ required: false }],
      colProps: {
        span: 22,
      },
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.remark')}`,
      },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [
    {
      field: 'name',
      type: RuleType.append,
      rules: [{ trigger: 'blur', required: true }],
    },
    {
      field: 'key',
      type: RuleType.append,
      rules: [{ trigger: 'blur', required: true }],
    },
  ];
};
