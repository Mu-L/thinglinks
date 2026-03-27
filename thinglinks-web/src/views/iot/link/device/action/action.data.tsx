import { Ref } from 'vue';
import { BasicColumn, FormSchema } from '/@/components/Table';
import { useI18n } from '/@/hooks/web/useI18n';
import { ActionEnum } from '/@/enums/commonEnum';
import { FormSchemaExt } from '/@/api/thinglinks/common/formValidateService';
import { Tag, Tooltip } from 'ant-design-vue';
import { dictComponentProps, handleCopyTextV2 } from '/@/utils/thinglinks/common';
import { DictEnum } from '/@/enums/commonEnum';
import { useDict } from '/@/components/Dict';
import { CopyOutlined, EyeOutlined } from '@ant-design/icons-vue';

const { getDictLabel } = useDict();

const { t } = useI18n();

// 列表页字段
export const columns = ({ openRemarkModal }: { openRemarkModal: Fn }): BasicColumn[] => {
  return [
    // {
    //   title: t('iot.link.device.device.id'),
    //   dataIndex: 'id',
    // },
    // {
    //   title: t('iot.link.device.device.deviceIdentification'),
    //   dataIndex: 'deviceIdentification',
    // },
    {
      title: t('iot.link.device.device.actionType'),
      dataIndex: 'actionType',
    },
    {
      title: t('iot.link.device.device.message'),
      dataIndex: 'message',
      ellipsis: true,
      showSorterTooltip: true,
      customRender: ({ record }) => {
        return (
          <div>
            {record.message ? (
              <span>
                <CopyOutlined
                  onClick={() => handleCopyTextV2(record.message || '')}
                  style={{ marginRight: '10px' }}
                />
                <EyeOutlined
                  onClick={() =>
                    openRemarkModal(record.message, t('iot.link.device.device.content'))
                  }
                  style={{ marginRight: '10px' }}
                />
              </span>
            ) : null}
            <Tooltip placement="topLeft" title={record.message}>
              <span>{record.message}</span>
            </Tooltip>
          </div>
        );
      },
    },
    {
      title: t('iot.link.device.device.remark'),
      dataIndex: 'remark',
      ellipsis: true,
      showSorterTooltip: true,
      customRender: ({ record }) => {
        // return record.remark;
        return (
          <div>
            <Tooltip placement="topLeft" title={record.remark}>
              <span>{record.remark}</span>
            </Tooltip>
          </div>
        );
      },
    },
    {
      title: t('iot.link.device.device.createdTime'),
      dataIndex: 'createdTime',
      sorter: true,
      width: 180,
    },
    {
      title: t('iot.link.device.device.status'),
      dataIndex: 'status',
      customRender: ({ record }) => {
        // return getDictLabel('LINK_PRODUCT_STATUS', record.status.toString(), '');
        if (record?.status == 0) {
          return (
            <Tag color="green" style={{ marginRight: '0px' }}>
              {getDictLabel('LINK_DEVICE_ACTION_STATUS', record.status, '')}
            </Tag>
          );
        } else if (record?.status == 1) {
          return (
            <Tag color="red" style={{ marginRight: '0px' }}>
              {getDictLabel('LINK_DEVICE_ACTION_STATUS', record.status, '')}
            </Tag>
          );
        }
      },
    },
  ];
};

export const searchFormSchema = (): FormSchema[] => {
  return [
    {
      label: t('iot.link.device.device.actionType'),
      field: 'actionType',
      component: 'ApiSelect',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        // placeholder: `请选择${t('iot.link.device.device.actionType')}`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_ACTION_TYPE),
      },
      colProps: { span: 5 },
    },
    {
      label: t('iot.link.device.device.status'),
      field: 'status',
      component: 'ApiSelect',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: true,
        // placeholder: `请选择${t('iot.link.device.device.actionType')}`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_ACTION_STATUS),
      },
      colProps: { span: 5 },
    },
    {
      field: 'createTimeRange',
      label: t('iot.link.device.device.createdTime'),
      component: 'RangePicker',
      colProps: { span: 8 },
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
      show: true,
      rules: [{ required: true }],
      componentProps: {
        disabled: true,
        // placeholder: `请输入${t('iot.link.device.device.deviceIdentification')}`,
      },
    },
    {
      label: t('iot.link.device.device.actionType'),
      field: 'ApiSelect',
      component: 'Select',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: false,
        // placeholder: `请选择${t('iot.link.device.device.actionType')}`,
        ...dictComponentProps(DictEnum.LINK_DEVICE_ACTION_TYPE),
      },
    },
    {
      label: t('iot.link.device.device.message'),
      field: 'message',
      component: 'InputTextArea',
      show: true,
      rules: [{ required: false }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.message')}`,
      },
    },
    {
      label: t('iot.link.device.device.status'),
      field: 'status',
      component: 'ApiSelect',
      defaultValue: '0',
      show: true,
      componentProps: {
        disabled: false,
        allowClear: false,
        // placeholder: `请选择${t('iot.link.device.device.status')}`,
        ...dictComponentProps(DictEnum.LINK_PRODUCT_STATUS),
      },
    },
    {
      label: t('iot.link.device.device.remark'),
      field: 'remark',
      component: 'InputTextArea',
      show: true,
      rules: [{ required: false }],
      componentProps: {
        disabled: false,
        // placeholder: `请输入${t('iot.link.device.device.remark')}`,
      },
    },
  ];
};

// 前端自定义表单验证规则
export const customFormSchemaRules = (_): Partial<FormSchemaExt>[] => {
  return [];
};
