import { Ref } from 'vue';
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
import { DescItem } from '/@/components/Description/index';
import { EyeOutlined, EyeInvisibleOutlined } from '@ant-design/icons-vue';
import CopyableText from '/@/components/CopyableText';
import {
  DeviceAuthMode,
  DeviceNodeType,
} from '/@/enums/link/device';

const { getDictLabel } = useDict();
const { t } = useI18n();
// 列表页字段
export const columns = (): BasicColumn[] => {
  return [
    {
      title: t('iot.link.device.device.appId'),
      dataIndex: 'appId',
    },
    {
      title: t('iot.link.device.device.deviceIdentification'),
      dataIndex: 'deviceIdentification',
    },
    {
      title: t('iot.link.device.device.connectStatus'),
      dataIndex: ['connectStatus'],
      customRender: ({ record }) => {
        return getDictLabel('LINK_DEVICE_CONNECT_STATUS', record.connectStatus, '');
      },
    },
    {
      title: t('iot.link.device.device.clientId'),
      dataIndex: 'clientId',
    },
    {
      title: t('iot.link.device.device.deviceStatus'),
      dataIndex: ['echoMap', 'deviceStatus'],
      customRender: ({ record }) => {
        ``;
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
      label: t('iot.link.device.device.deviceName'),
      field: 'deviceName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.device.device.deviceIdentification'),
      field: 'deviceIdentification',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.device.device.clientId'),
      field: 'clientId',
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
      label: t('iot.link.device.device.nodeType'),
      field: 'nodeType',
      colProps: { span: 6 },
      component: 'ApiSelect',
      show: true,
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_NODE_TYPE),
      },
    },
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
      label: '',
      labelWidth: 0,
      colProps: {
        span: 22,
      },
      field: 'd',
      component: 'FormTitle',
      componentProps: {
        title: t('iot.link.engine.linkage.basicInfo'),
      },
    },
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
      label: t('iot.link.device.device.deviceName'),
      field: 'deviceName',
      component: 'Input',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.deviceName')}`,
      },
    },
    {
      label: t('iot.link.device.device.nodeType'),
      field: 'nodeType',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_NODE_TYPE),
      },
    },
    // {
    //   label: t('iot.link.product.product.protocolType'),
    //   field: 'protocolType',
    //   component: 'ApiSelect',
    //   show: true,
    //   rules: [{ required: true }],
    //   componentProps: {
    //     ...dictComponentProps(DictEnum.LINK_PRODUCT_PROTOCOL_TYPE),
    //   },
    // },
    {
      label: t('iot.link.device.device.authMode'),
      field: 'authMode',
      component: 'ApiSelect',
      defaultValue: '0',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        allowClear: false,
        // placeholder: `请选择${t('iot.link.device.device.authMode')}`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_AUTH_MODE),
      },
      helpMessage: [
        t('iot.link.device.device.authModeMsg[0]'),
        t('iot.link.device.device.authModeMsg[1]'),
        t('iot.link.device.device.authModeMsg[2]'),
        t('iot.link.device.device.authModeMsg[3]'),
      ],
    },
    {
      label: t('iot.link.device.device.userName'),
      field: 'userName',
      component: 'Input',
      // show: ({ values }) => {
      //   return values.authMode == '0';
      // },
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.userName')}`,
      },
    },
    {
      label: t('iot.link.device.device.password'),
      field: 'password',
      component: 'StrengthMeter',
      // show: ({ values }) => {
      //   return values.authMode == '0';
      // },
      rules: [
        { required: true, message: '请输入密码' },
        {
          pattern: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&.])[A-Za-z\d@$!%*?&.]{6,20}$/,
          message: '密码必须包含大小写字母、数字和特殊字符，且长度在 6 到 20 个字符之间',
        },
      ],
      componentProps: {
        disabled: false,
        autocomplete: 'off',
        // placeholder: `请输入${t('iot.link.device.device.password')}`,
      },
    },
    {
      label: t('iot.link.device.device.selectCertSerialNumber'),
      field: 'certSerialNumber',
      component: 'ApiCacertSelectNodeCard',
      show: ({ values }) => {
        return values.authMode == DeviceAuthMode.SSL_TLS_CERTIFICATE;
      },
      rules: [{ required: true }],
      componentProps: ({ formModel, formActionType }) => {
        return {
          type: 'certSerialNumber',
          onSelect: (value) => {
            const { validateFields } = formActionType;
            formModel.certSerialNumber = value?.serialNumber;
            validateFields(['serialNumber']);
          },
          value: formModel.serialNumber,
        };
      },
    },
    {
      label: t('iot.link.product.product.productName'),
      field: 'productName',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.product.product.productIdentification'),
      field: 'productIdentification',
      component: 'ApiSelectTable',
      rules: [{ required: true }],
      show: ({ values }) => {
        return values.nodeType;
      },
      componentProps: ({ formModel, formActionType, schema }) => {
        return {
          type: formModel.productName,
          typeName: 'productName',
          api: query,
          params: {},
          columns: [
            {
              title: 'ID',
              dataIndex: 'id',
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
              title: t('iot.link.product.product.productIdentification'),
              dataIndex: 'productIdentification',
            },
            {
              title: t('iot.link.product.product.productName'),
              dataIndex: 'productName',
            },
            {
              title: t('iot.link.product.product.model'),
              dataIndex: 'model',
            },
          ],
          title: '选择产品',
          subTitle: 'product',
          onSelect: (e) => {
            const { validate } = formActionType;
            formModel.productName = e.productName;
            formModel.productIdentification = e.productIdentification;
            validate(['productIdentification']);
          },
        };
      },
    },
    {
      label: t('iot.link.device.device.deviceStatus'),
      field: 'deviceStatus',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_DEVICE_STATUS),
      },
    },
    {
      label: t('iot.link.device.device.connector'),
      field: 'connector',
      component: 'ApiSelect',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.connector')}`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_CONNECTOR),
      },
    },

    {
      label: '',
      labelWidth: 0,
      colProps: {
        span: 23,
      },
      field: 'dd',
      component: 'FormTitle',
      componentProps: {
        title: t('iot.link.engine.linkage.geoInfo'),
      },
    },
    // {
    //   label: '地理信息',
    //   colProps: {
    //     span: 23,
    //   },
    //   field: 'dd',
    //   component: 'Divider',
    //   componentProps: {
    //     type: 'vertical',
    //     style: {
    //      height: '20px',
    //      lineHeight: '20px',
    //      borderLeft: '3px solid #1A66FF',
    //      fontSize: '16px',
    //      color: '#2e3033',
    //      fontWeight: 600,
    //      margin: 0,
    //     }
    //   },
    // },
    {
      field: 'area',
      label: t('iot.link.device.device.area'),
      colProps: {
        span: 22,
      },
      rules: [{ required: true }],
      component: 'Cascader',
      componentProps: ({ formModel, formActionType }) => {
        return {
          options: citiesGd,
          disabled: true,
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
      dynamicRules: ({ values }) => {
        return [
          {
            required: values.map ? true : false,
          },
        ];
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
          disabled: true,
          // placeholder: `请输入${t('iot.link.device.device.address')}`,
          onBlur: (e) => {
            const { updateSchema } = formActionType;
            updateSchema({
              field: 'map',
              componentProps: {
                address: e.target.value,
                isInput: true, // 触发地图组件的输入事件，让地图组件重新渲染zu
              },
            });
          },
        };
      },
      dynamicRules: ({ values }) => {
        return [
          {
            required: values.map ? true : false,
          },
        ];
      },
    },
    {
      label: t('iot.link.device.device.map'),
      colProps: {
        span: 22,
      },
      field: 'map',
      rules: [{ required: true }],
      component: 'AMap',
      componentProps: ({ formModel, formActionType }) => {
        // console.log(formModel, '地图渲染');
        return {
          address: formModel.address,
          latitude: formModel.latitude,
          longitude: formModel.longitude,
          onChange: (e) => {
            console.log(111111, e);
          },
          onUpdateMap: (e) => {
            console.log(e);
            formModel.map = e;
          },
          onAddressClick: (e, addressComponent, formattedAddress) => {
            const { updateSchema } = formActionType;
            console.log(e);
            console.log(addressComponent);
            console.log(formattedAddress);
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
          },
        };
      },
      dynamicRules: ({ values }) => {
        return [
          {
            required: values.map ? true : false,
          },
        ];
      },
    },
    // {
    //   label: '其他信息',
    //   colProps: {
    //     span: 23,
    //   },
    //   field: 'dd',
    //   component: 'Divider',
    //   componentProps: {
    //     type: 'vertical',
    //     style: {
    //      height: '20px',
    //      lineHeight: '20px',
    //      borderLeft: '3px solid #1A66FF',
    //      fontSize: '16px',
    //      color: '#2e3033',
    //      fontWeight: 600,
    //      margin: 0,
    //     }
    //   },
    // },
    {
      label: '',
      labelWidth: 0,
      colProps: {
        span: 23,
      },
      field: 'dd',
      component: 'FormTitle',
      componentProps: {
        title: t('iot.link.device.device.otherInfo'),
      },
    },
    {
      label: t('iot.link.device.device.encryptMethod'),
      field: 'encryptMethod',
      component: 'ApiSelect',
      defaultValue: '0',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: false,
        // placeholder: `请选择${t('iot.link.device.device.encryptMethod')}`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_ENCRYPT_METHOD),
      },
    },
    {
      label: t('iot.link.device.device.signKey'),
      field: 'signKey',
      component: 'Input',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.signKey')}`,
      },
      colSlot: 'signKey',
      // renderComponentContent: () => {
      //   const [, { setFieldsValue }] = useForm(); // 解构出 setFieldsValue 方法
      //   return {
      //     addonAfter: () => (
      //       <ReloadOutlined
      //         onClick={() => {
      //           // 弹窗提示是否随机生成
      //           Modal.confirm({
      //             title: '提示',
      //             content: '是否随机生成一个 16 位字符？',
      //             onOk() {
      //               console.log(setFieldsValue);
      //               // 生成随机字符串
      //               const randomString = generateRandomString();
      //               // 更新表单的值
      //               setFieldsValue({ signKey: randomString });
      //             },
      //             onCancel() {},
      //           });
      //         }}
      //       />
      //     ),
      //   };
      // },
    },

    {
      label: t('iot.link.device.device.encryptKey'),
      field: 'encryptKey',
      component: 'Input',
      show: ({ values }) => {
        return ['1', '2'].indexOf(values.encryptMethod) != -1;
      },
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.encryptKey')}`,
      },
      colSlot: 'encryptKey',
    },
    {
      label: t('iot.link.device.device.encryptVector'),
      field: 'encryptVector',
      component: 'Input',
      show: ({ values }) => {
        return ['1', '2'].indexOf(values.encryptMethod) != -1;
      },
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.encryptVector')}`,
      },
      colSlot: 'encryptVector',
    },
    {
      label: 'gatewayName',
      field: 'gatewayName',
      component: 'Input',
      show: false,
    },
    {
      label: t('iot.link.device.device.gateway'),
      field: 'gatewayId',
      component: 'ApiSelectTable',
      show: ({ values }) => {
        return values.nodeType == DeviceNodeType.SUB_DEVICE;
      },
      rules: [{ required: true }],
      componentProps: ({ formModel, formActionType }) => {
        return {
          type: formModel.gatewayName,
          typeName: 'deviceName',
          api: deviceQuery,
          params: {
            nodeType: 1,
          },
          columns: [
            {
              title: 'ID',
              dataIndex: 'id',
              width: 180,
            },
            {
              title: t('iot.link.device.device.deviceIdentification'),
              dataIndex: 'deviceIdentification',
              width: 180,
            },
            {
              title: t('iot.link.device.device.deviceName'),
              dataIndex: 'deviceName',
            },
            {
              title: t('iot.link.device.device.nodeType'),
              dataIndex: 'model',
              width: 180,
            },
          ],
          title: '选择网关设备',
          subTitle: 'device',
          onSelect: (e) => {
            const { validate } = formActionType;
            formModel.gatewayName = e.deviceName;
            formModel.gatewayId = e.deviceIdentification;
            validate(['gatewayId']);
          },
        };
      },
    },

    {
      label: t('iot.link.device.device.deviceTags'),
      field: 'deviceTags',
      component: 'Input',
      show: true,
      rules: [{ required: false }],
      componentProps: {
        mode: 'tags',
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.deviceTags')}`,
      },
    },
    {
      label: t('iot.link.device.device.deviceSdkVersion'),
      field: 'deviceSdkVersion',
      component: 'Input',
      defaultValue: 'v1',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.deviceSdkVersion')}`,
      },
    },
    {
      label: t('iot.link.device.device.fwVersion'),
      field: 'fwVersion',
      component: 'Input',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.fwVersion')}`,
      },
    },
    {
      label: t('iot.link.device.device.swVersion'),
      field: 'swVersion',
      component: 'Input',
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.swVersion')}`,
      },
    },
    {
      label: t('iot.link.device.device.description'),
      field: 'description',
      component: 'Input',
      show: true,
      rules: [{ required: false }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.description')}`,
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
        // placeholder: `${t('common.inputText')}` + `${t('iot.link.device.device.remark')}`,
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

// 详情页字段
export function detailDeviceSchema(): DescItem[] {
  const labelStyle = {
    width: '140px',
    'font-weight': '600',
    'font-size': '14px',
  };
  return [
    {
      field: 'certSerialNumber',
      label: t('iot.link.device.device.certSerialNumber'),
      labelStyle,
    },
    {
      field: 'deviceIdentification',
      label: t('iot.link.device.device.deviceIdentification'),
      labelStyle,
      render: (curVal) => {
        return <CopyableText text={curVal || ''} />;
      },
    },
    {
      field: 'clientId',
      label: t('iot.link.device.device.clientId'),
      labelStyle,
      render: (curVal) => {
        return <CopyableText text={curVal || ''} />;
      },
    },
    {
      field: 'nodeType',
      label: t('iot.link.device.device.nodeType'),
      labelStyle,
      render: (curVal) => {
        return getDictLabel(DictEnum.LINK_DEVICE_NODE_TYPE, curVal, '');
      },
    },
    {
      field: 'authMode',
      label: t('iot.link.device.device.authMode'),
      labelStyle,
      render: (curVal) => {
        return getDictLabel(DictEnum.LINK_DEVICE_AUTH_MODE, curVal, '');
      },
    },
    {
      field: 'productResultVO.protocolType',
      label: t('iot.link.product.product.protocolType'),
      labelStyle,
    },
    {
      field: 'productResultVO.productIdentification',
      label: t('iot.link.product.product.productIdentification'),
      labelStyle,
    },
    {
      field: 'productResultVO.productName',
      label: t('iot.link.product.product.productName'),
      labelStyle,
    },
    {
      field: 'productResultVO.dataFormat',
      label: t('iot.link.product.product.dataFormat'),
      labelStyle,
    },
    {
      field: 'productResultVO.manufacturerId',
      label: t('iot.link.product.product.manufacturerId'),
      labelStyle,
    },
    {
      field: 'productResultVO.manufacturerName',
      label: t('iot.link.product.product.manufacturerName'),
      labelStyle,
    },
    {
      field: 'productResultVO.model',
      label: t('iot.link.product.product.model'),
      labelStyle,
    },
    {
      field: 'productResultVO.productType',
      label: t('iot.link.product.product.productType'),
      labelStyle,
      render: (curVal) => {
        return getDictLabel(DictEnum.LINK_PRODUCT_TYPE, curVal, '');
      },
    },
    {
      field: 'productResultVO.productVersion',
      label: t('iot.link.product.product.productVersion'),
      labelStyle,
    },
    {
      field: 'swVersion',
      label: t('iot.link.device.device.swVersion'),
      labelStyle,
    },
    {
      field: 'fwVersion',
      label: t('iot.link.device.device.fwVersion'),
      labelStyle,
    },
    {
      field: 'deviceSdkVersion',
      label: t('iot.link.device.device.deviceSdkVersion'),
      labelStyle,
    },
    {
      field: 'encryptMethod',
      label: t('iot.link.device.device.encryptMethod'),
      labelStyle,
      render: (curVal) => {
        return getDictLabel(DictEnum.LINK_DEVICE_ENCRYPT_METHOD, curVal, '');
      },
    },
    {
      field: 'encryptKey',
      label: t('iot.link.device.device.encryptKey'),
      labelStyle,
      desensitize: true,
    },
    {
      field: 'encryptVector',
      label: t('iot.link.device.device.encryptVector'),
      labelStyle,
      desensitize: true,
    },
    {
      field: 'signKey',
      label: t('iot.link.device.device.signKey'),
      labelStyle,
      desensitize: true,
    },
    {
      field: 'userName',
      label: t('iot.link.device.device.userName'),
      labelStyle,
      render: (curVal) => {
        return <CopyableText text={curVal || ''} />;
      },
    },
    {
      field: 'password',
      label: t('iot.link.device.device.password'),
      labelStyle,
      desensitize: true,
    },
    {
      field: 'lastHeartbeatTime',
      label: t('iot.link.device.device.lastHeartbeatTime'),
      labelStyle,
    },
    {
      field: 'createdBy',
      label: t('iot.link.device.device.createdBy'),
      labelStyle,
    },
    {
      field: 'createdTime',
      label: t('iot.link.device.device.createdTime'),
      labelStyle,
    },
    {
      field: 'updatedBy',
      label: t('iot.link.device.device.updatedBy'),
      labelStyle,
    },
    {
      field: 'updatedTime',
      label: t('iot.link.device.device.updatedTime'),
      labelStyle,
    },
  ];
}
