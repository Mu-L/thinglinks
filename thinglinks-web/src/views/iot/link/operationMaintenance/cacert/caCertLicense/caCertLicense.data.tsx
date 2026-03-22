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
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.certName'),
      dataIndex: 'certName',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.serialNumber'),
      dataIndex: 'serialNumber',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.commonName'),
      dataIndex: 'commonName',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.organization'),
      dataIndex: 'organization',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.organizationalUnit'),
      dataIndex: 'organizationalUnit',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.countryName'),
      dataIndex: 'countryName',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.provinceName'),
      dataIndex: 'provinceName',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.localityName'),
      dataIndex: 'localityName',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.email'),
      dataIndex: 'email',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.licenseBase64'),
      dataIndex: 'licenseBase64',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.businessLicenseFileid'),
      dataIndex: 'businessLicenseFileid',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.authorizationCertFileid'),
      dataIndex: 'authorizationCertFileid',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.algorithm'),
      dataIndex: 'algorithm',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.param1'),
      dataIndex: 'param1',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.param2'),
      dataIndex: 'param2',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.extendParams'),
      dataIndex: 'extendParams',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.notBefore'),
      dataIndex: 'notBefore',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.notAfter'),
      dataIndex: 'notAfter',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.revokeTime'),
      dataIndex: 'revokeTime',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.state'),
      dataIndex: 'state',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.remark'),
      dataIndex: 'remark',
    },
    {
      title: t('iot.link.operationMaintenance.cacert.caCertLicense.createdOrgId'),
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
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.certName'),
      field: 'certName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.serialNumber'),
      field: 'serialNumber',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.commonName'),
      field: 'commonName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.organization'),
      field: 'organization',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.organizationalUnit'),
      field: 'organizationalUnit',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.countryName'),
      field: 'countryName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.provinceName'),
      field: 'provinceName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.localityName'),
      field: 'localityName',
      component: 'Input',
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.notBefore'),
      field: 'notBefore',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.notAfter'),
      field: 'notAfter',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.revokeTime'),
      field: 'revokeTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
      colProps: { span: 6 },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.state'),
      field: 'state',
      component: 'ApiSelect',
      colProps: { span: 6 },
      componentProps: {
        ...dictComponentProps(DictEnum.LINK_CA_LICENSE_STATUS),
      },
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
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.certName'),
      field: 'certName',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.serialNumber'),
      field: 'serialNumber',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.commonName'),
      field: 'commonName',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.organization'),
      field: 'organization',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.organizationalUnit'),
      field: 'organizationalUnit',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.countryName'),
      field: 'countryName',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.provinceName'),
      field: 'provinceName',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.localityName'),
      field: 'localityName',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.email'),
      field: 'email',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.licenseBase64'),
      field: 'licenseBase64',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.businessLicenseFileid'),
      field: 'businessLicenseFileid',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.authorizationCertFileid'),
      field: 'authorizationCertFileid',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.algorithm'),
      field: 'algorithm',
      component: 'Input',
      defaultValue: '0',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.param1'),
      field: 'param1',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.param2'),
      field: 'param2',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.extendParams'),
      field: 'extendParams',
      component: 'Input',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.notBefore'),
      field: 'notBefore',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.notAfter'),
      field: 'notAfter',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.revokeTime'),
      field: 'revokeTime',
      component: 'DatePicker',
      componentProps: {
        format: 'YYYY-MM-DD HH:mm:ss',
        valueFormat: 'YYYY-MM-DD HH:mm:ss',
        showTime: { defaultValue: dateUtil('00:00:00', 'HH:mm:ss') },
      },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.state'),
      field: 'state',
      component: 'Input',
      defaultValue: '0',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.createdOrgId'),
      field: 'createdOrgId',
      component: 'Input',
    },
  ];
};
// 导入页字段
export const importSchema = (_type: Ref<ActionEnum>): FormSchema[] => {
  return [
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.certName'),
      field: 'certName',
      component: 'Input',
      required: true,
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.caCertPem'),
      field: 'caCertPem',
      component: 'InputTextArea',
      required: true,
      componentProps: {
        'auto-size': { minRows: 4, maxRows: 6 },
      },
    },
    {
      label: t('iot.link.operationMaintenance.cacert.caCertLicense.remark'),
      field: 'remark',
      component: 'InputTextArea',
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
